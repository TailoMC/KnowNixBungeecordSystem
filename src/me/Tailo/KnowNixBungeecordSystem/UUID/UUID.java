package me.Tailo.KnowNixBungeecordSystem.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UUID {

	public static String getUUID(String playername) {
		
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playername);
		
		if(p != null) {
			return p.getUniqueId().toString();
		}
		
		try {
			return UUIDFetcher.getUUID(playername).toString();
		} catch(Exception e) {			
		}
		
		return "";
		
	}
	
	public static java.util.UUID getidUUID(String playername) {
		
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playername);
		
		if(p != null) {
			return p.getUniqueId();
		}
		
		try {
			return UUIDFetcher.getUUID(playername);
		} catch(Exception e) {			
		}
		
		return null;
		
	}
	
}
