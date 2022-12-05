package dev.fly_yeseul.bbs1.vos;

import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.enums.UserRegisterResult;

import java.util.Date;

public class UserRegisterVo extends UserEntity {
    public static Object setResult;
    // x instanceof IEntity
    // 결과를 돌려주기 위해 Vo를 만든다.
    private UserRegisterResult result;

    public UserRegisterVo() {
    }

    public UserRegisterVo(UserEntity userEntity) {
        super(userEntity);
    }

    public UserRegisterVo(int index, String email, String password, String nickname, String contact, int level, Date registeredAt, Date modifiedAt, boolean isdeleted, boolean issuspended) {
        super(index, email, password, nickname, contact, level, registeredAt, modifiedAt, isdeleted, issuspended);
    }

    public UserRegisterResult getResult() {
        return result;
    }

    public void setResult(UserRegisterResult result) {
        this.result = result;
    }
}
