package com.louisweigel.cocoacreate

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object CocoaCreateDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
		val pack = generator.createPack()

		val helper = ExistingFileHelper.withResourcesFromArg()
		CocoaCreate.REGISTRATE.setupDatagen(pack, helper)
	}
}
