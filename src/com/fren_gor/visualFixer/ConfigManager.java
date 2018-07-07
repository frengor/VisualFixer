package com.fren_gor.visualFixer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private final static int version = 1;

	public static void load(Main m) throws IOException {

		File f = new File(m.getDataFolder(), "config.yml");

		if (!m.getDataFolder().exists()) {
			m.getDataFolder().mkdirs();
		}
		if (!f.exists())
			m.saveResource("config.yml", true);

		YamlConfiguration y = new YamlConfiguration();

		try {
			y.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		int v = 0;

		if (y.contains("config-version")) {

			v = y.getInt("config-version");

		}

		if (v >= version) {

			return;

		}

		m.saveResource("config.yml", true);

		List<String> lnew = new ArrayList<>();

		for (String s : readFile(f)) {

			if (s.isEmpty() || s.equals(" ")) {
				lnew.add("");
				continue;
			}
			if (y.contains(s.split(":")[0])) {

				s = s.split(":")[0] + ": " + y.getString(s.split(":")[0]);

			}

			lnew.add(s);

		}

		writeFile(f, lnew.toArray(new String[lnew.size()]));

	}

	public static void writeFile(File file, String... lines) throws IOException {

		if (!file.exists() || file.isDirectory())

			file.createNewFile();

		if (lines.length == 0)
			return;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

			for (String s : lines) {

				writer.write(s);
				writer.newLine();

			}
		} finally {

			writer.close();

		}

	}

	public static List<String> readFile(File file) throws IOException {
		List<String> l = new ArrayList<>();
		BufferedReader reader = null;
		String currentLine = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

			while ((currentLine = reader.readLine()) != null) {
				l.add(currentLine);
			}
		} finally {

			reader.close();

		}
		return l;
	}

}
