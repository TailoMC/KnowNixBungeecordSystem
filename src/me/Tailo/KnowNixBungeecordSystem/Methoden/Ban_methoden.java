package me.Tailo.KnowNixBungeecordSystem.Methoden;

import me.Tailo.KnowNixBungeecordSystem.Bots.Discord;
import me.Tailo.KnowNixBungeecordSystem.Listener.Ban_Listener;
import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.ConsoleCommandSender;

public class Ban_methoden {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public Ban_methoden(main main) {
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	public static void ban(String name, String reason, CommandSender sender) {
		
		String uuid = UUID.getUUID(name);
		
		if(uuid.equals("")) {
			sender.sendMessage(plugin.prefix + "§cDieser Spieler existiert nicht!");
			return;
		}
		
		String creator = sender.getName();
		if(sender instanceof ConsoleCommandSender) {
			creator = "System";
		}
		
		if(reason == null) {
			if(!Ban_Listener.isBanned(uuid)) {
				
				MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + 0 + "', '" + creator + "')");
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
				if(target != null) {
					target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
				}
				for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
					if(players.hasPermission("mod")) {
						if(Settings_methoden.ban(players)) {
							players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
						}
					}
				}
				
				Discord.sendBanMessage(name, creator, "-", "Permanent");
				
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde schon gebannt!");
			}
		} else {
			if(!Ban_Listener.isBanned(uuid)) {
				
				MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + 0 + "', '" + creator + "')");
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
				if(target != null) {
					target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest gebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
				}
				for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
					if(players.hasPermission("mod")) {
						if(Settings_methoden.ban(players)) {
							players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
							players.sendMessage("§4Grund §8» §7" + reason);
						}
					}
				}
				
				Discord.sendBanMessage(name, creator, reason, "Permanent");
				
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde schon gebannt!");
			}
		}
		
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void tempban(String name, String reason, String timevalue, CommandSender sender) {
		
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
			if(!Ban_Listener.isBanned(uuid)) {
				String time = timevalue.toLowerCase();
				if(time.endsWith("s") | time.endsWith("m") | time.endsWith("h") | time.endsWith("d")) {
					String timetype = time.substring(time.length()-1);
					long value = Integer.parseInt(time.substring(0, time.length()-1));
					
					if(timetype.equals("s")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eSekunde §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunde");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Sekunde");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eSekunden gebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + value + " Sekunden");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Sekunden");
							
						}
						
					}
					if(timetype.equals("m")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eMinute §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Minute");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Minute");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eMinuten §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Minuten");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Minuten");
							
						}
						
					}
					if(timetype.equals("h")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eStunde §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Stunde");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Stunde");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eStunden §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Stunden");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Stunden");
							
						}
						
					}
					if(timetype.equals("d")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + 0 +  "', '" + (System.currentTimeMillis() + (value*24*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eTag §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Tag");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Tag");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eTage §cgebannt!\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Dauer §8» §7" + value + " Tage");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, "-", value + " Tage");
							
						}
						
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§cFlasches Format: <Zeit> + [s/m/h/d]");
				}
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde bereits gebannt!");
			}
		} else {
			if(!Ban_Listener.isBanned(uuid)) {
				String time = timevalue.toLowerCase();
				if(time.endsWith("s") | time.endsWith("m") | time.endsWith("h") | time.endsWith("d")) {
					String timetype = time.substring(time.length()-1);
					long value = Integer.parseInt(time.substring(0, time.length()-1));
					
					if(timetype.equals("s")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eSekunde §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunde");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Sekunde");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eSekunden §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Sekunden");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Sekunden");
							
						}
						
					}
					if(timetype.equals("m")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eMinute §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Minute");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Minute");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eMinuten §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Minuten");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Minuten");
							
						}
						
					}
					if(timetype.equals("h")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eStunde §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Stunde");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Stunde");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eStunden §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Stunden");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Stunden");
							
						}
						
					}
					if(timetype.equals("d")) {
						ProxiedPlayer target = ProxyServer.getInstance().getPlayer(id);
						
						MySQL.update("INSERT INTO ban (UUID, playername, reason, time, creator) VALUES ('" + uuid + "', '" + name + "', '" + reason +  "', '" + (System.currentTimeMillis() + (value*24*60*60*1000)) + "', '" + creator + "')");
						
						if(value == 1) {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eTag §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Tag");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Tag");
							
						} else {
							if(target != null) {
								target.disconnect("§6§lKnowNix.de\n \n§cDu wurdest für §6" + value + " §eTage §cgebannt!\n§4Grund: §c" + reason + "\n \n§aIn unserem Forum kannst du einen Entbannungsantrag stellen!");
							}
							for(ProxiedPlayer players: ProxyServer.getInstance().getPlayers()) {
								if(players.hasPermission("mod")) {
									if(Settings_methoden.ban(players)) {
										players.sendMessage(plugin.prefix + "§7Der Spieler §e" + name + " §7wurde von §c" + creator + " §7vom Netzwerk gebannt!");
										players.sendMessage("§4Grund §8» §7" + reason);
										players.sendMessage("§4Dauer §8» §7" + value + " Tage");
									}
								}
							}
							
							Discord.sendBanMessage(name, creator, reason, value + " Tage");
							
						}
						
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§cFlasches Format: <Zeit> + [s/m/h/d]");
				}
			} else {
				sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde bereits gebannt!");
			}
		}
		
	}
	
}
