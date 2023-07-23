package net.itshamza.mgrr;

import com.mojang.logging.LogUtils;
import net.itshamza.mgrr.effect.ModEffects;
import net.itshamza.mgrr.entity.ModEntityCreator;
import net.itshamza.mgrr.item.ModItems;
import net.itshamza.mgrr.item.ModTabs;
import net.itshamza.mgrr.network.NetworkCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MGRRMod.MOD_ID)
public class MGRRMod
{
    public static final String MOD_ID = "mgrr";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MGRRMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //
        ModItems.register(modEventBus);
        //
        modEventBus.addListener(this::commonSetup);
        ModEntityCreator.register(modEventBus);
        ModEffects.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkCore.register();
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){}
    }
}
