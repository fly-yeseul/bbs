package dev.fly_yeseul.bbs1.services;

import dev.fly_yeseul.bbs1.daos.BbsDao;
import dev.fly_yeseul.bbs1.entities.BbsBoardEntity;
import dev.fly_yeseul.bbs1.entities.BbsCommentEntity;
import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.enums.BbsArticleReadResult;
import dev.fly_yeseul.bbs1.enums.BbsBoardListResult;
import dev.fly_yeseul.bbs1.enums.BbsCommentWriteResult;
import dev.fly_yeseul.bbs1.vos.BbsArticleReadVo;
import dev.fly_yeseul.bbs1.vos.BbsBoardListVo;
import dev.fly_yeseul.bbs1.vos.BbsCommentWriteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service(value = "dev.fly_yeseul.bbs1.services.BbsService")
public class BbsService {
    public static final int[] ARTICLE_COUNT_PER_PAGE = new int[] {10, 20, 50};
    // 페이지 수 배열 선언 (한 페이지에 10개, 20개, 50개
    private final BbsDao bbsDao;
    // 지역변수 선언


    @Autowired
    public BbsService(BbsDao bbsDao) {
        this.bbsDao = bbsDao;
    }

    // 페이지당 글목록 수 설정
    public void listBoard(BbsBoardListVo bbsBoardListVo, UserEntity userEntity) throws SQLException {
        boolean isArticleCountPerPageValid = false;
        for (int count : ARTICLE_COUNT_PER_PAGE) {
            // 배열 10, 20, 30 동안
            if(bbsBoardListVo.getArticleCountPerPage() == count){
                // 사용자가 요청한 페이지랑 이거랑 맞으면
                isArticleCountPerPageValid = true;
                // 맞다고 하고
                break;
                // 빠져나온다
            }

        }
        if(!isArticleCountPerPageValid){
//            // 값이 거짓이면 (기본값)
            bbsBoardListVo.setArticleCountPerPage(ARTICLE_COUNT_PER_PAGE[0]);
//            // 10페이지로 결정
        }

//        if(bbsBoardListVo.getRequestPage() < 1){
//            // 요청한 그게 1보다 작으면
//            bbsBoardListVo.setArticleCountPerPage(ARTICLE_COUNT_PER_PAGE[0]);
//            // 1페이지
//        }
        if (bbsBoardListVo.getRequestPage() < 1) {
            bbsBoardListVo.setRequestPage(1);
        }
        // 요청한 페이지가 1보다 작으면 1페이지 (이전페이지)



        int userLevel = 10; // 비회원 레벨
        if (userEntity != null) {
            userLevel = userEntity.getLevel();
        }
        // user 가 null 이 아니면 userlevel 은 DB 에서 가져온다


        this.bbsDao.selectBoard(bbsBoardListVo);
        // 게시판 선택

        if (bbsBoardListVo.getIndex() == 0) {
            bbsBoardListVo.setResult(BbsBoardListResult.NOT_FOUND);
            // 게시판 인덱스 넘버가 0이면 (존재하지않음) => not found 출력
        } else if (bbsBoardListVo.getListLevel() < userLevel) {
            // 레벨의 절댓값이 높은게 낮은 권한임. (1 최고관리자, 9 일반유저)
            // 유저레벨이 게시판 레벨보다 높으면(권한이 없는거임)
            bbsBoardListVo.setResult(BbsBoardListResult.NOT_ALLOWED);
            // user level 이 리스트 열람 레벨보다 낮으면 not allowed 출력
        } else {
            final int pageRangeFactor = 4;
            // 숫자 개수

            // 페이지당 글 갯수
            int articleCountPerPage = bbsBoardListVo.getArticleCountPerPage();

            // 총 글 갯수
            int totalArticleCount = this.bbsDao.selectArticleCount(bbsBoardListVo);

            // 요청 페이지
            int requestPage = bbsBoardListVo.getRequestPage();

            int minPage = 1;
            // 최초 페이지(젤 첫페이지)

            int maxPage = Math.max(minPage, totalArticleCount / articleCountPerPage + (totalArticleCount % articleCountPerPage == 0 ? 0 : 1));
            // 최대 페이지(젤 마지막 페이지) > [[[ minPage VS 총 페이지 ]]] 중에서 큰거! (총 게시글 수/페이지당게시글수)의 나머지가 0이면 0, 아니면 1

            if (bbsBoardListVo.getRequestPage() > maxPage) {
                bbsBoardListVo.setRequestPage(maxPage);
            }       // 요청 페이지가 최대 페이지보다 크면 최대 페이지 보여준당

            int startPage = Math.max(minPage, requestPage - pageRangeFactor);
            int endPage = Math.min(maxPage, requestPage + pageRangeFactor);
            bbsBoardListVo.setArticleCountPerPage(articleCountPerPage);
            bbsBoardListVo.setRequestPage(requestPage);
            bbsBoardListVo.setMinPage(minPage);
            bbsBoardListVo.setMaxPage(maxPage);
            bbsBoardListVo.setStartPage(startPage);
            bbsBoardListVo.setEndPage(endPage);

            this.bbsDao.selectArticle(bbsBoardListVo);
            bbsBoardListVo.setResult(BbsBoardListResult.SUCCESS);
        }
    }

    // 게시글 읽기
    public void readArticle(BbsArticleReadVo bbsArticleReadVo, UserEntity userEntity) throws SQLException {
        this.bbsDao.selectArticle(bbsArticleReadVo);
        // NOT_FOUND 의 경우의 수도 고려해야 한다.
        if(bbsArticleReadVo.getBoardId() == null){
            bbsArticleReadVo.setResult(BbsArticleReadResult.NOT_FOUND);
            return;
        }
        // 이 자리에 왔다는건 위에가 null 이 아니라는 뜻이니까 확인 가능하다.
        BbsBoardEntity bbsBoardEntity = new BbsBoardEntity();
        bbsBoardEntity.setId(bbsArticleReadVo.getBoardId());
        this.bbsDao.selectBoard(bbsBoardEntity);

        int userLevel = userEntity == null ? 10 : userEntity.getLevel();
        // userEntity 가 null 이면 10을 부여, 아니면 userEntity 에서 level 값을 받아온다.

        if(bbsBoardEntity.getReadLevel() < userLevel) {
            // userLevel 이 게시판 레벨보다 크면
            bbsArticleReadVo.setResult(BbsArticleReadResult.NOT_ALLOWED);
            // 접근금지시킴
            return;
        }
        bbsArticleReadVo.setResult(BbsArticleReadResult.SUCCESS);
    }

    // TODO (댓글쓰기)
    public void writeComment(BbsCommentWriteVo bbsCommentWriteVo, UserEntity userEntity) throws SQLException {
        this.bbsDao.selectArticle(new BbsBoardListVo());
//
//        if(bbsCommentWriteVo.getArticleIndex() == null) {
//            bbsCommentWriteVo.setResult(BbsCommentWriteResult.NOT_FOUND);
//            return;
//        }

        BbsCommentEntity bbsCommentEntity = new BbsCommentEntity();
        bbsCommentEntity.setIndex(bbsCommentWriteVo.getIndex());

    }
}