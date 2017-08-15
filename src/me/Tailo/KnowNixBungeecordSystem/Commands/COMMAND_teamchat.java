package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_teamchat extends Command {

	@SuppressWarnings("unused")
	private main plugin;

	public COMMAND_teamchat(String name, main main) {
		super(name);
		this.plugin = main;
	}
	
	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("sup") | sender.hasPermission("builder") | sender.hasPermission("content")) {
			if(args.length < 1) {
				sender.sendMessage("§c/teamchat <Nachricht>");
			} else {
				if(sender instanceof ProxiedPlayer) {
					
					ProxiedPlayer p = (ProxiedPlayer) sender;
					
					String message = "";
					for(int msg = 0; msg < args.length; msg++) {
						message = message + args[msg] + " ";
					}
					
					teamchat(p, message);

				} else {
					
					String message = "";
					for(int msg = 0; msg < args.length; msg++) {
						message = message + args[msg] + " ";
					}
					
					for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
						if(players.hasPermission("sup") | players.hasPermission("builder") | players.hasPermission("content")) {
							if(Settings_methoden.teamchat(players)) {
								
								message = message.replace(" ", " §7");
								
								players.sendMessage("§cTeamChat §8» §4§lSYSTEM§8: §7" + message);
								
							}
						}
					}
					
				}
			}
		}
		
	}
	
	public static void teamchat(ProxiedPlayer p, String message) {
		
		for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
			if(players.hasPermission("sup") | players.hasPermission("builder") | players.hasPermission("content")) {
				if(Settings_methoden.teamchat(players)) {
					
					message = message.replace(" ", " §7");
					
					TextComponent tc = new TextComponent("§cTeamChat §8» " + p.getDisplayName() + "§8: §7" + message);
					TextComponent server = new TextComponent("§8(" + p.getServer().getInfo().getName() + ")");
					server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aZu " + p.getDisplayName() + " §aspringen!").create()));
					server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpserver " + p.getName()));
					
					tc.addExtra(server);
					
					players.sendMessage(tc);
				}
			}
		}
		
	}

}
