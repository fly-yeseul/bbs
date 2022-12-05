package dev.fly_yeseul.bbs1.controllers;

import dev.fly_yeseul.bbs1.entities.UserEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



public abstract class StandardController {
    protected StandardController() {

    }

    protected final UserEntity getUserEntity(HttpServletRequest request) {
        return this.getUserEntity(request.getSession());
    }


    protected final UserEntity getUserEntity(HttpSession session) {
        final String attrName = "userEntity";
        UserEntity userEntity = null;
        if(session.getAttribute("userEntity") instanceof UserEntity) {
            userEntity = (UserEntity) session.getAttribute(attrName);
        }
        return userEntity;
    }
}
