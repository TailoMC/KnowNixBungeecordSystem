package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.util.ArrayList;
import java.util.List;

import me.Tailo.KnowNixBungeecordSystem.System.main;

public class PartyManager {
	
	static String PLAYER;
	static BungeeParty PARTY;
	
	public static BungeeParty getParty(String player) {
		
		PLAYER = player;
		return main.party.get(main.getparty.get(player));
		
	}
	
	public static void inviteToParty(String player, BungeeParty party) {
		
		PLAYER = player;
		PARTY = party;
		
		List<String> list = main.inviteparty.get(player);
		
		if(list == null) {
			list = new ArrayList<String>();
		}
		
		list.add(party.getOwner().getName());
		
		main.inviteparty.put(player, list);
		
	}
	
	public static void removeFromParty(String player, BungeeParty party) {
		
		PLAYER = player;
		PARTY = party;
		party.getMembers().remove(player);
		
		main.getparty.remove(player);
		
	}
	
	public static void addToParty(String player, BungeeParty party) {
		
		PLAYER = player;
		PARTY = party;
		party.getMembers().add(player);
		
		main.getparty.put(player, party.getOwner().getName());
		
	}
	
}
