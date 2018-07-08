package com.fren_gor.visualFixer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.fren_gor.visualFixer.libraries.Metrics;
import com.fren_gor.visualFixer.libraries.org.inventivetalent.update.spiget.SpigetUpdate;
import com.fren_gor.visualFixer.libraries.org.inventivetalent.update.spiget.UpdateCallback;
import com.fren_gor.visualFixer.libraries.org.inventivetalent.update.spiget.comparator.VersionComparator;

public class Main extends JavaPlugin {

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

		Metrics m = new Metrics(this);

		if (getConfig().getBoolean("fix-pots")) {

			Bukkit.getPluginManager().registerEvents(new FlowerPot(), this);

		}
		if (getConfig().getBoolean("fix-double-plants")) {

			Bukkit.getPluginManager().registerEvents(new DoublePlant(), this);

		}
		if (getConfig().getBoolean("fix-fast-break")) {

			Bukkit.getPluginManager().registerEvents(new FastBreak(), this);

		}
		if (getConfig().getBoolean("fix-pot-place")) {

			Bukkit.getPluginManager().registerEvents(new PlacePot(), this);

		}
		if (getConfig().getBoolean("fix-pistons")) {

			Bukkit.getPluginManager().registerEvents(new PistonExtension(), this);

		}

		m.addCustomChart(new Metrics.SimplePie("fix_pots_take", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

			}
		}));

		m.addCustomChart(new Metrics.SimplePie("fix_double_plants", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

			}
		}));

		m.addCustomChart(new Metrics.SimplePie("fix_fast_break", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

			}
		}));

		m.addCustomChart(new Metrics.SimplePie("fix_pots_place", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

			}
		}));
		
		m.addCustomChart(new Metrics.SimplePie("fix_piston_break", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("fix-pistons") ? "Enabled" : "Disabled";

			}
		}));
		
		m.addCustomChart(new Metrics.SimplePie("advanced_checks", new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getConfig().getBoolean("advanced-check") ? "Enabled" : "Disabled";

			}
		}));

		SpigetUpdate updater = new SpigetUpdate(this, 58442);

		// This converts a semantic version to an integer and checks if the
		// updated version is greater
		updater.setVersionComparator(VersionComparator.SEM_VER);

		updater.checkForUpdate(new UpdateCallback() {
			@Override
			public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
				//// A new version is available
				// newVersion - the latest version
				// downloadUrl - URL to the download
				// hasDirectDownload - whether the update is available for a
				//// direct download on spiget.org
				Bukkit.getConsoleSender().sendMessage("§e" + getName() + " is updating!");
				if (hasDirectDownload) {
					if (updater.downloadUpdate()) {
						// Update downloaded, will be loaded when the server
						// restarts
						Bukkit.getConsoleSender()
								.sendMessage("§bUpdate downloaded, will be loaded when the server restarts");
					} else {
						// Update failed
						getLogger().warning("Update download failed, reason is " + updater.getFailReason());
					}
				}
			}

			@Override
			public void upToDate() {
				//// Plugin is up-to-date
				Bukkit.getConsoleSender().sendMessage("§b" + getName() + " is up to date!");
			}
		});

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

			b.getWorld().refreshChunk(c.getX() - 1, c.getZ() + 1);
			b.getWorld().refreshChunk(c.getX() - 1, c.getZ() - 1);
			b.getWorld().refreshChunk(c.getX() + 1, c.getZ() + 1);
			b.getWorld().refreshChunk(c.getX() + 1, c.getZ() - 1);

			b.getWorld().refreshChunk(c.getX(), c.getZ() + 1);
			b.getWorld().refreshChunk(c.getX() - 1, c.getZ());
			b.getWorld().refreshChunk(c.getX() + 1, c.getZ());
			b.getWorld().refreshChunk(c.getX(), c.getZ() - 1);

			return true;

		}

		if (label.equalsIgnoreCase("visualfixer") || label.equalsIgnoreCase("visualfixer:visualfixer")) {

			if (!sender.hasPermission("visualfix.visualfixer")) {

				sender.sendMessage("§bInstalled §9VisualFixer §bversion " + getDescription().getVersion());

				return true;
			}

			if (args.length == 0) {

				sender.sendMessage("§bInstalled §9VisualFixer §bversion " + getDescription().getVersion());
				sender.sendMessage("§7Use " + ChatColor.UNDERLINE + "/visualfixer reload§r §/to reload");

				return true;

			}

			if (args[0].equalsIgnoreCase("reload")) {

				BlockBreakEvent.getHandlerList().unregister(this);
				PlayerInteractEvent.getHandlerList().unregister(this);

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

				Metrics m = new Metrics(this);

				if (getConfig().getBoolean("fix-pots")) {

					Bukkit.getPluginManager().registerEvents(new FlowerPot(), this);

				}
				if (getConfig().getBoolean("fix-double-plants")) {

					Bukkit.getPluginManager().registerEvents(new DoublePlant(), this);

				}
				if (getConfig().getBoolean("fix-fast-break")) {

					Bukkit.getPluginManager().registerEvents(new FastBreak(), this);

				}
				if (getConfig().getBoolean("fix-pot-place")) {

					Bukkit.getPluginManager().registerEvents(new PlacePot(), this);

				}
				if (getConfig().getBoolean("fix-pistons")) {

					Bukkit.getPluginManager().registerEvents(new PistonExtension(), this);

				}

				m.addCustomChart(new Metrics.SimplePie("fix_pots_take", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

					}
				}));

				m.addCustomChart(new Metrics.SimplePie("fix_double_plants", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

					}
				}));

				m.addCustomChart(new Metrics.SimplePie("fix_fast_break", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

					}
				}));

				m.addCustomChart(new Metrics.SimplePie("fix_pots_place", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled";

					}
				}));
				
				m.addCustomChart(new Metrics.SimplePie("fix_piston_break", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("fix-pistons") ? "Enabled" : "Disabled";

					}
				}));
				
				m.addCustomChart(new Metrics.SimplePie("advanced_checks", new Callable<String>() {
					@Override
					public String call() throws Exception {

						return getConfig().getBoolean("advanced-check") ? "Enabled" : "Disabled";

					}
				}));

				SpigetUpdate updater = new SpigetUpdate(this, 58442);

				// This converts a semantic version to an integer and checks if
				// the
				// updated version is greater
				updater.setVersionComparator(VersionComparator.SEM_VER);

				updater.checkForUpdate(new UpdateCallback() {
					@Override
					public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
						//// A new version is available
						// newVersion - the latest version
						// downloadUrl - URL to the download
						// hasDirectDownload - whether the update is available
						//// for a
						//// direct download on spiget.org
						Bukkit.getConsoleSender().sendMessage("§e" + getName() + " is updating!");
						if (hasDirectDownload) {
							if (updater.downloadUpdate()) {
								// Update downloaded, will be loaded when the
								// server
								// restarts
								Bukkit.getConsoleSender()
										.sendMessage("§bUpdate downloaded, will be loaded when the server restarts");
							} else {
								// Update failed
								getLogger().warning("Update download failed, reason is " + updater.getFailReason());
							}
						}
					}

					@Override
					public void upToDate() {
						//// Plugin is up-to-date
						Bukkit.getConsoleSender().sendMessage("§b" + getName() + " is up to date!");
					}
				});

				sender.sendMessage("§aReload complete.");

				return true;
			}

		}

		return false;

	}
}
