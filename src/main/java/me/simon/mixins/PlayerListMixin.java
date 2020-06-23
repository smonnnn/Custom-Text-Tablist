package me.simon.mixins;

import me.simon.Main;
import me.simon.commands.util.TextFormatter;
import me.simon.config.Config;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHeaderS2CPacket.class)
public class PlayerListMixin {
    @Shadow private Text footer;

    @Shadow private Text header;

    @Inject(at= @At("RETURN"), method = "<init>")
    public void PlayerListHeaderS2CPacket(CallbackInfo ci) {
        this.footer = new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.footer));
        this.header = new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.header));
    }
}
