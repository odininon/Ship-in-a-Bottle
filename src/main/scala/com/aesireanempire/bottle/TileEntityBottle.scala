package com.aesireanempire.bottle

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity

class TileEntityBottle extends TileEntity {

    final var itemInBottle: ItemStack = null

    def placeItemInBottle(stack: ItemStack): Boolean = {
        if (itemInBottle == null && stack != null) {
            itemInBottle = stack.copy()
            itemInBottle.stackSize = 1
            true
        } else {
            false
        }
    }

    def takeItemFromBottle(): ItemStack = {
        val ret = itemInBottle.copy()
        itemInBottle = null
        ret
    }


    override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit = {
        readFromNBT(pkt.func_148857_g())
    }

    override def getDescriptionPacket: Packet = {
        val tagCompound: NBTTagCompound = new NBTTagCompound
        writeToNBT(tagCompound)
        new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, tagCompound)
    }


    override def writeToNBT(tag: NBTTagCompound) {
        writeNBT(tag)
    }


    override def readFromNBT(tag: NBTTagCompound) {
        readNBT(tag)
    }

    private def readNBT(compound: NBTTagCompound) = {
        if (compound.hasKey("item")) {
            this.itemInBottle = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("item"))
        }
    }

    private def writeNBT(compound: NBTTagCompound) = {
        if (this.itemInBottle != null) {
            compound.setTag("item", this.itemInBottle.writeToNBT(new NBTTagCompound))
        }
    }

}
