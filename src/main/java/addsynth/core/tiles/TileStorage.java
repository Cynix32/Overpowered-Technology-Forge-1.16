package addsynth.core.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.CommonInventory;
import addsynth.core.inventory.IStorageInventory;
import addsynth.core.inventory.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is a TileEntity that has a single storage inventory.
 *  It has no Item filter and machines can insert and extract items. */
public abstract class TileStorage extends TileBase implements IStorageInventory {

  protected final CommonInventory inventory;

  public TileStorage(final TileEntityType type, final int number_of_slots){
    super(type);
    this.inventory = CommonInventory.create(this, number_of_slots);
  }

  @Override
  public void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    if(inventory != null){inventory.load(nbt);}
  }

  @Override
  public CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    if(inventory != null){inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory);
      }
      return super.getCapability(capability, direction);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    update_data();
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, inventory);
  }

  @Override
  public CommonInventory getInventory(){
    return inventory;
  }

}
