package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_ping extends Command {

	private main plugin;

	public COMMAND_ping(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			sender.sendMessage(plugin.prefix + "§3Dein Ping: §a" + p.getPing() + "ms") ;
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
	}
}
