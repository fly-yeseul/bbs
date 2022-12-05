package dev.fly_yeseul.bbs1.vos;

import dev.fly_yeseul.bbs1.entities.BbsArticleEntity;
import dev.fly_yeseul.bbs1.entities.BbsCommentEntity;
import dev.fly_yeseul.bbs1.enums.BbsArticleReadResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BbsArticleReadVo extends BbsArticleEntity {
    private BbsArticleReadResult result;
    private String userNickname;
    private ArrayList<BbsCommentEntity> comments;
    private HashMap<Integer, String> commentUserNicknames;

    public BbsArticleReadVo() {
    }

    public BbsArticleReadVo(BbsArticleEntity bbsArticleEntity) {
        super(bbsArticleEntity);
    }

    public BbsArticleReadVo(int index, String boardId, String userEmail, String title, String content, Date writtenAt, int view) {
        super(index, boardId, userEmail, title, content, writtenAt, view);
    }

    public BbsArticleReadResult getResult() {
        return result;
    }

    public void setResult(BbsArticleReadResult result) {
        this.result = result;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public ArrayList<BbsCommentEntity> getComments() {
        if(this.comments == null) {
            this.comments = new ArrayList<>();
        }
        return comments;
    }

    public void setComments(ArrayList<BbsCommentEntity> comments) {
        this.comments = comments;
    }

    public HashMap<Integer, String> getCommentUserNicknames() {
        if(this.commentUserNicknames == null) {
            this.commentUserNicknames = new HashMap<>();
        }
        return commentUserNicknames;
    }

    public void setCommentUserNicknames(HashMap<Integer, String> commentUserNicknames) {
        this.commentUserNicknames = commentUserNicknames;
    }
}
