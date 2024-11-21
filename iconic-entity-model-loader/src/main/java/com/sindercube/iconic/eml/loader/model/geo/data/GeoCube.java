package com.sindercube.iconic.eml.loader.model.geo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.eml.utils.Vec2i;
import com.sindercube.iconic.eml.utils.Vec3r;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

public record GeoCube (
	Vec3d origin, Vec3d size, Vec2i uvOffset, float scale, boolean mirror,
	Vec3d pivot, Vec3r rotation, boolean visible
) {

    public static final Codec<GeoCube> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3d.CODEC.fieldOf("origin").forGetter(GeoCube::origin),
            Vec3d.CODEC.fieldOf("size").forGetter(GeoCube::size),
            Vec2i.CODEC.optionalFieldOf("uv", Vec2i.ZERO).forGetter(GeoCube::uvOffset),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(GeoCube::scale),
            Codec.BOOL.optionalFieldOf("mirror", false).forGetter(GeoCube::mirror),
            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(GeoCube::pivot),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(GeoCube::rotation),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(GeoCube::visible)
    ).apply(instance, GeoCube::new));

    public boolean isSimple() {
        return sumVec(this.pivot) + sumVec(this.rotation) == 0;
    }

    public static double sumVec(Vec3d vec) {
        return vec.x + vec.y + vec.z;
    }

    public ModelPartBuilder addToBuilder(ModelPartBuilder builder, Vec3d parentPivot) {
        Vec3d offset = this.origin;
        Vec3d size = this.size;

        offset = offset.multiply(1, -1, 1);
        offset = offset.add(
                -size.x + size.x,
                -size.y + 24,
                0
        );
        offset = offset.subtract(parentPivot);

        return builder.uv(uvOffset.x, uvOffset.y).cuboid(
                        (float)offset.x, (float)offset.y, (float)offset.z,
                        (float)size.x, (float)size.y, (float)size.z, new Dilation(scale)
        );
    }

    public ModelTransform getTransformation() {
        return ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );
    }

}
