package com.fren_gor.visualFixer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
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

import com.fren_gor.visualFixer.v1_13.Kelp;
import com.fren_gor.visualFixer.v1_13.TallSeagrass;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

    public static int version;
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        version = Integer.parseInt(ReflectionUtil.getCompleteVersion().split("_")[1]);

        getCommand("visualfixer").setTabCompleter(this);

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
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.FlowerPot(), this);
            else
                Bukkit.getPluginManager().registerEvents(new FlowerPot(), this);
        }
        if (getConfig().getBoolean("fix-double-plants")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.DoublePlant(), this);
            else
                Bukkit.getPluginManager().registerEvents(new DoublePlant(), this);
        }
        if (getConfig().getBoolean("fix-fast-break")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.FastBreak(), this);
            else
                Bukkit.getPluginManager().registerEvents(new FastBreak(), this);
        }
        if (getConfig().getBoolean("fix-pot-place")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.PlacePot(), this);
            else
                Bukkit.getPluginManager().registerEvents(new PlacePot(), this);
        }
        if (getConfig().getBoolean("fix-pistons")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.PistonExtension(), this);
            else
                Bukkit.getPluginManager().registerEvents(new PistonExtension(), this);
        }
        if (getConfig().getBoolean("fix-double-plant-place")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.DoublePlantsPlace(), this);
            else
                Bukkit.getPluginManager().registerEvents(new DoublePlantsPlace(), this);
        }

        if (getConfig().getBoolean("fix-doors")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Door(), this);
            else
                Bukkit.getPluginManager().registerEvents(new Door(), this);
        }

        if (getConfig().getBoolean("fix-beds")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Bed(), this);
            else
                Bukkit.getPluginManager().registerEvents(new Bed(), this);
        }

        if (version > 8 && getConfig().getBoolean("fix-chorus")) {
            if (version >= 13)
                Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Chorus(), this);
            else
                Bukkit.getPluginManager().registerEvents(new Chorus(), this);
        }

        if (version >= 13) {
            if (getConfig().getBoolean("fix-kelps")) {
                Bukkit.getPluginManager().registerEvents(new Kelp(), this);
            }

            if (getConfig().getBoolean("fix-tall-seagrass")) {
                Bukkit.getPluginManager().registerEvents(new TallSeagrass(), this);
            }
        }

        Metrics m = new Metrics(this, 2823);
        m.addCustomChart(new SimplePie("fix_pots_take", () -> getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_double_plants", () -> getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_fast_break", () -> getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_pots_place", () -> getConfig().getBoolean("fix-fast-break") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_piston_break", () -> getConfig().getBoolean("fix-pistons") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("advanced_checks", () -> getConfig().getBoolean("advanced-check") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_kelp_break", () -> getConfig().getBoolean("fix-kelps") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_tall_seagrass_break", () -> getConfig().getBoolean("fix-tall-seagrass") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_chorus", () -> getConfig().getBoolean("fix-chorus") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_double_plant_place", () -> getConfig().getBoolean("fix-double-plant-place") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_doors_place", () -> getConfig().getBoolean("fix-doors") ? "Enabled" : "Disabled"));
        m.addCustomChart(new SimplePie("fix_beds_place", () -> getConfig().getBoolean("fix-beds") ? "Enabled" : "Disabled"));

        checkForUpdates();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender instanceof Player)
            if (!sender.hasPermission("visualfix.visualfixer"))
                return Collections.emptyList();
        return Collections.singletonList("reload");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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

            sender.sendMessage("§aVisual reloaded");
            return true;
        }

        if (label.equalsIgnoreCase("visualfixer") || label.equalsIgnoreCase("visualfixer:visualfixer")) {
            if (!sender.hasPermission("visualfix.visualfixer")) {
                sender.sendMessage("§bInstalled §9VisualFixer §bversion " + getDescription().getVersion());
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage("§bInstalled §9VisualFixer §bversion " + getDescription().getVersion());
                TextComponent t = new TextComponent(
                        new ComponentBuilder("Use ").color(net.md_5.bungee.api.ChatColor.GRAY).create());
                TextComponent t1 = new TextComponent(new ComponentBuilder("/visualfixer reload")
                        .color(net.md_5.bungee.api.ChatColor.GRAY).underlined(true)
                        .event(new ClickEvent(Action.RUN_COMMAND, "/visualfixer reload")).create());
                TextComponent t2 = new TextComponent(
                        new ComponentBuilder(" to reload").color(net.md_5.bungee.api.ChatColor.GRAY).create());
                t1.addExtra(t2);
                t.addExtra(t1);
                if (version > 11) {
                    sender.spigot().sendMessage(t);
                } else if (sender instanceof Player) {
                    ((Player) sender).sendRawMessage(ComponentSerializer.toString(t));
                } else {
                    sender.sendMessage("§7Use " + ChatColor.UNDERLINE + "/visualfixer reload§r§7 to reload");
                }
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

                if (getConfig().getBoolean("fix-pots")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.FlowerPot(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new FlowerPot(), this);

                }
                if (getConfig().getBoolean("fix-double-plants")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.DoublePlant(),
                                this);
                    else
                        Bukkit.getPluginManager().registerEvents(new DoublePlant(), this);

                }
                if (getConfig().getBoolean("fix-fast-break")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.FastBreak(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new FastBreak(), this);

                }
                if (getConfig().getBoolean("fix-pot-place")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.PlacePot(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new PlacePot(), this);

                }
                if (getConfig().getBoolean("fix-pistons")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.PistonExtension(),
                                this);
                    else
                        Bukkit.getPluginManager().registerEvents(new PistonExtension(), this);

                }
                if (getConfig().getBoolean("fix-double-plant-place")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.DoublePlantsPlace(),
                                this);
                    else
                        Bukkit.getPluginManager().registerEvents(new DoublePlantsPlace(), this);

                }

                if (getConfig().getBoolean("fix-doors")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Door(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new Door(), this);

                }

                if (getConfig().getBoolean("fix-beds")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Bed(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new Bed(), this);

                }

                if (version > 8 && getConfig().getBoolean("fix-chorus")) {

                    if (version >= 13)
                        Bukkit.getPluginManager().registerEvents(new com.fren_gor.visualFixer.v1_13.Chorus(), this);
                    else
                        Bukkit.getPluginManager().registerEvents(new Chorus(), this);

                }

                if (version >= 13) {

                    if (getConfig().getBoolean("fix-kelps")) {

                        Bukkit.getPluginManager().registerEvents(new Kelp(), this);

                    }

                    if (getConfig().getBoolean("fix-tall-seagrass")) {

                        Bukkit.getPluginManager().registerEvents(new TallSeagrass(), this);

                    }

                }

                sender.sendMessage("§aReload complete.");
                return true;
            }
        }

        return false;
    }

    private void checkForUpdates() {
        final int resource_id = 58442;
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resource_id).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNextLine()) {
                    if (!this.getDescription().getVersion().equalsIgnoreCase(scanner.next())) {
                        Bukkit.getScheduler().runTask(this, () -> {
                            getLogger().info("A new version of " + this.getDescription().getName() + " is out! Download it at https://www.spigotmc.org/resources/" + resource_id);
                        });
                    }
                }
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(this, () -> {
                    getLogger().info("Cannot look for updates: " + e.getMessage());
                });
            }
        });
    }
}
