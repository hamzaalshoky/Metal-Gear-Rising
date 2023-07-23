package net.itshamza.mgrr.network.animation;

import net.itshamza.mgrr.animation.AnimationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendAnimationsToServer {
    private final int playerId;
    private final String animId;
    public SendAnimationsToServer(String animId, int playerId){
        this.playerId = playerId;
        this.animId = animId;
    }
    public SendAnimationsToServer(FriendlyByteBuf buf) {
        this.playerId = buf.readInt();
        this.animId = buf.readUtf();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerId);
        buf.writeUtf(animId);
    }
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            assert instance.level != null;
            AbstractClientPlayer player = (AbstractClientPlayer) instance.level.getEntity(playerId);
            AnimationManager.playAnimation(player, animId);
        });
    }
}
