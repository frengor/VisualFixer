package com.fren_gor.visualFixer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FastBreak implements Listener {

	private static List<Player> l = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBreakFixer(BlockBreakEvent e) {

		Block b = e.getBlock();

		ItemStack i = e.getPlayer().getItemInHand();

		if (l.contains(e.getPlayer())) {
			return;
		}

		if (i == null || !isTool(i.getType())) {
			return;
		}

		if (e.getPlayer().getItemInHand().getItemMeta().hasEnchant(Enchantment.DIG_SPEED)
				|| e.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {

			l.add(e.getPlayer());

			new BukkitRunnable() {

				@Override
				public void run() {
					
					synchronized (l) {
						l.remove(e.getPlayer());
					}

				}
			}.runTaskLaterAsynchronously(Main.instance, 20);

			b.getWorld().refreshChunk(b.getChunk().getX(), b.getChunk().getZ());
		}

	}

	private boolean isTool(Material l) {

		if (l.toString().contains("SWORD") || l.toString().contains("AXE") || l.toString().contains("PICKAXE")
				|| l.toString().contains("HOE") || l.toString().contains("SPADE")) {
			return true;
		}
		return false;

	}

}
