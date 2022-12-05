package dev.fly_yeseul.bbs1.vos;

import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.enums.UserLoginResult;

import java.util.Date;

public class UserLoginVo extends UserEntity {
    public static Object setResult;

    private UserLoginResult result;

    public UserLoginVo(){

    }

    public UserLoginVo(UserEntity userEntity){
        super(userEntity);
    }

    public UserLoginVo(int index, String email, String password, String nickname, String contact, int level, Date registeredAt, Date modifiedAt, boolean isdeleted, boolean issuspended) {
        super(index, email, password, nickname, contact, level, registeredAt, modifiedAt, isdeleted, issuspended);
    }

    public UserLoginVo(UserLoginResult result) {
        this.result = result;
    }

    public UserLoginVo(UserEntity userEntity, UserLoginResult result) {
        super(userEntity);
        this.result = result;
    }

    public UserLoginVo(int index, String email, String password, String nickname, String contact, int level, Date registeredAt, Date modifiedAt, boolean isdeleted, boolean issuspended, UserLoginResult result) {
        super(index, email, password, nickname, contact, level, registeredAt, modifiedAt, isdeleted, issuspended);
        this.result = result;
    }

    public static Object getSetResult() {
        return setResult;
    }

    public static void setSetResult(Object setResult) {
        UserLoginVo.setResult = setResult;
    }

    public UserLoginResult getResult() {
        return result;
    }

    public void setResult(UserLoginResult result) {
        this.result = result;
    }
}

