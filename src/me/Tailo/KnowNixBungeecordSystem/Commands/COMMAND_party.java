package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_party extends Command {

	@SuppressWarnings("unused")
	private main plugin;

	public COMMAND_party(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(args.length == 0) {
				
				sendUsage(p);
				
			} else
				if(args.length == 1) {
					
					if(args[0].equalsIgnoreCase("leave")) {
						
						Party_methoden.leave(p);
						
					} else
						if(args[0].equalsIgnoreCase("list")) {
							
							Party_methoden.list(p);
							
						} else {
							sendUsage(p);
						}
					
				} else
					if(args.length == 2) {
						
						if(args[0].equalsIgnoreCase("invite")) {
							
							Party_methoden.invite(p, args[1]);
							
						} else
							if(args[0].equalsIgnoreCase("accept")) {
								
								Party_methoden.accept(p, args[1]);
								
							} else
								if(args[0].equalsIgnoreCase("kick")) {
									
									Party_methoden.kick(p, args[1]);
									
								} else
									if(args[0].equalsIgnoreCase("promote")) {
										
										Party_methoden.promote(p, args[1]);
										
									} else{
										sendUsage(p);
									}
						
					} else {
						sendUsage(p);
					}
			
			} else {
				sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
			}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendUsage(ProxiedPlayer p) {
		
		p.sendMessage("§7==========§e[ Party ]§7==========");
		p.sendMessage("§e/party invite <Spielername> §8- §7Lade einen Spieler in deine Party ein.");
		p.sendMessage("§e/party accept <Spielername> §8- §7Nimmt die Anfrage an.");
		p.sendMessage("§e/party leave §8- §7Verlasse deine Party.");
		p.sendMessage("§e/party kick <Spielername> §8- §7Werfe einen Spieler aus deiner Party.");
		p.sendMessage("§e/party promote <Spielername> §8- §7Mache einen anderen Spieler zum Leiter deiner Party.");
		p.sendMessage("§e/p <Nachricht> §8- §7Sende einen Nachricht an alle Mitglieder der Party.");
		p.sendMessage("§7==========§e[ Party ]§7==========");
		
	}

}
