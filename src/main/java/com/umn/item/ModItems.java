package com.umn.item;

import com.umn.UndertaleMinecraftNewStory;
// V V V 这里的路径已更新 V V V
import com.umn.item.custom.knife.RealKnifeItem;
import com.umn.item.custom.knife.WornDaggerItem;
// ^ ^ ^ 这里的路径已更新 ^ ^ ^
import com.umn.item.material.ModToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // 注册逻辑保持不变
    public static final Item WORN_DAGGER = registerItem("worn_dagger",
            new WornDaggerItem(ModToolMaterials.CHARA, 3, -2.4f,
                    new FabricItemSettings()));

    public static final Item THE_REAL_KNIFE = registerItem("the_real_knife",
            new RealKnifeItem(ModToolMaterials.CHARA, 7, -2.4f,
                    new FabricItemSettings().fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM,
                new Identifier(UndertaleMinecraftNewStory.MOD_ID, name), item);
    }

    public static void registerModItems() {
        UndertaleMinecraftNewStory.LOGGER.info("Registering Mod Items for " + UndertaleMinecraftNewStory.MOD_ID);
    }
}