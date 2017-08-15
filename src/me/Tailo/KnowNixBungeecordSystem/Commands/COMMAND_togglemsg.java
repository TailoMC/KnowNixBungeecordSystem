package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_togglemsg extends Command {

	public COMMAND_togglemsg(String name, main main) {
		super(name);
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("ban")) {
				
					if(p.hasPermission("sup") || p.hasPermission("content")) {
									
						Settings_methoden.toggleBan(p);
						
						return;
					} 
					
				} else
				
				if(args[0].equalsIgnoreCase("team")) {
								
					if(p.hasPermission("sup") || p.hasPermission("content") || p.hasPermission("builder")) {
									
						Settings_methoden.toggleTeamChat(p);
						
						return;
					}
								
				}
				
				if(args[0].equalsIgnoreCase("notify")) {
					
					if(p.hasPermission("sup")) {
						
						Settings_methoden.toggleNotify(p);
						
						return;
					}
					
				}
				
				if(args[0].equalsIgnoreCase("bangui")) {
					
					if(p.hasPermission("sup") || p.hasPermission("content")) {
									
						Settings_methoden.toggleBan(p);
						
					} 
					
				} else
				
				if(args[0].equalsIgnoreCase("teamchatgui")) {
								
					if(p.hasPermission("sup") || p.hasPermission("content") || p.hasPermission("builder")) {
									
						Settings_methoden.toggleTeamChat(p);
						
					}
								
				}
				
				if(args[0].equalsIgnoreCase("notifygui")) {
					
					if(p.hasPermission("sup") || p.hasPermission("content")) {
						
						Settings_methoden.toggleNotify(p);
						
					}
					
				}
				
				setupmsg(p);
				
			} else {
				
				setupmsg(p);
				
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}
	
	private void setupmsg(ProxiedPlayer p) {
		
		if(p.hasPermission("sup") || p.hasPermission("content")) {
			setupmsgteam(p);
		} else if(p.hasPermission("builder")) {
			setupmsgbuilder(p);
		}
		
	}

	@SuppressWarnings("deprecation")
	private void setupmsgteam(ProxiedPlayer p) {
		
		p.sendMessage("§6----------§3[§6Benachrichtigungen§3]§6----------");
		
		TextComponent ban = new TextComponent();
		String banc = getColor(p, "ban");
		ban.setText(banc + "Ban/Mute/Kick " + banc + "Benachrichtigungen");
		ban.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3/togglemsg ban").create()));
		ban.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/togglemsg bangui"));
		
		TextComponent team = new TextComponent();
		String teamc = getColor(p, "teamchat");
		team.setText(teamc + "Teamchat");
		team.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3/togglemsg teamchat").create()));
		team.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/togglemsg teamchatgui"));
		
		TextComponent notify = new TextComponent();
		String notifyc = getColor(p, "notify");
		notify.setText(notifyc + "Verstoß " + notifyc + "Benachtichtigungen");
		notify.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3/togglemsg notify").create()));
		notify.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/togglemsg notifygui"));
		
		p.sendMessage("");
		p.sendMessage(ban);
		p.sendMessage("");
		p.sendMessage(team);
		p.sendMessage("");
		p.sendMessage(notify);
		p.sendMessage("");
		
		p.sendMessage("§6---------------------------------");
		
	}

	@SuppressWarnings("deprecation")
	private void setupmsgbuilder(ProxiedPlayer p) {
		
		p.sendMessage("§6----------§3[§6Benachrichtigungen§3]§6----------");
		
		TextComponent team = new TextComponent();
		String teamc = getColor(p, "teamchat");
		team.setText(teamc + "Teamchat");
		team.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3/togglemsg teamchat").create()));
		team.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/togglemsg teamchatgui"));
		
		p.sendMessage("");
		p.sendMessage(team);
		p.sendMessage("");
		
		p.sendMessage("§6---------------------------------");
		
	}
	
	private String getColor(ProxiedPlayer p, String cmd) {
		
		boolean bol = false;
		
		if (cmd.equals("ban")) {
			bol = Settings_methoden.ban(p);
		} else if (cmd.equals("teamchat")) {
			bol = Settings_methoden.teamchat(p);
		} else if (cmd.equals("notify")) {
			bol = Settings_methoden.notify(p);
		}
		
		if(bol) {
			return "§a";
		} else {
			return "§c";
		}
		
	}

}
