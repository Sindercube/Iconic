package com.sindercube.iconic.textContent.mixin;

import com.sindercube.iconic.textContent.TextContentRegistry;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.TextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(TextCodecs.class)
public class TextCodecsMixin {

	@ModifyVariable(method = "createCodec", index = 1, at = @At("STORE"))
	private static TextContent.Type<?>[] addCustomTextContent(TextContent.Type<?>[] types) {
		List<TextContent.Type<?>> contents = new ArrayList<>(List.of(types));
		TextContentRegistry.REGISTRY.stream().forEach(contents::add);
		return contents.toArray(TextContent.Type<?>[]::new);
	}

}
