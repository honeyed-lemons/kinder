package phyner.kinder.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.blocks.entities.OysterBlockEntity;

public class IncubatorBlock extends BlockWithEntity {
    public IncubatorBlock(Settings settings){
        super(settings);
        setDefaultState(getDefaultState());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world,BlockState state,BlockEntityType<T> type){
        return super.getTicker(world,
                state,
                type);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos,BlockState state){
        return new IncubatorBlockEntity(pos,
                state);
    }

    @Override
    public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit){
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
        }
        return super.onUse(state,world,pos,player,hand,hit);
    }

    public void neighborUpdate(BlockState state,World world,BlockPos pos,Block sourceBlock,BlockPos sourcePos,boolean notify){
        if (!world.isClient) {
            if (world.isReceivingRedstonePower(pos)) {
                BlockEntity be = world.getBlockEntity(pos);
                if (be instanceof IncubatorBlockEntity) {
                    ((IncubatorBlockEntity) be).help(world,
                            pos);
                }
            }
        }
    }
}
