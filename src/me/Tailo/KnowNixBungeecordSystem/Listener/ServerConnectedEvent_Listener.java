package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.Methoden.BungeeParty;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.PartyManager;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectedEvent_Listener implements Listener {

	private main plugin;

	public ServerConnectedEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getPluginManager().registerListener(main, this);
	}
	
	@EventHandler
	public void onServerConnected(ServerConnectedEvent e) {
		
		ProxiedPlayer p = e.getPlayer();
		
		Friend_methoden.updateServer(p, e.getServer().getInfo().getName().toUpperCase());
		
		if(e.getServer().getInfo().getName().toLowerCase().startsWith("lobby")) {
			if(plugin.silentlobby.contains(p.getName())) {
				plugin.silentlobby.remove(p.getName());
			}
			if(plugin.viplobby.contains(p.getName())) {
				plugin.viplobby.remove(p.getName());
			}
		}
		if(e.getServer().getInfo().getName().startsWith("silentlobby")) {
			if(!plugin.silentlobby.contains(p.getName())) {
				plugin.silentlobby.add(p.getName());
			}
		}	
		if(e.getServer().getInfo().getName().startsWith("viplobby")) {
			if(!plugin.viplobby.contains(p.getName())) {
				plugin.viplobby.add(p.getName());
			}
		}
		
		BungeeParty party = PartyManager.getParty(p.getName());
		
		if(party != null && party.getOwner() == p) {
			
			Party_methoden.sendParty(party.getMembers(), e.getServer().getInfo());
			
		}
		
	}

}
