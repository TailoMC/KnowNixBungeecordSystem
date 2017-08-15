package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_tpserver extends Command {
	
	private main plugin;

	public COMMAND_tpserver(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(p.hasPermission("sup")) {
				if(args.length == 1) {
					
					ProxiedPlayer teleportplayer = ProxyServer.getInstance().getPlayer(args[0]);
					
					if(teleportplayer != null) {
						
						ServerInfo teleportplayerloc = teleportplayer.getServer().getInfo();
						
						p.connect(teleportplayerloc);
						
						p.sendMessage(plugin.prefix + "§3Du hast dich zu §6" + teleportplayer.getDisplayName() + " §3teleportiert!");
						p.sendMessage(plugin.prefix + "§3Du bist jetzt auf §6" + teleportplayerloc.getName() + "§3!");
						
					} else {
						p.sendMessage(plugin.prefix + "§3Der Spieler §6" + args[0] + " §3ist nicht online!");
					}
					
				} else {
					p.sendMessage(plugin.prefix + "§3/teleport [Spieler]");
				}				
			}	
		} else {
			sender.sendMessage("Du kannst den teleport Befehl nicht benutzen. Diese Funktion können nur Spieler Ingame nutzen!");
		}
		
	}
	
	

}
