package com.umn.character; // <--- 包名已更新为 com.umn.character

import net.minecraft.nbt.NbtCompound;

// 我们实现了刚才创建的接口
// 内容改编自你提供的 PlayerDataComponent.java
public class PlayerDataComponent implements IPlayerDataComponent {
    private int level = 1;
    private int exp = 0;
    private int killCount = 0;

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getExp() {
        return this.exp;
    }

    @Override
    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public void addExp(int amount) {
        this.exp += amount;
        // TODO: 在这里添加升级逻辑 (例如 LV = 19 后变为真刀)
        // 我们稍后会回来实现这一点
    }

    @Override
    public int getKillCount() {
        return this.killCount;
    }

    @Override
    public void incrementKillCount() {
        this.killCount++;
    }

    // 从NBT（存档数据）中读取数据
    @Override
    public void readFromNbt(NbtCompound tag) {
        // 为了安全起见，检查 tag 是否包含我们的键
        if (tag.contains("level")) {
            this.level = tag.getInt("level");
            this.exp = tag.getInt("exp");
            this.killCount = tag.getInt("killCount");
        }
    }

    // 将数据写入NBT（存档数据）
    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("level", this.level);
        tag.putInt("exp", this.exp);
        tag.putInt("killCount", this.killCount);
    }
}