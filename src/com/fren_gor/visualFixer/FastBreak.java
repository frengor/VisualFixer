package com.fren_gor.visualFixer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

		if (Main.instance.getConfig().getBoolean("advanced-checks")) {

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

			e.getPlayer().sendBlockChange(e.getBlock().getLocation(), Material.AIR, (byte) 0);

		}

	}

	private boolean isTool(Material l) {

		if (l.toString().contains("SWORD") || l.toString().contains("AXE") || l.toString().contains("PICKAXE")
				|| l.toString().contains("HOE") || l.toString().contains("SPADE")) {
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

				e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.AIR, (byte) 0);

			}
			
		}
	}

}
