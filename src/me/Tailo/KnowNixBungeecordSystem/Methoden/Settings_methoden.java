package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Settings_methoden {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Settings_methoden(main main) {
		this.plugin = main;
	}
	
	public static boolean friendonline(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT friendonline FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("friendonline");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public static boolean friendrequest(String uuid) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT friendrequest FROM friends WHERE UUID = '" + uuid + "'");
			while(rs.next()) {
				return rs.getBoolean("friendrequest");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static boolean party(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT party FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("party");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static boolean msg(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT msg FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("msg");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static boolean ban(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT ban FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("ban");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static boolean notify(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT notify FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("notify");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static boolean teamchat(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT teamchat FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
			while(rs.next()) {
				return rs.getBoolean("teamchat");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleFriendrequest(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT friendrequest FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("friendrequest");
				
				if(bol) {
					MySQL.update("UPDATE friends SET friendrequest = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun keine Freundschaftsanfragen!");
				} else {
					MySQL.update("UPDATE friends SET friendrequest = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun Freundschaftsanfragen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleBan(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT ban FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("ban");
				
				if(bol) {
					MySQL.update("UPDATE settings SET ban = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun keine Ban/Mute/Kick Benachtichtigungen!");
				} else {
					MySQL.update("UPDATE settings SET ban = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun Ban/Mute/Kick Benachtichtigungen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleNotify(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT notify FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("notify");
				
				if(bol) {
					MySQL.update("UPDATE settings SET notify = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun keine Verstoﬂ Benachtichtigungen!");
				} else {
					MySQL.update("UPDATE settings SET notify = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun Verstoﬂ Benachtichtigungen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleTeamChat(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT teamchat FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("teamchat");
				
				if(bol) {
					MySQL.update("UPDATE settings SET teamchat = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun keine TeamChat Benachtichtigungen!");
				} else {
					MySQL.update("UPDATE settings SET teamchat = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.prefix + "ß3Du erh‰lst nun TeamChat Benachtichtigungen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleFriendonline(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT friendonline FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("friendonline");
				
				if(bol) {
					MySQL.update("UPDATE friends SET friendonline = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun keine Freund-Online/Offlinebenachrichtigungen!");
				} else {
					MySQL.update("UPDATE friends SET friendonline = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun Freund-Online/Offlinebenachrichtigungen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleParty(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT party FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("party");
				
				if(bol) {
					MySQL.update("UPDATE friends SET party = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun keine Partyanfragen!");
				} else {
					MySQL.update("UPDATE friends SET party = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun Partyanfragen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleMsg(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT msg FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("msg");
				
				if(bol) {
					MySQL.update("UPDATE friends SET msg = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun keine Privaten Nachtichten!");
				} else {
					MySQL.update("UPDATE friends SET msg = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "ß3Du erh‰lst nun Private Nachtichten!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createFriendsIfNotExists(ProxiedPlayer p) {
		
		boolean bol = false;
		
		try {
			ResultSet rs = MySQL.getResult("SELECT playername FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			bol = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!bol) {
			
			MySQL.update("INSERT INTO friends (UUID, playername, friends, requests, server, jump, friendrequest, friendonline, party, msg, hideserver) VALUES ('" + p.getUniqueId().toString() + "', '" + p.getName() + "', '', '', '', '" + 1 + "', '" + 1 + "', '" + 1 + "', '" + 1 + "', '" + 1 + "', '" + 0 + "')");
			
		}
		
	}
	
	public static void createNickIfNotExists(ProxiedPlayer p) {
		
		if(p.hasPermission("premiumplus")) {
			
			boolean bol = false;
			
			try {
				ResultSet rs = MySQL.getResult("SELECT playername FROM nick WHERE UUID = '" + p.getUniqueId().toString() + "'");
				bol = rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!bol) {
				
				MySQL.update("INSERT INTO nick (UUID, playername, nick, premium, keepnick) VALUES ('" + p.getUniqueId().toString() + "', '" + p.getName() + "', '0', '1', '0')");
				
			}
			
		}
		
	}
	
	public static void createNotifyIfNotExists(ProxiedPlayer p) {
		
		if(p.hasPermission("sup") || p.hasPermission("builder") || p.hasPermission("content")) {
			
			boolean bol = false;
			
			try {
				ResultSet rs = MySQL.getResult("SELECT playername FROM settings WHERE UUID = '" + p.getUniqueId().toString() + "'");
				bol = rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!bol) {
				
				MySQL.update("INSERT INTO settings (UUID, playername, ban, teamchat, notify) VALUES ('" + p.getUniqueId().toString() + "', '" + p.getName() + "', '1', '1', '1')");
				
			}

		}
		
	}
	
}
