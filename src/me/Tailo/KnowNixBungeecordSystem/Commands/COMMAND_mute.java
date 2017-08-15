package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Mute_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_mute extends Command {

	@SuppressWarnings("unused")
	private main plugin;

	public COMMAND_mute(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("srmod")) {
			if(args.length == 1) {
				
				Mute_methoden.mute(args[0], null, sender);
				
			} else
				if(args.length >= 2) {
					
					String reason = "";
					for(int msg = 1; msg < args.length; msg++) {
						reason = reason + args[msg] + " ";
					}
					
					Mute_methoden.mute(args[0], reason, sender);
					
				} else {
					sender.sendMessage("§c/mute [Spielername] [Grund]");
				}
		}
		
	}

}
