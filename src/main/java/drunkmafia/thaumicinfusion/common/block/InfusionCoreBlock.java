package drunkmafia.thaumicinfusion.common.block;

import com.sun.istack.internal.NotNull;
import drunkmafia.thaumicinfusion.common.block.tile.InfusionCoreTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.blocks.BlockStoneDevice;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TilePedestal;

import java.util.Arrays;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 19/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusionCoreBlock extends BlockStoneDevice {

    protected InfusionCoreBlock() {
        setBlockName(infusionCoreBlock_UnlocalizedName);
        setCreativeTab(TITab.tab);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, 1, 3);
        world.setTileEntity(x, y, z, createNewTileEntity(world, 1));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
        if (world.isRemote)
            return true;

        if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemWandCasting)
            return false;

        TilePedestal ped = (TilePedestal)world.getTileEntity(x, y, z);
        ItemStack inv = ped.getStackInSlot(0);
        ItemStack equipped = player.getCurrentEquippedItem();

        if (inv != null) {
            if(equipped != null && inv.getItem() == equipped.getItem() && inv.getItemDamage() == equipped.getItemDamage()){
                if(inv.stackSize < 64) {
                    inv.stackSize += equipped.stackSize;
                    if(inv.stackSize > 64)
                        equipped.stackSize = inv.stackSize - 64;
                    else equipped.stackSize = 0;
                }

                ped.setInventorySlotContents(0, inv);
                player.setCurrentItemOrArmor(0, equipped);

                player.inventory.markDirty();
                world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
                return true;
            }

            InventoryUtils.dropItemsAtEntity(world, x, y, z, player);
            world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
            return true;
        }

        if (equipped != null) {
            ped.setInventorySlotContents(0, equipped);
            player.setCurrentItemOrArmor(0, null);

            player.inventory.markDirty();

            world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);

            return true;
        }
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new InfusionCoreTile();
    }
}