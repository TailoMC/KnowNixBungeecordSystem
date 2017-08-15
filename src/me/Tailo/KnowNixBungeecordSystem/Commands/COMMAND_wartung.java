package me.Tailo.KnowNixBungeecordSystem.Commands;

import java.io.File;
import java.io.IOException;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class COMMAND_wartung extends Command {

	private main plugin;

	public COMMAND_wartung(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("admin") || sender.hasPermission("srmod") || sender.hasPermission("dev") || sender.hasPermission("srdev") || sender instanceof ConsoleCommandSender) {
			
			try {
				if(!plugin.getDataFolder().exists()) {
					plugin.getDataFolder().mkdir();
				}
				File file = new File(plugin.getDataFolder().getPath(), "settings.yml");
				if(!file.exists()) {
					file.createNewFile();
				}
				Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
					
				if(args.length >= 1) {
					
					String info = "";
					for(int i = 0; i < args.length; i++) {
						info = info + args[i] + " ";
					}
					
					plugin.wartung = true;
					plugin.info = ChatColor.translateAlternateColorCodes('&', info);
					
					for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
						if(!(players.hasPermission("sup") || players.hasPermission("content") || players.hasPermission("builder"))) {
							players.disconnect(plugin.info);
						}
					}
					
					cfg.set("wartung.wartung", true);
					cfg.set("wartung.info", plugin.info);
					
					sender.sendMessage(plugin.prefix + "§3Der Server ist jetzt im Wartungsmodus!");
					
				} else {
					
					if(cfg.getBoolean("wartung.wartung")) {
						plugin.wartung = false;
						cfg.set("wartung.wartung", false);
						
						sender.sendMessage(plugin.prefix + "§3Der Server ist jetzt nicht mehr im Wartungsmodus!");
					} else {
						
						sender.sendMessage(plugin.prefix + "§c/wartung [Info]");
						
					}
				}
				
				save(cfg, file);
			
			} catch(IOException ex) {			
			}
			
		}
		
	}
	
	public static void loadWartung(main plugin) {
		
		try {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			File file = new File(plugin.getDataFolder().getPath(), "settings.yml");
			if(!file.exists()) {
				file.createNewFile();
			}
			Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			
			if(cfg.getBoolean("wartung.wartung")) {
				plugin.wartung = true;
				plugin.info = cfg.getString("wartung.info");
			} else {
				plugin.wartung = false;
			}
		
		} catch(IOException ex) {			
		}
		
	}
	
	public static void save(Configuration cfg, File file) {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
