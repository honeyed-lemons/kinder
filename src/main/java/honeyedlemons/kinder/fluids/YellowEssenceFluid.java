package honeyedlemons.kinder.fluids;

import honeyedlemons.kinder.init.KinderFluidHandling;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class YellowEssenceFluid extends EssenceFluid{
    @Override
    public Fluid getFlowing() {
        return KinderFluidHandling.FLOWING_YELLOW_ESSENCE;
    }

    @Override
    public Fluid getStill() {
        return KinderFluidHandling.STILL_YELLOW_ESSENCE;
    }

    @Override
    public Item getBucketItem() {
        return KinderItems.YELLOW_ESSENCE_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return KinderFluidHandling.YELLOW_ESSENCE_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends YellowEssenceFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends YellowEssenceFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
