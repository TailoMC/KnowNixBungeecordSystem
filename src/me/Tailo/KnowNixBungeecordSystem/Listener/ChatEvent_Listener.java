package me.Tailo.KnowNixBungeecordSystem.Listener;

import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_teamchat;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Mute_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Notify_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent_Listener implements Listener {
	
	private static main plugin;

	@SuppressWarnings("static-access")
	public ChatEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(ChatEvent e) {
		if(e.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			String msg = ChatColor.stripColor(e.getMessage()).toLowerCase();
			
			CommandSender sender = ProxyServer.getInstance().getConsole();
			
			if(!(p.hasPermission("sup") || p.hasPermission("vip") || p.hasPermission("builder") || p.hasPermission("content"))) {
				
				if(!Mute_Listener.isMuted(p.getUniqueId().toString())) {
					
					if(!e.getMessage().startsWith("/")) {
						
						for(String word : plugin.werbung) {
							
							String testmsg = msg.toLowerCase();
							testmsg = testmsg + " ";
							
							if(testmsg.toLowerCase().contains(word)) {
								
								Notify_methoden.sendChatAdNotify(p, msg);
															
								return;
							}
							
						}
						
					}
					
					for(String word : plugin.domain) {
						
						if(msg.toLowerCase().contains(word)) {
							e.setCancelled(true);
							
							if(plugin.adviolation.containsKey(p.getName()) && plugin.adviolation.get(p.getName()) > 2) {
								
								plugin.adviolation.remove(p.getName());
								
								Mute_methoden.tempmute(p.getName(), "Werbung", "10d", sender);
								
							} else {
								
								if(!plugin.adviolation.containsKey(p.getName())) plugin.adviolation.put(p.getName(), 0);
								
								plugin.adviolation.put(p.getName(), plugin.adviolation.get(p.getName()) + 1);
								
								Notify_methoden.sendChatAdNotify(p, msg + " §a(" + plugin.adviolation.get(p.getName()) + " §aMal) §b(Geblockt)");
								
								p.sendMessage("§cBitte keine Werbung!");
							}
							
							return;
						}
						
					}
					
					if(e.getMessage().equalsIgnoreCase("ez")) {
						
						e.setCancelled(true);
						
						if(plugin.verboseviolations.containsKey(p.getName()) && plugin.verboseviolations.get(p.getName()) > 1) {
							
							plugin.verboseviolations.remove(p.getName());
							
							Mute_methoden.tempmute(p.getName(), "Unangemessene Wortwahl", "1h", sender);
							
						} else {
							
							if(!plugin.verboseviolations.containsKey(p.getName())) plugin.verboseviolations.put(p.getName(), 0);
							
							plugin.verboseviolations.put(p.getName(), plugin.verboseviolations.get(p.getName()) + 1);
							
							Notify_methoden.sendChatViolationNotify(p, msg + " §a(" + plugin.verboseviolations.get(p.getName()) + " §aMal) §b(Geblockt)");
							
							p.sendMessage("§cBitte achte auf deine Wortwahl!");
						}
						
						return;
						
					}
					
					for(String word : plugin.zensur) {
						
						if(msg.toLowerCase().contains(word)) {
							e.setCancelled(true);
							
							if(plugin.verboseviolations.containsKey(p.getName()) && plugin.verboseviolations.get(p.getName()) > 1) {
								
								plugin.verboseviolations.remove(p.getName());
								
								Mute_methoden.tempmute(p.getName(), "Unangemessene Wortwahl", "1h", sender);
								
							} else {
								
								if(!plugin.verboseviolations.containsKey(p.getName())) plugin.verboseviolations.put(p.getName(), 0);
								
								plugin.verboseviolations.put(p.getName(), plugin.verboseviolations.get(p.getName()) + 1);
								
								Notify_methoden.sendChatViolationNotify(p, msg + " §a(" + plugin.verboseviolations.get(p.getName()) + " §aMal) §b(Geblockt)");
								
								p.sendMessage("§cBitte achte auf deine Wortwahl!");
							}
							
							return;
						}
						
					}
					
					for(char c : msg.toCharArray()) {
						
						if(msg.contains("" + c + c + c + c)) {
							
							e.setCancelled(true);
							
							p.sendMessage("§cBitte kein Zeichenspamm!");
							
							return; 
							
						}
						
					}
					
					for(String word : msg.split(" ")) {
						if(word.toUpperCase() == word & word.length() > 3) {
							if(word.toUpperCase() != word.toLowerCase()) {
								if(ProxyServer.getInstance().getPlayer(word) == null) {
									e.setCancelled(true);
									
									p.sendMessage("§cBitte schreibe nicht alle Buchstaben eines Wortes groß!");
									
									break;
								}
							}
						}
					}
				}
				
			} else {
				
				if(!msg.startsWith("/")) {
					if(plugin.teamchat.contains(p.getName())) {
						e.setCancelled(true);
						
						COMMAND_teamchat.teamchat(p, msg);
						
					}
				}
				
			}
			
		}
	}
	
}
