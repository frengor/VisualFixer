package com.fren_gor.visualFixer;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoublePlant implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDoublePlant(PlayerInteractEvent e) {

		if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK || e.getClickedBlock().getType() != Material.DOUBLE_PLANT)
			return;

		Chunk c = e.getClickedBlock().getChunk();
		e.getClickedBlock().getWorld().refreshChunk(c.getX(), c.getZ());

	}
	
}
