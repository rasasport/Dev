package ru.exsite.dev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemStackBuilder {

	private final ItemStack ITEM_STACK;

	public ItemStackBuilder(Material mat) {
		this.ITEM_STACK = new ItemStack(mat);
	}

	public ItemStackBuilder(ItemStack item) {
		this.ITEM_STACK = item;
	}

	public ItemStackBuilder withAmount(int amount) {
		this.ITEM_STACK.setAmount(amount);
		return this;
	}

	public ItemStackBuilder withName(String name) {
		ItemMeta meta = this.ITEM_STACK.getItemMeta();
		meta.setDisplayName(name);
		this.ITEM_STACK.setItemMeta(meta);
		return this;
	}

	@SuppressWarnings("unchecked")
	public ItemStackBuilder withLore(String name) {
		ItemMeta meta = this.ITEM_STACK.getItemMeta();
		Object lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<Object>();
		}

		((List<String>) lore).add(name);
		meta.setLore((List<String>) lore);
		this.ITEM_STACK.setItemMeta(meta);
		return this;
	}

	public ItemStackBuilder withDurability(int durability) {
		this.ITEM_STACK.setDurability((short) durability);
		return this;
	}
	
	public ItemStackBuilder withSetSkullOwner(String Owner) {
		Material type = this.ITEM_STACK.getType();
		if (type != Material.SKULL_ITEM) {
			throw new IllegalArgumentException("withColor is only applicable for leather armor!");
		} else {
			SkullMeta meta = (SkullMeta) this.ITEM_STACK.getItemMeta();
			meta.setOwner(Owner);
			this.ITEM_STACK.setItemMeta(meta);
			return this;
		}
	}

	@SuppressWarnings("deprecation")
	public ItemStackBuilder withData(int data) {
		this.ITEM_STACK.setData(new MaterialData(this.ITEM_STACK.getType(), (byte) data));
		return this;
	}

	public ItemStackBuilder withEnchantment(Enchantment enchantment, int level) {
		this.ITEM_STACK.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemStackBuilder withEnchantment(Enchantment enchantment) {
		this.ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
		return this;
	}

	public ItemStackBuilder withType(Material material) {
		this.ITEM_STACK.setType(material);
		return this;
	}

	public ItemStackBuilder clearLore() {
		ItemMeta meta = this.ITEM_STACK.getItemMeta();
		meta.setLore(new ArrayList<String>());
		this.ITEM_STACK.setItemMeta(meta);
		return this;
	}

	public ItemStackBuilder clearEnchantments() {
		Iterator<Enchantment> var2 = this.ITEM_STACK.getEnchantments().keySet()
				.iterator();

		while (var2.hasNext()) {
			Enchantment enchantment = (Enchantment) var2.next();
			this.ITEM_STACK.removeEnchantment(enchantment);
		}

		return this;
	}

	public ItemStackBuilder withColor(Color color) {
		Material type = this.ITEM_STACK.getType();
		if (type != Material.LEATHER_BOOTS
				&& type != Material.LEATHER_CHESTPLATE
				&& type != Material.LEATHER_HELMET
				&& type != Material.LEATHER_LEGGINGS) {
			throw new IllegalArgumentException(
					"withColor is only applicable for leather armor!");
		} else {
			LeatherArmorMeta meta = (LeatherArmorMeta) this.ITEM_STACK
					.getItemMeta();
			meta.setColor(color);
			this.ITEM_STACK.setItemMeta(meta);
			return this;
		}
	}

	public ItemStack build() {
		return this.ITEM_STACK;
	}
}
