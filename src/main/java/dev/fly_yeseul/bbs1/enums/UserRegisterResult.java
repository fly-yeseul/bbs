package dev.fly_yeseul.bbs1.enums;

public enum UserRegisterResult {
    // 비밀번호 입력/재입력 일치 확인은 Javascript단 이야기

    DUPLICATE_EMAIL,
    DUPLICATE_NICKNAME,
    DUPLICATE_CONTACT,
    FAILURE,
    SUCCESS
}
