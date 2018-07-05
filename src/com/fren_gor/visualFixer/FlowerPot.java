package com.fren_gor.visualFixer;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FlowerPot implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onFlower(PlayerInteractEvent e) {

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Object te = getTitleEntity(e.getClickedBlock());

		try {
			if (e.getClickedBlock().getType() == Material.FLOWER_POT
					&& getFlowerPotContenent(e.getClickedBlock()) != 0) {

				if (Main.version > 10) {
					
					e.setCancelled(true);

				}

				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.FLOWER_POT, (byte) 0);

				if (te == null || ReflectionUtil.invoke(te, "getUpdatePacket") == null) {

					return;

				}

				sendPacket(e.getPlayer(), ReflectionUtil.invoke(te, "getUpdatePacket"));

				e.getPlayer().updateInventory();

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void sendPacket(Player player, Object packet) {

		Object crp = ReflectionUtil.cast(player, ReflectionUtil.getCBClass("entity.CraftPlayer"));

		Object ep = ReflectionUtil.invoke(crp, "getHandle");
		Object playerConnection = ReflectionUtil.getField(ep, "playerConnection");

		try {
			playerConnection.getClass().getMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"))
					.invoke(playerConnection, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}

	}

	public static Object getTitleEntity(Block b) {

		Object w = ReflectionUtil.cast(b.getWorld(), ReflectionUtil.getCBClass("CraftWorld"));

		try {
			return w.getClass().getMethod("getTileEntityAt", int.class, int.class, int.class).invoke(w,
					b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int getFlowerPotContenent(Block b) {

		Object te = getTitleEntity(b);

		try {
			return (int) ReflectionUtil.getNMSClass("Item").getMethod("getId", ReflectionUtil.getNMSClass("Item"))
					.invoke(null,
							Main.version == 7 ? ReflectionUtil.invoke(te, "a")
									: Main.version == 8 ? ReflectionUtil.invoke(te, "b")
											: Main.version == 9 ? ReflectionUtil.invoke(te, "d")
													: ReflectionUtil.invoke(te, "getItem"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
