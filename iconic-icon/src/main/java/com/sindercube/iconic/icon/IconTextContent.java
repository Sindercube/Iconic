package com.sindercube.iconic.icon;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record IconTextContent (
	Identifier icon
) implements TextContent {

	public static final MapCodec<IconTextContent> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Identifier.CODEC.fieldOf("icon").forGetter(IconTextContent::icon)
	).apply(instance, IconTextContent::new));

	public static final Type<IconTextContent> TYPE = new Type<>(CODEC, "icon");

	@Override
	public Type<?> getType() {
		return TYPE;
	}


    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) {
        int index = icon.hashCode();
        String rawText = String.valueOf((char)index);
        Style iconStyle = Style.EMPTY.withFont(icon.withPrefixedPath("icons/"));
        return Text.literal(rawText).setStyle(iconStyle);
    }

	@Override
	public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof IconTextContent content)) return false;
		return this.icon.equals(content.icon);
    }

	@Override
    public int hashCode() {
        return icon.hashCode();
    }

	@Override
    public String toString() {
        return String.format("icon{path='%s'}", icon);
    }

}
