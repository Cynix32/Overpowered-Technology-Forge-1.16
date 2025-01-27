package addsynth.energy.lib.tiles.energy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** Standard Generators are generators that have an Input inventory and
 *  accept fuel items to be consumed to produce Energy. 
 * @author ADDSynth
 */
public abstract class TileStandardGenerator extends TileAbstractGenerator implements IInputInventory {

  protected final InputInventory input_inventory;

  public TileStandardGenerator(final TileEntityType type, final Item[] filter){
    super(type);
    this.input_inventory = InputInventory.create(this, 1, filter);
  }

  @Override
  public void tick(){
    if(onServerSide()){
      try{
        if(energy.isEmpty()){
          if(input_inventory.isEmpty() == false){
            setGeneratorData();
            changed = true;
          }
        }
        if(energy.tick()){
          changed = true;
        }
        if(changed){
          update_data();
          changed = false;
        }
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  @Override
  public void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    if(input_inventory != null){ input_inventory.load(nbt);}
  }

  @Override
  public CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    if(input_inventory != null){ input_inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(input_inventory, null, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public final void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, input_inventory);
  }

  @Override
  public final InputInventory getInputInventory(){
    return input_inventory;
  }

}
