package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import me.Tailo.KnowNixBungeecordSystem.Listener.Mute_Listener;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party_methoden {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public Party_methoden(main main) {
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public static void leave(ProxiedPlayer p) {
		
		if(PartyManager.getParty(p.getName()) != null) {
			
			if(PartyManager.getParty(p.getName()).getOwner() == p) {
				
				PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + "§7Die Party von §6" + p.getDisplayName() + " §7wurde aufgelöst!");
				
				for(String members : PartyManager.getParty(p.getName()).getMembers()) {
					plugin.getparty.remove(members);
				}
				
				plugin.party.remove(p.getName());
				
			} else {
				
				BungeeParty party = PartyManager.getParty(p.getName());
				
				party.sendMessage(plugin.partypf + "§6" + p.getDisplayName() + " §7hat die Party verlassen!");
				p.sendMessage(plugin.partypf + "§7Du hast die Party von §6" + PartyManager.getParty(p.getName()).getOwner().getDisplayName() + " §7verlassen!");
				PartyManager.removeFromParty(p.getName(), party);
				plugin.getparty.remove(p.getName());
				
				if(party.getMembers().size() <= 1) {
					plugin.party.remove(p.getName());
					plugin.getparty.remove(party.getOwner().getName());
					
					party.getOwner().sendMessage(plugin.partypf + "§cDie Party wurde aufgelöst!");
				}
				
			}
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist in keiner Party!");
		}
		
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void promote(ProxiedPlayer p, String name) {
		
		if(plugin.getparty.containsKey(p.getName())) {
			
			if(PartyManager.getParty(p.getName()).getOwner() == p) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
				
				if(target != null) {
					
					if(PartyManager.getParty(p.getName()).getMembers().contains(target.getName())) {
						
						if(PartyManager.getParty(p.getName()).getOwner() != target) {
							
							PartyManager.getParty(p.getName()).setOwner(target);
							PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + "§6" + p.getDisplayName() + " §7 hat die Leitung der Party übernommen!");
							
						} else {
							p.sendMessage(plugin.partypf + "§cDu kannst dir nicht selber die Partyleitung geben!");
						}
						
					} else {
						p.sendMessage(plugin.partypf + "§6" + name + " §cist nicht in deiner Party!");
					}
					
				} else {
					p.sendMessage(plugin.partypf + "§6" + name + " §cist nicht in deiner Party!");
				}
				
			} else {
				p.sendMessage(plugin.partypf + "§cDu bist nicht der Owner der Party!");
			}
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist in keiner Party!");
		}
		
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void list(ProxiedPlayer p) {
		
		if(PartyManager.getParty(p.getName()) != null) {
			
			List<String> members = PartyManager.getParty(p.getName()).getMembers();
			
			p.sendMessage(plugin.partypf + "§eDie Partymitglieder:");
			
			for(String s : members) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(s);
				
				if(PartyManager.getParty(p.getName()).getOwner() == target) {
					p.sendMessage(plugin.partypf + "§n" + target.getDisplayName());
				} else {
					p.sendMessage(plugin.partypf + target.getDisplayName());
				}
			}
			
			p.sendMessage(plugin.partypf + "§7================");
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist in keiner Party!");
		}
		
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void invite(ProxiedPlayer p, String name) {
		
		ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
		
		if(target != null) {
			
			if(target != p) {
				
				if(Settings_methoden.party(target)) {
					
					if(PartyManager.getParty(target.getName()) == null) {
						
						if(PartyManager.getParty(p.getName()) != null) {
							
							if(PartyManager.getParty(p.getName()).getOwner() == p) {
								
								PartyManager.inviteToParty(target.getName(), PartyManager.getParty(p.getName()));
								
							} else {
								
								p.sendMessage(plugin.partypf + "§cDu bist nicht der Owner der Party!");
								
							}
							
						} else {
							
							List<String> list = plugin.inviteparty.get(target.getName());
							
							if(list == null) {
								list = new ArrayList<String>();
							}
							
							list.add(p.getName());
							
							plugin.inviteparty.put(target.getName(), list);
							
						}						
						
						target.sendMessage(plugin.partypf + "§6" + p.getDisplayName() + " §7hat dich in seine Party eingeladen!");
						
						TextComponent tc = new TextComponent();
						tc.setText(plugin.partypf + "§e/party §eaccept §e" + p.getDisplayName() + " §7um §7seiner §7Party §7beizutreten.");
						tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/party accept " + p.getName()));
						tc.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aPartyanfrage von §6" + p.getDisplayName() + " §aannehmen").create()));
						
						target.sendMessage(tc);
						
						p.sendMessage(plugin.partypf + "§7Du hast §6" + target.getDisplayName() + " §7in deine Party eingeladen!");
					
					} else {
						
						p.sendMessage(plugin.partypf + "§cDieser Spieler ist schon in einer Party!");
						
					}
					
				} else {
					p.sendMessage(plugin.partypf + "§cDieser Spieler möchte keine Partyeinladungen empfangen!");
				}
				
			} else {
				p.sendMessage(plugin.partypf + "§cDu kannst dich selber nicht in eine Party einladen!");
			}
			
		} else {
			
			if(Nicks_methoden.isNickedPlayer(name)) {
				p.sendMessage(plugin.partypf + "§cDieser Spieler möchte keine Partyeinladungen empfangen!");
			} else {
				p.sendMessage(plugin.partypf + "§cDieser Spieler ist nicht online!");
			}
			
		}
		
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void accept(ProxiedPlayer p, String name) {
		
		if(PartyManager.getParty(p.getName()) == null) {
			
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
			
			if(target != null) {
				
				List<String> list = plugin.inviteparty.get(p.getName());
				if(list != null) {
					
					if(list.contains(target.getName())) {
						
						if(PartyManager.getParty(target.getName()) == null) {
							
							List<String> members = new ArrayList<String>();
							
							PartyManager.addToParty(p.getName(), new BungeeParty(target, members));
							
							target.sendMessage(plugin.partypf + "§6" + p.getDisplayName() + " §7ist der Party beigetreten!");
							
							plugin.inviteparty.remove(target.getName());
							
						} else {
							
							PartyManager.addToParty(p.getName(), PartyManager.getParty(target.getName()));
							
							PartyManager.getParty(target.getName()).sendMessage(plugin.partypf + "§6" + p.getDisplayName() + " §7ist der Party beigetreten!");
							
							plugin.inviteparty.remove(target.getName());
							
						}
						
						p.sendMessage(plugin.partypf + "§aDu bist der Party von §6" + target.getDisplayName() + " §abeigetreten!");
						
						plugin.inviteparty.remove(p.getName());
						
					} else {
						p.sendMessage(plugin.partypf + "§cDu wurdest von §6" + p.getDisplayName() + " §cin keine Party eingeladen!");
					}
					
				} else {
					p.sendMessage(plugin.partypf + "§cDu wurdest in keine Party eingeladen!");
				}
				
			} else {
				p.sendMessage(plugin.partypf + "§cDie Party von §6" + name + " §cexistiert nicht!");
			}
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist schon in einer Party!");
		}
		
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void kick(ProxiedPlayer p, String name) {
		
		if(plugin.getparty.containsKey(p.getName())) {
			
			if(PartyManager.getParty(p.getName()).getOwner() == p) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
				
				if(target != null) {
					
					if(PartyManager.getParty(p.getName()).getMembers().contains(target.getName())) {
						
						if(PartyManager.getParty(p.getName()).getOwner() != target) {
							
							PartyManager.removeFromParty(target.getName(), PartyManager.getParty(p.getName()));
							PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + "§6" + target.getDisplayName() + " §7wurde aus der Party geworfen!");
							ProxyServer.getInstance().getPlayer(name).sendMessage(plugin.partypf + "§7Du wurdest aus der Party von §6" + p.getDisplayName() + " §7geworfen!");
							plugin.getparty.remove(name);
							
							if(PartyManager.getParty(p.getName()).getMembers().size() == 1) {
								plugin.party.remove(p.getName());
								plugin.getparty.remove(p.getName());
								
								p.sendMessage(plugin.partypf + "§cDie Party wurde aufgelöst!");
							}
							
						} else {
							p.sendMessage(plugin.partypf + "§cDu kannst dich nicht aus deiner eignen Party rauswerfen!");
						}
						
					} else {
						p.sendMessage(plugin.partypf + "§6" + name + " §cist nicht in deiner Party!");
					}
					
				} else {
					p.sendMessage(plugin.partypf + "§6" + name + " §cist nicht in deiner Party!");
				}
				
			} else {
				p.sendMessage(plugin.partypf + "§cDu bist nicht der Owner der Party!");
			}
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist in keiner Party!");
		}
		
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void chat(ProxiedPlayer p, String message) {
		
		if(PartyManager.getParty(p.getName()) != null) {
			
			if(!Mute_Listener.isMuted(p.getUniqueId().toString())) {
				
				if(PartyManager.getParty(p.getName()).getOwner() == p) {
					PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + "§n" + p.getDisplayName() + " §7> §e" + message);
				} else {
					PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + p.getDisplayName() + " §7> §e" + message);
				}
				
			} else {
				Mute_Listener.mutemsg(p);
			}
			
		} else {
			p.sendMessage(plugin.partypf + "§cDu bist in keiner Party!");
		}
		
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void disconnect(ProxiedPlayer p) {
		
		plugin.inviteparty.remove(p.getName());
		
		if(plugin.getparty.containsKey(p.getName())) {
			
			if(PartyManager.getParty(p.getName()) != null) {
				
				if(PartyManager.getParty(p.getName()).getOwner() == p) {
					
					PartyManager.getParty(p.getName()).sendMessage(plugin.partypf + "§7Die Party von §6" + p.getName() + " §7wurde aufgelöst!");
					
					for(String members : PartyManager.getParty(p.getName()).getMembers()) {
						plugin.getparty.remove(members);
					}
					
					plugin.party.remove(p.getName());
					
				} else {
					
					BungeeParty party = PartyManager.getParty(p.getName());
					
					party.sendMessage(plugin.partypf + "§6" + p.getName() + " §7hat die Party verlassen!");
					p.sendMessage(plugin.partypf + "§7Du hast die Party von §6" + PartyManager.getParty(p.getName()).getOwner().getName() + " §7verlassen!");
					PartyManager.removeFromParty(p.getName(), party);
					plugin.getparty.remove(p.getName());
					
					if(party.getMembers().size() <= 1) {
						plugin.party.remove(p.getName());
						plugin.getparty.remove(party.getOwner().getName());
						
						party.getOwner().sendMessage(plugin.partypf + "§cDie Party wurde aufgelöst!");
					}
					
				}
				
			}
			
		}
		
	}
	
	public static void sendParty(List<String> party, ServerInfo server) {
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		String members = "";
		for(String member : party) {
			members = members + member + ";";
		}
		members = members.substring(0, members.length() - 1);
		
		try {
			out.writeUTF("party");
			out.writeUTF(members);
		} catch(Exception ex) {
		}
		
		server.sendData("PartySystem", b.toByteArray());
		
	}
	
}
