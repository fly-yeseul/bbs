package dev.fly_yeseul.bbs1.entities;

import dev.fly_yeseul.bbs1.interfaces.IEntity;

public class UserLevelEntity implements IEntity<UserLevelEntity> {
    private int level;
    private String text;

    public UserLevelEntity() {
    }

    public UserLevelEntity(int level, String text) {
        this.level = level;
        this.text = text;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public UserLevelEntity clone() {
        UserLevelEntity userLevelEntity = new UserLevelEntity();
        userLevelEntity.level = this.level;
        userLevelEntity.text = this.text;

        return userLevelEntity;
    }

    @Override
    public void copyValuesOf(UserLevelEntity userLevelEntity) {
        this.level = userLevelEntity.level;
        this.text = userLevelEntity.text;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserLevelEntity)){
            return false;
        }
        UserLevelEntity userLevelEntity = (UserLevelEntity) obj;
        return this.level == userLevelEntity.level;
    }
}
