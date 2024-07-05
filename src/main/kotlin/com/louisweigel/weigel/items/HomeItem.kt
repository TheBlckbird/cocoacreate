package com.louisweigel.weigel.items

import com.louisweigel.weigel.data_persistance.StateSaverAndLoader
import com.louisweigel.weigel.WeigelCreate
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import org.apache.logging.log4j.core.jmx.Server

class HomeItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (player.isSneaking) {
            setPosition(world, player, hand);
        } else {
            teleport(world, player, hand);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    fun teleport(world: World, player: PlayerEntity, hand: Hand): String? {
        val serverState = StateSaverAndLoader.load(world.server ?: return "Something went wrong")

        if (!serverState.homeIsSet) {
            return "No position is set";
        }

        val x = serverState.homeX
        val y = serverState.homeY
        val z = serverState.homeZ

        val newX = if (x < 0) x.toDouble() - 0.5 else x.toDouble() + 0.5
        val newZ = if (z < 0) z.toDouble() - 0.5 else z.toDouble() + 0.5

        player.teleport(newX, y, newZ);
        player.itemCooldownManager.set(this, 100);
        return null;
    }

    private fun setPosition(world: World, player: PlayerEntity, hand: Hand): String? {
        val pos = player.pos

        val server = world.server ?: return "Something went wrong"
        val serverState = StateSaverAndLoader.load(server)

        serverState.homeIsSet = true
        serverState.homeX = pos.x.toInt()
        serverState.homeY = pos.y
        serverState.homeZ = pos.z.toInt()

        val dimensionValue = world.registryKey.value
        if (dimensionValue.namespace + ":" + dimensionValue.path != "minecraft:overworld") {
            return "Your home must be in the Overworld"
        }

        return null
    }
}