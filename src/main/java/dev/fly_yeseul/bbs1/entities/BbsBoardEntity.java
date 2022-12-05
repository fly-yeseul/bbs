package dev.fly_yeseul.bbs1.entities;

import dev.fly_yeseul.bbs1.interfaces.IEntity;

public class BbsBoardEntity implements IEntity<BbsBoardEntity> {
    private int index;
    private String id;
    private String name;
    private int listLevel;
    private int readLevel;
    private int writeLevel;
    private int commentLevel;

    // 기본생성자
    public BbsBoardEntity() {
    }

    public BbsBoardEntity(BbsBoardEntity bbsBoardEntity){
        this.copyValuesOf(bbsBoardEntity);
    }

    public BbsBoardEntity(int index, String id, String name, int listLevel, int readLevel, int writeLevel, int commentLevel) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.listLevel = listLevel;
        this.readLevel = readLevel;
        this.writeLevel = writeLevel;
        this.commentLevel = commentLevel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListLevel() {
        return listLevel;
    }

    public void setListLevel(int listLevel) {
        this.listLevel = listLevel;
    }

    public int getReadLevel() {
        return readLevel;
    }

    public void setReadLevel(int readLevel) {
        this.readLevel = readLevel;
    }

    public int getWriteLevel() {
        return writeLevel;
    }

    public void setWriteLevel(int writeLevel) {
        this.writeLevel = writeLevel;
    }

    public int getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(int commentLevel) {
        this.commentLevel = commentLevel;
    }


    @Override
    public BbsBoardEntity clone() {
        BbsBoardEntity bbsBoardEntity = new BbsBoardEntity();
        bbsBoardEntity.index = this.index;
        bbsBoardEntity.id = this.id;
        bbsBoardEntity.name = this.name;
        bbsBoardEntity.listLevel = this.listLevel;
        bbsBoardEntity.readLevel = this.readLevel;
        bbsBoardEntity.writeLevel = this.writeLevel;
        bbsBoardEntity.commentLevel = this.commentLevel;
        return bbsBoardEntity;
    }

    @Override
    public void copyValuesOf(BbsBoardEntity bbsBoardEntity) {
        this.index = bbsBoardEntity.index;
        this.id = bbsBoardEntity.id;
        this.name = bbsBoardEntity.name;
        this.listLevel = bbsBoardEntity.listLevel;
        this.readLevel = bbsBoardEntity.readLevel;
        this.writeLevel = bbsBoardEntity.writeLevel;
        this.commentLevel = bbsBoardEntity.commentLevel;
    }
}
