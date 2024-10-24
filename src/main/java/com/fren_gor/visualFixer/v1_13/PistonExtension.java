package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fren_gor.visualFixer.Main;

public class PistonExtension implements Listener {

	public PistonExtension() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonBreak(BlockBreakEvent e) {
		
		if (!e.isCancelled() || e.getBlock().getType() != Material.PISTON_HEAD) {
			return;
		}

		BlockFace b = ((Directional) e.getBlock().getBlockData()).getFacing().getOppositeFace();

		e.getPlayer().sendBlockChange(e.getBlock().getRelative(b).getLocation(),
				e.getBlock().getRelative(b).getBlockData());

	}

	private class Advanced implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onPistonBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| e.getClickedBlock().getType() != Material.PISTON_HEAD) {
				return;
			}

			BlockFace b = ((Directional) e.getClickedBlock().getBlockData()).getFacing().getOppositeFace();

			e.getPlayer().sendBlockChange(e.getClickedBlock().getRelative(b).getLocation(), 
					e.getClickedBlock().getRelative(b).getBlockData());

		}
	}

}
