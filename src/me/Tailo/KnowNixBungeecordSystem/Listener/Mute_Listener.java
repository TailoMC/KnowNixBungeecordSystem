package me.Tailo.KnowNixBungeecordSystem.Listener;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Mute_Listener implements Listener {

	private static main plugin;
	@SuppressWarnings("static-access")
	public Mute_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@EventHandler
	public void onChat(ChatEvent e) {
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			
		String uuid = p.getUniqueId().toString();
		
		if(isMuted(uuid)) {
			if(!e.getMessage().startsWith("/")) {
				e.setCancelled(true);
				mutemsg(p);
			}
		}
 		
	}
	
	public static boolean isMuted(String uuid) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT playername FROM mute WHERE UUID = '" + uuid + "'");
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void mutemsg(ProxiedPlayer p) {
		
		String uuid = p.getUniqueId().toString();
		
		String reason = "0";
		long timeend = 0;
		
		ResultSet rs = MySQL.getResult("SELECT reason, time FROM mute WHERE UUID = '" + uuid + "'");
		
		try {
			while(rs.next()) {
				reason = rs.getString("reason");
				timeend = rs.getLong("time");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		if(timeend != 0) {
			long now = System.currentTimeMillis();
			long time = (timeend-now)/1000;
			
			if(!reason.equals("0")) {
				
				if(!(now >= timeend)) {
					p.sendMessage(plugin.prefix + "§cDu wurdest gemuted!");
					p.sendMessage("§4Grund: §c" + reason);
					if(time < 60) {
						p.sendMessage("§3Du bist noch §6" + time + " §eSekunden §3gemuted!");
					} else
						if(time < 3600) {
							p.sendMessage("§3Du bist noch §6" + time/60 + " §eMinuten §3und §6" + time%60 +  " §eSekunden §3gemuted!");
						} else
							if(time < 86400) {
								p.sendMessage("§3Du bist noch §6" + time/3600 + " §eStunden §6" + (time%3600)/60 + " §eMinuten §3und §6" + (time%3600)%60 +  " §eSekunden §3gemuted!");
							} else {
								p.sendMessage("§3Du bist noch §6" + time/86400 + " §eTage §6" + (time%86400)/3600 + " §eStunden §6" + ((time%86400)%3600)/60 + " §eMinuten §3und §6" + ((time%86400)%3600)%60 +  " §eSekunden §3gemuted!");
							}
				}
			} else {
				if(!(now >= timeend)) {
					p.sendMessage(plugin.prefix + "§cDu wurdest gemuted!");
					if(time < 60) {
						p.sendMessage("§3Du bist noch §6" + time + " §eSekunden §3gemuted!");
					} else
						if(time < 3600) {
							p.sendMessage("§3Du bist noch §6" + time/60 + " §eMinuten §3und §6" + time%60 +  " §eSekunden §3gemuted!");
						} else
							if(time < 86400) {
								p.sendMessage("§3Du bist noch §6" + time/3600 + " §eStunden §6" + (time%3600)/60 + " §eMinuten §3und §6" + (time%3600)%60 +  " §eSekunden §3gemuted!");
							} else {
								p.sendMessage("§3Du bist noch §6" + time/86400 + " §eTage §6" + (time%86400)/3600 + " §eStunden §6" + ((time%86400)%3600)/60 + " §eMinuten §3und §6" + ((time%86400)%3600)%60 +  " e3Sekunden §3gemuted!");
							}
				} else {
					
					MySQL.update("DELETE FROM mute WHERE UUID = '" + uuid + "'");
					
				}
			}
		} else {
			if(!reason.equals("0")) {				
				p.sendMessage(plugin.prefix + "§cDu wurdest gemuted!");
				p.sendMessage("§4Grund: §c" + reason);
			} else {
				p.sendMessage(plugin.prefix + "§cDu wurdest gemuted!");
			}
		}
		
	}
	
}
