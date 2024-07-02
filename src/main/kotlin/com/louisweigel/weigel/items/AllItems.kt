package com.louisweigel.weigel.items

import com.louisweigel.weigel.WeigelCreate
import com.tterrag.registrate.util.entry.RegistryEntry

object AllItems {
    val HOME_ITEM: RegistryEntry<HomeItem> =
        WeigelCreate.REGISTRATE.item("home", ::HomeItem)
            .register()

    // Load this class
    fun register() {}
}