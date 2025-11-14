package com.umn.mixin;

import com.umn.util.IPlayerDataSaver; // <--- 导入我们的新接口
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 1. 告诉 Mixin 我们要修改 PlayerEntity 类
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerDataSaver {

    // 2. 在 PlayerEntity 类中添加我们的自定义字段
    private int level = 1;
    private int exp = 0;
    private int killCount = 0;

    // 3. 实现接口中的所有方法 (Getter/Setter)
    // 这些方法现在是 PlayerEntity 类的一部分了
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
        // TODO: 升级逻辑将在这里实现
    }

    @Override
    public int getKillCount() {
        return this.killCount;
    }

    @Override
    public void incrementKillCount() {
        this.killCount++;
    }

    // 4. 注入 NBT 读写方法，用于数据持久化 (保存/加载)

    // 当玩家数据被写入 NBT (保存) 时...
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        // 我们把我们的数据也写进去
        nbt.putInt("level", this.level);
        nbt.putInt("exp", this.exp);
        nbt.putInt("killCount", this.killCount);
    }

    // 当玩家数据从 NBT (加载) 中读取时...
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        // 我们也把我们的数据读出来
        if (nbt.contains("level")) { // 检查数据是否存在
            this.level = nbt.getInt("level");
            this.exp = nbt.getInt("exp");
            this.killCount = nbt.getInt("killCount");
        }
    }
}