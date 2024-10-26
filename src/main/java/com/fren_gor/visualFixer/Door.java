package com.fren_gor.visualFixer;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Door implements Listener {

	public Door() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoorBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || !e.getBlock().getType().toString().contains("_DOOR"))
			return;

		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, -1, 0),
				e.getBlock().getRelative(BlockFace.DOWN).getType(), e.getBlock().getRelative(BlockFace.DOWN).getData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation(), e.getBlock().getType(), e.getBlock().getData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 1, 0),
				e.getBlock().getRelative(BlockFace.UP).getType(), e.getBlock().getRelative(BlockFace.UP).getData());

	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoorPlace(BlockPlaceEvent e) {

		if (!e.isCancelled() || !e.getBlockPlaced().getType().toString().contains("_DOOR"))
			return;

		e.getPlayer().sendBlockChange(e.getBlockPlaced().getLocation().clone().add(0, -1, 0),
				e.getBlockPlaced().getRelative(BlockFace.DOWN).getType(), e.getBlockPlaced().getRelative(BlockFace.DOWN).getData());
		e.getPlayer().sendBlockChange(e.getBlockPlaced().getLocation(), e.getBlockPlaced().getType(), e.getBlock().getData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 1, 0),
				e.getBlockPlaced().getRelative(BlockFace.UP).getType(), e.getBlockPlaced().getRelative(BlockFace.UP).getData());

	}

	private class Advanced implements Listener {

		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onDoorBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || !(
					(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType().toString().contains("_DOOR"))
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null && e.getMaterial().toString().contains("_DOOR"))
				)
			)
				return;

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, -1, 0),
					e.getClickedBlock().getRelative(BlockFace.DOWN).getType(), e.getClickedBlock().getRelative(BlockFace.DOWN).getData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getType(), e.getClickedBlock().getData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 1, 0),
					e.getClickedBlock().getRelative(BlockFace.UP).getType(), e.getClickedBlock().getRelative(BlockFace.UP).getData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 2, 0),
					e.getClickedBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType(), e.getClickedBlock().getRelative(BlockFace.UP).getData());
			
		}
	}

}
