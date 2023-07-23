package net.itshamza.mgrr.animation;

import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.itshamza.mgrr.MGRRMod;
import net.itshamza.mgrr.network.animation.GetAnimationsFromServer;
import net.itshamza.mgrr.network.NetworkCore;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class AnimationManager {
    public static void playAnimation(String animId){
        NetworkCore.sendToServer(new GetAnimationsFromServer(animId));
    }
    public static void playAnimation(AbstractClientPlayer player, String animId){
        if (MGRRAnimations.animationData.get(player) != null) {
            MGRRAnimations.animationData.get(player).replaceAnimationWithFade(new AbstractFadeModifier(2){
                @Override protected float getAlpha(String modelName, TransformType type, float progress) {return 0.2f;}
            }, new KeyframeAnimationPlayer(
                    Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(MGRRMod.MOD_ID, animId))))
                    .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).
                    setFirstPersonConfiguration(new FirstPersonConfiguration().setShowLeftArm(true).setShowLeftItem(true).setShowRightArm(true).setShowRightItem(true))
            );
        }
    }

}
