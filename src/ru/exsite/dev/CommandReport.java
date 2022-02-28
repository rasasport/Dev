package ru.exsite.dev;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReport implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if(!(sender instanceof Player)) {
	          sender.sendMessage("Данная команда для игроков.");
	          return false; 
	    }
	    Player p = (Player)sender;
		if(!p.hasPermission("Dev.report")) {
			p.sendMessage("§cDev §8» §6У Вас не достаточно прав.");
			return true;
		}
        if (args.length < 2) {
            p.sendMessage("§cDev §8» §6Использование: §c/" + label + " §8<§aИгрок§8> <§aСообщение§8>");
            return false;
        }
        Player pl = Bukkit.getPlayer(args[0]);
        if(pl == null) {
        	p.sendMessage("§cDev §8» §6Данный игрок не в сети.");
        	return false;
        }
		String msg = "";
		for (int chat = 1; chat < args.length; ++chat) {
			msg = msg + args[chat] + " ";
		}
		msg = msg.substring(0, msg.length()-1);
		for(Player x:Bukkit.getOnlinePlayers()) {
			if(Main.DevSpectate.get(x.getName()) != null) {
				if(Main.DevSpectate.get(x.getName()).booleanValue()) {
					x.sendMessage("§cDev §8» §6Жалоба на игрока: §c"+pl.getName());
					x.sendMessage("§cDev §8» §6Сообщение: §c"+msg);
					x.sendMessage("§cDev §8» §6Отправитель: §c"+p.getName());
					x.teleport(new Location(pl.getLocation().getWorld(), pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), pl.getLocation().getYaw(), pl.getLocation().getPitch()));
				}
			}
		}
		p.sendMessage("§cDev §8» §6Жалоба на игрока §c"+pl.getName()+"§6 успешно отправлена.");
		return false;
	}

}
