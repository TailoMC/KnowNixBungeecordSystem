package me.Tailo.KnowNixBungeecordSystem.Permission;

import me.Tailo.KnowNixBungeecordSystem.Permission.Permission_methoden.Group;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUID;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class COMMAND_perm extends Command {

	private main plugin;

	public COMMAND_perm(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("admin") | sender instanceof ConsoleCommandSender) {
			
			if(args.length == 3) {
				
				if(args[1].equalsIgnoreCase("setgroup")) {
					
					String uuid = UUID.getUUID(args[0]);
					
					if(uuid != null) {
						
						Group group = Permission_methoden.getGroup(args[2]);
						
						Permission_methoden.setGroup(uuid, group);
					
						sender.sendMessage(plugin.prefix + "§6" + args[0] + " §3wurde die Gruppe §6" + Permission_methoden.getGroupName(group) + " §3zugewiesen!");
						
					} else {
						sender.sendMessage(plugin.prefix + "§cDieser Spieler existiert nicht!");
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§c/perm [Spielername] setgroup [Gruppe]");
					sender.sendMessage(plugin.prefix + "§c/perm [Spielername] lookup");	
				}
				
			} else
				if(args.length == 2) {
					
					if(args[1].equalsIgnoreCase("lookup")) {
						
						java.util.UUID uuid = UUIDFetcher.getUUID(args[0]);
						
						if(uuid != null) {
							sender.sendMessage(plugin.prefix + "§bName: §a" + UUIDFetcher.getName(uuid));
							sender.sendMessage(plugin.prefix + "§bRang: §a" + Permission_methoden.getGroupName(Permission_methoden.getGroup(uuid)));
							sender.sendMessage(plugin.prefix + "§bUUID: §a" + uuid);
						} else {
							sender.sendMessage(plugin.prefix + "§cDieser Spieler existiert nicht!");
						}
						
					} else {
						sender.sendMessage(plugin.prefix + "§c/perm [Spielername] setgroup [Gruppe]");
						sender.sendMessage(plugin.prefix + "§c/perm [Spielername] lookup");	
					}
					
				} else {
					sender.sendMessage(plugin.prefix + "§c/perm [Spielername] setgroup [Gruppe]");
					sender.sendMessage(plugin.prefix + "§c/perm [Spielername] lookup");	
				}
			
		}
		
	}

}
