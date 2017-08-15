package me.Tailo.KnowNixBungeecordSystem.Methoden;

import me.Tailo.KnowNixBungeecordSystem.Bots.Discord;
import me.Tailo.KnowNixBungeecordSystem.Listener.Mute_Listener;
import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.ConsoleCommandSender;

public class Mute_methoden {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Mute_methoden(main main) {
		this.plugin = main;
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void mute(String name, String reason, CommandSender sender) {
		
		String uuid = UUID.getUUID(name);
		
		if(uuid.equals("")) {
			sender.sendMessage(plugin.prefix + "§cDieser Spieler existiert nicht!");
			return;
		}
		
		String creator = sender.getName();
		if(sender instanceof ConsoleCommandSender) {
			creator = "System";
		}
		
		if(sender.hasPermission("sup") || sender.hasPermission("content")) {
			if(reason == null) {
				if(!Mute_Listener.isMuted(uuid)) {
					
					MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + 0 + "', '" + creator + "')");
					
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
					if(target != null) {
						target.sendMessage(plugin.prefix + "§3Du wurdest gemuted!");
					}
					for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
						if(players.hasPermission("sup") || players.hasPermission("content")) {
							if(Settings_methoden.ban(players)) {
								players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
							}
						}
					}
					
					Discord.sendMuteMessage(name, creator, "-", "Permanent");
					
				} else {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde schon gemuted!");
				}
			} else {
				if(!Mute_Listener.isMuted(uuid)) {
					
					MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + 0 + "', '" + creator + "')");
					
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
					if(target != null) {
						target.sendMessage(plugin.prefix + "§3Du wurdest gemuted! §4Grund: §c " + reason);
					}
					for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
						if(players.hasPermission("sup") || players.hasPermission("content")) {
							if(Settings_methoden.ban(players)) {
								players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
								players.sendMessage("§4Grund §8» §7" + reason);
							}
						}
					}
					
					Discord.sendMuteMessage(name, creator, reason, "Permanent");
					
					
				} else {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde schon gemuted!");
				}
			}
		}
		
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void tempmute(String name, String reason, String timevalue, CommandSender sender) {
		
		String uuid = UUID.getUUID(name);
		java.util.UUID id = UUID.getidUUID(name);
		
		if(uuid.equals("")) {
			sender.sendMessage(plugin.prefix + "§cDieser Spieler existiert nicht!");
			return;
		}
		
		String creator = sender.getName();
		if(sender instanceof ConsoleCommandSender) {
			creator = "System";
		}
		
		if(reason == null) {
			if(!Mute_Listener.isMuted(uuid)) {
				String time = timevalue.toLowerCase();
				if(time.endsWith("s") | time.endsWith("m") | time.endsWith("h") | time.endsWith("d")) {
					String timetype = time.substring(time.length()-1);
					long value = Integer.parseInt(time.substring(0, time.length()-1));
					
					if(timetype.equals("s")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Sekunde gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunde");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Sekunde");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Sekunden gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunden");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Sekunden");
							
						}
						
					}
					if(timetype.equals("m")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Minute gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Minute");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Minute");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Minuten gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Minuten");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Minuten");
							
						}
						
					}
					if(timetype.equals("h")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Stunde gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Stunde");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Stunde");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Stunden gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Stunden");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Stunden");
							
						}
						
					}
					if(timetype.equals("d")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*24*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Tag gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Tag");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Tag");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Tage gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Dauer §8» §7" + value + " Tage");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, "-", value + " Tage");
							
						}
						
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§cFlasches Format: <Zeit> + [s/m/h/d]");
				}
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde bereits gemuted!");
			}
		} else {
			if(!Mute_Listener.isMuted(uuid)) {
				String time = timevalue.toLowerCase();
				if(time.endsWith("s") | time.endsWith("m") | time.endsWith("h") | time.endsWith("d")) {
					String timetype = time.substring(time.length()-1);
					long value = Integer.parseInt(time.substring(0, time.length()-1));
					
					if(timetype.equals("s")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Sekunde gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunde");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Sekunde");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Sekunden gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunden");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Sekunden");
							
						}
						
					}
					if(timetype.equals("m")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Minute gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Minute");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Minute");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Minuten gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Minuten");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Minuten");
							
						}
						
					}
					if(timetype.equals("h")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Stunde gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Stunde");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Stunde");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Stunden gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Stunden");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Stunden");
							
						}
						
					}
					if(timetype.equals("d")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO mute (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*24*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Tag gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Tag");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Tag");
							
						} else {
							if(target != null) {
								target.sendMessage(plugin.prefix + "§6Du wurdest für §3" + value + " §6Tage gemuted!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("sup") || players.hasPermission("content")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7gemutet!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Tage");
									}
								}
							}
							
							Discord.sendMuteMessage(name, creator, reason, value + " Tage");
							
						}
						
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§cFlasches Format: <Zeit> + [s/m/h/d]");
				}
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde bereits gemuted!");
			}
		}
		
	}

}
