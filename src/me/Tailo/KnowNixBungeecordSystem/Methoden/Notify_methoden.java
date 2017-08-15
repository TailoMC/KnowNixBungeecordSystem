package me.Tailo.KnowNixBungeecordSystem.Methoden;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Notify_methoden {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Notify_methoden(main main) {
		this.plugin = main;
	}
	
	public static void sendChatAcNotify(ProxiedPlayer target, String info, String servername) {
		
		String s = ChatColor.translateAlternateColorCodes('&', info);
		
		TextComponent prefix = new TextComponent("§c§lAntiCheat §8» §r ");
		
		TextComponent tc = new TextComponent();
		tc.setText(s);
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bZu §c" + target.getName() + " §bauf §c" + servername + " §bteleportieren").create()));
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpserver " + target.getName()));
		
		prefix.addExtra(tc);
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
			
			if(players.hasPermission("mod") || players.hasPermission("sup")) {
				
				if(Settings_methoden.notify(players)) {
					
					players.sendMessage(prefix);
					
				}
				
			}
			
		}
		
	}

	public static void sendChatAdNotify(ProxiedPlayer target, String msg) {
		
		msg = msg.replace(" ", " §7");
		
		TextComponent tc = new TextComponent("§a§l§kX" + plugin.prefix + " " + target.getDisplayName() + " §a► §7" + msg + " §7| ");
		TextComponent punish = new TextComponent("§c§lPunish §7[Werbung]");
		punish.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + target.getName() + " §cfür §aWerbung §cbestrafen!").create()));
		punish.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + target.getName() + " werbung"));
		
		tc.addExtra(punish);
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
			
			if(players.hasPermission("sup") || players.hasPermission("content")) {
				
				if(Settings_methoden.notify(players)) {
					
					players.sendMessage(tc);
					
				}
				
			}
			
		}
		
	}
	
	public static void sendChatViolationNotify(ProxiedPlayer target, String msg) {
		
		msg = msg.replace(" ", " §7");
		
		TextComponent tc = new TextComponent("§a§l§kX" + plugin.prefix + " " + target.getDisplayName() + " §a► §7" + msg + " §7| ");
		TextComponent punish = new TextComponent("§c§lPunish §7[Wortwahl]");
		punish.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + target.getName() + " §cfür §aWortwahl §cbestrafen!").create()));
		punish.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + target.getName() + " wortwahl"));
		
		tc.addExtra(punish);
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
			
			if(players.hasPermission("sup") || players.hasPermission("content")) {
				
				if(Settings_methoden.notify(players)) {
					
					players.sendMessage(tc);
					
				}
				
			}
			
		}
		
	}
	
}
