package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fren_gor.visualFixer.Main;

public class TallSeagrass implements Listener {

	public TallSeagrass() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@EventHandler
	public void onTallSeagrassBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || !e.getBlock().getType().toString().equals("TALL_SEAGRASS"))
			return;

		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, -1, 0),
				e.getBlock().getRelative(BlockFace.DOWN).getBlockData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation(), e.getBlock().getBlockData());
		e.getPlayer().sendBlockChange(e.getBlock().getLocation().clone().add(0, 1, 0),
				e.getBlock().getRelative(BlockFace.UP).getBlockData());

	}

	private class Advanced implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onTallSeagrassBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| !e.getClickedBlock().getType().toString().equals("TALL_SEAGRASS"))
				return;

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, -1, 0),
					e.getClickedBlock().getRelative(BlockFace.DOWN).getBlockData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), e.getClickedBlock().getBlockData());
			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation().clone().add(0, 1, 0),
					e.getClickedBlock().getRelative(BlockFace.UP).getBlockData());

		}
	}

}
