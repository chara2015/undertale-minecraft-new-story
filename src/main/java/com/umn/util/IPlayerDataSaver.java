package com.umn.util;

// 这是一个简单的数据接口，取代了 IPlayerDataComponent
public interface IPlayerDataSaver {

    // 它只包含我们需要的方法，不再需要继承 Component

    int getLevel();
    void setLevel(int level);

    int getExp();
    void setExp(int exp);
    void addExp(int amount); // 升级逻辑将在这里

    int getKillCount();
    void incrementKillCount();
}