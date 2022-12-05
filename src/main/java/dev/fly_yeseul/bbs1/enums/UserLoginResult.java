package dev.fly_yeseul.bbs1.enums;

public enum UserLoginResult {
    // 로그인 성공
    // 비밀번호 다름
    // 가입되지 않은 회원 (email 조차 없음)
    //
    SUCCESS,
    FAILURE,
    DELETED,
    SUSPENDED
}
