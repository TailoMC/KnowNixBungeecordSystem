package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_gannounce extends Command {

	private main plugin;

	public COMMAND_gannounce(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("srmod")) {
			
			if(args.length != 0) {
				
				try {
					
					String msg = "";
					for(String s : args) {
						msg = msg + " " + s;
					}
					String zeile1 = ChatColor.translateAlternateColorCodes('&', msg.split(";")[0]);
					String zeile2 = ChatColor.translateAlternateColorCodes('&', msg.split(";")[1]);
					
					Title title = plugin.getProxy().createTitle()
				    		.reset()
				    		.title(new ComponentBuilder(zeile1).create())
				    		.subTitle(new ComponentBuilder(zeile2).create());
					
					for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					    players.sendTitle(title);
					}
					
				} catch(Exception e) {
					sender.sendMessage(plugin.prefix + "§c/announce [Zeile1]§4;§c[Zeile2]");
				}
				
			} else {
				sender.sendMessage(plugin.prefix + "§c/announce [Zeile1];[Zeile2]");
			}
			
		}
		
	}

}
