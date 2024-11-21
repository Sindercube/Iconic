package com.sindercube.iconic.eml.loader.model.geo;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.iconic.eml.loader.model.geo.data.GeoGroup;
import com.sindercube.iconic.eml.loader.model.geo.data.GeoTexture;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record GeoModelData(
    List<GeoGroup> groups,
    GeoTexture texture
) {

    public static final Codec<GeoModelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeoGroup.CODEC.listOf().optionalFieldOf("bones", List.of()).forGetter(GeoModelData::groups),
            GeoTexture.CODEC.fieldOf("description").forGetter(GeoModelData::texture)
    ).apply(instance, GeoModelData::new));

    public TexturedModelData toModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        Map<String, GroupData> cachedGroupData = new HashMap<>();
        for (GeoGroup group : this.groups) {
            Optional<String> parentName = group.parentName();
            ModelPartData groupData;
            if (parentName.isPresent() && cachedGroupData.containsKey(parentName.get())) {
                GroupData parentData = cachedGroupData.get(parentName.get());
                groupData = group.toModelData(parentData.modelData, parentData.groupData.pivot());
            } else {
                groupData = group.toModelData(root, Vec3d.ZERO);
            }
            cachedGroupData.put(group.name(), new GroupData(groupData, group));
        }

        return TexturedModelData.of(data, this.texture.width(), this.texture.height());
    }

    record GroupData(ModelPartData modelData, GeoGroup groupData) {}

}
