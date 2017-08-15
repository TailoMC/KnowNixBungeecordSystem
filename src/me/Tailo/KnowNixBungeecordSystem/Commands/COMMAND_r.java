package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_r extends Command {

	private main plugin;

	public COMMAND_r(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			if(args.length > 0) {
				
				if(plugin.msg.containsKey(sender.getName())) {
					
					ProxiedPlayer p = (ProxiedPlayer) sender;
					
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(plugin.msg.get(p.getName()));
					
					if(target != null) {
						
						String message = "";
						for(int msg = 0; msg < args.length; msg++) {
							message = message + args[msg] + " ";
						}
						
						COMMAND_msg.msg(p, target, message);
						
					} else {
						sender.sendMessage(plugin.msgpf + "§cDieser Spieler ist nicht mehr online!");							
					}
					
				} else {
					sender.sendMessage(plugin.msgpf + "§cDu musst erst einem Spieler schreiben oder ein Spieler muss dir erst schreiben!");
				}
				
			} else {
				sender.sendMessage(plugin.msgpf + "§e/r <Nachricht> §cum dem letzten Spieler dem du oder der dir geschrieben hat einen Nachricht zu schicken!");
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
