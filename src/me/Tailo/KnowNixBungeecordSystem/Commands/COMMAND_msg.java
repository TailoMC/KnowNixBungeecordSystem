package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Listener.Mute_Listener;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Nicks_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_msg extends Command {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public COMMAND_msg(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length >= 2) {
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			if(target != null) {
				
				if(target != sender) {
					
					if(sender instanceof ProxiedPlayer) {
						
						ProxiedPlayer p = (ProxiedPlayer) sender;
						
						if(p.hasPermission("sup") || Friend_methoden.isFriended(p, target.getUniqueId().toString())) {
							
							String message = "";
							for(int msg = 1; msg < args.length; msg++) {
								message = message + args[msg] + " ";
							}
							
							msg(p, target, message);
							
						} else {
							sender.sendMessage(plugin.msgpf + "§cDieser Spieler ist nicht online oder du bist nicht mit diesem Spieler befreundet!");
						}
						
					} else {
						
						String message = "";
						for(int msg = 1; msg < args.length; msg++) {
							message = message + args[msg] + " ";
						}
						
						sender.sendMessage(plugin.msgpf + "§4§lSYSTEM §7➡ " + target.getDisplayName() + "§7: §e" + message);
						target.sendMessage(plugin.msgpf + "§4§lSYSTEM §7➡ " + target.getDisplayName() + "§7: §e" + message);
						
					}
					
				} else {
					sender.sendMessage(plugin.msgpf + "§cDu kannst keine Nachricht an dich selber schreiben!");
				}
			} else {
				if(Nicks_methoden.isNickedPlayer(args[0])) {
					sender.sendMessage(plugin.msgpf + "§cDieser Spieler möchte keine privaten Nachrichten empfangen!");
				} else {
					sender.sendMessage(plugin.msgpf + "§cDieser Spieler ist nicht online oder du bist nicht mit diesem Spieler befreundet!");
				}
			}
		} else {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			p.sendMessage("§c/msg [Spieler] [Nachricht]");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void msg(ProxiedPlayer p, ProxiedPlayer target, String message) {
		
		String uuid = p.getUniqueId().toString();
		
		if(!Mute_Listener.isMuted(uuid)) {
			
			if(Settings_methoden.msg(target) || p.hasPermission("sup")) {
				
				p.sendMessage(plugin.msgpf + p.getDisplayName() + " §7➡ " + target.getDisplayName() + "§7: §e" + message);
				target.sendMessage(plugin.msgpf + p.getDisplayName() + " §7➡ " + target.getDisplayName() + "§7: §e" + message);
				
				plugin.msg.put(target.getName(), p.getName());
				plugin.msg.put(p.getName(), target.getName());
				
			} else {
				p.sendMessage(plugin.msgpf + "§cDieser Spieler möchte keine privaten Nachrichten empfangen!");
			}
			
		} else {
			Mute_Listener.mutemsg(p);
		}
		
	}

}
