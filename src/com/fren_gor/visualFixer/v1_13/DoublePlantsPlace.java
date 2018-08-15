package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.fren_gor.visualFixer.Main;

public class DoublePlantsPlace implements Listener {

	public DoublePlantsPlace() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoublePlant(BlockPlaceEvent e) {

		if (e.getItemInHand() != null && isDoublePlant(e.getItemInHand().getType()) && e.isCancelled()) {

			e.getPlayer().sendBlockChange(e.getBlock().getLocation(), e.getBlock().getBlockData());
			e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 1, 0),
					e.getBlock().getRelative(BlockFace.UP).getBlockData());
			e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 2, 0),
					e.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getBlockData());

		}

	}

	private class Advanced implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onDoublePlant(PlayerInteractEvent e) {

			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null && isDoublePlant(e.getItem().getType())
					&& e.isCancelled()) {

				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getBlockData());
				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 1, 0),
						e.getClickedBlock().getRelative(BlockFace.UP).getBlockData());
				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 2, 0),
						e.getClickedBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getBlockData());

			}

		}
	}

	private boolean isDoublePlant(Material m) {

		if (m == Material.ROSE_BUSH || m == Material.SUNFLOWER || m == Material.LILAC || m == Material.PEONY
				|| m == Material.TALL_GRASS || m == Material.LARGE_FERN)
			return true;
		return false;

	}
}
