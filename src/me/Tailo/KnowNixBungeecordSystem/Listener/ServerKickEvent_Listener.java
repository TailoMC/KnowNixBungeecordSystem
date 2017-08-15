package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.Methoden.JoinLobby_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Notify_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public ServerKickEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerKick(ServerKickEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		e.setCancelled(true);
		
		String checkreason = e.getKickReason().replace("§f", "");
		
		if(!checkreason.equals("Afk")) {
			
			String reason = e.getKickReason().replaceAll("§c", "§c");
			
			if(reason.contains("Flying is not enabled on this server")) {
				
				Notify_methoden.sendChatAcNotify(p, "§b§l" + p.getName() + "§c §e► §9§lFly §e(Wurde §ewegen §efliegen §egekickt §e(Fly-Hack §eoder §elagging)) §a[Ping: §a" + p.getPing() + "§a]", p.getServer().getInfo().getName());
			}
			
			if(p.getServer() != null && !p.getServer().getInfo().getName().contains("lobby")) {
				
				if(plugin.silentlobby.contains(p.getName())) {
					
					ServerInfo info = ProxyServer.getInstance().getServerInfo("silentlobby-1");
					
					e.setCancelServer(info);
					
				} else if(plugin.viplobby.contains(p.getName())) {
					
					ServerInfo info = ProxyServer.getInstance().getServerInfo("viplobby-1");
					
					e.setCancelServer(info);
					
				} else {
					
					e.setCancelServer(JoinLobby_methoden.getLobby());
					
				}
				
			} else {
				
				if(e.getKickedFrom().getName().toLowerCase().startsWith("lobby")) {
					
					e.setCancelled(false);
					
					return;
				}
				
				e.setCancelServer(null);
			}
			
			p.sendMessage(e.getKickReason());
			
		} else {
			
			p.disconnect("§6§lKnowNix\n \n§eDu wurdest auf Grund von Inaktivität gekickt!");
			
		}
		
	}

}
