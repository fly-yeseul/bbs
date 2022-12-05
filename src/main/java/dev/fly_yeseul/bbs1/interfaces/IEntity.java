package dev.fly_yeseul.bbs1.interfaces;

public interface IEntity<T extends IEntity<?>> {
    // T는 IEntity ArrayList를 상속받고 이는 IEntity
    T clone();

    void copyValuesOf(T t);
}
