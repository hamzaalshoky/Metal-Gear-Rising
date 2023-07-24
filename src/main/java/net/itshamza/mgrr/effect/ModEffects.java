package net.itshamza.mgrr.effect;

import net.itshamza.mgrr.MGRRMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MGRRMod.MOD_ID);

    public static final RegistryObject<MobEffect> MALFUNCTION = MOB_EFFECTS.register("stun", MalfunctionEffect::new);
    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
