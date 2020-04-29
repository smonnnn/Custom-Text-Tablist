package me.simon.mixins;

import me.simon.Main;
import me.simon.commands.util.TextFormatter;
import me.simon.commands.util.TickCounter;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiteralText.class)
public abstract class LiteralTextMixin {

    @Mutable
    @Final
    @Shadow
    public String string;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void LiteralText(String string, CallbackInfo ci) {

        if (Main.settings.enableColor) {
            this.string = Main.TF.formatString(string);
        } else {
            this.string = string;
        }
    }


}