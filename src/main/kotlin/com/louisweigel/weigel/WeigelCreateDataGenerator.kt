package com.louisweigel.weigel

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object WeigelCreateDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
		val pack = generator.createPack()

		val helper = ExistingFileHelper.withResourcesFromArg()
		WeigelCreate.REGISTRATE.setupDatagen(pack, helper)
	}
}