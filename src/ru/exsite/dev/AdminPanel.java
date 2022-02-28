package ru.exsite.dev;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class AdminPanel implements Listener {
	public static String GUIname = "";
	
	public static ItemStack kick = new ItemStackBuilder(Material.STICK).withName("§cDev §8» §6Кикнуть игрока").build();
	public static ItemStack ban = new ItemStackBuilder(Material.BRICK).withName("§cDev §8» §6Выдать бан игроку").build();
	public static ItemStack mute = new ItemStackBuilder(Material.RECORD_11).withName("§cDev §8» §6Выдать мут игроку").build();
	public static ItemStack jail = new ItemStackBuilder(Material.EXP_BOTTLE).withName("§cDev §8» §6Посадить игрока в тюрьму").build();
	public static ItemStack invsee = new ItemStackBuilder(Material.CHEST).withName("§cDev §8» §6Посмотреть инвентарь игрока").build();
	public static ItemStack endersee = new ItemStackBuilder(Material.ENDER_CHEST).withName("§cDev §8» §6Посмотреть эндер-сундук игрока").build();
	public static ItemStack clear = new ItemStackBuilder(Material.FEATHER).withName("§cDev §8» §6Очистить инвентарь").build();
	
	public static void openAdminPanel(Player p, Player pl) {
		AdminPanel.GUIname = "§cDev §8» §6Действие над §c"+pl.getName();
		Main.DevSelect.put(p.getName(), pl.getName());
		Inventory inv = Bukkit.createInventory((InventoryHolder)null, (int)9*3, AdminPanel.GUIname);
		inv.setItem(10, AdminPanel.kick);
		inv.setItem(11, AdminPanel.ban);
		inv.setItem(12, AdminPanel.mute);
		inv.setItem(13, AdminPanel.jail);
		inv.setItem(14, AdminPanel.invsee);
		inv.setItem(15, AdminPanel.endersee);
		inv.setItem(16, AdminPanel.clear);
		p.openInventory(inv);
	}
}
