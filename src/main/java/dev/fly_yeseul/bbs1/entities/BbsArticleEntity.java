package dev.fly_yeseul.bbs1.entities;

import dev.fly_yeseul.bbs1.interfaces.IEntity;

import java.util.Date;

public class BbsArticleEntity implements IEntity<BbsArticleEntity>{
    // DB Column 이랑 1:1 매칭되어야 한다.
    private int index;
    private String boardId;
    private String userEmail;
    private String title;
    private String content;
    private Date writtenAt;
    private int view;


    // 기본 생성자
    public BbsArticleEntity() {
    }

    // IEntity 쓰기위한 생성자
    public BbsArticleEntity(BbsArticleEntity bbsArticleEntity) {
        this.copyValuesOf(bbsArticleEntity);
    }

    //오만거 다 들어간 생성자
    public BbsArticleEntity(int index, String boardId, String userEmail, String title, String content, Date writtenAt, int view) {
        this.index = index;
        this.boardId = boardId;
        this.userEmail = userEmail;
        this.title = title;
        this.content = content;
        this.writtenAt = writtenAt;
        this.view = view;
    }


    // Getter & Setter
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWrittenAt() {
        return writtenAt;
    }

    public void setWrittenAt(Date writtenAt) {
        this.writtenAt = writtenAt;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }


    // IEntity interface
   @Override
    public BbsArticleEntity clone() {
        BbsArticleEntity entity = new BbsArticleEntity();
        entity.index = this.index;
        entity.boardId = this.boardId;
        entity.userEmail = this.userEmail;
        entity.title = this.title;
        entity.content = this.content;
        entity.writtenAt = this.writtenAt;
        entity.view = this.view;
        return entity;
    }

    @Override
    public void copyValuesOf(BbsArticleEntity bbsArticleEntity) {
        this.index = bbsArticleEntity.index;
        this.boardId = bbsArticleEntity.boardId;
        this.userEmail = bbsArticleEntity.userEmail;
        this.title = bbsArticleEntity.title;
        this.content = bbsArticleEntity.content;
        this.writtenAt = bbsArticleEntity.writtenAt;
        this.view = bbsArticleEntity.view;
    }
}
