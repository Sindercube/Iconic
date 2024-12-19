package com.sindercube.iconic.mixin;

import com.sindercube.iconic.event.ExtraClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Inject(method = "onFinishedLoading", at = @At("RETURN"))
	public void afterResourceReload(@Coerce Object c, CallbackInfo ci) {
		MinecraftClient client = (MinecraftClient)(Object)this;
		ExtraClientLifecycleEvents.AFTER_RESOURCE_RELOAD.invoker().afterResourcesReloaded(client);
	}

}
