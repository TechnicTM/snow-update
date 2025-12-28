package net.technic.snow_update.entity.client.yeti.juvenile;


import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class JuvenileYetiAnimations {

	
	public static final AnimationDefinition THROW1 = AnimationDefinition.Builder.withLength(2.0F)
	.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.625F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0833F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(97.7614F, -14.8687F, -2.0031F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0833F, KeyframeAnimations.degreeVec(104.7565F, -2.7918F, 1.2668F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.375F, KeyframeAnimations.degreeVec(104.7565F, -2.7918F, 1.2668F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5F, KeyframeAnimations.degreeVec(-57.7435F, -2.7918F, 1.2668F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.75F, KeyframeAnimations.degreeVec(-57.7435F, -2.7918F, 1.2668F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.posVec(-1.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5F, KeyframeAnimations.posVec(-1.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5833F, KeyframeAnimations.degreeVec(2.5F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0833F, KeyframeAnimations.degreeVec(2.5F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.3333F, KeyframeAnimations.degreeVec(2.5F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.4644F, 2.507F, -0.3265F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-5.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.375F, KeyframeAnimations.degreeVec(-5.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-4.9284F, 2.4535F, -1.0819F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("snowball", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(0.7073F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(0.7083F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(1.5407F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(1.5417F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.addAnimation("snowball2", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.build();

public static final AnimationDefinition THROW2 = AnimationDefinition.Builder.withLength(2.5F)
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-5.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(-5.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-4.9284F, -2.4535F, 1.0819F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.625F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.1667F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.6667F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("snowball", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-242.2979F, 6.6485F, 3.4787F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-241.9332F, 11.0686F, 5.8448F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-242.4776F, 2.2174F, 1.1549F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(-259.8585F, -2.7658F, 0.0179F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-56.9332F, 11.0686F, 5.8448F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-56.9332F, 11.0686F, 5.8448F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
		new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, -4.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.9583F, KeyframeAnimations.posVec(0.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4583F, KeyframeAnimations.posVec(0.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -3.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5833F, KeyframeAnimations.degreeVec(2.5F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0833F, KeyframeAnimations.degreeVec(2.5F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(2.5F, -10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.875F, KeyframeAnimations.degreeVec(7.4644F, -2.507F, 0.3265F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("snowball2", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(0.999F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(1.7907F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
		new Keyframe(1.7917F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.build();

public static final AnimationDefinition MELEE_ATTACK = AnimationDefinition.Builder.withLength(0.625F)
	.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("sack", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4167F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-115.0838F, -4.5305F, 2.1175F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-37.2524F, 15.7627F, 19.6574F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("snowball", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-115.0838F, 4.5305F, -2.1175F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.375F, KeyframeAnimations.degreeVec(-45.7556F, -12.7F, -15.5794F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("snowball2", new AnimationChannel(AnimationChannel.Targets.SCALE, 
		new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
	))
	.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.addAnimation("nose", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
		new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4167F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
	))
	.build();


}