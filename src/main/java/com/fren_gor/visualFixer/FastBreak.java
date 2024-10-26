package com.fren_gor.visualFixer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class FastBreak implements Listener {

	public FastBreak() {

		if (Main.instance.getConfig().getBoolean("advanced-check")) {

			Bukkit.getPluginManager().registerEvents(new Advanced(), Main.instance);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBreakFixer(BlockBreakEvent e) {

		ItemStack i = e.getPlayer().getItemInHand();

		if (e.isCancelled() || i == null || !isTool(i.getType())) {
			return;
		}

		if (e.getPlayer().getItemInHand().getItemMeta().hasEnchant(Enchantment.DIG_SPEED)
				|| e.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {

			Location l = e.getBlock().getLocation().clone().add(-1, -1, -1);

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int z = 0; z < 3; z++) {

						if (x == 1 && y == 1 && z == 1) {

							e.getPlayer().sendBlockChange(e.getBlock().getLocation(), Material.AIR, (byte) 0);

							continue;
						}

						Block b = e.getBlock().getWorld().getBlockAt(l.clone().add(x, y, z));

						e.getPlayer().sendBlockChange(b.getLocation(), b.getType(), b.getData());

					}
				}
			}

		}

	}

	private boolean isTool(Material l) {

		if (l.toString().contains("SWORD") || l.toString().contains("AXE") || l.toString().contains("PICKAXE")
				|| l.toString().contains("HOE") || l.toString().contains("SPADE") || l.toString().contains("SHOVEL")) {
			return true;
		}
		return false;

	}

	/*
	 * private boolean hasState(Material type) { return type ==
	 * Material.FLOWER_POT || type == Material.BANNER || type == Material.BEACON
	 * || type == Material.BREWING_STAND || type == Material.CHEST || type ==
	 * Material.COMMAND || type == Material.MOB_SPAWNER || type ==
	 * Material.DISPENSER || type == Material.DROPPER || type ==
	 * Material.FURNACE || type == Material.BURNING_FURNACE || type ==
	 * Material.JUKEBOX || type == Material.NOTE_BLOCK || type ==
	 * Material.WALL_SIGN || type == Material.SIGN_POST || type ==
	 * Material.SKULL; }
	 */

	private class Advanced implements Listener {

		@SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		public void onBreakFixer(PlayerInteractEvent e) {

			ItemStack i = e.getPlayer().getItemInHand();

			if (!e.isCancelled() || e.getAction() != Action.LEFT_CLICK_BLOCK || i == null || !isTool(i.getType()))
				return;

			if (e.getPlayer().getItemInHand().getItemMeta().hasEnchant(Enchantment.DIG_SPEED)
					|| e.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {

				Location l = e.getClickedBlock().getLocation().clone().add(-1, -1, -1);

				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {
						for (int z = 0; z < 3; z++) {

							if (x == 1 && y == 1 && z == 1) {

								e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR,
										(byte) 0);

								continue;
							}

							Block b = e.getClickedBlock().getWorld().getBlockAt(l.clone().add(x, y, z));

							e.getPlayer().sendBlockChange(b.getLocation(), b.getType(), b.getData());

						}
					}
				}

			}

		}
	}

}
