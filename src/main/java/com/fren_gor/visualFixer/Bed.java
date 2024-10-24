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

public class Bed implements Listener {

	public Bed() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBedBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || e.getBlock().getType() != Material.valueOf("BED_BLOCK"))
			return;
		
		BlockFace b = BlockFace.EAST;

		switch (e.getBlock().getData()) {
		case 0:
			b = BlockFace.SOUTH;
			break;
		case 1:
			b = BlockFace.WEST;
			break;
		case 2:
			b = BlockFace.NORTH;
			break;
		case 3:
			break;
		default:
			return;
		}

		e.getPlayer().sendBlockChange(e.getBlock().getRelative(b).getLocation(), Material.valueOf("BED_BLOCK"),
				e.getBlock().getRelative(b).getData());

	}

	private class Advanced implements Listener {

		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onBedBreak(PlayerInteractEvent e) {
			
			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| e.getClickedBlock().getType() != Material.valueOf("BED_BLOCK"))
				return;
			
			BlockFace b = BlockFace.EAST;

			switch (e.getClickedBlock().getData()) {
			case 0:
				b = BlockFace.SOUTH;
				break;
			case 1:
				b = BlockFace.WEST;
				break;
			case 2:
				b = BlockFace.NORTH;
				break;
			case 3:
				break;
			default:
				return;
			}
			
			e.getPlayer().sendBlockChange(e.getClickedBlock().getRelative(b).getLocation(), Material.valueOf("BED_BLOCK"),
					e.getClickedBlock().getRelative(b).getData());

		}
	}

}
