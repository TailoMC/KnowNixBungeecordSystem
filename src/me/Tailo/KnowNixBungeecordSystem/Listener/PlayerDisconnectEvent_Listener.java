package me.Tailo.KnowNixBungeecordSystem.Listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;

public class PlayerDisconnectEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public PlayerDisconnectEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e) {
		
		ProxiedPlayer p = e.getPlayer();
		
		Party_methoden.leave(p);
		
		Friend_methoden.sendOfflineMsg(p);
		Friend_methoden.updateServer(p, "");
		
	}

}
