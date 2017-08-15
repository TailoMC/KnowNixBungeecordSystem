package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabCompleteEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public TabCompleteEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@EventHandler
	public void onTabComplete(TabCompleteEvent e) {
		
		if(e.getCursor().equals("/") || e.getCursor().toLowerCase().contains("/help") || e.getCursor().toLowerCase().contains("/?") || e.getCursor().toLowerCase().contains("bukkit") || e.getCursor().toLowerCase().contains("reflex") || e.getCursor().toLowerCase().contains("spartan") || e.getCursor().toLowerCase().contains("version") || e.getCursor().toLowerCase().contains("ver") || e.getCursor().toLowerCase().contains("about")) {
			e.setCancelled(true);
		}
		
		if(e.getCursor().startsWith("/") && !e.getCursor().contains(" ")) {
			e.setCancelled(true);
		}
		
	}

}
