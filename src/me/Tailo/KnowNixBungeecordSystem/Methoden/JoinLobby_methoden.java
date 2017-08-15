package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class JoinLobby_methoden {
	
	public static ServerInfo getLobby() {
		
		Map<ServerInfo, Integer> servers = new HashMap<ServerInfo, Integer>();
		
		ServerInfo lobbyserver = null;
		
		for(String servernames: ProxyServer.getInstance().getServers().keySet()) {
			if(servernames.startsWith("lobby")) {
				
				ServerInfo server = ProxyServer.getInstance().getServerInfo(servernames);
				int players = server.getPlayers().size();
				servers.put(server, players);
				
			}
		}
		
		int min = Collections.min(servers.values());
		
		for(ServerInfo lobby: servers.keySet()) {
			
			int value = servers.get(lobby);
			if(value == min) {
				lobbyserver = lobby;
				break;
			}
		}
		
		return lobbyserver;
		
	}
	
}
