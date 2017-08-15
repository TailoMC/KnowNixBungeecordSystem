package me.Tailo.KnowNixBungeecordSystem.Bots;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;

public class Teamspeak {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Teamspeak(main main) {
		this.plugin = main;
	}
	
	static HashMap<String, String> verification = new HashMap<>();
	
	static TS3Api api;
	static TS3Query query;
	
	public static void connect() {
		
		TS3Config config = new TS3Config();
		config.setHost("localhost");
		
		query = new TS3Query(config);
		query.connect();
	
		api = query.getApi();
		api.login("USERNAME", "PASSWORD");
		api.selectVirtualServerById(1);
		api.setNickname("KnowNix.de ª Bot");
		
		api.registerAllEvents();
		api.addTS3Listeners(new Teamspeak_Listener(plugin));
		
		System.out.println("[System] Der Teamspeak-Bot wurde gestartet!");
		
	}
	
	public static void disconnect() {
		
		query.exit();
		
		System.out.println("[System] Der Teamspeak-Bot wurde gestoppt!");
		
	}
	
	public static boolean isVerified(String id) {
		
		try {
			
			ResultSet rs = MySQL.getResult("SELECT UUID FROM ts WHERE tsid = '" + id + "'");
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
		
	}
	
	public static boolean isVerified(ProxiedPlayer p) {
		
		try {
			
			ResultSet rs = MySQL.getResult("SELECT tsid FROM ts WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
		
	}
	
	public static String getTsID(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT tsid FROM ts WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				return rs.getString("tsid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;		
		
	}
	
	public static boolean hasPremium(String id) {
		
		String uuid = null;
		
		ResultSet rs = MySQL.getResult("SELECT UUID FROM ts WHERE tsid = '" + id + "'");
		
		try {
			while(rs.next()) {
				uuid = rs.getString("UUID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(uuid != null) {
			
			ResultSet rs2 = MySQL.getResult("SELECT rank FROM permissions WHERE UUID = '" + uuid + "'");
			
			try {
				while(rs2.next()) {
					return rs2.getString("rank").equals("premium");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
		
	}
	
	public static void removeVerification(ProxiedPlayer p) {
		
		MySQL.update("DELETE FROM ts WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
	}
	
	@SuppressWarnings("deprecation")
	public static void notifyVerification(ProxiedPlayer p, String id) {
		
		ClientInfo client = api.getClientByUId(id);
		
		if(client != null) {
			
			if(Teamspeak.isVerified(p)) {
				
				p.sendMessage(plugin.tspf + "ß7Deine alte Identit‰t wurde entfernt!");
				
				Teamspeak.removeVerification(p);
				
			}
			
			verification.put(id, p.getName());
			
			api.sendPrivateMessage(client.getId(), "Bitte sende deinen Minecraft-Namen in den Chat um die Verifizierung abzuschlieﬂen!");
			api.sendPrivateMessage(client.getId(), "Falls du dich nicht verifizieren wolltest schreibe \"abort\" in den Chat!");
			
			p.sendMessage(plugin.tspf + "ßaBitte best‰tige deine Identit‰t auf dem Teamspeak!");
			
		} else {
			
			p.sendMessage(plugin.tspf + "ßcDu bist nicht auf dem Teamspeak online oder hast eine falsche Identit‰t angegeben!");
			
		}
		
	}
	
}
