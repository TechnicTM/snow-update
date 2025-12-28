package net.technic.snow_update.entity.client.yeti.juvenile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.technic.snow_update.entity.JuvenileYetiEntity;

@SuppressWarnings("unused")
public class JuvenileYetiModel<T extends JuvenileYetiEntity> extends HierarchicalModel<T> {

	
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart head;

	public JuvenileYetiModel(ModelPart root) {

		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.body = root.getChild("body");
		this.head = root.getChild("body").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(79, 23).mirror().addBox(-3.0F, -4.0F, -4.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(79, 23).addBox(-4.0F, -4.0F, -4.0F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 12.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 58).addBox(-10.0F, -18.0F, -6.0F, 20.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition sack_ropeholder_r1 = body.addOrReplaceChild("sack_ropeholder_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, 1.0F, -20.8F, 20.0F, 5.0F, 23.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(10.0F, -12.0F, 18.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition sack = body.addOrReplaceChild("sack", CubeListBuilder.create().texOffs(0, 28).addBox(-10.0F, -6.0F, 0.0F, 20.0F, 16.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(94, 93).addBox(-5.0F, -12.0F, 4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(94, 93).addBox(-8.0F, -9.0F, 3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(94, 93).addBox(0.0F, -10.0F, 3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 4.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(60, 74).mirror().addBox(0.0F, 0.0F, -3.0F, 7.0F, 25.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(26, 0).mirror().addBox(0.0F, 0.0F, -3.0F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(10.0F, -18.0F, -3.0F));

		//PartDefinition snowball = left_arm.addOrReplaceChild("snowball", CubeListBuilder.create().texOffs(94, 109).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 22.0F, -6.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(60, 74).addBox(-7.0F, 0.0F, -4.0F, 7.0F, 25.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-7.0F, 0.0F, -4.0F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(-10.0F, -18.0F, -2.0F));

		//PartDefinition snowball2 = right_arm.addOrReplaceChild("snowball2", CubeListBuilder.create().texOffs(94, 109).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 22.0F, -7.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(26, 105).addBox(-6.0F, -11.8F, -7.0F, 12.0F, 13.0F, 10.0F, new CubeDeformation(0.5F))
		.texOffs(1, 1).addBox(-2.5F, -4.0F, -11.0F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(60, 51).addBox(-6.0F, -12.0F, -7.0F, 12.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -4.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 102).mirror().addBox(-8.0F, -3.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(26, 102).addBox(6.0F, -3.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -6.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition lefteyebrow = head.addOrReplaceChild("lefteyebrow", CubeListBuilder.create().texOffs(118, 9).addBox(1.0F, -2.5F, -0.2F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.0F));

		PartDefinition righteyebrow = head.addOrReplaceChild("righteyebrow", CubeListBuilder.create().texOffs(118, 9).mirror().addBox(-6.0F, -2.5F, -0.2F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -3.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(JuvenileYetiEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		//this.animateWalk(JuvenileYetiAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		this.animate(entity.attackAnimationState, JuvenileYetiAnimations.THROW2, ageInTicks);
		//this.animate(entity.idleAnimationState, JuvenileYetiAnimations.IDLE, ageInTicks, 1.0F);
		this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
		this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * -limbSwingAmount;
	}

	private void applyHeadRotation(JuvenileYetiEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);


	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}

}