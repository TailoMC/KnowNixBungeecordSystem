package me.Tailo.KnowNixBungeecordSystem.Bots;

import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;

public class Teamspeak_Listener implements TS3Listener {

	private main plugin;

	public Teamspeak_Listener(main main) {
		this.plugin = main;
	}

	@Override
	public void onChannelCreate(ChannelCreateEvent e) {}

	@Override
	public void onChannelDeleted(ChannelDeletedEvent e) {}

	@Override
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {}

	@Override
	public void onChannelEdit(ChannelEditedEvent e) {}

	@Override
	public void onChannelMoved(ChannelMovedEvent e) {}

	@Override
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {}

	@Override
	public void onClientJoin(ClientJoinEvent e) {
		
		if(Teamspeak.isVerified(e.getUniqueClientIdentifier())) {
			
			Teamspeak.api.addClientToServerGroup(7, e.getClientDatabaseId());
			
			if(Teamspeak.hasPremium(e.getUniqueClientIdentifier())) {
				
				Teamspeak.api.addClientToServerGroup(19, e.getClientDatabaseId());
				
			}
			
		}
		
	}

	@Override
	public void onClientLeave(ClientLeaveEvent e) {}

	@Override
	public void onClientMoved(ClientMovedEvent e) {}

	@Override
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {}

	@Override
	public void onServerEdit(ServerEditedEvent e) {}

	@SuppressWarnings("deprecation")
	@Override
	public void onTextMessage(TextMessageEvent e) {
		
		if(e.getTargetMode() == TextMessageTargetMode.CLIENT) {
			
			ClientInfo client = Teamspeak.api.getClientInfo(e.getInvokerId());
			
			if(Teamspeak.verification.containsKey(client.getUniqueIdentifier())) {
				
				if(e.getMessage().equalsIgnoreCase("abort")) {
					
					Teamspeak.verification.remove(client.getUniqueIdentifier());
					
					Teamspeak.api.sendPrivateMessage(client.getId(), "Die Verifizierung wurde abgebrochen!");
					
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(Teamspeak.verification.get(client.getUniqueIdentifier()));
					
					if(p != null) {
						p.sendMessage(plugin.tspf + "§cDeine Verifizierung wurde abgebrochen!");
					}
					
				} else {
					
					String name = Teamspeak.verification.get(client.getUniqueIdentifier());
					
					if(e.getMessage().equals(name)) {
						
						ProxiedPlayer p = ProxyServer.getInstance().getPlayer(name);
						
						if(p != null) {
							
							Teamspeak.verification.remove(client.getUniqueIdentifier());
							
							MySQL.update("INSERT INTO ts (UUID, tsid) VALUES ('" + p.getUniqueId().toString() + "', '" + client.getUniqueIdentifier() + "')");
							
							Teamspeak.api.sendPrivateMessage(client.getId(), "Deine Verifizierung wurde erfolgreich abgeschlossen!");
							p.sendMessage(plugin.tspf + "§aDeine Verifizierung wurde erfolgreich abgeschlossen!");
							
							Teamspeak.api.addClientToServerGroup(7, client.getDatabaseId());							
							if(Teamspeak.hasPremium(client.getUniqueIdentifier())) {								
								Teamspeak.api.addClientToServerGroup(19, client.getDatabaseId());								
							}
							
						} else {
							
							Teamspeak.api.sendPrivateMessage(client.getId(), "Deine Verifizierung konnte nicht abgeschlossen werden, da du nicht in Minecraft online bist!");
							
						}
						
					} else {
						
						Teamspeak.api.sendPrivateMessage(client.getId(), "Dein Minecraft-Name ist nicht richtig!");
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
