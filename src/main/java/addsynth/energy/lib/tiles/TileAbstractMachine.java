package addsynth.energy.lib.tiles;

import addsynth.core.tiles.TileBase;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/** All machines that only receive energy to do work derive from this class. */
public abstract class TileAbstractMachine extends TileBase implements ITickableTileEntity, IEnergyConsumer {

  protected final Receiver energy;

  public TileAbstractMachine(final TileEntityType type, final Receiver energy){
    super(type);
    this.energy = energy;
  }

  @Override
  public void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    if(energy != null){ energy.loadFromNBT(nbt);}
  }

  @Override
  public CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    if(energy != null){ energy.saveToNBT(nbt);}
    return nbt;
  }

  @Override
  public Receiver getEnergy(){
    return energy;
  }

}
