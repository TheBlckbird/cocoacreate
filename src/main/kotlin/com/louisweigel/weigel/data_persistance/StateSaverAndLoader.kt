package com.louisweigel.weigel.data_persistance

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.Position
import net.minecraft.world.PersistentState
import net.minecraft.world.dimension.DimensionType

class StateSaverAndLoader : PersistentState() {
    var homeIsSet = false
    var homeX = 0
    var homeY = 0.0
    var homeZ = 0

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putBoolean("homeIsSet", homeIsSet)
        nbt.putInt("homeX", homeX)
        nbt.putDouble("homeY", homeY)
        nbt.putInt("homeZ", homeZ)
        return nbt
    }

    companion object {
        fun load(nbt: NbtCompound): StateSaverAndLoader {
            val state = StateSaverAndLoader()
            state.homeIsSet = nbt.getBoolean("homeIsSet")
            state.homeX = nbt.getInt("homeX")
            state.homeY = nbt.getDouble("homeY")
            state.homeZ = nbt.getInt("homeZ")
            return state
        }

        fun load(server: MinecraftServer): StateSaverAndLoader {
            val state = server.overworld
                .persistentStateManager
                .getOrCreate(Companion::load, ::StateSaverAndLoader, "homePosition")

            state.markDirty()

            return state
        }
    }
}