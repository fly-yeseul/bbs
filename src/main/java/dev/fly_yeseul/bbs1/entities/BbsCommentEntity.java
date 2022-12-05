package dev.fly_yeseul.bbs1.entities;

import dev.fly_yeseul.bbs1.interfaces.IEntity;

import java.util.Date;

public class BbsCommentEntity implements IEntity<BbsCommentEntity> {
    private int index;
    private int articleIndex;
    private String userEmail;
    private String content;
    private Date writtenAt;

    public BbsCommentEntity() {
    }

    public BbsCommentEntity(BbsCommentEntity bbsCommentEntity) {
        this.copyValuesOf(bbsCommentEntity);
    }

    public BbsCommentEntity(int index, int articleIndex, String userEmail, String content, Date writtenAt) {
        this.index = index;
        this.articleIndex = articleIndex;
        this.userEmail = userEmail;
        this.content = content;
        this.writtenAt = writtenAt;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getArticleIndex() {
        return articleIndex;
    }

    public void setArticleIndex(int articleIndex) {
        this.articleIndex = articleIndex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    @Override
    public BbsCommentEntity clone() {
        BbsCommentEntity bbsCommentEntity = new BbsCommentEntity();
        bbsCommentEntity.index = this.index;
        bbsCommentEntity.articleIndex = this.articleIndex;
        bbsCommentEntity.userEmail = this.userEmail;
        bbsCommentEntity.content = this.content;
        bbsCommentEntity.writtenAt = this.writtenAt;
        return bbsCommentEntity;
    }

    @Override
    public void copyValuesOf(BbsCommentEntity bbsCommentEntity) {
        this.index = bbsCommentEntity.index;
        this.articleIndex = bbsCommentEntity.articleIndex;
        this.userEmail = bbsCommentEntity.userEmail;
        this.content = bbsCommentEntity.content;
        this.writtenAt = bbsCommentEntity.writtenAt;

    }
}
