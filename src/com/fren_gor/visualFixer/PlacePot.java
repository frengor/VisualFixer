package com.fren_gor.visualFixer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlacePot implements Listener {

	private static Constructor<?> blockPosition, packetPlayOutBlockChange;
	private static Method getHandle;

	static {
		try {
			if (!ReflectionUtil.versionIs1_7())
				blockPosition = ReflectionUtil.getNMSClass("BlockPosition").getDeclaredConstructor(int.class, int.class,
						int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			if (!ReflectionUtil.versionIs1_7())
				packetPlayOutBlockChange = ReflectionUtil.getNMSClass("PacketPlayOutBlockChange")
						.getDeclaredConstructor(ReflectionUtil.getNMSClass("World"),
								ReflectionUtil.getNMSClass("BlockPosition"));
			else
				packetPlayOutBlockChange = ReflectionUtil.getNMSClass("PacketPlayOutBlockChange")
						.getDeclaredConstructor(int.class, int.class, int.class, ReflectionUtil.getNMSClass("World"));
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}

		try {
			getHandle = ReflectionUtil.getCBClass("CraftWorld").getDeclaredMethod("getHandle");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		/*
		 * try { getType =
		 * ReflectionUtil.getNMSClass("WorldServer").getDeclaredMethod(
		 * "getType", ReflectionUtil.getNMSClass("BlockPosition")); } catch
		 * (NoSuchMethodException | SecurityException e) { e.printStackTrace();
		 * }
		 */
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlacePot(PlayerInteractEvent e) {

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.FLOWER_POT) {
			if (e.isCancelled()) {
				if (FlowerPot.getFlowerPotContenent(e.getClickedBlock()) == 0) {

					Object te = FlowerPot.getTitleEntity(e.getClickedBlock());

					e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

					e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.FLOWER_POT, (byte) 0);

					Object p = ReflectionUtil.invoke(te, "getUpdatePacket");

					if (te == null || p == null) {

						return;

					}

					FlowerPot.sendPacket(e.getPlayer(), ReflectionUtil.invoke(te, "getUpdatePacket"));

					e.getPlayer().updateInventory();

				}
			} else {

				Object p = null;
				try {
					if (ReflectionUtil.versionIs1_7()) {
						p = packetPlayOutBlockChange.newInstance(e.getClickedBlock().getLocation().getBlockX(),
								e.getClickedBlock().getLocation().getBlockY(),
								e.getClickedBlock().getLocation().getBlockZ(),
								getHandle.invoke(e.getClickedBlock().getWorld()));
					} else {
						Object b = blockPosition.newInstance(e.getClickedBlock().getLocation().getBlockX(),
								e.getClickedBlock().getLocation().getBlockY(),
								e.getClickedBlock().getLocation().getBlockZ());
						p = packetPlayOutBlockChange.newInstance(getHandle.invoke(e.getClickedBlock().getWorld()), b);
					}
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					e1.printStackTrace();
					return;
				}

				for (Player pl : Bukkit.getOnlinePlayers()) {

					if (pl.getWorld().getName().equalsIgnoreCase(e.getPlayer().getWorld().getName())
							&& !pl.getUniqueId().equals(e.getPlayer().getUniqueId())) {
						pl.sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);
						FlowerPot.sendPacket(pl, p);
					}
				}

			}
		}

	}

}
