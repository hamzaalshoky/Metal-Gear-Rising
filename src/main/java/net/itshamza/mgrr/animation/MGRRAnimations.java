package net.itshamza.mgrr.animation;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.itshamza.mgrr.MGRRMod;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.IdentityHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MGRRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MGRRAnimations {
    public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> animationData = new IdentityHashMap<>();
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onClientSetup(FMLClientSetupEvent event) {
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MGRRAnimations::registerPlayerAnimation);
    }
    private static void registerPlayerAnimation(AbstractClientPlayer player, AnimationStack stack) {
        var layer = new ModifierLayer<>();
        stack.addAnimLayer(1000, layer); //Register the layer with a priority
        MGRRAnimations.animationData.put(player, layer);
    }
}