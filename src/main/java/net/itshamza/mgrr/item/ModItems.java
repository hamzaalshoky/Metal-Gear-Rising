package net.itshamza.mgrr.item;

import net.itshamza.mgrr.MGRRMod;
import net.itshamza.mgrr.entity.ModEntityCreator;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MGRRMod.MOD_ID);
    // weapons
    public static final RegistryObject<Item> HIGH_FREQUENCY_BLADE = ITEMS.register("high_frequency_blade", HighFrequencyBladeItem::new);
    // spawn eggs
    public static final RegistryObject<Item> CYBORG_SPAWN_EGG = ITEMS.register("cyborg_spawn_egg",
                () -> new ForgeSpawnEggItem(ModEntityCreator.CYBORG,14265190, 1973794,
                        new Item.Properties()));
   public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
