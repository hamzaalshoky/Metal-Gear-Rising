package net.itshamza.mgrr.item;

import net.itshamza.mgrr.animation.AnimationManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MGRWeapon extends SwordItem {
    private final int parryTiming;
    public MGRWeapon(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, int parryTiming) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.parryTiming = parryTiming;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemStack){return parryTiming;}
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(pUsedHand.equals(InteractionHand.MAIN_HAND)) {
            pPlayer.startUsingItem(pUsedHand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        } else return new InteractionResultHolder<>(InteractionResult.PASS, pPlayer.getItemInHand(pUsedHand));
    }
    @Override
    @SuppressWarnings("deprecation")
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration){
        this.parry();
    }
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if(pLivingEntity instanceof Player player){
            player.getCooldowns().addCooldown(pStack.getItem(), parryTiming*2);
            player.stopUsingItem();
        }
        return pStack;
    }
    public boolean canParry() {
        return true;
    }
    public void parry(){
        if(this.canParry()){AnimationManager.playAnimation("parry");}
    }
}
