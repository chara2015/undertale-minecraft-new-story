package com.umn.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;

public class KnifeItem extends SwordItem {

    /**
     * 这是“刀”的构造函数
     * @param material   这个武器的材质 (例如木头、石头)
     * @param attackDamage  攻击伤害 (整数)
     * @param attackSpeed   攻击速度 (浮点数)
     * @param settings     物品的通用设置
     */
    public KnifeItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}