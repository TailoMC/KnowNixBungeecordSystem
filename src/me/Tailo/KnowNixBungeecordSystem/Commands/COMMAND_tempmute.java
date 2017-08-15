package me.Tailo.KnowNixBungeecordSystem.Commands;

import me.Tailo.KnowNixBungeecordSystem.Methoden.Mute_methoden;
import me.Tailo.KnowNixBungeecordSystem.System.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class COMMAND_tempmute extends Command {

	private main plugin;

	public COMMAND_tempmute(String name, main main) {
		super(name);
		this.plugin = main;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("srmod")) {
			if(args.length == 2) {
				
				try {
					Mute_methoden.tempmute(args[0], null, args[1], sender);
				} catch(Exception e) {
					sender.sendMessage(plugin.prefix + "§cDie Zeit muss eine §eZahl §csein!");
				}
				
			} else
				if(args.length >= 3) {
					
					String reason = "";
					for(int msg = 2; msg < args.length; msg++) {
						reason = reason + args[msg] + " ";
					}
					
					try {
						Mute_methoden.tempmute(args[0], reason, args[1], sender);
					} catch(Exception e) {
						sender.sendMessage(plugin.prefix + "§cDie Zeit muss eine §eZahl §csein!");
					}
					
				} else {
					sender.sendMessage("§c/tempmute [Spielername] [Zeit] [Grund]");
				}
		}
	}

}
