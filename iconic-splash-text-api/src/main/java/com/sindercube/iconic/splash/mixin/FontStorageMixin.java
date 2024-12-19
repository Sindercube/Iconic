package com.sindercube.iconic.splash.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.font.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

@Mixin(FontStorage.class)
public abstract class FontStorageMixin {

	@Shadow @Final private static Random RANDOM;
	@Shadow private GlyphRenderer blankGlyphRenderer;
	@Shadow @Final private Int2ObjectMap<IntList> charactersByWidth;

	@Shadow
	public abstract GlyphRenderer getGlyphRenderer(int codePoint);

	/**
	 * @author Sindercube
	 * @reason 1.13 obfuscated text rendering
	 */
//	@Overwrite
//	public GlyphRenderer getObfuscatedGlyphRenderer(Glyph glyph) {
//		IntList intList = this.charactersByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
//		return intList != null && !intList.isEmpty() ? this.getGlyphRenderer(intList.getInt(RANDOM.nextInt(intList.size()))) : this.blankGlyphRenderer;
//	}



//	@Redirect(
//		method = "applyFilters", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", ordinal = 0))
//	public void test(List<Font.FontFilterPair> allFonts, Set<FontFilterType> activeFilters, CallbackInfoReturnable<List<Font>> cir, @Local(name = "list") LocalRef<List<Font>> list) {
//		list.set(List.of(list.get().getFirst()));
//		System.out.println(list);
//		System.out.println("AAAAAAAAAAAAA");
//	}

}
