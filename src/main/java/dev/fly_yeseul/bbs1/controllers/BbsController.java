package dev.fly_yeseul.bbs1.controllers;

import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.enums.BbsBoardListResult;
import dev.fly_yeseul.bbs1.services.BbsService;
import dev.fly_yeseul.bbs1.vos.BbsArticleReadVo;
import dev.fly_yeseul.bbs1.vos.BbsBoardListVo;
import dev.fly_yeseul.bbs1.vos.BbsCommentWriteVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller (value = "dev.fly_yeseul.bbs1.controllers.BbsController")
// 컨트롤러 표시해주기
@RequestMapping(value = "/bbs")     // Mapping
public class BbsController extends StandardController {

    private final BbsService bbsService;
    // 지역변수 선언


    @Autowired
    // 생성자에 명시된 매개변수를 스프링이 인식 가능한 범위 내에서 객체화해야 한다고 지정
    public BbsController(BbsService bbsService) {
        this.bbsService = bbsService;
    }


    // /뒤에 아무것도 안썼을때 1페이지 뜨게 하기
    // Overloading
    @RequestMapping(value = "/list/{boardId}", method = RequestMethod.GET)
    public String getList(
            HttpSession session,
            Model model,
            BbsBoardListVo bbsBoardListVo,
            @PathVariable(name = "boardId", required = true) String boardId
            // 주소상에 있는 변수
    ) throws SQLException {
        return getList(session, model, bbsBoardListVo, boardId, 1);
    }


    // 글목록 페이지 보기
    @RequestMapping(value = "list/{boardId}/{page}", method = RequestMethod.GET)
    // Mapping
    public String getList(
            HttpSession session,
            Model model,
            BbsBoardListVo bbsBoardListVo,
            // 주소상에 들어가는 변수
            @PathVariable(name = "boardId", required = true) String boardId,
            @PathVariable(name = "page", required = false) int page
    ) throws SQLException {
        // BbsBoardListResult.NOT_FOUND.name() => "NOT_FOUND".equals("NOT_FOUND")
        bbsBoardListVo.setId(boardId);
        bbsBoardListVo.setRequestPage(page);
//        UserEntity userEntity = null;
//        if(session.getAttribute("userEntity") instanceof UserEntity){
//            //instanceof 로 형변환 가능하다는 확신을 받음
//            userEntity = (UserEntity) session.getAttribute("userEntity");
//        }
        this.bbsService.listBoard(bbsBoardListVo, this.getUserEntity(session));
        // 세션에서 userEntity 와 boardListVo를 매개변수로 하여 페이지 수를 확인한다.

        model.addAttribute("bbsBoardListVo", bbsBoardListVo);
        // 속성 이름이 bbsBoardListVo인 bbsBoardListVo에 속성을 부여한다.

        return "bbs/list";            // 페이지를 표시한다. (html 연결)
    }


    // 게시글을 읽는 코드, 주소는 http://localhost:8080/bbs/read/{articleId} 이다.
    // GET Method 이므로 가져온당
    @RequestMapping(value = "read/{articleId}", method = RequestMethod.GET)
    public ModelAndView getRead(            // 데이터와 View 를 동시에~~
                                            ModelAndView modelAndView,
                                            HttpServletRequest request,
                                            HttpSession session,
                                            BbsArticleReadVo bbsArticleReadVo,
                                            @PathVariable(name = "articleId", required = true) int articleId
    ) throws SQLException {
        UserEntity userEntity = this.getUserEntity(session);
        // 세션의 userEntity 를 받아와서 여기 userEntity 로 대입한다.

        bbsArticleReadVo.setIndex(articleId);
        // article Id를 Article Index 로 받아온다.

        this.bbsService.readArticle(bbsArticleReadVo, userEntity);
        modelAndView.addObject("p", request.getParameter("p"));
        modelAndView.addObject("bbsArticleReadVo", bbsArticleReadVo);
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.setViewName("bbs/read");
        return modelAndView;
    }

    // TODO (글쓰기 기능)
    @RequestMapping(value = "read/{articleId}", method = RequestMethod.POST)
    public ModelAndView postRead(
            ModelAndView modelAndView,
            HttpServletRequest request,
            HttpSession session,
            BbsCommentWriteVo bbsCommentWriteVo,

            @PathVariable(name = "articleId", required = true) int articleId
    ) throws SQLException {
        UserEntity userEntity = this.getUserEntity(session);
        bbsCommentWriteVo.setIndex(articleId);
        return modelAndView;
    }
}
        /*
        필요
        BbsCommentWriteVo (BbsCommentEntity 상속)
        BbsCommentWriteResult (열거형)

        확인
         - 전달 받은 articleId 값에 해당하는 게시글이 있는지 확인 후 없다면 실패 (BbsCommentWriteResult.NOT_FOUND)
         - 우선 로그인한 사용자인지 확인 후 로그인된 상태가 아니라면 거절 (BbsCommentWriteResult.NOT_ALLOWED)
         - 전달 받은 articleId 값으로 판단한 게시판의 댓글 작성 권한과 사용자 레벨 비교 후 권한 없으면 실패 (BbsCommentWriteResult.SUCCESS)
         - ModelAndView 객체 통해 "bbs/read" 페이지 보여주며 끝
//         -
        */


