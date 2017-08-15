package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUIDFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Friend_methoden {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public Friend_methoden(main main) {
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	public static void list(ProxiedPlayer p, int page) {
		
		String string = "";
		
		ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				string = rs.getString("friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(string.split(";")));
		
		list.remove("");
		
		if(!string.equals("")) {
			
			List<String> online = new ArrayList<>();
			List<String> offline = new ArrayList<>();
			
			for(String uuid : list) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(target != null) {
					
					online.add(uuid);
					
				} else {
					
					offline.add(uuid);
					
				}
				
			}
			
			List<String> friendlist = new ArrayList<String>();
			for(String uuid : online) {
				friendlist.add(uuid);
			}
			for(String uuid : offline) {
				friendlist.add(uuid);
			}
			
			int max = page * 10;
			int min = max - 10;
			
			p.sendMessage(plugin.friendpf + "§bDeine Freunde: §7(" + list.size() + ")");
			p.sendMessage("§7(Seite " + page + ")");
			
			for(int count = min; count < max && count < friendlist.size();) {
				
				String uuid = friendlist.get(count);
				
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(player != null) {
					
					String servername;
					
					if(hasServerHidden(p)) {
						servername = "§oVersteckt";
					} else {
						servername = player.getServer().getInfo().getName().toUpperCase();
					}
					
					p.sendMessage("§e- " + player.getDisplayName() + " §a(Online auf " + servername + ")");
					
				} else {
					
					p.sendMessage("§e- " + getRankColor(UUID.fromString(uuid)) + UUIDFetcher.getName(UUID.fromString(uuid)) + " §7(Offline)");
					
				}
							
				count ++;
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu hast noch keine Freunde!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendRequest(ProxiedPlayer p, String target) {
		
		String uuid = me.Tailo.KnowNixBungeecordSystem.UUID.UUID.getUUID(target);
		
		if(!p.getName().equalsIgnoreCase(target)) {
			
			if(!Nicks_methoden.isNickedPlayer(target)) {
				
				if(!uuid.equals("") && isUserExists(uuid)) {
					
					if(!isFriended(p, uuid)) {
						
						if(!isRequestSent(p, uuid)) {
							
							if(!hasAlreadyRequest(p, uuid)) {
								
								if(!hasMaximumRequests(p)) {
									
									if(Settings_methoden.friendrequest(uuid)) {
										
										if(isUserExists(uuid)) {
											
											String requests = null;
											
											ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + uuid + "'");
											
											try {
												while(rs.next()) {
													requests = rs.getString("requests");
												}
											} catch (SQLException e) {
												e.printStackTrace();
											}
											
											List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
											
											list.add(p.getUniqueId().toString());
											
											requests = list.toString();
											
											requests = requests.substring(1, requests.length()-1).replace(", ", ";");
											
											MySQL.update("UPDATE friends SET requests = '" + requests + "' WHERE UUID = '" + uuid + "'");
											
										} else {
											
											p.sendMessage(plugin.friendpf + "§cDieser Spieler wurde nicht gefunden!");
											
										}
										
										p.sendMessage(plugin.friendpf + "§aDeine Freundschaftsanfrage wurde an " + getRankColor(UUID.fromString(uuid)) + target + " §agesendet!");
										
										ProxiedPlayer player = ProxyServer.getInstance().getPlayer(target);
										
										if(player != null) {
											player.sendMessage(plugin.friendpf + "§7Du §7hast §7eine §7Freundschaftsanfrage §7von §6" + p.getDisplayName() + " §7erhalten!");
											TextComponent accept = new TextComponent(plugin.friendpf + "§7Mit '§e/friend §eaccept " + p.getName() + "§7' §7kannst §7du §7diese §7annehmen!");
											accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAnfrage von " + p.getName() + " §aannehmen!").create()));
											accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + p.getName()));
											TextComponent deny = new TextComponent(plugin.friendpf + "§7Mit '§e/friend §edeny " + p.getName() + "§7' §7kannst §7du §7diese §7ablehnen!");
											deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cAnfrage von " + p.getName() + " §cablehnen!").create()));
											deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + p.getName()));
											player.sendMessage(accept);
											player.sendMessage(deny);
										}
										
									} else {
										
										p.sendMessage(plugin.friendpf + "§cDieser Spieler nimmt keine Freundschaftsanfragen an!");
										
									}
									
								} else {
									
									p.sendMessage(plugin.friendpf + "§cDieser Spieler hat bereits das Maximum von Freundschaftsanfragen erreicht!");
									
								}
								
								
							} else {
								
								acceptRequest(p, target);
								
							}
							
						} else {
							
							p.sendMessage(plugin.friendpf + "§cDu hast §6" + target + " §cbereits eine Freundschaftsanfrage geschickt!");
							
						}
						
					} else {
						
						p.sendMessage(plugin.friendpf + "§cDu bist bereits mit §6" + target + " §cbefreundet!");
						
					}
					
				} else {
					
					p.sendMessage(plugin.friendpf + "§cDieser Spieler existiert nicht!");
					
				}
				
			} else {
				
				p.sendMessage(plugin.friendpf + "§cDieser Spieler nimmt keine Freundschaftsanfragen an!");
				
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu kannst dir nicht selber eine Freundschaftsanfrage stellen!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void showRequest(ProxiedPlayer p) {
		
		String requests = "";
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				requests = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
		
		list.remove("");
		
		if(!requests.equals("")) {
			
			p.sendMessage(plugin.friendpf + "§7Du hast §e" + list.size() + " §7Freundschaftsanfrage(n):");
			p.sendMessage("");
			
			for(String uuid : list) {
				
				UUID id = UUID.fromString(uuid);
				
				p.sendMessage("§e- " + UUIDFetcher.getName(id));
				
			}
			
			p.sendMessage("");
			p.sendMessage(plugin.friendpf + "§7Mit '§e/friend accept <Spielername>§7' kannst du eine Anfrage annehmen!");
			p.sendMessage(plugin.friendpf + "§7Mit '§e/friend deny <Spielername>§7' kannst du eine Anfrage ablehnen!");
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu hast keine Freundschaftsanfragen!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void acceptRequest(ProxiedPlayer p, String target) {
		
		String requests = null;
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				requests = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
		
		String uuid = me.Tailo.KnowNixBungeecordSystem.UUID.UUID.getUUID(target);
		
		if(list.contains(uuid)) {
			
			list.remove(uuid);
			
			requests = list.toString();
			
			requests = requests.substring(1, requests.length()-1).replace(", ", ";");
			
			MySQL.update("UPDATE friends SET requests = '" + requests + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			addFriend(p.getUniqueId().toString(), uuid);
			
			p.sendMessage(plugin.friendpf + "§aDu hast die Freundschaftsanfrage von " + getRankColor(UUID.fromString(uuid)) + target + " §aangenommen!");
			
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(target);
			
			if(player != null) {
				player.sendMessage(plugin.friendpf + p.getDisplayName() + " §7hat deine Freundschaftsanfrage angenommen!");
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§6" + target + " §chat dir keine Freundschaftsanfrage gestellt!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void clear(ProxiedPlayer p) {
		
		String string = "";
		
		ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				string = rs.getString("friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(string.split(";")));
		
		list.remove("");
		
		if(!string.equals("")) {
			
			for(String uuids : list) {
				
				String friends = null;
				
				rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + uuids + "'");
				
				try {
					while(rs.next()) {
						friends = rs.getString("friends");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				List<String> friendlist = new ArrayList<String>(Arrays.asList(friends.split(";")));
				
				friendlist.remove(p.getUniqueId().toString());
				
				friends = friendlist.toString();
				
				friends = friends.substring(1, friends.length()-1).replace(", ", ";");
				
				MySQL.update("UPDATE friends SET friends = '" + friends + "' WHERE UUID = '" + uuids + "'");
				
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuids));
				
				if(player != null) {
					player.sendMessage(plugin.friendpf + "§7Du bist jetzt nicht mehr mit §6" + p.getDisplayName() + " §7befreundet!");
				}
				
			}
			
			MySQL.update("UPDATE friends SET friends = '' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			p.sendMessage(plugin.friendpf + "§aAlle deine Freundschaften wurden aufgelöst!");
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu hast keine Freunde!");
			
		}		
		
	}
	
	@SuppressWarnings("deprecation")
	public static void acceptAll(ProxiedPlayer p) {
		
		String requests = null;
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				requests = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
		
		list.remove("");
		
		if(!list.isEmpty()) {
			
			for(String uuid : list) {
				
				addFriend(p.getUniqueId().toString(), uuid);
				
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(player != null) {
					player.sendMessage(plugin.friendpf + p.getDisplayName() + " §7hat deine Freundschaftsanfrage angenommen!");
				}
				
			}
			
			MySQL.update("UPDATE friends SET requests = '' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			p.sendMessage(plugin.friendpf + "§aDu hast alle Freundschaftsanfragen §aangenommen!");
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu hast keine Freundschaftsanfragen!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void denyAll(ProxiedPlayer p) {
		
		String requests = null;
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				requests = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
		
		list.remove("");
		
		if(!list.isEmpty()) {
			
			for(String uuid : list) {
				
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(player != null) {
					player.sendMessage(plugin.friendpf + "§6" + p.getDisplayName() + " §7hat deine Freundschaftsanfrage abgelehnt!");
				}
				
			}
			
			MySQL.update("UPDATE friends SET requests = '' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			p.sendMessage(plugin.friendpf + "§aDu hast alle Freundschaftsanfragen §aabgelehnt!");
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu hast keine Freundschaftsanfragen!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void denyRequest(ProxiedPlayer p, String target) {
		
		String requests = null;
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				requests = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
		
		String uuid = me.Tailo.KnowNixBungeecordSystem.UUID.UUID.getUUID(target);
		
		if(list.contains(uuid)) {
			
			list.remove(uuid);
			
			requests = list.toString();
			
			requests = requests.substring(1, requests.length()-1).replace(", ", ";");
			
			MySQL.update("UPDATE friends SET requests = '" + requests + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			p.sendMessage(plugin.friendpf + "§3Du hast die Freundschaftsanfrage von " + target + " §3abgelehnt!");
			
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(target);
			
			if(player != null) {
				player.sendMessage(plugin.friendpf + "§6" + p.getDisplayName() + " §7hat deine Freundschaftsanfrage abgelehnt!");
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§6" + target + " §chat dir keine Freundschaftsanfrage gestellt!");
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendOnlineMsg(ProxiedPlayer p) {
		
		if(isUserExists(p.getUniqueId().toString())) {
			
			String friends = null;
			
			ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			try {
				while(rs.next()) {
					friends = rs.getString("friends");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>(Arrays.asList(friends.split(";")));
			
			list.remove("");
			
			for(String uuid : list) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(target != null) {
					
					if(Settings_methoden.friendonline(target)) {
						
						target.sendMessage(plugin.friendpf + "§e" + p.getDisplayName() + " §7ist jetzt §aonline§7!");
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendOfflineMsg(ProxiedPlayer p) {
		
		if(isUserExists(p.getUniqueId().toString())) {
			
			String friends = null;
			
			ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			try {
				while(rs.next()) {
					friends = rs.getString("friends");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>(Arrays.asList(friends.split(";")));
			
			list.remove("");
			
			for(String uuid : list) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
				
				if(target != null) {
					
					if(Settings_methoden.friendonline(target)) {
						
						target.sendMessage(plugin.friendpf + "§e" + p.getDisplayName() + " §7ist jetzt §coffline§7!");
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void deleteFriend(ProxiedPlayer p, String name) {
		
		String uuid = me.Tailo.KnowNixBungeecordSystem.UUID.UUID.getUUID(name);
		
		String friends = null;
		
		ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				friends = rs.getString("friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(friends.split(";")));
		
		if(list.contains(uuid) & !uuid.equals("")) {
			
			list.remove(uuid);
			
			friends = list.toString();
			
			friends = friends.substring(1, friends.length()-1).replace(", ", ";");
			
			MySQL.update("UPDATE friends SET friends = '" + friends + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + uuid + "'");
			
			try {
				while(rs.next()) {
					friends = rs.getString("friends");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			list = new ArrayList<String>(Arrays.asList(friends.split(";")));
			
			list.remove(p.getUniqueId().toString());
			
			friends = list.toString();
			
			friends = friends.substring(1, friends.length()-1).replace(", ", ";");
			
			MySQL.update("UPDATE friends SET friends = '" + friends + "' WHERE UUID = '" + uuid + "'");
			
			p.sendMessage(plugin.friendpf + "§7Du bist jetzt nicht mehr mit §6" + name + " §7befreundet!");
			
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
			
			if(player != null) {
				player.sendMessage(plugin.friendpf + "§7Du bist jetzt nicht mehr mit §6" + p.getDisplayName() + " §7befreundet!");
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu bist nicht mit §6" + name + " §cbefreundet!");
			
		}
		
	}
	
	public static void updateServer(ProxiedPlayer p, String name) {
		
		if(isUserExists(p.getUniqueId().toString())) {
			
			if(hasServerHidden(p)) {
				if(name.equals("")) {
					MySQL.update("UPDATE friends SET server = '' WHERE UUID = '" + p.getUniqueId().toString() + "'");
				} else {
					MySQL.update("UPDATE friends SET server = '§oVersteckt' WHERE UUID = '" + p.getUniqueId().toString() + "'");
				}
			} else {
				MySQL.update("UPDATE friends SET server = '" + name + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
			}
			
		}
		
	}
	
	private static void addFriend(String uuid1, String uuid2) {
		
		String friends = null;
		
		ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + uuid1 + "'");
		
		try {
			while(rs.next()) {
				friends = rs.getString("friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(friends.split(";")));
		
		list.add(uuid2);
		
		friends = list.toString();
		
		friends = friends.substring(1, friends.length()-1).replace(", ", ";");
		
		MySQL.update("UPDATE friends SET friends = '" + friends + "', server = '" + ProxyServer.getInstance().getPlayer(UUID.fromString(uuid1)).getServer().getInfo().getName().toUpperCase() + "' WHERE UUID = '" + uuid1 + "'");
		
		rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + uuid2 + "'");
		
		try {
			while(rs.next()) {
				friends = rs.getString("friends");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		list = new ArrayList<String>(Arrays.asList(friends.split(";")));
		
		list.add(uuid1);
		
		friends = list.toString();
		
		friends = friends.substring(1, friends.length()-1).replace(", ", ";");
		
		MySQL.update("UPDATE friends SET friends = '" + friends + "', server = '" + ProxyServer.getInstance().getPlayer(UUID.fromString(uuid2)).getServer().getInfo().getName().toUpperCase() + "' WHERE UUID = '" + uuid2 + "'");
		
	}
	
	@SuppressWarnings("deprecation")
	public static void jump(ProxiedPlayer p, String name) {
		
		String uuid = me.Tailo.KnowNixBungeecordSystem.UUID.UUID.getUUID(name);
		
		if(isFriended(p, uuid) & !uuid.equals("")) {
			
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
			
			if(target != null) {
				
				if(hasJumpEnabled(uuid)) {
					
					ServerInfo info = target.getServer().getInfo();
					
					if(info != p.getServer().getInfo()) {
						
						if(!info.getName().startsWith("silentlobby")) {
							
							p.sendMessage(plugin.friendpf + "§3Du verbindest dich jetzt mit dem selben Server wie §6" + name + "§3!");
							
							p.connect(info);
							
						} else {
							
							p.sendMessage(plugin.friendpf + "§cDu kannst nicht auf diesen Server springen!");
							
						}
						
					} else {
						
						p.sendMessage(plugin.friendpf + "§3Du bist bereits auf dem selben Server wie §6" + name + "§3!");
						
					}
					
				} else {
					
					p.sendMessage(plugin.friendpf + "§6" + name + " §clässt keine Freunde zu sich springen!");
					
				}
				
			} else {
				
				p.sendMessage(plugin.friendpf + "§6" + name + " §cist nicht online!");
				
			}
			
		} else {
			
			p.sendMessage(plugin.friendpf + "§cDu bist nicht mit §6" + name + " §cbefreundet!");
			
		}
		
	}
	
	public static void preLoad(ProxiedPlayer p) {
		
		Executors.newCachedThreadPool().execute(new Runnable() {			
			@Override
			public void run() {
				String string = "";
				
				ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
				
				try {
					while(rs.next()) {
						string = rs.getString("friends");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				List<String> list = new ArrayList<String>(Arrays.asList(string.split(";")));
				
				list.remove("");
				
				if(!string.equals("")) {
					
					list.forEach(uuid -> UUIDFetcher.getName(UUID.fromString(uuid)));
					
				}
			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleJump(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT jump FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("jump");
				
				if(bol) {
					MySQL.update("UPDATE friends SET jump = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "§3Freunde können jetzt nicht mehr zu dir springen!");
				} else {
					MySQL.update("UPDATE friends SET jump = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					p.sendMessage(plugin.friendpf + "§3Freunde können jetzt zu dir springen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleHideServer(ProxiedPlayer p) {
		
		ResultSet rs = MySQL.getResult("SELECT hideserver FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				boolean bol = rs.getBoolean("hideserver");
				
				if(bol) {
					MySQL.update("UPDATE friends SET hideserver = '" + 0 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					updateServer(p, p.getServer().getInfo().getName().toUpperCase());
					p.sendMessage(plugin.friendpf + "§3Freunde können jetzt deinen Server sehen!");
				} else {
					MySQL.update("UPDATE friends SET hideserver = '" + 1 + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
					updateServer(p, "-");
					p.sendMessage(plugin.friendpf + "§3Freunde können jetzt nicht mehr deinen Server sehen!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static boolean hasServerHidden(ProxiedPlayer p) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT hideserver FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			while(rs.next()) {
				return rs.getBoolean("hideserver");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;		
		
	}
	
	private static boolean hasJumpEnabled(String uuid) {
		
		try {
			ResultSet rs = MySQL.getResult("SELECT jump FROM friends WHERE UUID = '" + uuid + "'");
			
			while(rs.next()) {
				return rs.getBoolean("jump");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;		
		
	}
	
	private static boolean hasAlreadyRequest(ProxiedPlayer p, String uuid) {
		
		if(isUserExists(p.getUniqueId().toString())) {
			
			String requests = null;
			
			ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			try {
				while(rs.next()) {
					requests = rs.getString("requests");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
			
			if(list.contains(uuid)) return true;
			
			return false;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean isFriended(ProxiedPlayer p, String uuid) {
		
		if(isUserExists(p.getUniqueId().toString())) {
			
			String friends = null;
			
			ResultSet rs = MySQL.getResult("SELECT friends FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			try {
				while(rs.next()) {
					friends = rs.getString("friends");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>(Arrays.asList(friends.split(";")));
			
			if(list.contains(uuid)) return true;
			
			return false;
			
		} else {
			
			return false;
			
		}
		
	}
	
	private static boolean isRequestSent(ProxiedPlayer p, String uuid) {
		
		if(isUserExists(uuid)) {
			
			String requests = null;
			
			ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + uuid + "'");
			
			try {
				while(rs.next()) {
					requests = rs.getString("requests");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>(Arrays.asList(requests.split(";")));
			
			if(list.contains(p.getUniqueId().toString())) return true;
			
			return false;
			
		} else {
			
			return false;
			
		}
		
	}
	
	private static boolean hasMaximumRequests(ProxiedPlayer p) {
		
		String string = "";
		
		ResultSet rs = MySQL.getResult("SELECT requests FROM friends WHERE UUID = '" + p.getUniqueId().toString() + "'");
		
		try {
			while(rs.next()) {
				string = rs.getString("requests");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>(Arrays.asList(string.split(";")));
		
		list.remove("");
		
		if(list.size() == 45) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	private static String getRankColor(UUID uuid) {
		
		ResultSet rs = MySQL.getResult("SELECT rank FROM permissions WHERE UUID = '" + uuid.toString() + "'");
		
		try {
			while(rs.next()) {
				String groupname = rs.getString("rank");
				
				if (groupname.equalsIgnoreCase("premium")) {
					return "§6";
				} else if (groupname.equalsIgnoreCase("premiumplus")) {
					return "§6";
				} else if (groupname.equalsIgnoreCase("vip")) {
					return "§5";
				} else if (groupname.equalsIgnoreCase("builder")) {
					return "§e";
				} else if (groupname.equalsIgnoreCase("content")) {
					return "§b";
				} else if (groupname.equalsIgnoreCase("sup")) {
					return "§9";
				} else if (groupname.equalsIgnoreCase("mod")) {
					return "§c";
				} else if (groupname.equalsIgnoreCase("srmod")) {
					return "§c";
				} else if (groupname.equalsIgnoreCase("dev")) {
					return "§b";
				} else if (groupname.equalsIgnoreCase("srdev")) {
					return "§b";
				} else if (groupname.equalsIgnoreCase("admin")) {
					return "§4";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "§a";
		
	}
	
	private static boolean isUserExists(String uuid) {		
		try {
			ResultSet rs = MySQL.getResult("SELECT playername FROM friends WHERE UUID = '" + uuid + "'");
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
}
