package net.itshamza.mgrr.entity.client;

import net.itshamza.mgrr.MGRRMod;
import net.itshamza.mgrr.entity.custom.CyborgEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CyborgModel extends AnimatedGeoModel<CyborgEntity> {

    @Override
    public ResourceLocation getModelResource(CyborgEntity object) {
        return new ResourceLocation(MGRRMod.MOD_ID, "geo/cyborg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CyborgEntity object) {
        return new ResourceLocation(MGRRMod.MOD_ID, "textures/entity/cyborg/cyborg_basic.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CyborgEntity animatable) {
        return new ResourceLocation(MGRRMod.MOD_ID, "animations/cyborg.animation.json");
    }
}
