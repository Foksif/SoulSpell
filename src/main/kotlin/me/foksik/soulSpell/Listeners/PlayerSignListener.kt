package me.foksik.soulSpell.Listeners

import org.bukkit.plugin.Plugin
import me.foksik.soulSpell.Utils.ChatUtil.message
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class PlayerSignListener(private val plugin: Plugin): Listener {

    private val signedKey = NamespacedKey(plugin, "signed_by")

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val inventory = player.inventory
        val mainHandItem = player.inventory.itemInMainHand
        val offHandItem = player.inventory.itemInOffHand


        val clickedBlock: Block? = event.clickedBlock
        if (clickedBlock?.type != Material.ANVIL || mainHandItem.type == Material.AIR) return

        if (!offHandItem.type.name.endsWith("_DYE")) return

        val itemMeta: ItemMeta = mainHandItem.itemMeta ?: return

        val existingSignatures = itemMeta.persistentDataContainer.get(signedKey, PersistentDataType.STRING)?.split(",")?.toMutableSet() ?: mutableSetOf()
        if (player.name in existingSignatures) {
            player.message("&cВы уже подписали этот предмет!")
            return
        }

        val dyeColor = try {
            ChatColor.valueOf(offHandItem.type.name.removeSuffix("_DYE"))
        } catch (e: IllegalArgumentException) {
            ChatColor.WHITE
        }

        val signature = "${dyeColor}#${player.name}"
        val lore = itemMeta.lore?.toMutableList() ?: mutableListOf()
        lore.add(signature)
        itemMeta.lore = lore

        existingSignatures.add(player.name)
        itemMeta.persistentDataContainer.set(signedKey, PersistentDataType.STRING, existingSignatures.joinToString(","))

        mainHandItem.itemMeta = itemMeta

        offHandItem.amount -= 1

        val dropLocation = clickedBlock.location.add(0.5, 1.5, 0.5)

        player.world.dropItem(dropLocation, mainHandItem)
        inventory.setItemInMainHand(null)

        player.message("&aПредмет подписан: $signature")
    }
}