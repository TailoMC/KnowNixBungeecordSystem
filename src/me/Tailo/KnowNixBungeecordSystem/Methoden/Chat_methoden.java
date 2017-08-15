package me.Tailo.KnowNixBungeecordSystem.Methoden;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import me.Tailo.KnowNixBungeecordSystem.System.main;

public class Chat_methoden {

	private static main plugin;

	@SuppressWarnings("static-access")
	public Chat_methoden(main main) {
		this.plugin = main;
		loadBlacklists();
	}
	
	private static void loadBlacklists() {
		
		plugin.zensur.add("fuck");
		plugin.zensur.add("fick");
		plugin.zensur.add("furck");
		plugin.zensur.add("hure");
		plugin.zensur.add("hvre");
		plugin.zensur.add("wichser");
		plugin.zensur.add("wixer");
		plugin.zensur.add("wixxer");
		plugin.zensur.add("wixxer");
		plugin.zensur.add("f u c k");
		plugin.zensur.add("f i c k");
		plugin.zensur.add("fu ck");
		plugin.zensur.add("fuc k");
		plugin.zensur.add("f uck");
		plugin.zensur.add("fi ck");
		plugin.zensur.add("f ick");
		plugin.zensur.add("fic k");
		plugin.zensur.add("spast");
		plugin.zensur.add("spasst");
		plugin.zensur.add("spas st");
		plugin.zensur.add("lappen");
		plugin.zensur.add("lapppen");
		plugin.zensur.add("noob");
		plugin.zensur.add("n o o b");
		plugin.zensur.add("n oob");
		plugin.zensur.add("no ob");
		plugin.zensur.add("noo b");
		plugin.zensur.add("n oo b");
		plugin.zensur.add("n o ob");
		plugin.zensur.add("no o b");
		plugin.zensur.add("nazi");
		plugin.zensur.add("opfer");
		plugin.zensur.add("fotze");
		plugin.zensur.add("arsch");
		plugin.zensur.add("nutte");
		plugin.zensur.add("bastard");
		plugin.zensur.add("l2p");
		plugin.zensur.add("missgeburt");
		plugin.zensur.add("fehlgeburd");
		plugin.zensur.add("penis");
		plugin.zensur.add("pernis");
		plugin.zensur.add("vagina");
		plugin.zensur.add(" scheide ");
		plugin.zensur.add("scheiß");
		plugin.zensur.add("scheis");
		plugin.zensur.add(" heil ");
		plugin.zensur.add("hitler");
		plugin.zensur.add(" ez ");
		plugin.zensur.add("e2");
		plugin.zensur.add(" e z ");
		plugin.zensur.add("huso");
		plugin.zensur.add(" ar sch ");
		plugin.zensur.add(" ars ch ");
		plugin.zensur.add("misset");
		plugin.zensur.add("mißgeburt");
		plugin.zensur.add("misgeburt");
		plugin.zensur.add("huhre");
		plugin.zensur.add("huure");
		plugin.zensur.add("huuure");
		plugin.zensur.add("hurre");
		plugin.zensur.add("mudda");
		plugin.zensur.add("schlampe");
		plugin.zensur.add("schlamppe");
		plugin.zensur.add(" anal ");
		plugin.zensur.add(" annal ");
		plugin.zensur.add("sexx");
		plugin.zensur.add("sex ");
		plugin.zensur.add(" nap ");
		plugin.zensur.add("möse");
		plugin.zensur.add("m ö s e");
		plugin.zensur.add("m u s c h i");
		plugin.zensur.add("mushi");
		plugin.zensur.add("muschi");
		plugin.zensur.add("mushi");
		plugin.zensur.add("dildo");
		plugin.zensur.add("d i l d o");
		plugin.zensur.add("knecht");
		plugin.zensur.add("hoden");
		plugin.zensur.add("h0den");
		plugin.zensur.add("hodden");
		plugin.zensur.add("pissnelke");
		plugin.zensur.add("fresse");
		plugin.zensur.add("fressse");
		plugin.zensur.add("maul");
		plugin.zensur.add("rammler");
		plugin.zensur.add("bitch");
		plugin.zensur.add("tunte");
		plugin.zensur.add("vergaß");
		plugin.zensur.add("vergas");
		plugin.zensur.add("nigger");
		plugin.zensur.add("neger");
		plugin.zensur.add("nigga");
		plugin.zensur.add("lppn");
		
		plugin.domain.add(".de");
		plugin.domain.add(".com");
		plugin.domain.add(".net");
		plugin.domain.add(".org");
		plugin.domain.add(".info");
		plugin.domain.add(".eu");
		plugin.domain.add(".tv");
		plugin.domain.add(".ch");
		
		plugin.werbung.add(" de ");
		plugin.werbung.add(" com ");
		plugin.werbung.add(" org ");
		plugin.werbung.add(" info ");
		plugin.werbung.add(" eu ");
		plugin.werbung.add(" tv ");
		plugin.werbung.add(" ch ");
		plugin.werbung.add("www");
		plugin.werbung.add("http");
		plugin.werbung.add("abonniert");
		plugin.werbung.add("aboniert");
		plugin.werbung.add("abbonniert");
		plugin.werbung.add("abboniert");
		plugin.werbung.add("abboniert");
		
		ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
			
			@Override
			public void run() {
				plugin.adviolation.clear();
				plugin.verboseviolations.clear();
			}
		}, 0, 1, TimeUnit.HOURS);
		
	}

}
