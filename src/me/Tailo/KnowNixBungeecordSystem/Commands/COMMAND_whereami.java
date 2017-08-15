package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_whereami extends Command {

	private main plugin;

	public COMMAND_whereami(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(args.length == 1) {
				if(p.hasPermission("sup")) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
					
					if(target != null) {
						p.sendMessage(plugin.prefix + "§3Der Spieler §6" + target.getName() + " §3ist auf dem Server §6" + target.getServer().getInfo().getName().toUpperCase() + "§3!");
					} else {
						p.sendMessage(plugin.prefix + "§cDieser Spieler ist nicht online!");
					}

				}
			} else {
				p.sendMessage(plugin.prefix + "§3Du bist auf dem Server §6" + p.getServer().getInfo().getName().toUpperCase() + "§3!");
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
