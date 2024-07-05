package com.louisweigel.weigel.items

import com.louisweigel.weigel.data_persistance.PlayerData
import com.louisweigel.weigel.data_persistance.StateSaverAndLoader
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.PlayerManager
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.TeleportTarget
import net.minecraft.world.World

class HomeItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (world.isClient) {
            return TypedActionResult.success(player.getStackInHand(hand));
        }


        if (player.isSneaking) {
            val maybeError = setPosition(world, player, hand)

            if (maybeError != null) {
                player.sendMessage(maybeError, true)
            }
        } else {
            val maybeError = teleport(world, player, hand)

            if (maybeError != null) {
                player.sendMessage(maybeError, true)
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    fun teleport(world: World, player: PlayerEntity, hand: Hand): Text? {
        val playerState = StateSaverAndLoader.loadPlayerData(player) ?: return Text.literal("Something went wrong")

        if (!playerState.homeIsSet) {
            return Text.literal("No position is set")
        }

        val x = playerState.homeX
        val y = playerState.homeY
        val z = playerState.homeZ

        val newX = if (x < 0) x.toDouble() - 0.5 else x.toDouble() + 0.5
        val newZ = if (z < 0) z.toDouble() - 0.5 else z.toDouble() + 0.5

        FabricDimensions.teleport(player, world.server?.overworld, TeleportTarget(Vec3d(newX, y, newZ), Vec3d.ZERO, 0F, 0F))
        player.itemCooldownManager.set(this, 100);
        return null;
    }

    private fun setPosition(world: World, player: PlayerEntity, hand: Hand): Text? {
        val dimensionValue = world.registryKey.value
        if (dimensionValue.namespace + ":" + dimensionValue.path != "minecraft:overworld") {
            return Text.literal("Your home must be in the Overworld")
        }

        val playerState = StateSaverAndLoader.loadPlayerData(player) ?: return Text.literal("Something went wrong")
        val pos = player.pos

        playerState.homeIsSet = true
        playerState.homeX = pos.x.toInt()
        playerState.homeY = pos.y
        playerState.homeZ = pos.z.toInt()

        return null
    }
}