package honeyedlemons.kinder.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractVaryingGemEntity extends AbstractGemEntity {
    public AbstractVaryingGemEntity(EntityType<? extends AbstractGemEntity> entityType, Level world) {
        super(entityType, world);
    }

    public abstract boolean UsesUniqueNames();

    public abstract int[] neglectedColors();

    public boolean isValid(int color) {
        if (this.neglectedColors() != null) {
            for (int neglectedColor : this.neglectedColors()) {
                if (neglectedColor == color) {
                    return false;
                }
            }
        }
        return true;
    }

    public int generateGemColorVariant() {
        int color;
        do {
            color = this.random.nextInt(16);
        }
        while (!this.isValid(color));
        return color;
    }
}
