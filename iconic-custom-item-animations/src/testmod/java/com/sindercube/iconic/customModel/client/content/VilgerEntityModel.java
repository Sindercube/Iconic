package com.sindercube.iconic.customModel.client.content;

import com.sindercube.iconic.customModel.api.content.entity.model.LoadedEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VilgerEntityModel extends EntityModel<VillagerEntityRenderState> {

    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    protected final ModelPart nose;

    public VilgerEntityModel(ModelPart root) {
		super(root);
        this.head = root.getChild("head");
        this.nose = this.head.getChild("nose");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

	@Override
	public void setAngles(VillagerEntityRenderState state) {
		super.setAngles(state);
		this.head.yaw = state.yawDegrees * 0.017453292F;
		this.head.pitch = state.pitch * 0.017453292F;
		if (state.headRolling) {
			this.head.roll = 0.3F * MathHelper.sin(0.45F * state.age);
			this.head.pitch = 0.4F;
		} else {
			this.head.roll = 0.0F;
		}

		this.rightLeg.pitch = MathHelper.cos(state.limbFrequency * 0.6662F) * 1.4F * state.limbAmplitudeMultiplier * 0.5F;
		this.leftLeg.pitch = MathHelper.cos(state.limbFrequency * 0.6662F + 3.1415927F) * 1.4F * state.limbAmplitudeMultiplier * 0.5F;
		this.rightLeg.yaw = 0.0F;
		this.leftLeg.yaw = 0.0F;
	}

}
