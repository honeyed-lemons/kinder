package phyner.kinder.blocks.cropblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

abstract public class GemCropBlock extends CropBlock {
    public GemCropBlock(Settings settings){
        super(settings);
    }
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);

    @Override
    public VoxelShape getOutlineShape(BlockState state,BlockView world,BlockPos pos,ShapeContext context){
        return SHAPE;
    }
    abstract public ItemConvertible getSeedsItem();

}
