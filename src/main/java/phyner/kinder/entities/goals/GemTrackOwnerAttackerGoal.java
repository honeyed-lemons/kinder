/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package phyner.kinder.entities.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import phyner.kinder.entities.AbstractGemEntity;

import java.util.EnumSet;

public class GemTrackOwnerAttackerGoal
        extends TrackTargetGoal {

    public GemTrackOwnerAttackerGoal(AbstractGemEntity tamable,int lastAttackedTime) {
        super(tamable, false);
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart(){
        return false;
    }
}

