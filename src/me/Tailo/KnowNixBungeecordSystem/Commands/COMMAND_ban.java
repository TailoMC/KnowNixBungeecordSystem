package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Ban_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_ban extends Command {	

	@SuppressWarnings("unused")
	private main plugin;

	public COMMAND_ban(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {		
		
		if(sender.hasPermission("srmod")) {
			
			if(args.length == 1) {
				
				Ban_methoden.ban(args[0], null, sender);
				
			} else
				if(args.length >= 2) {
					
					String reason = "";
					for(int msg = 1; msg < args.length; msg++) {
						reason = reason + args[msg] + " ";
					}
					
					Ban_methoden.ban(args[0], reason, sender);
					
				} else {
					sender.sendMessage("§c/ban [Spielername] [Grund]");
				}
		}		
		
	}

}
