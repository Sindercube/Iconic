package com.sindercube.iconic.constructs.testmod.client.content;

import com.sindercube.iconic.constructs.testmod.content.VilgerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VilgerEntityModel extends LoadedEntityModel<VilgerEntity> {

    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    protected final ModelPart nose;

    public VilgerEntityModel(ModelPart root, Identifier id) {
		super(root, id);
        this.head = root.getChild("head");
        this.nose = this.head.getChild("nose");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    @Override
    public void setAngles(VilgerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
        this.head.roll = 0.0F;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;
    }

	public static TexturedModelData getModelData(Identifier id) {
		return EmlModelManager.getModel(id);
	}

}
