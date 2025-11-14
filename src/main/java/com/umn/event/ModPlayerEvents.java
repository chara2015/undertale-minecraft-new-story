package com.umn.event;

import com.umn.UndertaleMinecraftNewStory;
// V V V 移除了 com.umn.character.ModComponents V V V
import com.umn.util.IPlayerDataSaver; // <--- 1. 导入我们新的 Mixin 接口
import com.umn.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModPlayerEvents {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {

            UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 1] 生物死亡事件已触发，正在检查攻击者...");

            // 1. 检查攻击者是否是玩家 (这部分不变)
            if (damageSource.getAttacker() instanceof ServerPlayerEntity player) {

                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 2] 攻击者确认为玩家: {}", player.getName().getString());

                ItemStack mainHandStack = player.getMainHandStack();

                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 3] 玩家手持物品: {}", Registries.ITEM.getId(mainHandStack.getItem()).toString());
                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 3] 期望的物品是 '{}:worn_dagger' 或 '{}:the_real_knife'",
                        UndertaleMinecraftNewStory.MOD_ID, UndertaleMinecraftNewStory.MOD_ID);

                // 2. 检查玩家手上拿的是否是我们的刀 (这部分不变)
                if (mainHandStack.isOf(ModItems.WORN_DAGGER) || mainHandStack.isOf(ModItems.THE_REAL_KNIFE)) {

                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 4] 物品检查通过！准备增加经验值。");

                    //
                    // 3. (*** 这就是修改的核心 ***)
                    //
                    // 我们不再需要调用 .maybeGet().ifPresent()
                    // 因为我们通过 Mixin 知道 "player" 100% 实现了我们的接口
                    // 我们只需要一个简单的强制类型转换：
                    //
                    IPlayerDataSaver playerData = (IPlayerDataSaver)player;

                    // 4. 增加EXP和杀敌数 (调用 playerData 上的方法)
                    playerData.addExp(10); // 暂时固定每次击杀获得10点EXP
                    playerData.incrementKillCount();

                    // 5. (可选) 发送反馈信息给玩家
                    player.sendMessage(Text.literal("EXP +10"), true);

                    // 6. (调试用) 在服务器后台打印当前数据
                    UndertaleMinecraftNewStory.LOGGER.info("Player {} now has {} EXP, {} LV, {} Kills.",
                            player.getName().getString(),
                            playerData.getExp(),
                            playerData.getLevel(),
                            playerData.getKillCount());

                } else {
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 5] 物品检查失败，玩家未使用指定的刀。");
                }
            } else {
                if (damageSource.getAttacker() != null) {
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 6] 攻击者不是玩家，而是: {}", damageSource.getAttacker().toString());
                } else {
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 6] 攻击者为null (例如摔死、淹死等)。");
                }
            }
        });
    }
}