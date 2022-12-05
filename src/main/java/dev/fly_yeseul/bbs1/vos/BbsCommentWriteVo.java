package dev.fly_yeseul.bbs1.vos;

import dev.fly_yeseul.bbs1.entities.BbsCommentEntity;
import dev.fly_yeseul.bbs1.enums.BbsCommentWriteResult;

import java.sql.Timestamp;
import java.util.Date;

public class BbsCommentWriteVo extends BbsCommentEntity {
    private BbsCommentWriteResult result;

    // 1. 기본생성자
    // 2. 전체선택 > Select None
    // 3. Getter & Setter

    public BbsCommentWriteVo() {
    }

    public BbsCommentWriteVo(BbsCommentEntity bbsCommentEntity) {
        super(bbsCommentEntity);
    }

    public BbsCommentWriteVo(int index, int articleIndex, String userEmail, String content, Date writtenAt) {
        super(index, articleIndex, userEmail, content, writtenAt);
    }

    public BbsCommentWriteResult getResult() {
        return result;
    }

    public void setResult(BbsCommentWriteResult result) {
        this.result = result;
    }
}


