package me.Tailo.KnowNixBungeecordSystem.Commands;

import java.util.ArrayList;
import java.util.List;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_staff extends Command {

	private main plugin;

	public COMMAND_staff(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
			
		if(sender.hasPermission("sup") | sender.hasPermission("content") | sender.hasPermission("builder")) {
			
			List<String> staff = new ArrayList<String>();
			
			for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
				if(players.hasPermission("sup") | players.hasPermission("content") | players.hasPermission("builder")) {
					if(players != sender) {
						String info = "§a" + players.getServer().getInfo().getName().toUpperCase() + "§7 | §b" + players.getDisplayName();
						staff.add(info);
					} else {
						String info = "§a" + players.getServer().getInfo().getName().toUpperCase() + "§7 | §n" + players.getDisplayName();
						staff.add(info);
					}
				}
			}
			
			if(staff.size() != 0) {
				sender.sendMessage(plugin.prefix + "§1Teammitglieder online: " + staff.size());
				sender.sendMessage("§aSERVER §7| §bSPIELER");
				sender.sendMessage("§c---------------");
				for(int i = 0; i < staff.size();) {
					sender.sendMessage(staff.get(i));
					i++;
				}
				sender.sendMessage("§c---------------");
			} else {
				sender.sendMessage(plugin.prefix + "§cEs ist zur Zeit kein Teammitglied online!");
			}
			
		}
		
	}

}
