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

    public static final RegistryObject<Item> CYBORG_SPAWN_EGG = ITEMS.register("cyborg_spawn_egg",
                () -> new ForgeSpawnEggItem(ModEntityCreator.CYBORG,14265190, 1973794,
                        new Item.Properties()));

   //public static final RegistryObject<Item> POISONOUS_SPINE = ITEMS.register("poisonous_spine",
    //       () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

  // public static final RegistryObject<Item> POISONOUS_SPEAR = ITEMS.register("poisonous_spear",
  //         () -> new SwordItem(Tiers.IRON, 3, -1.0f,
  //                  new Item.Properties().tab(ModCreativeModeTabs.AFRICA_TAB)));

   public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
