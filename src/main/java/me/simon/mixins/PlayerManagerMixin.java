package me.simon.mixins;

import io.netty.buffer.ByteBuf;
import me.simon.Main;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow public abstract void sendToAll(Packet<?> packet);

    @Inject(at= @At("HEAD"), method = "updatePlayerLatency")
    public void updatePlayerLatency(CallbackInfo ci) {
        this.sendToAll(new PlayerListHeaderS2CPacket());
    }

    @Inject(at=@At("TAIL"), method = "onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        player.sendChatMessage(new LiteralText(Main.settings.motd), MessageType.CHAT);
    }
}
