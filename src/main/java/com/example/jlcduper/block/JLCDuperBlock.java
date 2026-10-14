package com.example.jlcduper.block;

import com.example.jlcduper.JLCModBlockEntities;
import com.example.jlcduper.JLCModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class JLCDuperBlock extends BaseEntityBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public JLCDuperBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(LIT, false);
	}

	@Override
	protected MapCodec<? extends JLCDuperBlock> codec() {
		return simpleCodec(JLCDuperBlock::new);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new JLCDuperBlockEntity(blockPos, blockState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, JLCModBlockEntities.JLC_DUPER, JLCDuperBlockEntity::tick);
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		if (!level.isClientSide()) {
			level.playSound(null, pos, JLCModSounds.JLC_DUPER_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof JLCDuperBlockEntity duper) {
				player.openMenu(duper);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved) {
		super.affectNeighborsAfterRemoval(state, level, pos, moved);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof JLCDuperBlockEntity duper) {
			Containers.dropContents(level, pos, duper);
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
}