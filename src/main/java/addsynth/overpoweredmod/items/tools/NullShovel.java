package addsynth.overpoweredmod.items.tools;

import addsynth.core.items.ToolConstants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;

public class NullShovel extends ShovelItem {

  public NullShovel(final String name){
    super(OverpoweredTiers.VOID, ToolConstants.shovel_damage, ToolConstants.shovel_speed, new Item.Properties().tab(CreativeTabs.tools_creative_tab));
    OverpoweredTechnology.registry.register_item(this, name);
  }

  @Override
  public boolean canBeDepleted(){
    return false;
  }

  @Override
  public boolean isFoil(ItemStack stack){
      return true;
  }
  
  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.EPIC;
  }

}
