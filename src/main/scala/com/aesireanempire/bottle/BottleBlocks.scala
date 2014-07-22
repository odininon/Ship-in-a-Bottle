package com.aesireanempire.bottle

import cpw.mods.fml.common.registry.GameRegistry

object BottleBlocks {

    def preInit() {
        GameRegistry.registerBlock(BlockBottle, classOf[ItemBlockBottle], "Bottle")
        GameRegistry.registerTileEntity(classOf[TileEntityBottle], "Bottle")
    }

    def init() {

    }
}
