package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fren_gor.visualFixer.Main;

public class FlowerPot implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onFlower(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !Main.instance.getConfig().getBoolean("cancel-pots-take"))
			return;

		if (e.getClickedBlock().getType().toString().startsWith("POTTED_")) {
			
			e.setCancelled(true);

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getBlockData());

			e.getPlayer().updateInventory();

		}

	}

}
