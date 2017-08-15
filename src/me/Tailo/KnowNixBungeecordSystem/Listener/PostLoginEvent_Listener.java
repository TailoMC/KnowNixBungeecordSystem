package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.Permission.Permission_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public PostLoginEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPostLogin(PostLoginEvent e) {
		
		ProxiedPlayer p = e.getPlayer();
		
		Permission_methoden.loadPermissions(p, Permission_methoden.getGroup(p));
		
		int MaxPlayers = ProxyServer.getInstance().getConfig().getPlayerLimit();
		int OnlinePlayers = ProxyServer.getInstance().getPlayers().size();
		
		if(OnlinePlayers >= MaxPlayers) {
			if(!Ban_Listener.isBanned(p.getUniqueId().toString())) {
				if(!(p.hasPermission("premium") | p.hasPermission("joinfull"))) {
					p.disconnect("§6§lKnowNix.de\n \n§c§lDer Server ist voll!\n§3Um dennoch zu §ejoinen§3 brauchst du §6Premium§3!");
				}
			}
		}
		
		Settings_methoden.createFriendsIfNotExists(p);
		Settings_methoden.createNickIfNotExists(p);
		Settings_methoden.createNotifyIfNotExists(p);
		
		if(!Ban_Listener.isBanned(p.getUniqueId().toString())) {
			
			Friend_methoden.sendOnlineMsg(p);		
			Friend_methoden.preLoad(p);
			
		}
		
		if(plugin.silentlobby.contains(p.getName()) && !(p.hasPermission("vip") || p.hasPermission("sup") || p.hasPermission("content") || p.hasPermission("builder"))) {
			plugin.silentlobby.remove(p.getName());
		}
		if(plugin.viplobby.contains(p.getName()) && !(p.hasPermission("vip") || p.hasPermission("sup") || p.hasPermission("content") || p.hasPermission("builder"))) {
			plugin.viplobby.remove(p.getName());
		}
		
	}

}
