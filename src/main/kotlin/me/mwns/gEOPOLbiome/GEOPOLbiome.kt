package me.mwns.gEOPOLbiome

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.UUID
import kotlin.random.Random

class GEOPOLbiome : JavaPlugin(), Listener {

    val playerTemp = mutableMapOf<UUID, Int>()
    var tempUpdate: BukkitTask? = null
    var season = 0
    var twentyDay2: Long? = -1L




    companion object {
        lateinit var instance: GEOPOLbiome
            private set
    }


    override fun onEnable() {
        saveDefaultConfig()

        instance = this

        logger.info("GeopolBiome Starting")
        server.pluginManager.registerEvents(this,this)
        server.pluginManager.registerEvents(StoneBreakDrop(), this)


        val world = server.getWorld("world")

        tempUpdate = Bukkit.getScheduler().runTaskTimer(this, Runnable {

            val day = world?.fullTime?.div(24000)
            val twentyDay1 = world?.fullTime?.div(24000)
            var seasonInt = config.getInt("season")

            season = config.getInt("season")

            if (day?.rem(20) == 0L) {
                twentyDay2 = twentyDay1

                if (season != 5 || season == 0){
                    seasonInt += 1
                    config.set("season", seasonInt)
                    saveConfig()
                }

                else if (season >= 5) {
                    seasonInt = 1
                    config.set("season", seasonInt)
                    saveConfig()
                }

                if (summer()) {
                    Bukkit.broadcastMessage("It is now Summer")
                }
            }
        }, 0L, 200L)


    }

    override fun onDisable() {
        logger.info("GeopolBiome shutting")
        tempUpdate?.cancel()
    }

    fun summer(): Boolean {
        if (season != 1) {

            return false
        }
        return true
    }

    @EventHandler
    fun dayShower(event: PlayerJoinEvent) {
        val player = event.player
        val playerID = player.uniqueId
        playerTemp[playerID] = 24

        val world = server.getWorld("world")
    }

}
