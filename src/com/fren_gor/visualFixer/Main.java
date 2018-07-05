package com.fren_gor.visualFixer;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public static int version;

	@Override
	public void onEnable() {

		version = Integer.parseInt(ReflectionUtil.getVersion().split("_")[1]);

		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		if (!new File(getDataFolder(), "config.yml").exists())
			saveResource("config.yml", true);

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

	}

}
