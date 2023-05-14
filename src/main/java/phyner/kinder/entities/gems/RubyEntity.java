package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.init.KinderItems;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes(){
        return createDefaultGemAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH,
                GemAttributeHealth()).add(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                GemAttributeSpeed()).add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                GemAttributeDamage());
    }

    static Double GemAttributeHealth(){
        return 25.0;
    }

    static Double GemAttributeSpeed(){
        return 0.5;
    }

    static Double GemAttributeDamage(){
        return 3.0;
    }
    @Override
    public boolean IsSoliderGem() {
        return true;
    }

    @Override
    public ItemStack gemItem() {
        return new ItemStack(KinderItems.RUBY_GEM);
    }
}
