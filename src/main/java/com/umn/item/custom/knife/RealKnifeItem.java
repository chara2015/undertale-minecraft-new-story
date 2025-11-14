package com.umn.item.custom.knife; // <--- 包名已更新

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem; // <--- 继承 ToolItem
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// 改编自你提供的 RealKnifeItem.java
public class RealKnifeItem extends ToolItem { // <--- 继承 ToolItem
    private final float attackDamage;

    // 构造函数匹配 ToolItem
    public RealKnifeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings);
        // 注意：你提供的代码 中, attackSpeed 参数没有被使用。
        // 我们将攻击伤害的计算方式保留
        this.attackDamage = attackDamage + toolMaterial.getAttackDamage();
    }

    // 红色名字
    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.RED);
    }

    // 红色提示
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // 我们将使用你在 RealKnifeItem.java 中使用的新翻译键
        tooltip.add(Text.translatable("item.undertale-minecraft-new-story.the_real_knife.tooltip").formatted(Formatting.RED));
        super.appendTooltip(stack, world, tooltip, context);
    }

    // 永不损坏
    @Override
    public boolean isDamageable() {
        return false;
    }

    // 高攻速和自定义伤害
    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            // 基础伤害
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
            // 超高攻速 (+95.0)
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", 95.0, EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot);
    }
}