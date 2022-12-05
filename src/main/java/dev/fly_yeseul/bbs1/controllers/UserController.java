package dev.fly_yeseul.bbs1.controllers;


import dev.fly_yeseul.bbs1.enums.UserLoginResult;
import dev.fly_yeseul.bbs1.enums.UserRegisterResult;
import dev.fly_yeseul.bbs1.services.UserService;
import dev.fly_yeseul.bbs1.vos.UserLoginVo;
import dev.fly_yeseul.bbs1.vos.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller(value = "dev.fly_yeseul.bbs1.controllers.UserController")
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired      // datasource가 할라면 Dao 객ㄹ체화 > service 객채화 > controller 객체화
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 맵핑
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin() {
        return "user/login";
    }


    // TODO
    // UserLoginVo
    // UserLoginResult
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public UserLoginVo postLogin(
            HttpServletRequest request,
            UserLoginVo userLoginVo
    ) throws SQLException {
        this.userService.login(userLoginVo);
        // 세션 기억
        if (userLoginVo.getResult() == UserLoginResult.SUCCESS) {
            HttpSession session = request.getSession();       // http 세션 메서드가 리턴된다.
            // 일회성이 아니라 유지된다.
            session.setAttribute("userEntity", userLoginVo.clone());
        }
        return userLoginVo;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public String getLogout(HttpSession session) {
        session.removeAttribute("userEntity");
//        session.setAttribute("userEntity", null);
        return null;
    }

    // (POST) http://127.0.0.1:8000/user/register
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public UserRegisterVo postRegister(     // 스프링이 부른다 : sql exception
                                            UserRegisterVo userRegisterVo
    ) throws SQLException {
//        System.out.println(userRegisterVo.getEmail());
//        System.out.println(userRegisterVo.getPassword());
//        System.out.println(userRegisterVo.getNickname());
//        System.out.println(userRegisterVo.getContact());
        this.userService.register(userRegisterVo);
        return userRegisterVo;
    }
}
