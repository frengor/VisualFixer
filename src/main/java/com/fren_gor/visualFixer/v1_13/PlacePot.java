package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlacePot implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void doPlacePot(PlayerInteractEvent e) {

		if (e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType().toString().contains("POTTED")) {

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR.createBlockData());

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getBlockData());

			e.getPlayer().updateInventory();

		}

	}
}
