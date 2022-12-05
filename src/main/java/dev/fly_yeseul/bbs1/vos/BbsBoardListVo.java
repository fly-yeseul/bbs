package dev.fly_yeseul.bbs1.vos;

import dev.fly_yeseul.bbs1.entities.BbsArticleEntity;
import dev.fly_yeseul.bbs1.entities.BbsBoardEntity;
import dev.fly_yeseul.bbs1.enums.BbsBoardListResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class BbsBoardListVo extends BbsBoardEntity {

    private BbsBoardListResult result;
    private int articleCountPerPage;
    private int requestPage;
    private int startPage;
    private int endPage;
    private int minPage = 1;
    private int maxPage;

    private ArrayList<BbsArticleEntity> articles;
    private HashMap<Integer, Integer> articleCommentCounts;
    private HashMap<Integer, String> articleNicknames;


    // 기본 생성자
    public BbsBoardListVo() {
    }

    // bbsBoardEntity 만 매개변수로 하는 생성자
    public BbsBoardListVo(BbsBoardEntity bbsBoardEntity) {
        super(bbsBoardEntity);
    }

    public BbsBoardListVo(int index, String id, String name, int listLevel, int readLevel, int writeLevel, int commentLevel) {
        super(index, id, name, listLevel, readLevel, writeLevel, commentLevel);
    }

    public BbsBoardListResult getResult() {
        return result;
    }

    public void setResult(BbsBoardListResult result) {
        this.result = result;
    }

    public int getArticleCountPerPage() {
        return articleCountPerPage;
    }

    public void setArticleCountPerPage(int articleCountPerPage) {
        this.articleCountPerPage = articleCountPerPage;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getMinPage() {
        return minPage;
    }

    public void setMinPage(int minPage) {
        this.minPage = minPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public ArrayList<BbsArticleEntity> getArticles() {
        if(this.articles == null){
            this.articles = new ArrayList<>();
        }
        return articles;
    }

    public void setArticles(ArrayList<BbsArticleEntity> articles) {
        this.articles = articles;
    }

    public HashMap<Integer, Integer> getArticleCommentCounts() {
        if(this.articleCommentCounts == null){
            this.articleCommentCounts = new HashMap<>();
        }
        return articleCommentCounts;
    }

    public void setArticleCommentCounts(HashMap<Integer, Integer> articleCommentCounts) {
        this.articleCommentCounts = articleCommentCounts;
    }

    public HashMap<Integer, String> getArticleNicknames() {
        if(this.articleNicknames == null) {
            this.articleNicknames = new HashMap<>();
        }
        return articleNicknames;
    }

    public void setArticleNicknames(HashMap<Integer, String> articleNicknames) {
        this.articleNicknames = articleNicknames;
    }
}
