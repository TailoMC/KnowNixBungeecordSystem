package me.Tailo.KnowNixBungeecordSystem.MySQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class MySQL {
	
	public static Connection con;
	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	
	public static void connect(main main) {
		if(!isConnected()) {
			
			loadConfig(main);
			
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
				System.out.println("[System] Verbindung mit MySQL erfolgreich hergestellt!");
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("[System] Verbindung mit MySQL konnte nicht hergestellt werden!");
			}
		}
		
		loadDatabases();
		
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				System.out.println("[System] Verbindung mit MySQL erfolgreich geschlossen!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		if(con != null) {
			return true;
		}		
		return false;
	}
	
	public static void update(String qry) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
		} catch (SQLException e) {
			if(e instanceof CommunicationsException) {
				System.out.println("[System] Verbindung mit MySQL verloren! Versuche erneut...");
				update(qry);
			}
			e.printStackTrace();
		}
	}
	
	public static ResultSet getResult(String qry) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(qry);
			return ps.executeQuery();
		} catch (SQLException e) {
			if(e instanceof CommunicationsException) {
				System.out.println("[System] Verbindung mit MySQL verloren! Versuche erneut...");
				return getResult(qry);
			}
			e.printStackTrace();
		}
		return null;
	}
	
	private static void loadConfig(main plugin) {
		
		try {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			File file = new File(plugin.getDataFolder().getPath(), "mysql.yml");
			boolean needDefault = false;
			if(!file.exists()) {
				file.createNewFile();
				needDefault = true;
			}
			Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			
			if(needDefault) {
				
				cfg.set("host", "localhost");
				cfg.set("port", "3306");
				cfg.set("database", "system");
				cfg.set("username", "system");
				cfg.set("password", "password");
				
				save(cfg, file);
				
			}
			
			host = cfg.getString("host");
			port = cfg.getString("port");
			database = cfg.getString("database");
			username = cfg.getString("username");
			password = cfg.getString("password");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loadDatabases() {
		
		update("CREATE TABLE IF NOT EXISTS permissions (UUID text, playername text, rank text)");
		update("CREATE TABLE IF NOT EXISTS ban (UUID text, playername text, reason text, time bigint, creator text)");
		update("CREATE TABLE IF NOT EXISTS mute (UUID text, playername text, reason text, time bigint, creator text)");
		update("CREATE TABLE IF NOT EXISTS friends (UUID text, playername text, friends text, requests text, server text, jump boolean, friendrequest boolean, friendonline boolean, party boolean, msg boolean, hideserver boolean)");
		update("CREATE TABLE IF NOT EXISTS nick (UUID text, playername text, nick boolean, premium boolean, keepnick boolean, lobbynick boolean, seenicked boolean)");
		update("CREATE TABLE IF NOT EXISTS settings (UUID text, playername text, ban boolean, teamchat boolean, notify boolean)");
		update("CREATE TABLE IF NOT EXISTS vanish (UUID text, playername text)");
		update("CREATE TABLE IF NOT EXISTS ts (UUID text, tsid text)");
		
	}
	
	public static void save(Configuration cfg, File file) {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
