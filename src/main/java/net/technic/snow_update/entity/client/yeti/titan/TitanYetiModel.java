package net.technic.snow_update.entity.client.yeti.titan;

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
import net.technic.snow_update.entity.TitanYetiEntity;

@SuppressWarnings("unused")
public class TitanYetiModel<T extends TitanYetiEntity> extends HierarchicalModel<T> {
	
	
	private final ModelPart bone2;
	private final ModelPart Head2;

	public TitanYetiModel(ModelPart root) {
		this.bone2 = root.getChild("bone2");
		this.Head2 = bone2.getChild("body2").getChild("Head2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body2 = bone2.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, -28.0F, -2.0F));

		PartDefinition bone3 = body2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(2, 176).addBox(-22.0F, -55.0F, 1.5F, 21.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 132).addBox(-15.0F, -49.0F, -5.0F, 33.0F, 18.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 31.0F, 0.0F));

		PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-17.5F, -7.5F, -11.0F, 35.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -30.5F, 4.2F, -0.7854F, 0.0F, 0.0F));

		PartDefinition Head2 = body2.addOrReplaceChild("Head2", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, -5.0F));

		PartDefinition bone = Head2.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(73, 62).addBox(-8.0F, -3.0F, -17.0F, 19.0F, 18.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(0, 81).addBox(-8.0F, -3.0F, -17.0F, 19.0F, 23.0F, 18.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone_r1 = bone.addOrReplaceChild("bone_r1", CubeListBuilder.create().texOffs(165, 39).addBox(11.0F, -27.0F, -66.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(165, 39).mirror().addBox(-11.0F, -27.0F, -66.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 61.0F, 10.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 61.0F, 29.0F));

		PartDefinition the_rock = bone.addOrReplaceChild("the_rock", CubeListBuilder.create(), PartPose.offset(2.0F, 7.0F, -16.0F));

		PartDefinition Right_Eyebrow = the_rock.addOrReplaceChild("Right_Eyebrow", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -1.0F));

		PartDefinition Right_Eyebrow2 = Right_Eyebrow.addOrReplaceChild("Right_Eyebrow2", CubeListBuilder.create(), PartPose.offset(-2.0F, 3.0F, 0.0F));

		PartDefinition Right_Eyebrow2_r1 = Right_Eyebrow2.addOrReplaceChild("Right_Eyebrow2_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-9.0F, -7.8F, -2.1F, 11.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0349F));

		PartDefinition Left_Eyebrow = the_rock.addOrReplaceChild("Left_Eyebrow", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -1.0F));

		PartDefinition Left_Eyebrow2 = Left_Eyebrow.addOrReplaceChild("Left_Eyebrow2", CubeListBuilder.create(), PartPose.offset(2.0F, 3.0F, 0.0F));

		PartDefinition Left_Eyebrow2_r1 = Left_Eyebrow2.addOrReplaceChild("Left_Eyebrow2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -6.8F, -1.1F, 11.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.0349F));

		PartDefinition Beard = bone.addOrReplaceChild("Beard", CubeListBuilder.create(), PartPose.offset(-0.6F, 15.0F, -8.5F));

		PartDefinition cube_r1 = Beard.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(77, 30).addBox(-21.0F, -2.0F, -11.2499F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 21.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition nose3 = bone.addOrReplaceChild("nose3", CubeListBuilder.create().texOffs(113, 41).addBox(-4.0F, -3.0F, -9.0F, 8.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 11.0F, -13.0F));

		PartDefinition ring = bone.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(95, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, -7.0F));

		PartDefinition R_Arm1 = body2.addOrReplaceChild("R_Arm1", CubeListBuilder.create().texOffs(157, 66).addBox(-13.0F, -6.0F, -6.0F, 13.0F, 56.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(204, 7).addBox(-13.3F, -6.0F, -6.0F, 13.0F, 56.0F, 13.0F, new CubeDeformation(0.5F)), PartPose.offset(-16.0F, -32.0F, 1.0F));

		PartDefinition R_Arm2 = body2.addOrReplaceChild("R_Arm2", CubeListBuilder.create().texOffs(157, 66).mirror().addBox(0.0F, -6.0F, -6.0F, 13.0F, 56.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(204, 7).mirror().addBox(0.3F, -6.0F, -6.0F, 13.0F, 56.0F, 13.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(19.0F, -32.0F, 1.0F));

		PartDefinition leg2 = bone2.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 122).mirror().addBox(-5.0F, -1.0F, -5.0F, 12.0F, 29.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -28.0F, 1.0F));

		PartDefinition leg1 = bone2.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 122).addBox(-7.0F, -1.0F, -5.0F, 12.0F, 29.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -28.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(TitanYetiEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(TitanYetiAnimations.WALK, limbSwing, limbSwingAmount, 6F, 3F);
		this.animate(entity.attackAnimationState, TitanYetiAnimations.SLAM, ageInTicks, 1.0F);
		this.animate(entity.idleAnimationState, TitanYetiAnimations.IDLE, ageInTicks, 1.0F);
		this.animate(entity.chargeStartAnimationState, TitanYetiAnimations.CHARGESTART, ageInTicks, 1.0F);
		this.animate(entity.chargeAnimationState, TitanYetiAnimations.CHARGE, ageInTicks, 1.0F);
		this.animate(entity.stunAnimationState, TitanYetiAnimations.STUN, ageInTicks, 1.0F);
		this.animate(entity.stunRecoveryAnimationState, TitanYetiAnimations.STUNEND, ageInTicks, 1.0F);
		this.animate(entity.chargeCrashAnimationState, TitanYetiAnimations.CHARGECRASH , ageInTicks, 1.0F);
		this.animate(entity.meleeAttackAnimationState, TitanYetiAnimations.MELEE2, ageInTicks, 1.0F);
		this.animate(entity.frozenAnimationState, TitanYetiAnimations.FROZEN, ageInTicks, 1.0F);
	}

	private void applyHeadRotation(TitanYetiEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head2.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head2.xRot = pHeadPitch * ((float)Math.PI / 180F);


	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bone2;
	}

	
}