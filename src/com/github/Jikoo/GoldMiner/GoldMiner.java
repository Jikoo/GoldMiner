package com.github.Jikoo.GoldMiner;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldMiner extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("GoldDigger enabled!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("GoldDigger disabled!");
	}
	
	@EventHandler
	public void OnBlockBreak(BlockBreakEvent event){
		if(!event.isCancelled()&&event.getBlock().getType().equals(Material.GOLD_ORE)&&!(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))){
			Material inhandmat = event.getPlayer().getItemInHand().getType();
			if(inhandmat.equals(Material.IRON_PICKAXE)||inhandmat.equals(Material.DIAMOND_PICKAXE)){
				ItemStack is = event.getPlayer().getItemInHand();
				if(is.getEnchantmentLevel(Enchantment.SILK_TOUCH)>0)
					return;
				else {
					Block b = event.getBlock();
					event.setCancelled(true);
					b.setType(Material.AIR);
					b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.STONE, 1));
					int nuggetsToSpray = new java.util.Random().nextInt(5+is.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS))+7;
					is.setDurability((short) (is.getDurability()+1));
					event.getPlayer().updateInventory();
					for (int i=0;i<nuggetsToSpray;i++)
						b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
				}
				
			}
		}
	}
}
