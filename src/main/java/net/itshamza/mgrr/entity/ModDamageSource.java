package net.itshamza.mgrr.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;

public class ModDamageSource {
    public static DamageSource parry(Player player, String weapon_name){
        return new EntityDamageSource("parry." + weapon_name, player);
    }
}
