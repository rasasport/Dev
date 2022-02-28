package ru.exsite.dev;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static Map<String, Boolean> DevMode = new HashMap<String, Boolean>();
	public static Map<String, Boolean> DevSpectate = new HashMap<String, Boolean>();
	public static Map<String, Integer> DevNum = new HashMap<String, Integer>();
	public static Map<String, String> DevSelect = new HashMap<String, String>();
	
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		this.getCommand("dev").setExecutor(this);
		this.getCommand("report").setExecutor(new CommandReport());
		saveDefaultConfig();
		int time = this.getConfig().getInt("timer");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {	
				if(Bukkit.getOnlinePlayers().size() > 1) {
					for(Player pl:Bukkit.getOnlinePlayers()) {
						if(DevSpectate.get(pl.getName()) != null) {
							if(DevSpectate.get(pl.getName()).booleanValue()) {
								int i = 0;
								for(Player r:Bukkit.getOnlinePlayers()) {
									if(DevNum.get(pl.getName()) == i) {
										if(!r.getName().equals(pl.getName())) {
											pl.sendMessage("§cDev §8» §6Телепорт к игроку §c"+r.getName());
											pl.teleport(new Location(r.getLocation().getWorld(), r.getLocation().getX(), r.getLocation().getY(), r.getLocation().getZ(), r.getLocation().getYaw(), r.getLocation().getPitch()));
										}
										if(DevNum.get(pl.getName()) >= Bukkit.getOnlinePlayers().size()) {
											DevNum.put(pl.getName(), 0);
										} else {
											DevNum.put(pl.getName(), DevNum.get(pl.getName())+1);
										}
										break;
									}
									i++;
								}
							}
						}
					}
				}
			}
		}, 0L, time*20L);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(AdminPanel.GUIname)) {
			if (e.getCurrentItem() != null) {
				Player p = (Player) e.getWhoClicked();
				String cmd = "";
				if (e.getCurrentItem().equals(AdminPanel.kick)) {
					cmd = this.getConfig().getString("AdminPanel.kick.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.kick.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.ban)) {
					cmd = this.getConfig().getString("AdminPanel.ban.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.ban.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.mute)) {
					cmd = this.getConfig().getString("AdminPanel.mute.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.mute.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.jail)) {
					cmd = this.getConfig().getString("AdminPanel.jail.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.jail.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.invsee)) {
					cmd = this.getConfig().getString("AdminPanel.invsee.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.invsee.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.endersee)) {
					cmd = this.getConfig().getString("AdminPanel.endersee.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.endersee.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				if (e.getCurrentItem().equals(AdminPanel.clear)) {
					cmd = this.getConfig().getString("AdminPanel.clear.command").replace("%player", DevSelect.get(p.getName()));
					if(plugin.getConfig().getString("AdminPanel.clear.send").equals("console")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					} else {
						p.chat("/"+cmd);
					}
				}
				e.setCancelled(true);
			}
		}
	}
	
    @EventHandler
    public void onPvP(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player target = (Player) e.getEntity();
            if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
                Player p = e.getDamager() instanceof Arrow ? (Player) ((Arrow) e.getDamager()).getShooter() : (Player) e.getDamager();
                if (p == target) {
                    return;
                }
                if(DevMode.get(p.getName()) != null) {
                	if(DevMode.get(p.getName()).booleanValue()) {
                		AdminPanel.openAdminPanel(p, target);
                		e.setCancelled(true);
                	}
                }
            }
        }
    }
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if(!(sender instanceof Player)) {
	          sender.sendMessage("Данная команда для игроков.");
	          return false; 
	    }
		Player p = (Player) sender;
		if (args.length == 0) {
			if(!p.hasPermission("Dev.devmode")) {
				p.sendMessage("§cDev §8» §6У Вас не достаточно прав.");
				return true;
			}
			if(DevMode.get(p.getName()) == null || !DevMode.get(p.getName()).booleanValue()) {
				DevMode.put(p.getName(), true);
				p.sendMessage("§cDev §8» §6Режим администратора активирован.");
				return false;
			} else {
				DevMode.put(p.getName(), false);
				DevSpectate.put(p.getName(), false);
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage("§cDev §8» §6Режим администратора успешно выключен.");
			}
			return false;
		} else {
			switch (args[0].toLowerCase()) {
			case "spectate":
				if(!p.hasPermission("Dev.spectate")) {
					p.sendMessage("§cDev §8» §6У Вас не достаточно прав.");
					return true;
				}
				if(DevMode.get(p.getName()) == null || !DevMode.get(p.getName()).booleanValue()) {
					p.sendMessage("§cDev §8» §6У Вас не включен режим администратора.");
					return false;
				}
				if(DevSpectate.get(p.getName()) == null || !DevSpectate.get(p.getName()).booleanValue()) {
					DevSpectate.put(p.getName(), true);
					DevNum.put(p.getName(), 0);
					p.sendMessage("§cDev §8» §6Режим наблюдения успешно активирован.");
					p.setGameMode(GameMode.SPECTATOR);
				} else {
					DevSpectate.put(p.getName(), false);
					p.sendMessage("§cDev §8» §6Режим наблюдения успешно деактивирован.");
					p.setGameMode(GameMode.SURVIVAL);
				}
				break;
			}
		}
		return false;
	}
}
