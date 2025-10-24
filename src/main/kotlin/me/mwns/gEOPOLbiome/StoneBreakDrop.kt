package me.mwns.gEOPOLbiome

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import kotlin.random.Random

class StoneBreakDrop : Listener {

    @EventHandler
    fun stoneBreak(event: BlockBreakEvent) {
        val chance = Random.nextDouble(0.01, 1.0)
        val block = event.block
        val player = event.player
        val blockBiome = block.biome
        val tool = player.itemInHand
        val gamemode = player.gameMode
        if (tool.enchantments == Enchantment.SILK_TOUCH || gamemode == GameMode.CREATIVE) {
            return
        }

        val plugin = GEOPOLbiome.instance

        val biomeStuff = plugin.config.getConfigurationSection("biomeNames")
        for (key in biomeStuff?.getKeys(false)!!) {
            val ore = biomeStuff.getString("$key.ore") ?: continue

            if (block.type == Material.STONE && blockBiome == Biome.valueOf(key.uppercase())) {
                if (chance <= biomeStuff.getDouble("$key.chance")) {
                    block.world.dropItem(block.location, ItemStack(Material.valueOf(ore.uppercase())))
                }
                else {
                    block.world.dropItem(block.location, ItemStack(Material.COBBLESTONE))
                }
            }
        }
        if (block.type == Material.STONE) {
            event.isDropItems = false
        }
    }

}