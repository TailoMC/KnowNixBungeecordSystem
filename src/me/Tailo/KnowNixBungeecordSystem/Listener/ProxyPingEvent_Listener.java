package me.Tailo.KnowNixBungeecordSystem.Listener;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import me.Tailo.KnowNixBungeecordSystem.System.main;

public class ProxyPingEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public ProxyPingEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		
		ServerPing ping = e.getResponse();
		
		if(plugin.wartung) {
			
			ServerPing.Protocol version = ping.getVersion();
			
			version.setName(plugin.info);
			version.setProtocol(2);
			
			ping.setVersion(version);
			
		}
		
	}
	
}
