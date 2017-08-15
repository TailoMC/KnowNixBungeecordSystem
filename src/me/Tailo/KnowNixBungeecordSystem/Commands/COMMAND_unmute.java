package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Bots.Discord;
import me.Tailo.KnowNixBungeecordSystem.Listener.Mute_Listener;
import me.Tailo.KnowNixBungeecordSystem.MySQL.MySQL;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import me.Tailo.KnowNixBungeecordSystem.UUID.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_unmute extends Command {

	private main plugin;

	public COMMAND_unmute(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("sup")) {
			if(args.length == 1) {
				String uuid = UUID.getUUID(args[0]);
				if(Mute_Listener.isMuted(uuid)) {
					
					MySQL.update("DELETE FROM mute WHERE UUID = '" + uuid + "'");
					
					sender.sendMessage(plugin.prefix + "§3Der Spieler §6" + args[0] + " §3wurde entmuted!");
					
					Discord.sendUnbanMessage(args[0], sender.getName());
					
				} else {
					sender.sendMessage(plugin.prefix + "§cDieser Spieler wurde nicht gemuted!");
				}
			} else {
				sender.sendMessage("§c/unmute [Spielername]");
			}
		}
		
	}

}
