package com.louisweigel.weigel.items

import com.louisweigel.weigel.WeigelCreate
import com.tterrag.registrate.util.entry.RegistryEntry

object AllItems {
    val HOME_ITEM = WeigelCreate.REGISTRATE.item<HomeItem>("home", ::HomeItem)
        .properties { p -> p.maxCount(1) }
        .lang("Home")
        .defaultModel()
        .register()

    // Load this class
    fun register() {}
}