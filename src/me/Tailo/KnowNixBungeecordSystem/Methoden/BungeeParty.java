package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.util.List;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeParty {
	
	ProxiedPlayer owner;
	List<String> members;
	
	public BungeeParty(ProxiedPlayer owner, List<String> members) {
		
		this.owner = owner;
		this.members = members;
		
		members.add(owner.getName());
		
		for(String member : members) {
			main.getparty.put(member, owner.getName());
		}
		
		main.party.put(owner.getName(), this);
		
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessage(String message) {
		
		for(String members : this.members) {
			ProxyServer.getInstance().getPlayer(members).sendMessage(message);
		}
		
	}
	
	public List<String> getMembers() {
		return members;		
	}
	
	public ProxiedPlayer getOwner() {
		return owner;
	}
	
	public void setMembers(List<String> members) {
		this.members = members;
	}
	
	public void setOwner(ProxiedPlayer owner) {
		this.owner = owner;
	}
	
}
