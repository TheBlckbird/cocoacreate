package com.louisweigel.weigel.items

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class HomeItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (player.isSneaking) {
            setPosition(player, hand);
        } else {
//			playerEntity.teleport(0.0, 100.0, 0.0);
            teleport(world, player, hand);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    fun teleport(world: World, player: PlayerEntity, hand: Hand): Boolean {
        val nbt = player.getStackInHand(hand).nbt

        if (nbt == null) {
            if (!world.isClient())
                player.sendMessage(Text.translatable("item.weigel.home.fail"), true);
            return false;
        }

        val x = nbt.getDouble("x");
        val z = nbt.getDouble("z");
        player.teleport(x, 100.0, z);
        player.itemCooldownManager.set(this, 100);
        return true;
    }

    private fun setPosition(player: PlayerEntity, hand: Hand): Boolean {
        val nbt = NbtCompound()
        val pos = player.pos
        nbt.putDouble("x", pos.x)
        nbt.putDouble("z", pos.z)
        player.sendMessage(Text.literal("X: " + pos.x + " Z: " + pos.z))
        player.getStackInHand(hand).nbt = nbt
        return true
    }
}