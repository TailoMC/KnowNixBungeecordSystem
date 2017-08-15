package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Party_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_p extends Command {

	private main plugin;

	public COMMAND_p(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			if(args.length > 0) {
				
				ProxiedPlayer p = (ProxiedPlayer) sender;
				
				String message = "";
				for(int msg = 0; msg < args.length; msg++) {
					message = message + args[msg] + " ";
				}
				
				Party_methoden.chat(p, message);
				
			} else {
				sender.sendMessage(plugin.partypf + "§cDu musst eine Nachricht eingeben!");
			}
			
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden!");
		}
		
	}

}
