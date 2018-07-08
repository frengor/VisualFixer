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

public class PistonExtension implements Listener {

	public PistonExtension() {

		if (Main.instance.getConfig().getBoolean("advanced-checks")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || e.getBlock().getType() != Material.PISTON_EXTENSION) {
			return;
		}

		BlockFace b = BlockFace.UP;

		switch (e.getBlock().getData()) {
		case 0:
			break;
		case 8:
			break;
		case 1:
			b = BlockFace.DOWN;
			break;
		case 2:
			b = BlockFace.SOUTH;
			break;
		case 3:
			b = BlockFace.NORTH;
			break;
		case 4:
			b = BlockFace.EAST;
			break;
		case 5:
			b = BlockFace.WEST;
			break;
		case 9:
			b = BlockFace.DOWN;
			break;
		case 10:
			b = BlockFace.SOUTH;
			break;
		case 11:
			b = BlockFace.NORTH;
			break;
		case 12:
			b = BlockFace.EAST;
			break;
		case 13:
			b = BlockFace.WEST;
			break;

		default:
			break;
		}

		e.getPlayer().sendBlockChange(e.getBlock().getRelative(b).getLocation(), e.getBlock().getRelative(b).getType(),
				e.getBlock().getRelative(b).getData());

	}

	private class Advanced implements Listener {

		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onPistonBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| e.getClickedBlock().getType() != Material.PISTON_EXTENSION) {
				return;
			}

			BlockFace b = BlockFace.UP;

			switch (e.getClickedBlock().getData()) {
			case 0:
				break;
			case 8:
				break;
			case 1:
				b = BlockFace.DOWN;
				break;
			case 2:
				b = BlockFace.SOUTH;
				break;
			case 3:
				b = BlockFace.NORTH;
				break;
			case 4:
				b = BlockFace.EAST;
				break;
			case 5:
				b = BlockFace.WEST;
				break;
			case 9:
				b = BlockFace.DOWN;
				break;
			case 10:
				b = BlockFace.SOUTH;
				break;
			case 11:
				b = BlockFace.NORTH;
				break;
			case 12:
				b = BlockFace.EAST;
				break;
			case 13:
				b = BlockFace.WEST;
				break;

			default:
				break;
			}

			e.getPlayer().sendBlockChange(e.getClickedBlock().getRelative(b).getLocation(),
					e.getClickedBlock().getRelative(b).getType(), e.getClickedBlock().getRelative(b).getData());

		}
	}

}
