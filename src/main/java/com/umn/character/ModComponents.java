package com.umn.character; // <--- 包名已更新为 com.umn.character

import com.umn.UndertaleMinecraftNewStory; // <--- 导入我们的主类
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

// 内容改编自你提供的 ModComponents.java
public class ModComponents implements EntityComponentInitializer {

    // 1. 创建一个唯一的“钥匙”来访问我们的组件
    // 我们使用了正确的接口和 Mod ID
    public static final ComponentKey<IPlayerDataComponent> PLAYER_DATA =
            ComponentRegistry.getOrCreate(
                    new Identifier(UndertaleMinecraftNewStory.MOD_ID, "player_data"),
                    IPlayerDataComponent.class
            );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // 2. 告诉CCA，当一个玩家实体被创建时，就给他附加我们的 PlayerDataComponent
        registry.registerForPlayers(PLAYER_DATA, player -> {

            // 使用我们主类的 Logger
            UndertaleMinecraftNewStory.LOGGER.info("[CCA-DEBUG] Attaching PlayerDataComponent to player: {}", player.getName().getString());
            return new PlayerDataComponent();

        }, RespawnCopyStrategy.ALWAYS_COPY); // ALWAYS_COPY 确保玩家死亡重生后数据保留
    }
}