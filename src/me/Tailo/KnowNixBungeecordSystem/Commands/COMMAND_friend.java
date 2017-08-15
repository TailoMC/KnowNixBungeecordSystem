package me.Tailo.KnowNixBungeecordSystem.Commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Friend_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Settings_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_friend extends Command {

	private main plugin;

	private static ArrayList<String> cooldown = new ArrayList<>();
	
	public COMMAND_friend(String name, main main) {
		super(name);
		this.plugin = main;
	}
	
	@SuppressWarnings("deprecation")
	private void usage(ProxiedPlayer p, int page) {
		
		if(page > 2 || page < 1) {
			page = 1;
		}
		
		switch (page) {
		case 1:
			p.sendMessage("§7==========§e[ Freunde ]§7==========");
			p.sendMessage("§7(Weitere Befehle: /friend 2)");
			p.sendMessage("§e/friend add <Spielername> §8- §7Sende einem Spieler eine Freundschaftsanfrage!");
			p.sendMessage("§e/friend accept <Spielername> §8- §7Nehme eine Freundschaftsanfrage an!");
			p.sendMessage("§e/friend deny <Spielername> §8- §7Lehne eine Freundschaftsanfrage ab!");
			p.sendMessage("§e/friend list <Seite>§8- §7Zeige eine Liste aller Freunde!");
			p.sendMessage("§e/friend remove <Spielername> §8- §7Entferne einen Freund!");
			p.sendMessage("§e/friend jump <Spielername> §8- §7Springe zu deinem Freund!");
			p.sendMessage("§e/msg <Spielername> <Nachricht>§8- §7Schreibe einem Spieler eine Nachricht!");
			p.sendMessage("§e/r <Spielername> <Nachricht>§8- §7Antworte auf eine Nachricht!");
			p.sendMessage("§7==========§e[ Freunde ]§7==========");
			break;
		case 2:
			p.sendMessage("§7==========§e[ Freunde ]§7==========");
			p.sendMessage("§7(Weitere Befehle: /friend 1)");
			p.sendMessage("§e/friend clear §8- §7Leere deine Freundesliste!");
			p.sendMessage("§e/friend requests §8- §7Zeige eine Liste aller Freundschaftsanfragen!");
			p.sendMessage("§e/friend acceptAll §8- §7Nehme alle Freundschaftsanfragen an!");
			p.sendMessage("§e/friend denyAll §8- §7Lehne alle Freundschaftsanfragen an!");
			p.sendMessage("§e/friend toggle §8- §7Erlaube/verbiete Freundschaftsanfragen!");
			p.sendMessage("§e/friend togglenotify §8- §7Online/Offline Benachrichtigungen anzeigen!");
			p.sendMessage("§e/friend togglejump §8- §7Erlaube/verbiete es Freunden zu dir zu springen!");
			p.sendMessage("§e/friend togglemsg §8- §7Erlaube/verbiete private Nachrichten!");
			if(p.hasPermission("vip")) {
				p.sendMessage("§e/friend hideserver §8- §7Verstecke deinen Server!");
			}
			p.sendMessage("§7==========§e[ Freunde ]§7==========");
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("list")) {
					Friend_methoden.list(p, 1);
					return;
				}
				
				if(args[0].equalsIgnoreCase("requests")) {
					Friend_methoden.showRequest(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("togglejump")) {
					Friend_methoden.toggleJump(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("toggle")) {
					Settings_methoden.toggleFriendrequest(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("togglenotify")) {
					Settings_methoden.toggleFriendonline(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("togglemsg")) {
					Settings_methoden.toggleMsg(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("acceptall")) {
					Friend_methoden.acceptAll(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("denyall")) {
					Friend_methoden.denyAll(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("hideserver")) {
					Friend_methoden.toggleHideServer(p);
					return;
				}
				
				if(args[0].equalsIgnoreCase("clear")) {
					TextComponent tc1 = new TextComponent(plugin.friendpf + "§cKlicke ");
					TextComponent tc2 = new TextComponent(" §cin §cden §cnächsten §c10 §cSekunden §cwenn §cdu §cdeine §cFreundesliste §clöschen §cmöchtest!");
					TextComponent clear = new TextComponent("§eHIER");
					clear.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cFreundesliste löschen!").create()));
					clear.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend clearconfirm"));
					tc1.addExtra(clear);
					tc1.addExtra(tc2);
					
					cooldown.add(p.getName());
					
					ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {						
						@Override
						public void run() {
							cooldown.remove(p.getName());
						}
					}, 10, TimeUnit.SECONDS);
					
					p.sendMessage(tc1);
					return;
				}
				
				if(args[0].equalsIgnoreCase("clearconfirm")) {
					if(cooldown.contains(p.getName())) {
						Friend_methoden.clear(p);
					} else {
						p.sendMessage(plugin.friendpf + "§cDu musst erst wieder '§e/friend clear§c' eingeben!");
					}
					return;
				}
				
				if(args[0].equalsIgnoreCase("2")) {
					usage(p, 2);
					return;
				}
				
				usage(p, 1);
				
			} else
				if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("list")) {
						try {
							Friend_methoden.list(p, Integer.parseInt(args[1]));
						} catch(Exception e) {
							Friend_methoden.list(p, 1);
						}
						return;
					}
					
					if(args[0].equalsIgnoreCase("add")) {
						Friend_methoden.sendRequest(p, args[1]);
						return;
					}
					
					if(args[0].equalsIgnoreCase("accept")) {
						Friend_methoden.acceptRequest(p, args[1]);
						return;
					}
					
					if(args[0].equalsIgnoreCase("deny")) {
						Friend_methoden.denyRequest(p, args[1]);
						return;
					}
					
					if(args[0].equalsIgnoreCase("remove")) {
						Friend_methoden.deleteFriend(p, args[1]);
						return;
					}
					
					if(args[0].equalsIgnoreCase("jump")) {
						Friend_methoden.jump(p, args[1]);
						return;
					}
					
					if(args[1].equalsIgnoreCase("2")) {
						usage(p, 2);
						return;
					}
					
					usage(p, 1);
					
				}
			
			usage(p, 1);
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
