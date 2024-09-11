package com.sindercube.iconic.splash.mixin;

import com.sindercube.iconic.splashText.util.IconicSplashTextResourceSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Final private Session session;

    @Inject(method = "getSplashTextLoader", at = @At("RETURN"), cancellable = true)
    public void getSplashTextLoader(CallbackInfoReturnable<SplashTextResourceSupplier> cir) {
        cir.setReturnValue(new IconicSplashTextResourceSupplier(this.session));
    }

}
