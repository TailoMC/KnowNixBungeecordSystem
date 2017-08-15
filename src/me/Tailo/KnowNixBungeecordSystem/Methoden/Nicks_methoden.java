package me.Tailo.KnowNixBungeecordSystem.Methoden;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;
import me.Tailo.KnowNixBungeecordSystem.System.main;

public class Nicks_methoden {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Nicks_methoden(main main) {
		this.plugin = main;
	}
	
	public static boolean isNickedPlayer(String name) {
		
		return plugin.nicks.contains(name.toLowerCase());
		
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
		
		server.sendData("NickSystem", b.toByteArray());
		
	}
	
}
