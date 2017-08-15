package me.Tailo.KnowNixBungeecordSystem.Permission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUIDFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Permission_methoden {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public Permission_methoden(main main) {
		this.plugin = main;
	}
	
	public static void loadPermissions(ProxiedPlayer p, Group group) {
		
		setDisplayName(p, group);
		
		loadExtraPermissions(p, group);
		
	}
	
	private static void setDisplayName(ProxiedPlayer p, Group group) {
		
		if(group == Group.SPIELER) {
			p.setDisplayName("§a" + p.getName());
		} else if(group == Group.PREMIUM) {
			p.setDisplayName("§6" + p.getName());
		} else if(group == Group.PREMIUMPLUS) {
			p.setDisplayName("§6" + p.getName());
		} else if(group == Group.VIP) {
			p.setDisplayName("§5" + p.getName());
		} else if(group == Group.BUILDER) {
			p.setDisplayName("§e" + p.getName());
		} else if(group == Group.CONTENT) {
			p.setDisplayName("§b" + p.getName());
		} else if(group == Group.SUP) {
			p.setDisplayName("§9" + p.getName());
		} else if(group == Group.MOD) {
			p.setDisplayName("§c" + p.getName());
		} else if(group == Group.SRMOD) {
			p.setDisplayName("§c" + p.getName());
		} else if(group == Group.DEV) {
			p.setDisplayName("§b" + p.getName());
		} else if(group == Group.SRDEV) {
			p.setDisplayName("§b" + p.getName());
		} else if(group == Group.ADMIN) {
			p.setDisplayName("§4" + p.getName());
		}
		
	}

	@SuppressWarnings("deprecation")
	public static void setGroup(String uuid, Group group) {
		
		if(group != Group.SPIELER) {
			
			if(isUserExists(uuid)) {
				
				MySQL.update("UPDATE permissions SET UUID = '" + uuid + "',playername = '" + UUIDFetcher.getName(UUID.fromString(uuid)) + "',rank = '" + getGroupName(group) + "' WHERE UUID = '" + uuid + "'");
				
			} else {
				
				MySQL.update("INSERT INTO permissions (UUID, playername, rank) VALUES ('" + uuid + "', '" + UUIDFetcher.getName(UUID.fromString(uuid)) + "', '" + getGroupName(group) + "')");
				
			}
			
		} else {
			
			if(isUserExists(uuid)) {
				
				MySQL.update("DELETE FROM permissions WHERE UUID = '" + uuid + "'");
				
			}
			
		}
		
		ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
		
		if(target != null) {
						
			loadExtraPermissions(target, group);
			setDisplayName(target, group);
			
			target.sendMessage(plugin.prefix + "§5Dein Rang wurde aktualisiert zu: §c" + getGroupName(group));
		}
		
	}
	
	public static Group getGroup(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT rank FROM permissions WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				String groupname = rs.getString("rank");
				
				Group group = Permission_methoden.getGroup(groupname);
				
				return group;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Group.SPIELER;
		
	}
	
	public static Group getGroup(UUID id) {
		
		ResultSet rs = MySQL.getResult("SELECT rank FROM permissions WHERE UUID = '" + id.toString() + "'");
		
		try {
			while(rs.next()) {
				String groupname = rs.getString("rank");
				
				Group group = Permission_methoden.getGroup(groupname);
				
				return group;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Group.SPIELER;
		
	}
	
	public static String getGroupName(Group group) {
		
		if(group == Group.ADMIN) return "Admin";
		if(group == Group.SRDEV) return "SrDev";
		if(group == Group.DEV) return "Dev";
		if(group == Group.SRMOD) return "SrMod";
		if(group == Group.MOD) return "Mod";
		if(group == Group.SUP) return "Sup";
		if(group == Group.CONTENT) return "Content";
		if(group == Group.BUILDER) return "Builder";
		if(group == Group.VIP) return "VIP";
		if(group == Group.PREMIUMPLUS) return "PremiumPlus";
		if(group == Group.PREMIUM) return "Premium";
		if(group == Group.SPIELER) return "Spieler";
		
		return "NULL";
		
	}
	
	public static Group getGroup(String string) {
		
		if (string.equalsIgnoreCase("premium")) {
			return Group.PREMIUM;
		} else if (string.equalsIgnoreCase("premiumplus")) {
			return Group.PREMIUMPLUS;
		} else if (string.equalsIgnoreCase("vip")) {
			return Group.VIP;
		} else if (string.equalsIgnoreCase("builder")) {
			return Group.BUILDER;
		} else if (string.equalsIgnoreCase("content")) {
			return Group.CONTENT;
		} else if (string.equalsIgnoreCase("sup")) {
			return Group.SUP;
		} else if (string.equalsIgnoreCase("mod")) {
			return Group.MOD;
		} else if (string.equalsIgnoreCase("srmod")) {
			return Group.SRMOD;
		} else if (string.equalsIgnoreCase("dev")) {
			return Group.DEV;
		} else if (string.equalsIgnoreCase("srdev")) {
			return Group.SRDEV;
		} else if (string.equalsIgnoreCase("admin")) {
			return Group.ADMIN;
		}
		
		return Group.SPIELER;
		
	}
	
	public static void loadExtraPermissions(ProxiedPlayer p, Group group) {
		
		if(group == Group.ADMIN) {
			p.addGroups("admin");
		}
		if(group == Group.SRDEV) {
			p.addGroups("srdev");
		}
		if(group == Group.DEV) {
			p.addGroups("dev");
		}
		if(group == Group.SRMOD) {
			p.addGroups("srmod");
		}
		if(group == Group.MOD) {
			p.addGroups("mod");
		}
		if(group == Group.SUP) {
			p.addGroups("sup");
		}
		if(group == Group.CONTENT) {
			p.addGroups("content");
		}
		if(group == Group.BUILDER) {
			p.addGroups("builder");
		}
		if(group == Group.VIP) {
			p.addGroups("vip");
		}
		if(group == Group.PREMIUMPLUS) {
			p.addGroups("premiumplus");
		}
		if(group == Group.PREMIUM) {
			p.addGroups("premium");
		}
		if(group == Group.SPIELER) {
			p.addGroups("spieler");
		}
		
	}
	
	public enum Group {
		
		SPIELER, PREMIUM, PREMIUMPLUS, VIP, BUILDER, CONTENT, SUP, MOD, SRMOD, DEV, SRDEV, ADMIN
		
	}
	
	private static boolean isUserExists(String uuid) {		
		try {
			ResultSet rs = MySQL.getResult("SELECT rank FROM permissions WHERE UUID = '" + uuid + "'");
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
	
}
