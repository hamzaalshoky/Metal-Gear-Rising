package net.itshamza.mgrr.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ModEntityUtils {
    public static List<? extends LivingEntity> entityCollector(Vec3 center, double radius, Level level){
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius), e -> true).stream().sorted(Comparator.comparingDouble(
                entityFound -> entityFound.distanceToSqr(center))).toList();
    }
}
