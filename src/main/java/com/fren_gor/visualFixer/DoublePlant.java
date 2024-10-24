package com.fren_gor.visualFixer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoublePlant implements Listener {

	public DoublePlant() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoublePlant(BlockBreakEvent e) {

		if (!e.isCancelled() && e.getBlock().getType() != Material.valueOf("DOUBLE_PLANT"))
			return;

		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, -1, 0),
				e.getBlock().getRelative(BlockFace.DOWN).getType(), e.getBlock().getRelative(BlockFace.DOWN).getData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation(), Material.valueOf("DOUBLE_PLANT"),
				e.getBlock().getData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 1, 0),
				e.getBlock().getRelative(BlockFace.UP).getType(), e.getBlock().getRelative(BlockFace.UP).getData());

	}

	private class Advanced implements Listener {
		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onDoublePlant(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| e.getClickedBlock().getType() != Material.valueOf("DOUBLE_PLANT"))
				return;

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, -1, 0),
					e.getClickedBlock().getRelative(BlockFace.DOWN).getType(),
					e.getClickedBlock().getRelative(BlockFace.DOWN).getData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.valueOf("DOUBLE_PLANT"),
					e.getClickedBlock().getData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 1, 0),
					e.getClickedBlock().getRelative(BlockFace.UP).getType(),
					e.getClickedBlock().getRelative(BlockFace.UP).getData());

		}
	}

}
