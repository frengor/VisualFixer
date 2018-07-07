package com.fren_gor.visualFixer;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.fren_gor.visualFixer.libraries.Metrics;

public class Main extends JavaPlugin implements Listener {

	public static int version;
	static Main instance;

	@Override
	public void onEnable() {
		instance = this;

		version = Integer.parseInt(ReflectionUtil.getVersion().split("_")[1]);

		try {
			ConfigManager.load(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			getConfig().load(new File(getDataFolder(), "config.yml"));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		if (getConfig().getBoolean("fix-pots")) {
			Bukkit.getPluginManager().registerEvents(new FlowerPot(), this);
		}
		if (getConfig().getBoolean("fix-double-plants")) {
			Bukkit.getPluginManager().registerEvents(new DoublePlant(), this);
		}
		if (getConfig().getBoolean("fix-fast-break")) {
			Bukkit.getPluginManager().registerEvents(new FastBreak(), this);
		}

		new Metrics(this);

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (label.equalsIgnoreCase("fixvisual") || label.equalsIgnoreCase("visualfixer:fixvisual")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cYou must be a player to do /fixvisual");
				return false;
			}

			Player p = (Player) sender;
			Block b = p.getLocation().getBlock();

			Chunk c = b.getChunk();
			b.getWorld().refreshChunk(c.getX(), c.getZ());

			return true;

		}
		return false;

	}

}
