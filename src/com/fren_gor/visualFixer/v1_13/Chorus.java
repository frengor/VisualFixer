package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fren_gor.visualFixer.Main;

public class Chorus implements Listener {

	public Chorus() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@EventHandler
	public void onChorusBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || !e.getBlock().getType().toString().contains("CHORUS"))
			return;

		Location l = e.getBlock().getLocation().clone().add(-1, -1, -1);

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {

					Block b = e.getBlock().getWorld().getBlockAt(l.clone().add(x, y, z));

					e.getPlayer().sendBlockChange(b.getLocation(), b.getBlockData());

				}
			}
		}
	}

	private class Advanced implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onChorusBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| !e.getClickedBlock().getType().toString().contains("CHORUS"))
				return;

			Location l = e.getClickedBlock().getLocation().clone().add(-1, -1, -1);

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int z = 0; z < 3; z++) {

						Block b = e.getClickedBlock().getWorld().getBlockAt(l.clone().add(x, y, z));

						e.getPlayer().sendBlockChange(b.getLocation(), b.getBlockData());

					}
				}
			}

		}
	}
}
