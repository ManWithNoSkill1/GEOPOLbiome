package me.mwns.gEOPOLbiome

import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class StoneBreakDrop : Listener {

    @EventHandler
    fun stoneBreak(event: BlockBreakEvent) {
        val chance = Random.nextDouble(0.01, 1.0)
        val block = event.block
        val blockBiome = block.biome

        val plugin = GEOPOLbiome.instance

        val biomeStuff = plugin.config.getConfigurationSection("biomeNames")
        for (key in biomeStuff?.getKeys(false)!!) {
            val ore = biomeStuff.getString("$key.ore") ?: continue

            if (block.type == Material.STONE && blockBiome == Biome.valueOf(key.uppercase())) {
                if (chance <= biomeStuff.getDouble("$key.chance")) {
                    block.world.dropItem(block.location, ItemStack(Material.valueOf(ore.uppercase())))
                }
                else {
                    block.world.dropItem(block.location, ItemStack(Material.STONE))
                }
            }
        }
        if (block.type == Material.STONE) {
            event.isDropItems = false
        }
    }

}