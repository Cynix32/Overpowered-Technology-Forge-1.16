package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(final String name){
    super(Block.Properties.of(Material.METAL, MaterialColor.WOOL).strength(3.5f, 300.0f));
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
  }

}
