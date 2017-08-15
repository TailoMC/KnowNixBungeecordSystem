package me.Tailo.KnowNixBungeecordSystem.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.Tailo.KnowNixBungeecordSystem.Bots.Discord;
import me.Tailo.KnowNixBungeecordSystem.Bots.Teamspeak;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_ban;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_friend;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_gannounce;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_hub;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_kick;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_msg;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_mute;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_p;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_party;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_ping;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_punish;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_r;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_staff;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_teamchat;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_teamspeak;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_tempban;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_tempmute;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_togglemsg;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_toggletc;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_tpserver;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_unban;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_unmute;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_wartung;
import me.Tailo.KnowNixBungeecordSystem.Commands.COMMAND_whereami;
import me.Tailo.KnowNixBungeecordSystem.Listener.Ban_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.ChatEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.Mute_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.PlayerDisconnectEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.PluginMessageEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.PostLoginEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.ProxyPingEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.ServerConnectEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.ServerConnectedEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.ServerKickEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Listener.TabCompleteEvent_Listener;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Ban_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.BungeeParty;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Chat_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Mute_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Nicks_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Notify_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.Permission.COMMAND_perm;
import me.Tailo.KnowNixBungeecordSystem.Permission.Permission_methoden;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class main extends Plugin {
	
	//----------[Prefix]----------
	public String prefix = "§6System §8» §r";
	public String partypf = "§5Party §8» §r";
	public String friendpf = "§4Freunde §8» §r";
	public String msgpf = "§eMSG §8» §r";
	public String tspf = "§bTeamSpeak §8» §r";
	
	//----------[Party]----------
	public static Map<String, BungeeParty> party = new HashMap<String, BungeeParty>();
	public static Map<String, String> getparty = new HashMap<String, String>();
	public static Map<String, List<String>> inviteparty = new HashMap<String, List<String>>();
	
	public ArrayList<String> nicks = new ArrayList<>();
	
	public List<String> zensur = new ArrayList<>();
	public List<String> domain = new ArrayList<>();
	public List<String> werbung = new ArrayList<>();
	public List<String> teamchat = new ArrayList<>();
	
	public List<String> silentlobby = new ArrayList<String>();
	public List<String> viplobby = new ArrayList<String>();
	
	public HashMap<String, Long> cooldown = new HashMap<>();
	public HashMap<String, String> msg = new HashMap<>();
	
	public HashMap<String, Integer> adviolation = new HashMap<>();
	public HashMap<String, Integer> verboseviolations = new HashMap<>();
	
	//----------[Wartung]----------
	public boolean wartung;
	public String info;
	
	private static main instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		new PostLoginEvent_Listener(this);
		new PlayerDisconnectEvent_Listener(this);
		new ServerConnectedEvent_Listener(this);
		new PluginMessageEvent_Listener(this);
		new ServerConnectEvent_Listener(this);
		new TabCompleteEvent_Listener(this);
		new ChatEvent_Listener(this);
		new ProxyPingEvent_Listener(this);
		new Ban_Listener(this);
		new Mute_Listener(this);
		new ServerKickEvent_Listener(this);
		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_friend("friend", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_party("party", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_msg("msg", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_r("r", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_p("p", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_gannounce("gannounce", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_ping("ping", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_staff("staff", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_teamchat("teamchat", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_teamchat("tc", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_toggletc("toggletc", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_tpserver("tpserver", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_whereami("whereami", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_perm("perm", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_kick("kick", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_ban("ban", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_mute("mute", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_unmute("unmute", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_unban("unban", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_punish("punish", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_tempban("tempban", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_tempmute("tempmute", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_togglemsg("togglemsg", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_wartung("wartung", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_teamspeak("teamspeak", this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new COMMAND_teamspeak("ts", this));
		
		new Friend_methoden(this);
		new Settings_methoden(this);
		new Party_methoden(this);
		new Permission_methoden(this);
		new Nicks_methoden(this);
		new Mute_methoden(this);
		new Ban_methoden(this);
		new Chat_methoden(this);
		new Notify_methoden(this);
		new Teamspeak(this);
		
		MySQL.connect(this);
		
		Discord.connect();
		Teamspeak.connect();
		
		ProxyServer.getInstance().registerChannel("BungeeCord");
		
		ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {		
				ProxyServer.getInstance().getPluginManager().unregisterCommands(ProxyServer.getInstance().getPluginManager().getPlugin("CloudSystemProxy"));
				ProxyServer.getInstance().getPluginManager().registerCommand(instance, new COMMAND_hub("hub", instance));
				ProxyServer.getInstance().getPluginManager().registerCommand(instance, new COMMAND_hub("lobby", instance));
				ProxyServer.getInstance().getPluginManager().registerCommand(instance, new COMMAND_hub("leave", instance));
				ProxyServer.getInstance().getPluginManager().registerCommand(instance, new COMMAND_hub("l", instance));
			}
		}, 1, TimeUnit.SECONDS);
		
	}
	
	@Override
	public void onDisable() {
		
		Discord.disconnect();
		Teamspeak.disconnect();
		
		MySQL.disconnect();
		
	}

}
