package phyner.kinder.entities;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public final class GemDefaultAnimations {
    public static final RawAnimation LEGS_WALK = RawAnimation.begin ().thenLoop ("legs.walk");
    public static final RawAnimation ARMS_WALK = RawAnimation.begin ().thenLoop ("arms.walk");
    public static final RawAnimation ARMS_USE = RawAnimation.begin ().thenPlay ("arms.use");


    public static <T extends GeoAnimatable> AnimationController<T> genericGemWalkLegsController (T animatable){
        return new AnimationController<> (animatable, "LegWalk", 1, state -> {
            if (state.isMoving ()) {
                return state.setAndContinue (LEGS_WALK);
            } else {
                return PlayState.STOP;
            }
        });
    }

    public static <T extends GeoAnimatable> AnimationController<T> genericGemWalkArmsController (T animatable){
        return new AnimationController<> (animatable, "ArmWalk", 2, state -> {
            if (state.isMoving ()) {
                return state.setAndContinue (ARMS_WALK);
            } else {
                return PlayState.STOP;
            }
        });
    }
}
