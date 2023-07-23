package net.itshamza.mgrr.item.weapon_effects;

import net.itshamza.mgrr.entity.ModDamageSource;
import net.itshamza.mgrr.entity.ModEntityUtils;
import net.itshamza.mgrr.item.MGRWeapon;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ParryEffect {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void parry(LivingHurtEvent event){
        ItemStack itemStack = event.getEntity().getMainHandItem();
        if(itemStack.getItem() instanceof MGRWeapon mgrWeapon && mgrWeapon.canParry()
        && event.getEntity() instanceof Player player && event.getSource().getDirectEntity() != null
        && player.getUseItem().equals(itemStack) && player.level instanceof ServerLevel level){
            double modifier = 1;
            double lx = player.getX() + player.getLookAngle().x * modifier;
            double ly = player.getY() + player.getLookAngle().y * modifier;
            double lz = player.getZ() + player.getLookAngle().z * modifier;
            for(LivingEntity entity : ModEntityUtils.entityCollector(new Vec3(lx, ly, lz), 2, player.level)){
                event.setCanceled(true);
                if(!entity.equals(player) && !entity.equals(event.getSource().getDirectEntity())){
                    entity.hurt(ModDamageSource.parry(player, mgrWeapon.toString()),
                            event.getAmount() + (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.5f);
                    level.sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 1, 0, 0, 0, 0);
                    player.hurtDuration = 0;
                } else if(entity.equals(event.getSource().getDirectEntity())){
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 5, Mth.nextFloat(RandomSource.create(), 0.8f, 1.2f));
                    event.getSource().getDirectEntity().hurt(ModDamageSource.parry(player, itemStack.getItem().toString()),
                            event.getAmount() * 2f + (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
        }
    }
}
