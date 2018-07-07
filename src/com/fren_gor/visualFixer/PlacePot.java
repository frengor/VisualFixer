package com.fren_gor.visualFixer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlacePot implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void donPlacwPot(PlayerInteractEvent e) {

		if (e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType() == Material.FLOWER_POT
				&& FlowerPot.getFlowerPotContenent(e.getClickedBlock()) == 0) {

			Object te = FlowerPot.getTitleEntity(e.getClickedBlock());

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

			e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.FLOWER_POT, (byte) 0);

			if (te == null || ReflectionUtil.invoke(te, "getUpdatePacket") == null) {

				return;

			}

			FlowerPot.sendPacket(e.getPlayer(), ReflectionUtil.invoke(te, "getUpdatePacket"));

			e.getPlayer().updateInventory();

		}

	}

}
