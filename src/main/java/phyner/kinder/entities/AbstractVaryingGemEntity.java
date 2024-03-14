package phyner.kinder.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public abstract class AbstractVaryingGemEntity extends AbstractGemEntity {
    public AbstractVaryingGemEntity(EntityType<? extends TameableEntity> entityType,World world){
        super(entityType,world);
    }

    public abstract boolean UsesUniqueNames();

    @Override
    public int generateGemColorVariant(){
        return this.initalGemColorVariant;
    }
}
