package me.simon.mixins.colorchat;

import me.simon.Main;
import me.simon.commands.util.TextFormatter;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiteralText.class)
public abstract class LiteralTextMixin extends BaseText {
    @Mutable
    @Final
    @Shadow
    private String string;

    private static final TextFormatter formatter = new TextFormatter();

    @Inject(method="<init>", at = @At("RETURN"))
    public void LiteralText(String string, CallbackInfo ci) {
        if(Main.settings.enableColor) {
            this.string = formatter.formatString(string);
        }
        else{
            this.string = string;
        }
    }
}
