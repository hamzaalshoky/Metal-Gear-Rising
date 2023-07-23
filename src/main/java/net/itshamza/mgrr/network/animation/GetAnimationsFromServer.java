package net.itshamza.mgrr.network.animation;

import net.itshamza.mgrr.network.NetworkCore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class GetAnimationsFromServer {
    private final String animId;

    public GetAnimationsFromServer(String animId) {
        this.animId = animId;
    }

    public GetAnimationsFromServer(FriendlyByteBuf buf) {
        this.animId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(animId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> NetworkCore.sendToPlayersNearbyAndSelf(new SendAnimationsToServer(this.animId, Objects.requireNonNull(context.getSender()).getId()), context.getSender()));
    }
}
