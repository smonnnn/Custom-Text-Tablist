package me.simon.mixins;

import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerListHeaderS2CPacket.class)
public interface PlayerListMixin extends Packet<ClientPlayPacketListener> {
    @Accessor
    void setFooter(Text footer);

    @Accessor
    void setHeader(Text header);
}
