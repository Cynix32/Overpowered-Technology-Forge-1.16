package addsynth.overpoweredmod.machines.fusion.chamber;

import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.tiles.TileStorageMachine;
import addsynth.core.util.data.AdvancementUtil;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;

public final class TileFusionChamber extends TileStorageMachine implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[]{ModItems.fusion_core};

  /** A standard TNT explosion is size of 4. */
  private static final float FUSION_CHAMBER_EXPLOSION_SIZE = 10.0f;

  public static final byte container_radius = 5;
  private boolean on;

  public TileFusionChamber(){
    super(Tiles.FUSION_CHAMBER, new SlotData[]{new SlotData(input_filter,1)});
  }

  public final boolean has_fusion_core(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){
      return false;
    }
    return stack.getCount() > 0;
  }

  public final boolean is_on(){
    return on;
  }

  public final void set_state(final boolean state, final ServerPlayerEntity player){
    if(on != state){ // Only run on state change
      int i;
      BlockPos position;
      for(Direction side: Direction.values()){
        for(i = 1; i < container_radius - 1; i++){
          position = worldPosition.relative(side, i);
          if(state){
            // TODO: DO NOT INSERT a Laser Effect block in the world! Replace this with some sort of
            //       OpenGL special effects that doesn't touch the world and immune to player interference.
            level.setBlock(position, Machines.fusion_control_laser_beam.defaultBlockState(), 3);
            // TEST why would we need block updates for this? Can this just be set to 2 for Client updates?
            if(player != null){
              AdvancementUtil.grantAdvancement(player, CustomAdvancements.FUSION_ENERGY);
            }
          }
          else{
            level.removeBlock(position, false);
          }
        }
      }
      on = state;
    }
  }

  public final void explode(){
    set_state(false, null);
    level.removeBlock(worldPosition, false);
    level.explode(null, worldPosition.getX()+0.5, worldPosition.getY()+0.5, worldPosition.getZ()+0.5, FUSION_CHAMBER_EXPLOSION_SIZE, true, Explosion.Mode.DESTROY);
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerFusionChamber(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

}
