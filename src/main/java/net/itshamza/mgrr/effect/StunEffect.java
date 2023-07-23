package net.itshamza.mgrr.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class StunEffect extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFFFF);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Stun the entity by setting their motion to zero
        entity.setDeltaMovement(0, 0, 0);
        // Stop the entity from moving or doing anything
        entity.setDeltaMovement(0, 0, 0);
        entity.setJumping(false);
        entity.setSprinting(false);
        entity.setShiftKeyDown(false);
        entity.setYHeadRot(0);
        entity.setXRot(0);
        // Prevent the entity from attacking
        if (entity instanceof Mob) {
            ((Mob) entity).setTarget(null);
        }
    }
}
