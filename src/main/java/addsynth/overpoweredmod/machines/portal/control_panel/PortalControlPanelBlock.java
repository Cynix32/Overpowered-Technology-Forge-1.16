package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class PortalControlPanelBlock extends MachineBlock {

  public static final DirectionProperty FACING = HorizontalBlock.FACING;

  public PortalControlPanelBlock(final String name){
    super(MaterialColor.SNOW);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new TranslationTextComponent("gui.addsynth_energy.tooltip.class_3_machine"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TilePortalControlPanel();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isClientSide == false){
      final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(pos, world, TilePortalControlPanel.class);
      if(tile != null){
        tile.check_portal(player.isCreative());
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context){
    return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder){
    builder.add(FACING);
  }

}
