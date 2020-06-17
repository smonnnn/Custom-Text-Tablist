package me.simon.mixins;

import me.simon.Main;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.*;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TranslatableText.class)
public abstract class TranslatableTextMixin  {//removed BaseText - again this causes a crash
    protected TranslatableTextMixin(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }



    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("RETURN"))//Without "(Ljava/lang/String;[Ljava/lang/Object;)V" the void would not be able to use all the variables
    public void TranslatableText(String key, Object[] args, CallbackInfo ci) {
        this.key = key;
        this.args = args;

        if(Main.settings.enableColor && !key.startsWith("chat.type.advancement")) {
            for (int i = 0; i < args.length; ++i) {
                Object object = args[i];
                if (object instanceof String) {
                    this.args[i] = new LiteralText((String) object);
                } else if (object instanceof Text) {
                    Text text = ((Text) object).copy();
                    this.args[i] = text;
                    //text.getStyle().setParent(this.getStyle()); //I just removed this. It's seemed useless, I might be wrong about that, but that being said removing it allows the mod to compile and run
                } else if (object == null) {
                       this.args[i] = "null";
                }
            }
        }
    }
    @Mutable
    @Final
    @Shadow
    private String key;
    @Mutable
    @Final
    @Shadow
    private Object[] args;
}
