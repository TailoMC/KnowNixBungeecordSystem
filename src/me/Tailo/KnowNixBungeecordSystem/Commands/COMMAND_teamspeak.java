package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Bots.Teamspeak;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_teamspeak extends Command {

	private main plugin;

	public COMMAND_teamspeak(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					ProxiedPlayer p = (ProxiedPlayer) sender;
					
					if(args.length == 1 && args[0].equalsIgnoreCase("show")) {
						
						String tsid = Teamspeak.getTsID(p);
						
						if(tsid != null) {
							
							p.sendMessage(plugin.tspf + "§eVerknüpfte Identität: §7" + tsid);
							
						} else {
							
							p.sendMessage(plugin.tspf + "§cDu hast dich noch nicht verifiziert!");
							
						}				
						
					} else if(args.length == 1 && args[0].equalsIgnoreCase("remove")) {
						
						if(Teamspeak.isVerified(p)) {
							
							Teamspeak.removeVerification(p);
							
							p.sendMessage(plugin.tspf + "§7Deine Verknüpfung mit dem Teamspeak wurde entfernt!");
							
						} else {
							
							p.sendMessage(plugin.tspf + "§cDu bist nicht verifiziert!");
							
						}	
						
					} else if(args.length == 2 && args[0].equalsIgnoreCase("set")) {
						
						Teamspeak.notifyVerification(p, args[1]);
						
					} else {
						
						p.sendMessage(plugin.tspf + "§eTeamspeak Verwaltung");
						p.sendMessage("§e/teamspeak show §7Zeige deine aktuell verknüpfte Identität");
						p.sendMessage("§e/teamspeak set [Identität] §7Verknüpfe deine Minecraft-Identität mit deiner Teamspeak-Identität");
						p.sendMessage("§e/teamspeak remove §7Entferne deine aktuell verknüpfte Identität");
						
					}
					
				}
			}).start();
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
