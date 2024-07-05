package com.louisweigel.weigel.data_persistance

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.world.PersistentState
import java.util.UUID

class StateSaverAndLoader : PersistentState() {
    var players: HashMap<UUID, PlayerData> = HashMap()

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        val playersNbt = NbtCompound()

        for ((uuid, playerData) in players) {
            val playerNbt = NbtCompound()

            playerNbt.putBoolean("homeIsSet", playerData.homeIsSet)
            playerNbt.putInt("homeX", playerData.homeX)
            playerNbt.putDouble("homeY", playerData.homeY)
            playerNbt.putInt("homeZ", playerData.homeZ)

            playersNbt.put(uuid.toString(), playerNbt)
        }

        return nbt
    }

    companion object {
        fun load(nbt: NbtCompound): StateSaverAndLoader {
            val state = StateSaverAndLoader()

            val playersNbt = nbt.getCompound("players")

            for (key in playersNbt.keys) {
                val playerData = PlayerData()

                playerData.homeIsSet = playersNbt.getCompound(key).getBoolean("homeIsSet")
                playerData.homeX = playersNbt.getCompound(key).getInt("homeX")
                playerData.homeY = playersNbt.getCompound(key).getDouble("homeY")
                playerData.homeZ = playersNbt.getCompound(key).getInt("homeZ")

                val uuid = UUID.fromString(key)
                state.players[uuid] = playerData
            }

            return state
        }

        fun load(server: MinecraftServer): StateSaverAndLoader {
            val state = server.overworld
                .persistentStateManager
                .getOrCreate(Companion::load, ::StateSaverAndLoader, "homePosition")

            state.markDirty()

            return state
        }

        fun loadPlayerData(player: PlayerEntity): PlayerData? {
            val serverState = player.server?.let { load(it) }
            val playerState = serverState?.players?.computeIfAbsent(player.uuid) { _ -> PlayerData() }

            return playerState
        }
    }
}