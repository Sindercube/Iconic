package com.sindercube.iconic.textContent;

import net.minecraft.text.TextContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextContentRegistry {

	private static final List<TextContent.Type<?>> CONTENTS = new ArrayList<>();


	public static void register(TextContent.Type<?>... contents) {
		CONTENTS.addAll(Arrays.asList(contents));
	}

	public static void addContents(List<TextContent.Type<?>> contents) {
		contents.addAll(CONTENTS);
	}

}
