package com.louisweigel.cocoacreate

import com.louisweigel.cocoacreate.items.AllItems
import com.tterrag.registrate.Registrate
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CocoaCreate : ModInitializer {
	const val MOD_ID: String = "cocoacreate"
	const val NAME: String = "Cocoa Create"
	val LOGGER: Logger = LoggerFactory.getLogger(NAME)
	val REGISTRATE: Registrate = Registrate.create(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!")
		AllItems.register()
		REGISTRATE.register()
	}
}
