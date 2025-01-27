package addsynth.core.container.slots;

import javax.annotation.Nonnull;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.items.ItemUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public final class InputSlot extends SlotItemHandler {

  private Item[] valid_items;

  private static final int default_stack_size = 64;
  private final int max_stack_size;

  public InputSlot(IInputInventory tile, int index, int xPosition, int yPosition){
    this(tile, index, null, default_stack_size, xPosition, yPosition);
  }

  public InputSlot(IInputInventory tile, int index, Item[] valid_items, int xPosition, int yPosition){
    this(tile, index, valid_items, default_stack_size, xPosition, yPosition);
  }

  public InputSlot(IInputInventory tile, int index, int max_stack_size, int xPosition, int yPosition){
    this(tile, index, null, max_stack_size, xPosition, yPosition);
  }

  public InputSlot(IInputInventory tile, int index, Item[] valid_items, int max_stack_size, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
    this.valid_items = valid_items;
    this.max_stack_size = max_stack_size;
  }

  @Override
  public final boolean mayPlace(@Nonnull final ItemStack stack){
    if(valid_items == null){
      return super.mayPlace(stack);
    }
    final Item item = stack.getItem();
    for(Item test_item : valid_items){
      if(item == test_item){
        return super.mayPlace(stack);
      }
    }
    return false;
  }

  public final void setFilter(final Item[] input){
    valid_items = input;
  }

  public final void setFilter(final ItemStack[] input){
    valid_items = ItemUtil.toItemArray(input);
  }

  @Override
  public int getMaxStackSize(){
    return max_stack_size;
  }

}
