package net.itshamza.mgrr.entity;

import net.itshamza.mgrr.MGRRMod;
import net.itshamza.mgrr.entity.client.CyborgRenderer;
import net.itshamza.mgrr.entity.custom.CyborgEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MGRRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityCreator {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MGRRMod.MOD_ID);

    // REGESTRIES

    public static final RegistryObject<EntityType<CyborgEntity>> CYBORG = ENTITY_TYPES.register("cyborg", () -> EntityType.Builder.of(CyborgEntity::new, MobCategory.MONSTER).sized(0.7F, 2F).build(new ResourceLocation(MGRRMod.MOD_ID, "cyborg").toString()));
    // ATTRIBUTES

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.CYBORG.get(), CyborgEntity.setAttributes());
    }

    // RENDERERS

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityCreator.CYBORG.get(), CyborgRenderer::new);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
