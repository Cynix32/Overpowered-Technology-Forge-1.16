package addsynth.overpoweredmod.machines.data_cable;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.tiles.TileBase;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;

public final class TileDataCable extends TileBase implements IBlockNetworkUser<DataCableNetwork>, ITickableTileEntity {

  private DataCableNetwork cable_network;

  public TileDataCable(){
    super(Tiles.DATA_CABLE);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      if(cable_network == null){
        BlockNetworkUtil.create_or_join(level, this, DataCableNetwork::new);
      }
    }
  }

  @Override
  public final void setRemoved(){
    super.setRemoved();
    BlockNetworkUtil.tileentity_was_removed(this, DataCableNetwork::new);
  }

  @Override
  public final void setBlockNetwork(final DataCableNetwork network){
    this.cable_network = network;
  }

  @Override
  @Nullable
  public final DataCableNetwork getBlockNetwork(){
    return cable_network;
  }

}
