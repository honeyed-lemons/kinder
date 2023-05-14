package phyner.kinder.entities;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public final class GemDefaultAnimations {
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");

    public static <T extends GeoAnimatable> AnimationController<T> genericGemWalkController(T animatable) {
        return new AnimationController<T>(animatable, "Walk", 5, state -> {
            if (state.isMoving())
                return state.setAndContinue(WALK);
            return PlayState.STOP;
        });
    }
}
