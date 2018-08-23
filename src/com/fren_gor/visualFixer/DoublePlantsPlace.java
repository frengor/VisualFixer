package com.fren_gor.visualFixer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoublePlantsPlace implements Listener {

	public DoublePlantsPlace() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoublePlant(BlockPlaceEvent e) {

		if (e.getItemInHand() != null && e.getItemInHand().getType() == Material.valueOf("DOUBLE_PLANT")
				&& e.isCancelled()) {

			e.getPlayer().sendBlockChange(e.getBlockAgainst().getLocation(), e.getBlockAgainst().getType(),
					e.getBlockAgainst().getData());
			e.getPlayer().sendBlockChange(e.getBlockAgainst().getLocation().clone().add(0, 1, 0),
					e.getBlockAgainst().getRelative(BlockFace.UP).getType(),
					e.getBlockAgainst().getRelative(BlockFace.UP).getData());

		}

	}

	private class Advanced implements Listener {
		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onDoublePlant(PlayerInteractEvent e) {

			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null
					&& e.getItem().getType() == Material.valueOf("DOUBLE_PLANT") && e.isCancelled()) {

					e.getPlayer().sendBlockChange(e.getClickedBlock().getRelative(e.getBlockFace()).getLocation(),
							e.getClickedBlock().getRelative(e.getBlockFace()).getType(),
							e.getClickedBlock().getRelative(e.getBlockFace()).getData());
					e.getPlayer().sendBlockChange(
							e.getClickedBlock().getRelative(e.getBlockFace()).getRelative(BlockFace.UP).getLocation(),
							e.getClickedBlock().getRelative(e.getBlockFace()).getRelative(BlockFace.UP).getType(),
							e.getClickedBlock().getRelative(e.getBlockFace()).getRelative(BlockFace.UP).getData());

			}

		}
	}

}
