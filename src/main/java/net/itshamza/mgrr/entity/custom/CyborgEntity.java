package net.itshamza.mgrr.entity.custom;

import net.itshamza.mgrr.effect.ModEffects;
import net.itshamza.mgrr.entity.MGREntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class CyborgEntity extends MGREntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public CyborgEntity(EntityType<? extends Monster> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, this.FLOAT_GOAL());
        this.goalSelector.addGoal(2, this.STROLL_GOAL());
        this.goalSelector.addGoal(2, this.SWIMMING_GOAL());
        this.goalSelector.addGoal(3, this.LOOK_AT_ENTITY_GOAL(Player.class));
        this.goalSelector.addGoal(5, this.RANDOM_LOOK_AROUND());
        this.goalSelector.addGoal(5, this.BLOCK_GOAL());
        this.goalSelector.addGoal(1, this.MELEE_ATTACK_GOAL());
        this.targetSelector.addGoal(3, this.NEAREST_TARGET_GOAL(Player.class));
    }

    // ANIMATIONS //

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }
    protected float getSoundVolume() {
        return 0.2F;
    }
    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            if (this.isAggressive()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk_spotted", true));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }

        if (this.isBlocking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("block", true));
            return PlayState.CONTINUE;
        }

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", true));
            return PlayState.CONTINUE;
        }

        if (this.isAggressive()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_spotted", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<CyborgEntity> event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    private PlayState deathPredicate(AnimationEvent<CyborgEntity> event) {
        if(this.isDeadOrDying() && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attackController",
                0, this::attackPredicate));
        data.addAnimationController(new AnimationController<>(this, "deathController",
                0, this::deathPredicate));
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    public void tick(){
        super.tick();
        if(CyborgEntity.this.hasEffect(ModEffects.STUN.get())){
            CyborgEntity.this.setDeltaMovement(0, 0, 0);
            // Stop the entity from moving or doing anything
            CyborgEntity.this.setDeltaMovement(0, 0, 0);
            CyborgEntity.this.setJumping(false);
            CyborgEntity.this.setSprinting(false);
            CyborgEntity.this.setShiftKeyDown(false);
            CyborgEntity.this.setYHeadRot(0);
            CyborgEntity.this.setXRot(0);
            // Prevent the entity from attacking
            CyborgEntity.this.setTarget(null);
        }
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BLOCKING, Boolean.FALSE);
        this.entityData.define(SPOTTED, Boolean.FALSE);
    }
    public void addAdditionalSaveData(CompoundTag p_29495_) {
        p_29495_.putBoolean("Blocking", isBlocking());
        p_29495_.putBoolean("Spotted", isSpotted());
    }
    public void readAdditionalSaveData(@NotNull CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.setBlocking(p_29478_.getBoolean("Blocking"));
        this.setSpotted(p_29478_.getBoolean("Spotted"));
    }
}
