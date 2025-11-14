package com.umn.character; // <--- 包名已更新为 com.umn.character

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

// 内容与你提供的 IPlayerDataComponent.java 完全一致
public interface IPlayerDataComponent extends Component {
    // 获取等级
    int getLevel();
    void setLevel(int level);

    // 获取经验
    int getExp();
    void setExp(int exp);
    // 增加经验，这个方法将包含升级逻辑
    void addExp(int amount);

    // 获取杀敌数
    int getKillCount();
    void incrementKillCount();

    // 注意：你提供的 PlayerDataComponent.java 中
    // 有 readFromNbt 和 writeToNbt 方法，但你的接口 中没有。
    // CCA 会自动调用实现类中的这两个方法，所以接口里不写是正确的。
}