package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_kick extends Command {

	private main plugin;

	public COMMAND_kick(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
			
		if(sender.hasPermission("sup") || sender.hasPermission("content")) {
			if(args.length == 1) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if(target != null) {
					target.disconnect("§6§lKnowNix\n \n§6Du wurdest gekickt!");
					for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
						if(players.hasPermission("sup") || players.hasPermission("content")) {
							if(Settings_methoden.ban(players)) {
								players.sendMessage(plugin.prefix + "§7Der Spieler §e" + target.getName() + " §7wurde von §c" + sender.getName() + " §7vom Netzwerk gekickt!");
							}
						}
	 				}
					
				} else {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler ist nicht online!");
				}
			} else
				if(args.length >= 2) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
					if(target != null) {
						String reason = "";
						for(int i = 1; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						target.disconnect("§6§lKnowNix\n \n§6Du wurdest gekickt!\n§4Grund: §c" + reason);
						for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
							if(players.hasPermission("sup") || players.hasPermission("content")) {
								if(Settings_methoden.ban(players)) {
									players.sendMessage(plugin.prefix + "§7Der Spieler §e" + target.getName() + " §7wurde von §c" + sender.getName() + " §7vom Netzwerk gekickt!");
									players.sendMessage("§4Grund §8» §7" + reason);
								}
							}
						}
						
					} else {
						sender.sendMessage(plugin.prefix + "§cDieser Spieler ist nicht online!");
					}
				} else {
					sender.sendMessage(plugin.prefix + "§c/kick [Spielername] [Grund]");
				}
		}
		
	}

}
