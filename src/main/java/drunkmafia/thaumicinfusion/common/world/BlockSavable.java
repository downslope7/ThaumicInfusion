package drunkmafia.thaumicinfusion.common.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by DrunkMafia on 29/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public abstract class BlockSavable extends Savable {

    private WorldCoord coordinates;

    public BlockSavable() {
    }

    public BlockSavable(WorldCoord coordinates) {
        this.coordinates = coordinates;
    }

    public boolean init = false;

    public void dataLoad(World world){
        init = true;
    }

    public boolean isInit(){
        return init;
    }

    @Override
    public abstract boolean equals(Object obj);

    public WorldCoord getCoords() {
        return coordinates;
    }

    public void writeNBT(NBTTagCompound tagCompound) {
        super.writeNBT(tagCompound);
        coordinates.writeNBT(tagCompound);
    }

    public void readNBT(NBTTagCompound tagCompound) {
        super.readNBT(tagCompound);
        coordinates = new WorldCoord();
        coordinates.readNBT(tagCompound);
    }
}
