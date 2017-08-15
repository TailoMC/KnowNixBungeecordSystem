package me.Tailo.KnowNixBungeecordSystem.Bots;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Discord {

	private static JDA jda;
	
	private static MessageChannel banns;
	private static MessageChannel mutes;
	
	public static void connect() {
		
		try {
			
			Game game = Game.of("KnowNix.de");
			
			jda = new JDABuilder(AccountType.BOT).setToken("BOTTOKEN").setAudioEnabled(false).setGame(game).buildBlocking();
			
			for(MessageChannel channel : jda.getTextChannels()) {
				
				if(channel.getName().equalsIgnoreCase("banns")) banns = channel;
				if(channel.getName().equalsIgnoreCase("mutes")) mutes = channel;
				
			}
			
			System.out.println("[System] Der Discord-Bot wurde gestartet!");
			
		} catch (Exception e) {
			System.err.println("[System] Der Discord-Bot konnte nicht gestartet werden!");
			e.printStackTrace();
		}
		
	}
	
	public static void disconnect() {
		
		jda.shutdown();
		
		System.out.println("[System] Der Discord-Bot wurde gestoppt!");
		
	}
	
	public static void sendBanMessage(String player, String creator, String reason, String time) {
		
		banns.sendMessage("─────« **Bann** »─────"
				+ "\n**Spieler:** " + player
				+ "\n**Ersteller:** " + creator
				+ "\n**Grund:** " + reason
				+ "\n**Dauer:** " + time
				+ "\n───────────────").complete();
		
	}
	
	public static void sendUnbanMessage(String player, String from) {
		
		banns.sendMessage("─────« **Entbann** »─────"
				+ "\n**Spieler:** " + player
				+ "\n**Von:** " + from
				+ "\n─────────────────").complete();
		
	}
	
	public static void sendMuteMessage(String player, String creator, String reason, String time) {
		
		mutes.sendMessage("─────« **Mute** »─────"
				+ "\n**Spieler:** " + player
				+ "\n**Ersteller:** " + creator
				+ "\n**Grund:** " + reason
				+ "\n**Dauer:** " + time
				+ "\n───────────────").complete();
		
	}
	
	public static void sendUnmuteMessage(String player, String from) {
		
		banns.sendMessage("─────« **Entmute** »─────"
				+ "\n**Spieler:** " + player
				+ "\n**Von:** " + from
				+ "\n─────────────────").complete();
		
	}
	
}
