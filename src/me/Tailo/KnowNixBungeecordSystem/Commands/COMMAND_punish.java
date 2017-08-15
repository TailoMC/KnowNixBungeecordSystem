package me.Tailo.KnowNixBungeecordSystem.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Ban_methoden;
import me.Tailo.KnowNixBungeecordSystem.Methoden.Mute_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;

public class COMMAND_punish extends Command {

	@SuppressWarnings("unused")
	private main plugin;

	public COMMAND_punish(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("mod")) {
			
			if(args.length == 1) {
				
				if(sender instanceof ProxiedPlayer) {
					
					ProxiedPlayer p = (ProxiedPlayer) sender;
					
					p.sendMessage("§e" + args[0] + " §cbestrafen für:");
					p.sendMessage("");
					
					TextComponent hacking = new TextComponent("§cHacking §7(Ban)");
					hacking.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aHacking §cbestrafen").create()));
					hacking.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " hacking"));					
					p.sendMessage(hacking);
					
					p.sendMessage("");
					
					TextComponent bugusing = new TextComponent("§cBugusing §7(Ban)");
					bugusing.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aBugusing §cbestrafen").create()));
					bugusing.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " bugusing"));					
					p.sendMessage(bugusing);					
					
					p.sendMessage("");
					
					TextComponent wortwahl = new TextComponent("§bWortwahl §7(Mute)");
					wortwahl.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aWortwahl §cbestrafen").create()));
					wortwahl.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " wortwahl"));					
					p.sendMessage(wortwahl);
					
					p.sendMessage("");
					
					TextComponent spam = new TextComponent("§bSpam §7(Mute)");
					spam.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aSpam §cbestrafen").create()));
					spam.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " spam"));					
					p.sendMessage(spam);
					
					p.sendMessage("");
					
					TextComponent werbung = new TextComponent("§bWerbung §7(Mute)");
					werbung.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aWerbung §cbestrafen").create()));
					werbung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " spam"));					
					p.sendMessage(werbung);
					
					p.sendMessage("");
					
				} else {
					sender.sendMessage("§c/punish [Spielername] [Vorlage]");
				}
				
			} else if(args.length == 2) {
				
				if(args[1].equalsIgnoreCase("hacking")) {
					
					Ban_methoden.tempban(args[0], "Hacking", "30d", sender);
					
				} else if(args[1].equalsIgnoreCase("bugusing")) {
					
					Ban_methoden.tempban(args[0], "Bugusing", "1d", sender);
					
				} else if(args[1].equalsIgnoreCase("wortwahl")) {
					
					Mute_methoden.tempmute(args[0], "Unangemessene Wortwahl", "1h", sender);
					
				} else if(args[1].equalsIgnoreCase("spam")) {
					
					Mute_methoden.tempmute(args[0], "Spam", "1d", sender);
					
				} else if(args[1].equalsIgnoreCase("werbung")) {
					
					Mute_methoden.tempmute(args[0], "Werbung", "10d", sender);
					
				} else {
					
					sender.sendMessage("§cDiese Vorlage existiert nicht!");
					
				}
				
			} else {
				sender.sendMessage("§c/punish [Spielername]");
			}
			
		} else if(sender.hasPermission("sup") || sender.hasPermission("content")) {
			
			if(args.length == 1) {
				
				if(sender instanceof ProxiedPlayer) {
					
					ProxiedPlayer p = (ProxiedPlayer) sender;
					
					p.sendMessage("§e" + args[0] + " §cbestrafen für:");
					p.sendMessage("");
					
					TextComponent wortwahl = new TextComponent("§bWortwahl §7(Mute)");
					wortwahl.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aWortwahl §cbestrafen").create()));
					wortwahl.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " wortwahl"));					
					p.sendMessage(wortwahl);
					
					p.sendMessage("");
					
					TextComponent spam = new TextComponent("§bSpam §7(Mute)");
					spam.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aSpam §cbestrafen").create()));
					spam.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " spam"));					
					p.sendMessage(spam);
					
					p.sendMessage("");
					
					TextComponent werbung = new TextComponent("§bWerbung §7(Mute)");
					werbung.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + args[0] + " §cfür §aWerbung §cbestrafen").create()));
					werbung.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punish " + args[0] + " spam"));					
					p.sendMessage(werbung);
					
					p.sendMessage("");
					
				} else {
					sender.sendMessage("§c/punish [Spielername] [Vorlage]");
				}
				
			} else if(args.length == 2) {
				
				if(args[1].equalsIgnoreCase("wortwahl")) {
					
					Mute_methoden.tempmute(args[0], "Unangemessene Wortwahl", "1h", sender);
					
				} else if(args[1].equalsIgnoreCase("spam")) {
					
					Mute_methoden.tempmute(args[0], "Spam", "1d", sender);
					
				} else if(args[1].equalsIgnoreCase("werbung")) {
					
					Mute_methoden.tempmute(args[0], "Werbung", "10d", sender);
					
				} else {
					
					sender.sendMessage("§cDiese Vorlage existiert nicht!");
					
				}
				
			} else {
				sender.sendMessage("§c/punish [Spielername]");
			}
			
		}
		
	}

}
