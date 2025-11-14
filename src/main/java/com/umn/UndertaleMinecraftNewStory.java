package com.umn;

import com.umn.event.ModPlayerEvents; // <--- 1. 导入我们刚才创建的事件类
import com.umn.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UndertaleMinecraftNewStory implements ModInitializer {

    public static final String MOD_ID = "undertale-minecraft-new-story";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        // 注册物品
        ModItems.registerModItems();

        // 注册事件 (就像你的蓝图 umn.java 中做的一样)
        ModPlayerEvents.register(); // <--- 2. 调用事件注册

        LOGGER.info("Undertale New Story Mod has initialized.");
    }
}