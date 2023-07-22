package net.itshamza.mgrr.entity.custom;

import net.itshamza.mgrr.entity.custom.ai.BlockGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class CyborgEntity extends Monster implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(CyborgEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(CyborgEntity.class, EntityDataSerializers.BOOLEAN);

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
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new BlockGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
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

    private PlayState attackPredicate(AnimationEvent event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    private PlayState deathPredicate(AnimationEvent event) {
        if(this.isDeadOrDying() && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
        data.addAnimationController(new AnimationController(this, "deathController",
                0, this::deathPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BLOCKING, Boolean.valueOf(false));
        this.entityData.define(SPOTTED, Boolean.valueOf(false));
    }

    public void addAdditionalSaveData(CompoundTag p_29495_) {
        p_29495_.putBoolean("Blocking", isBlocking());
        p_29495_.putBoolean("Spotted", isSpotted());
    }

    public void readAdditionalSaveData(CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.setBlocking(p_29478_.getBoolean("Blocking"));
        this.setSpotted(p_29478_.getBoolean("Spotted"));
    }

    public boolean isBlocking() {
        return this.entityData.get(BLOCKING).booleanValue();
    }

    public void setBlocking(boolean blocking) {
        this.entityData.set(BLOCKING, Boolean.valueOf(blocking));
    }

    public boolean isSpotted() {
        return this.entityData.get(SPOTTED).booleanValue();
    }

    public void setSpotted(boolean blocking) {
        this.entityData.set(SPOTTED, Boolean.valueOf(blocking));
    }
}
