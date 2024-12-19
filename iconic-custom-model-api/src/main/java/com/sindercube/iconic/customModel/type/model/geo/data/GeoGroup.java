package com.sindercube.iconic.customModel.type.model.geo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.customModel.util.Vec3r;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record GeoGroup (
	String name, Optional<String> parentName, List<GeoCube> cubes,
	Vec3d pivot, Vec3r rotation, boolean visible
) {

    public static final Codec<GeoGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(GeoGroup::name),
            Codec.STRING.optionalFieldOf("parent").forGetter(GeoGroup::parentName),
            GeoCube.CODEC.listOf().optionalFieldOf("cubes", List.of()).forGetter(GeoGroup::cubes),
            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(GeoGroup::pivot),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(GeoGroup::rotation),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(GeoGroup::visible)
    ).apply(instance, GeoGroup::new));

    public ModelPartData toModelData(ModelPartData root, Vec3d parentPivot) {
        ModelPartBuilder part = ModelPartBuilder.create();

        ArrayList<GeoCube> simpleCubes = new ArrayList<>();
        ArrayList<GeoCube> complexCubes = new ArrayList<>();
		for (GeoCube cube : cubes) {
			if (!cube.visible()) continue;
			if (cube.isSimple()) simpleCubes.add(cube);
			else complexCubes.add(cube);
		}

        for (GeoCube cube : simpleCubes) {
            part = cube.addToBuilder(part, pivot);
        }

        Vec3d pivot = this.pivot;
        if (parentName.isPresent()) pivot = pivot.subtract(parentPivot);
        Vec3d rotation = this.rotation;

        ModelTransform transform = ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );

        ModelPartData data = root.addChild(name, part, transform);

        var i = 0;
        for (GeoCube cube : complexCubes) {
            i++;
            ModelPartBuilder uniquePart = ModelPartBuilder.create();
            uniquePart = cube.addToBuilder(uniquePart, pivot);
            ModelTransform uniqueTransform = cube.getTransformation();
            data.addChild(name+i, uniquePart, uniqueTransform);
        }

        return data;
    }

}
