package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.Methoden.BungeeParty;
import me.Tailo.KnowNixBungeecordSystem.Methoden.JoinLobby_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Nicks_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.PartyManager;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Server;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Server.Connection;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public ServerConnectEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	public void onServerConnect(ServerConnectEvent e) {
		
		ProxiedPlayer p = e.getPlayer();
		
		if(plugin.wartung) {
			
			if(!(p.hasPermission("sup") || p.hasPermission("content") || p.hasPermission("builder"))) {
				e.setCancelled(true);
				p.disconnect(plugin.info);
				return;
			}
			
		}
		
		if(plugin.getparty.containsKey(p.getName())) {
			
			if(PartyManager.getParty(p.getName()).getOwner() == p) {
				
				if(!e.getTarget().getName().startsWith("silentlobby-1")) {
					
					Server server = new Server(e.getTarget().getAddress().getPort());
					
					int maxplayers = Integer.parseInt(server.parseData(Connection.MAX_PLAYERS));
					
					if(!((maxplayers - e.getTarget().getPlayers().size()) < PartyManager.getParty(p.getName()).getMembers().size())) {
						
						p.sendMessage(plugin.partypf + "§5Deine Party betritt jetzt §6" + e.getTarget().getName().toUpperCase() + "§5!");
						
						for(String members : PartyManager.getParty(p.getName()).getMembers()) {
							ProxiedPlayer target = ProxyServer.getInstance().getPlayer(members);
							if(target != p) {
								if(target.getServer().getInfo() != e.getTarget()) {
									target.connect(e.getTarget());
								}
							}
						}
						
					} else {
						e.setCancelled(true);
						
						p.sendMessage(plugin.partypf + "§cDeine Party hat keinen Platz auf diesem Server!");
					}
					
				}
				
			}
			
		}
		
		if(e.getTarget().getName().equals("fallback")) {
			
			e.setTarget(JoinLobby_methoden.getLobby());
			
			if(plugin.silentlobby.contains(p.getName())) {
				e.setTarget(ProxyServer.getInstance().getServerInfo("silentlobby-1"));
			}
			if(plugin.viplobby.contains(p.getName())) {
				e.setTarget(ProxyServer.getInstance().getServerInfo("viplobby-1"));
			}
			
		}
		
		if(!e.isCancelled() && p.getServer() != e.getTarget()) {
			
			BungeeParty party = PartyManager.getParty(p.getName());
			
			if(party != null && party.getOwner() == p) {
				
				Nicks_methoden.sendParty(party.getMembers(), e.getTarget());
				
			}
			
		}
		
	}

}
