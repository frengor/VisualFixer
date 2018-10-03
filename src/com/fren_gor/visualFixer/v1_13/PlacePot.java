package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.fren_gor.visualFixer.Main;

public class PlacePot implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void doPlacePot(PlayerInteractEvent e) {

		if (e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType().toString().contains("POTTED")) {

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getBlockData());

			e.getPlayer().updateInventory();

		}

	}
}
