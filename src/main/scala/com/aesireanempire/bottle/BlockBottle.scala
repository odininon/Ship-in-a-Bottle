package com.aesireanempire.bottle

import java.util

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

object BlockBottle extends BlockContainer(Material.glass) {

    setCreativeTab(CreativeTabs.tabDecorations)
    setBlockName("Bottle")

    override def createNewTileEntity(world: World, meta: Int): TileEntity = {
        new TileEntityBottle
    }

    override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, player: EntityLivingBase, stack: ItemStack) {
        if (player == null) {
            return
        }
        val te = world.getTileEntity(x, y, z)

        if (stack.hasTagCompound) {
            stack.getTagCompound.setInteger("x", x)
            stack.getTagCompound.setInteger("y", y)
            stack.getTagCompound.setInteger("z", z)
            te.readFromNBT(stack.getTagCompound)
        }
    }

    override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, dx: Int, dy: Float, dz: Float, side: Float): Boolean = {

        if (!world.isRemote) {
            world.getTileEntity(x, y, z) match {
                case te: TileEntityBottle =>
                    if (player.isSneaking) {
                        val item = te.takeItemFromBottle()
                        if (item != null) {
                            world.spawnEntityInWorld(new EntityItem(world, x.toDouble, y.toDouble, z.toDouble, item))
                            world.markBlockForUpdate(x, y, z)
                            true
                        }
                    } else {
                        if (te.placeItemInBottle(player.getCurrentEquippedItem)) {
                            player.getCurrentEquippedItem.stackSize = player.getCurrentEquippedItem.stackSize - 1
                            world.markBlockForUpdate(x, y, z)
                            true
                        }
                    }
                case _ =>
            }
        }

        true
    }

    override def onBlockHarvested(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer) {
        if (!player.capabilities.isCreativeMode) {
            dropBlockAsItem(world, x, y, z, meta, 0)
            world.setBlockToAir(x, y, z)
        }
    }

    override def getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): util.ArrayList[ItemStack] = {
        val drops = new util.ArrayList[ItemStack]()
        val bottle = new ItemStack(getItemDropped(metadata, world.rand, fortune), 1, damageDropped(metadata))
        val te = world.getTileEntity(x, y, z)

        if (te != null) {
            val tag = new NBTTagCompound
            te.writeToNBT(tag)

            if (!tag.hasNoTags) {
                bottle.setTagCompound(tag)
            }

            drops.add(bottle)
        }
        drops
    }
}
