package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.JoinLobby_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_hub extends Command {

	private main plugin;

	public COMMAND_hub(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {		
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(!p.getServer().getInfo().getName().toLowerCase().startsWith("lobby")) {
				
				if(!p.getServer().getInfo().getName().equalsIgnoreCase("silentlobby-1") && !p.getServer().getInfo().getName().equalsIgnoreCase("viplobby-1")) {
					
					if(plugin.silentlobby.contains(p.getName())) {
						
						ServerInfo info = ProxyServer.getInstance().getServerInfo("silentlobby-1");
						
						p.connect(info);
						
					} else if(plugin.viplobby.contains(p.getName())) {
						
						ServerInfo info = ProxyServer.getInstance().getServerInfo("viplobby-1");
						
						p.connect(info);
						
					} else {
						
						p.connect(JoinLobby_methoden.getLobby());
						
					}
					
				} else {
					
					p.connect(JoinLobby_methoden.getLobby());
					
				}
				
			} else {
				p.sendMessage(plugin.prefix + "§7Du bist schon in der Lobby!");
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
	}

}
