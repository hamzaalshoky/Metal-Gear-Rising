package net.itshamza.mgrr.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    HIGH_FREQUENCY(3, 1500, 16f, 2.5f, 10, null);
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> ingredientSupplier;
    ModTiers(int pLevel, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient){
        this.level = pLevel;
        this.uses = pUses;
        this.speed = pSpeed;
        this.damage = pDamage;
        this.enchantmentValue = pEnchantmentValue;
        this.ingredientSupplier = pRepairIngredient;
    }
    public int getUses() {
        return this.uses;
    }
    public float getSpeed() {
        return this.speed;
    }
    public float getAttackDamageBonus() {
        return this.damage;
    }
    public int getLevel() {
        return this.level;
    }
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    public @NotNull Ingredient getRepairIngredient() {
        return this.ingredientSupplier.get();
    }
}
