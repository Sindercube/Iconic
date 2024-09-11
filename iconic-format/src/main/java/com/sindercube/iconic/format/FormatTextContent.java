package com.sindercube.iconic.format;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.TextContent;
import org.jetbrains.annotations.Nullable;

public record FormatTextContent (
	Text format
) implements TextContent {

	public static final MapCodec<FormatTextContent> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		TextCodecs.CODEC.fieldOf("path").forGetter(FormatTextContent::format)
	).apply(instance, FormatTextContent::new));

	public static final Type<FormatTextContent> TYPE = new Type<>(CODEC, "format");


	@Override
	public Type<?> getType() {
		return TYPE;
	}


	@Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) {
        return null;
    }

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof FormatTextContent content)) return false;
		return this.format.equals(content.format);
	}

	@Override
	public int hashCode() {
		return format.hashCode();
	}

	@Override
	public String toString() {
		return String.format("format{format='%s'}", format);
	}

}
