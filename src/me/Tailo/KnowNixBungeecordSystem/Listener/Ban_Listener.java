package me.Tailo.KnowNixBungeecordSystem.Listener;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Ban_Listener implements Listener {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Ban_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onLogin (ServerConnectEvent e) {
		
		String uuid = e.getPlayer().getUniqueId().toString();
		
		if(isBanned(uuid)) {
			
			e.setCancelled(true);
			
			String reason = "0";
			long timeend = 0;
			
			ResultSet rs = MySQL.getResult("SELECT reason, time FROM ban WHERE UUID = '" + uuid + "'");
			
			try {
				while(rs.next()) {
					reason = rs.getString("reason");
					timeend = rs.getLong("time");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			ProxiedPlayer p = e.getPlayer();
			if(timeend != 0) {
				long now = System.currentTimeMillis();
				long time = (timeend-now)/1000;
				
				if(!reason.equals("0")) {
					if(!(now > timeend)) {
						if(time < 60) {
							p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n§3Restdauer: §6" + time + " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
						} else
							if(time < 3600) {
								p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n§3Restdauer: §6" + time/60 + " §eMinuten §3und §6" + time%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							} else
								if(time < 86400) {
									p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n§3Restdauer: §6" + time/3600 + " §eStunden §6" + (time%3600)/60 + " §eMinuten §3und §6" + (time%3600)%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
								} else {
									p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n§3Restdauer: §6" + time/86400 + " §eTage §6" + (time%86400)/3600 + " §eStunden §6" + ((time%86400)%3600)/60 + " §eMinuten §3und §6" + ((time%86400)%3600)%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
								}
					} else {
						
						MySQL.update("DELETE FROM ban WHERE UUID = '" + uuid + "'");
						
						e.setCancelled(false);
						
					}
				} else {
					if(!(now > timeend)) {
						if(time < 60) {
							p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§3Restdauer: §6" + time + " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
						} else
							if(time < 3600) {
								p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§3Restdauer: §6" + time/60 + " §eMinuten §3und §6" + time%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							} else
								if(time < 86400) {
									p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§3Restdauer: §6" + time/3600 + " §eStunden §6" + (time%3600)/60 + " §eMinuten §3und §6" + (time%3600)%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
								} else {
									p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§3Restdauer: §6" + time/86400 + " §eTage §6" + (time%86400)/3600 + " §eStunden §6" + ((time%86400)%3600)/60 + " §eMinuten §3und §6" + ((time%86400)%3600)%60 +  " §eSekunden\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
								}
					} else {						
						
						MySQL.update("DELETE FROM ban WHERE UUID = '" + uuid + "'");
						
						e.setCancelled(false);
						
					}
				}
			} else {
				if(!reason.equals("0")) {
					p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
				} else {
					p.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
				}
			}
		}
		
	}
	
	public static boolean isBanned(String uuid) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT playername FROM ban WHERE UUID = '" + uuid + "'");
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
		
	}

}
