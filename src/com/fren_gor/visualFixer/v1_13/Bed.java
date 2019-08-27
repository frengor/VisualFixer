package com.fren_gor.visualFixer.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.fren_gor.visualFixer.Main;

public class Bed implements Listener {

	public Bed() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@EventHandler
	public void onBedBreak(BlockBreakEvent e) {

		if (!e.isCancelled() || !e.getBlock().getType().toString().contains("_BED"))
			return;

		BlockFace b = ((org.bukkit.block.data.type.Bed) e.getBlock().getBlockData()).getFacing();

		if (((org.bukkit.block.data.type.Bed) e.getBlock().getBlockData()).getPart() == Part.HEAD) {
			b = b.getOppositeFace();
		}

		e.getPlayer().sendBlockChange(e.getBlock().getRelative(b).getLocation(),
				e.getBlock().getRelative(b).getBlockData());

	}

	private class Advanced implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onBedBreak(PlayerInteractEvent e) {

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK
					|| !e.getClickedBlock().getType().toString().contains("_BED"))
				return;

			BlockFace b = ((org.bukkit.block.data.type.Bed) e.getClickedBlock().getBlockData()).getFacing();

			if (((org.bukkit.block.data.type.Bed) e.getClickedBlock().getBlockData()).getPart() == Part.HEAD) {
				b = b.getOppositeFace();
			}
			
			e.getPlayer().sendBlockChange(e.getClickedBlock().getRelative(b).getLocation(),
					e.getClickedBlock().getRelative(b).getBlockData());

		}
	}

}
