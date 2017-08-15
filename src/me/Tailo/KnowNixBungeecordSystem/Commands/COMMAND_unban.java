package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Bots.Discord;
import me.Tailo.KnowNixBungeecordSystem.Listener.Ban_Listener;
import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_unban extends Command {

	private main plugin;

	public COMMAND_unban(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("mod")) {
			if(args.length == 1) {
				String uuid = UUID.getUUID(args[0]);
				if(Ban_Listener.isBanned(uuid)) {
					
					MySQL.update("DELETE FROM ban WHERE UUID = '" + uuid + "'");
					
					sender.sendMessage(plugin.prefix + "§3Der Spieler §6" + args[0] + " §3wurde entbannt!");
					
					Discord.sendUnbanMessage(args[0], sender.getName());
					
				} else {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde nicht gebannt!");
				}
			} else {
				sender.sendMessage("§c/unban [Spielername]");
			}
		}
		
	}

}
