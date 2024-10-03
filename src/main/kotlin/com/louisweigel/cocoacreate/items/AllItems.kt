package com.louisweigel.cocoacreate.items

import com.louisweigel.cocoacreate.CocoaCreate
import com.tterrag.registrate.util.entry.RegistryEntry
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item


object AllItems {
    val CHOCOLATE_COOKIES: RegistryEntry<Item> = CocoaCreate.REGISTRATE.item<Item>("chocolate_glazed_cookies", ::Item)
        .properties { props ->
            props.food(
                FoodComponent.Builder()
                    .hunger(7)
                    .saturationModifier(0.8f)
                    .build()
            )
        }
        .lang("Chocolate glazed cookies")
        .defaultModel()
        .register();

    // Load this class
    fun register() {}
}
