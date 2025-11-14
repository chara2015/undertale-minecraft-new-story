package com.umn.event;

import com.umn.UndertaleMinecraftNewStory; // <--- 导入我们的主类 (用于 Logger)
import com.umn.character.ModComponents; // <--- 导入我们的组件 (用于获取数据)
import com.umn.item.ModItems; // <--- 导入我们的物品 (用于检查)
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

// 这个类的逻辑完全基于你提供的 ModPlayerEvents.java 蓝图
public class ModPlayerEvents {

    // 注册所有事件的静态方法
    public static void register() {

        // 注册“生物死亡后”事件监听器
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {

            // [调试日志 1] (使用我们主类的 Logger)
            UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 1] 生物死亡事件已触发，正在检查攻击者...");

            // 1. 检查攻击者是否是玩家
            if (damageSource.getAttacker() instanceof ServerPlayerEntity player) {

                // [调试日志 2]
                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 2] 攻击者确认为玩家: {}", player.getName().getString());

                ItemStack mainHandStack = player.getMainHandStack();

                // [调试日志 3] (使用了我们正确的 Mod ID)
                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 3] 玩家手持物品: {}", Registries.ITEM.getId(mainHandStack.getItem()).toString());
                UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 3] 期望的物品是 '{}:worn_dagger' 或 '{}:the_real_knife'",
                        UndertaleMinecraftNewStory.MOD_ID, UndertaleMinecraftNewStory.MOD_ID);

                // 2. 检查玩家手上拿的是否是我们的刀
                // 我们的 ModItems 类中定义了 WORN_DAGGER 和 THE_REAL_KNIFE
                if (mainHandStack.isOf(ModItems.WORN_DAGGER) || mainHandStack.isOf(ModItems.THE_REAL_KNIFE)) {

                    // [调试日志 4]
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 4] 物品检查通过！准备增加经验值。");

                    // 3. 安全地获取玩家的自定义数据组件
                    // 我们的 ModComponents 类中定义了 PLAYER_DATA
                    ModComponents.PLAYER_DATA.maybeGet(player).ifPresent(playerData -> {

                        // 4. 增加EXP和杀敌数
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
                    });
                } else {
                    // [调试日志 5]
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 5] 物品检查失败，玩家未使用指定的刀。");
                }
            } else {
                // [调试日志 6]
                if (damageSource.getAttacker() != null) {
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 6] 攻击者不是玩家，而是: {}", damageSource.getAttacker().toString());
                } else {
                    UndertaleMinecraftNewStory.LOGGER.info("[DEBUG 6] 攻击者为null (例如摔死、淹死等)。");
                }
            }
        });
    }
}