package net.itshamza.mgrr.entity.custom.ai;

import net.itshamza.mgrr.entity.custom.CyborgEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BlockGoal extends Goal {

    private final CyborgEntity entity;
    private boolean isBlocking;
    private int blockTime;

    // Constructor
    public BlockGoal(CyborgEntity entity) {
        this.entity = entity;
        this.isBlocking = false;
        this.blockTime = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    // Override the startExecuting method
    @Override
    public void start() {
        this.isBlocking = true;
        this.blockTime = 20;
        this.entity.setBlocking(true); // Set the boolean value for geckolib animation
    }

    // Override the resetTask method
    @Override
    public void stop() {
        this.isBlocking = false;
        this.entity.setBlocking(false); // Reset the boolean value for geckolib animation
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        if (target != null && !this.entity.swinging && target.swinging) {
            return true;
        }
        return false;
    }

    // Override the tick method
    @Override
    public void tick() {
        if (this.isBlocking) {
            if (this.blockTime > 0) {
                this.blockTime--;
            } else {
                this.isBlocking = false;
                this.entity.setBlocking(false); // Reset the boolean value for geckolib animation
            }
        }
    }
}