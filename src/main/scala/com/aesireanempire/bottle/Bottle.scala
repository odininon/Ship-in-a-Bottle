package com.aesireanempire.bottle

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event._

@Mod(name = Bottle.MODNAME, modid = Bottle.MODID, modLanguage = "scala")
object Bottle {
    final val MODNAME = "Ship in a Bottle"
    final val MODID = "BottleShip"


    @Mod.EventHandler
    def preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.init(event.getSuggestedConfigurationFile)
        BottleBlocks.preInit()
    }

    @Mod.EventHandler
    def init(event: FMLInitializationEvent) {
        BottleBlocks.init()
    }

    @Mod.EventHandler
    def postInit(event: FMLPostInitializationEvent) {

    }
}
