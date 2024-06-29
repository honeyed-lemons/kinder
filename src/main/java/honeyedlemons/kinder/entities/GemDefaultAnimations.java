package honeyedlemons.kinder.entities;

import honeyedlemons.kinder.KinderMod;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public final class GemDefaultAnimations {
    public static final RawAnimation LEGS_WALK = RawAnimation.begin().thenLoop("legs.walk");
    public static final RawAnimation ARMS_WALK = RawAnimation.begin().thenLoop("arms.walk");
    public static final RawAnimation ARMS_USE = RawAnimation.begin().thenPlay("arms.use");
    public static final RawAnimation SITTING = RawAnimation.begin().thenPlay("sitting");

    public static final RawAnimation ARMS_IDLE = RawAnimation.begin().thenLoop("arms.idle");
    public static final RawAnimation HEAD_IDLE = RawAnimation.begin().thenLoop("head.idle");

    public static <T extends GeoAnimatable> AnimationController<T> genericGemWalkLegsController(T animatable) {
        return new AnimationController<>(animatable, "LegWalk", 0, state -> {
            if (state.isMoving()) {
                return state.setAndContinue(LEGS_WALK);
            } else {
                return PlayState.STOP;
            }
        });
    }

    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> genericGemSittingController(T animatable) {
        return new AnimationController<>(animatable, "GemSitting", 5, state -> {
            if (animatable.isPassenger()) {
                return state.setAndContinue(SITTING);
            } else {
                return PlayState.STOP;
            }
        });
    }

    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> genericGemArmsController(T animatable) {
        return new AnimationController<>(animatable, "GemArmsIdle", 5, state -> {
            if (!state.isMoving()) {
                return state.setAndContinue(ARMS_IDLE);
            } else {
                return state.setAndContinue(ARMS_WALK);
            }
        });
    }
    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> genericAttackAnimation(T animatable, RawAnimation attackAnimation) {
        return new AnimationController<>(animatable, "Attack", 0, state -> {
            if (animatable.swinging) {
                KinderMod.LOGGER.info("killmyself");
                return state.setAndContinue(attackAnimation);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> genericGemIdleHeadController(T animatable) {
        return new AnimationController<>(animatable, "GemIdleHead", 5, state -> state.setAndContinue(HEAD_IDLE));
    }
}

