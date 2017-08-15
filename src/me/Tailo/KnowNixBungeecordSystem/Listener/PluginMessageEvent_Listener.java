package me.Tailo.KnowNixBungeecordSystem.Listener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Ban_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Notify_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageEvent_Listener implements Listener {

	private main plugin;

	@SuppressWarnings("static-access")
	public PluginMessageEvent_Listener(main main) {
		this.plugin = main;
		plugin.getProxy().getInstance().getPluginManager().registerListener(main, this);
	}
	
	@EventHandler
	public void onPluginMessage(PluginMessageEvent e) {
		
		if(e.getTag().equalsIgnoreCase("BungeeCord")) {
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
			
			try {
				
				String channel = in.readUTF();
				
				if(channel.equalsIgnoreCase("party")) {
					
					String input = in.readUTF();
					
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
					
					Party_methoden.invite(p, input);
					
				} else if(channel.equalsIgnoreCase("removefriend")) {
						
					String input = in.readUTF();
						
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
						
					Friend_methoden.deleteFriend(p, input);
						
				} else if(channel.equalsIgnoreCase("acceptfriend")) {
							
					String input = in.readUTF();
							
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
							
					Friend_methoden.acceptRequest(p, input);
							
				} else if(channel.equalsIgnoreCase("denyfriend")) {
								
					String input = in.readUTF();
								
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
								
					Friend_methoden.denyRequest(p, input);
								
				} else if(channel.equalsIgnoreCase("sendrequest")) {
									
					String input = in.readUTF();
									
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
									
					Friend_methoden.sendRequest(p, input);
									
				} else if(channel.equalsIgnoreCase("jump")) {
										
					String input = in.readUTF();
										
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
										
					Friend_methoden.jump(p, input);
					
				} else if(channel.equalsIgnoreCase("addnick")) {
						
					String input = in.readUTF();
						
					plugin.nicks.add(input.toLowerCase());
						
				} else if(channel.equalsIgnoreCase("removenick")) {
							
					String input = in.readUTF();
							
					plugin.nicks.remove(input.toLowerCase());
						
				} else if(channel.equalsIgnoreCase("notify")) {
					
					String input = in.readUTF();
					
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
					
					Notify_methoden.sendChatAcNotify(p, input, p.getServer().getInfo().getName());
					
				} else if(channel.equalsIgnoreCase("punish")) {
					
					String input = in.readUTF();
					
					ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
					
					Ban_methoden.tempban(p.getName(), input, "30d", ProxyServer.getInstance().getConsole());
					
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

}
