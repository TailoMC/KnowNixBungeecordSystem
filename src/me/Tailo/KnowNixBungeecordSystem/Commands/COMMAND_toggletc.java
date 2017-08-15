package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_toggletc extends Command {

	private main plugin;

	public COMMAND_toggletc(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(p.hasPermission("sup") | p.hasPermission("builder") | p.hasPermission("content")) {
				
				if(plugin.teamchat.contains(p.getName())) {
					
					plugin.teamchat.remove(p.getName());
					p.sendMessage(plugin.prefix + "§bDu hast den permanenten Teamchat §cdeaktiviert§b!");
					
				} else {
					
					plugin.teamchat.add(p.getName());
					p.sendMessage(plugin.prefix + "§bDu hast den permanenten Teamchat §aaktiviert§b!");
					
				}
				
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
