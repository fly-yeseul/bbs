package dev.fly_yeseul.bbs1.entities;

import dev.fly_yeseul.bbs1.interfaces.IEntity;

import java.util.Date;

public class UserEntity implements IEntity<UserEntity> {

    private int index;
    private String email;
    private String password;
    private String nickname;
    private String contact;
    private int level;
    private Date registeredAt;
    private Date modifiedAt;
    private boolean isdeleted;
    private boolean issuspended;

    public UserEntity() {
        super();

    };

    public UserEntity(UserEntity userEntity){
        super();
        this.copyValuesOf(userEntity);
    }

    public UserEntity(int index, String email, String password, String nickname, String contact, int level, Date registeredAt, Date modifiedAt, boolean isdeleted, boolean issuspended) {
        super();
        this.index = index;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.contact = contact;
        this.level = level;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;
        this.isdeleted = isdeleted;
        this.issuspended = issuspended;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public boolean isIssuspended() {
        return issuspended;
    }
    // Get 이 아니라 그냥 Is~~~~다.

    public void setIssuspended(boolean issuspended) {
        this.issuspended = issuspended;
    }

    @Override
    public UserEntity clone() {
        UserEntity userEntity = new UserEntity();
        userEntity.index = this.index;
        userEntity.email = new String(this.email);
        userEntity.password = new String(this.password);
        userEntity.nickname = new String(this.nickname);
        userEntity.contact = new String(this.contact);
        userEntity.level = this.level;
        userEntity.registeredAt = (Date)this.registeredAt.clone();
        userEntity.modifiedAt = (Date)this.modifiedAt.clone();
        userEntity.isdeleted = this.isdeleted;
        userEntity.issuspended = this.issuspended;
        return userEntity;
    }

    @Override
    public void copyValuesOf(UserEntity userEntity) {
        this.index = userEntity.index;
        this.email = userEntity.email;
        this.password = userEntity.password;
        this.nickname = userEntity.nickname;
        this.contact = userEntity.contact;
        this.level = userEntity.level;
        this.registeredAt = userEntity.registeredAt;
        this.modifiedAt = userEntity.modifiedAt;
        this.isdeleted = userEntity.isdeleted;
        this.issuspended = userEntity.issuspended;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserEntity)){
            return false;
        }
        UserEntity userEntity = (UserEntity) obj;
        return this.index == userEntity.index;
//        return this.index == ((UserEntity) obj).index;
    }
}
