package com.aesireanempire.bottle

import java.util

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.util.StatCollector

class ItemBlockBottle(block: Block) extends ItemBlock(block) {

    private def getItemNameInStack(stack: ItemStack): String = {
        if (stack.hasTagCompound) {
            val tag = stack.getTagCompound
            if (tag.hasKey("item")) {
                val stack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"))
                return StatCollector.translateToLocal(stack.getUnlocalizedName)
            }
        }
        "None"
    }

    override def addInformation(stack: ItemStack, player: EntityPlayer, list: util.List[_], advanced: Boolean) {
        list.asInstanceOf[java.util.List[String]].add("Item Holding: " + getItemNameInStack(stack))
    }
}
