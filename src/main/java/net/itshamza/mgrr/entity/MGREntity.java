package net.itshamza.mgrr.entity;

import net.itshamza.mgrr.effect.ModEffects;
import net.itshamza.mgrr.entity.custom.CyborgEntity;
import net.itshamza.mgrr.entity.custom.ai.BlockGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class MGREntity extends Monster {
    public static final EntityDataAccessor<Boolean> BLOCKING = SynchedEntityData.defineId(CyborgEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(CyborgEntity.class, EntityDataSerializers.BOOLEAN);
    protected MGREntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // nikgub : I structured goals for future usage, goalCondition() defines additional conditions for a goal to be executed

    public Goal STROLL_GOAL() {
        return new RandomStrollGoal(this, 1.0D) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal FLOAT_GOAL(){
        return new  FloatGoal(this) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal SWIMMING_GOAL(){ return new RandomSwimmingGoal(this, 1.0D, 1) {
        public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal LOOK_AT_ENTITY_GOAL(Class<? extends LivingEntity> entity) {
        return new LookAtPlayerGoal(this, entity, 8.0F) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }

    public Goal RANDOM_LOOK_AROUND() {
        return new RandomLookAroundGoal(this) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal BLOCK_GOAL() {
        return new BlockGoal(this) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal MELEE_ATTACK_GOAL() {
        return new MeleeAttackGoal(this, 1.2F, true) {
            public boolean canUse() {
                return goalCondition() && super.canUse();
            }
        };
    }


    public Goal NEAREST_TARGET_GOAL(Class<? extends LivingEntity> entity) {
        return new NearestAttackableTargetGoal(this, entity, false) {
            public boolean canUse() {return goalCondition() && super.canUse();}};}


    public boolean goalCondition(){
        return !MGREntity.this.hasEffect(ModEffects.MALFUNCTION.get());
    }


    public boolean isBlocking() {
        return this.entityData.get(BLOCKING);
    }

    public void setBlocking(boolean blocking) {
        this.entityData.set(BLOCKING, blocking);
    }
    public boolean isSpotted() {
        return this.entityData.get(SPOTTED);
    }
    public void setSpotted(boolean blocking) {
        this.entityData.set(SPOTTED, blocking);
    }
}
