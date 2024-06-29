package honeyedlemons.kinder.fluids;

import honeyedlemons.kinder.init.KinderFluidHandling;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class BlueEssenceFluid extends EssenceFluid {
    @Override
    public Fluid getFlowing() {
        return KinderFluidHandling.FLOWING_BLUE_ESSENCE;
    }

    @Override
    public Fluid getSource() {
        return KinderFluidHandling.STILL_BLUE_ESSENCE;
    }

    @Override
    public Item getBucket() {
        return KinderItems.BLUE_ESSENCE_BUCKET;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return KinderFluidHandling.BLUE_ESSENCE_BLOCK.defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(state));
    }

    public static class Flowing extends BlueEssenceFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends BlueEssenceFluid {
        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }
}
