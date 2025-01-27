package addsynth.energy.lib.tiles.machines;

import addsynth.energy.lib.config.MachineData;
import addsynth.energy.lib.main.ICustomEnergyUser;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.TileAbstractMachine;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

/** Work Machines are the most commonly used Machine types. Their behaviour is
 *  specifically defined and are well managed.
 * @author ADDSynth
 */
public abstract class TileAbstractWorkMachine extends TileAbstractMachine implements ICustomEnergyUser {

  /** Do not call {@link #update_data()}. Instead set this to true whenever
   *  important data is changed. Check for this in the TileEntity's tick() function.
   */
  protected boolean changed;
  protected MachineState state;

  public TileAbstractWorkMachine(final TileEntityType type, final MachineState initial_state, final MachineData data){
    super(type, new Receiver(data.total_energy_needed, data.get_max_receive()));
    this.state = initial_state;
  }

  @Override
  public void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    state = MachineState.value[nbt.getInt("State")];
  }

  @Override
  public CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    nbt.putInt("State", state.ordinal());
    return nbt;
  }

  protected abstract void machine_tick();

  public float getWorkTimePercentage(){
    if(state == MachineState.RUNNING){
      return energy.getEnergyPercentage();
    }
    return 0.0f;
  }

  @Override
  public final double getAvailableEnergy(){
    return 0;
  }

  public final MachineState getState(){
    return state;
  }

  public final String getStatus(){
    return state.getStatus();
  }

}
