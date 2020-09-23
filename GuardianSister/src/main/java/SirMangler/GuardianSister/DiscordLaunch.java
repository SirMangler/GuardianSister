package SirMangler.GuardianSister;

import java.text.Normalizer;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordLaunch extends ListenerAdapter {
	private static JDA jda;

	static List<String> lines;
	static int count = 0;

	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(args[0]).buildBlocking();

			jda.getPresence().setStatus(OnlineStatus.INVISIBLE);
			jda.addEventListener(new DiscordLaunch());
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		System.out.println(event.getMessage().getContentStripped());

		if (event.getAuthor().getId().equalsIgnoreCase("417888570603339780")) {
			String string = event.getMessage().getContentStripped();
			StringBuilder sb = new StringBuilder(string.length());
			string = Normalizer.normalize(string, Normalizer.Form.NFD);
			for (char c : string.toCharArray()) {
				if (c <= '\u007F')
					sb.append(c);
			}

			String msg = sb.toString().toLowerCase();
			String raw_chars = event.getMessage().getContentRaw().replace(" ", "").replace(" ", "");

			if (msg.replace(" ", "").matches(".*(j|🇯|\\|)+(o|🇴|0)+(n|🇳|π)+(i|!|🇮|\\||1)+(l|🇱|!|\\||l)+.*")
					|| raw_chars.contains(":relaxed:⚐:skull_crossbones::skull_crossbones::raised_hand::frowning2:")
					|| raw_chars.contains("☺️⚐☠️☠️✋☹️") || raw_chars.contains("🙰□︎■︎■︎♓︎●︎")
					|| raw_chars.contains("🇯 🇴 🇳 🇳 🇮 🇱") || msg.contains(".--- --- -. -. .. .-..")
					|| msg.contains("_|0|\\\\||\\\\|!|_") || msg.contains("_)0|\\\\||\\\\|!1")
					|| msg.contains("yonnil")) { // || msg.matches(".* \\(.*\\)")
				count++;

				event.getMessage().delete().queue();
				event.getGuild().getController().kick(event.getMember(), "jonnil").queue();

				event.getGuild().getTextChannelById("466824732856025099")
						.sendMessage("Matched: `" + event.getMessage().getContentRaw() + "` (Count: " + count + ")")
						.queue();
			}
		}
	}
}
