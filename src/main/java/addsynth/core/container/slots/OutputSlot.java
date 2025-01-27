package addsynth.core.container.slots;

import javax.annotation.Nonnull;
import addsynth.core.inventory.IOutputInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.hooks.BasicEventHooks;
import net.minecraftforge.items.SlotItemHandler;

public final class OutputSlot extends SlotItemHandler {

  public OutputSlot(IOutputInventory tile, int index, int xPosition, int yPosition) {
    super(tile.getOutputInventory(), index, xPosition, yPosition);
  }

  @Override
  public final boolean mayPlace(@Nonnull final ItemStack stack){
    return false;
  }

  // NOTE: warning. it's possible this is run on client and server!
  @Override
  public final ItemStack onTake(final PlayerEntity player, final ItemStack stack){
    BasicEventHooks.firePlayerCraftingEvent(player, stack, new Inventory(0));
    setChanged();
    return stack;
  }

}
