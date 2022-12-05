package dev.fly_yeseul.bbs1.services;


import dev.fly_yeseul.bbs1.daos.UserDao;
import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.enums.UserLoginResult;
import dev.fly_yeseul.bbs1.enums.UserRegisterResult;
import dev.fly_yeseul.bbs1.utils.CryptoUtil;
import dev.fly_yeseul.bbs1.vos.UserLoginVo;
import dev.fly_yeseul.bbs1.vos.UserRegisterVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.sql.SQLException;

@Service(value = "dev.fly_yeseul.bbs1.services.UserService")
// value 주는 이유 : 나중에 파일 이름 겹칠 수 있음.
public class UserService {


    private final UserDao userDao;




    @Autowired
    public UserService(UserDao userdao, UserDao userDao) {
        this.userDao = userDao;
    }

    // TODO
    public void login(UserLoginVo userLoginVo) throws SQLException {
        // 회원 가입 안한사람
        // 정규화 해야함
        userLoginVo.setPassword(CryptoUtil.hashSha512(userLoginVo.getPassword()));
        this.userDao.select(userLoginVo);
        if(userLoginVo.getIndex() == 0) {
            userLoginVo.setResult(UserLoginResult.FAILURE);
        } else if(userLoginVo.isIsdeleted()){
            userLoginVo.setResult(UserLoginResult.DELETED);
        } else if (userLoginVo.isIssuspended()){
            userLoginVo.setResult(UserLoginResult.SUSPENDED);
        } else {
            userLoginVo.setResult(UserLoginResult.SUCCESS);
        }
    }

    public void register(UserRegisterVo userRegisterVo) throws SQLException {
        // DB에 접근하기 전에 Null 검사 해야 함
        // 이메일
        // ^([0-9a-zA-z_]{1,})@([0-9a-z]
        if(userRegisterVo.getNickname() != null &&
                !userRegisterVo.getNickname().matches("^([0-9a-zA-Z가-힣]{2,10})$")) {
            System.out.println("닉네임 안됨");
        } else if (userRegisterVo.getContact() != null &&
                !userRegisterVo.getContact().matches("^([0-9]{11})$")){
            System.out.println("폰번호이상함");
            //^(010)\-([0-9]{4})\-([0-9]{4})$
        }

//        String password = userRegisterVo.getPassword();
//        String hashedPassword = CryptoUtil.hashSha512(password);
//        userRegisterVo.setPassword(hashedPassword);

        userRegisterVo.setPassword(CryptoUtil.hashSha512(userRegisterVo.getPassword()));
        // 한번만 이용될거에 이름을 붙여주는 것은 좋지 않다.


        if (this.userDao.selectEmailCount(userRegisterVo) > 0) {
            userRegisterVo.setResult(UserRegisterResult.DUPLICATE_EMAIL);

        } else if (this.userDao.selectNicknameCount(userRegisterVo) > 0) {
            userRegisterVo.setResult(UserRegisterResult.DUPLICATE_NICKNAME);

        } else if (this.userDao.selectContactCount(userRegisterVo) > 0) {
            userRegisterVo.setResult(UserRegisterResult.DUPLICATE_CONTACT);

        } else {
            userRegisterVo.setResult(this.userDao.insert(userRegisterVo) ?
                    UserRegisterResult.SUCCESS:
                    UserRegisterResult.FAILURE);
            // 삼항식이 훨씬 간단해보인다.
        }
        // return 없이 else if 돌려도 상관 없다.


    }
}
