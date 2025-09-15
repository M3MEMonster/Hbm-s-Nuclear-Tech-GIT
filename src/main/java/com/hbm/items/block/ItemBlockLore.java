package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockOreFluid;
import com.hbm.blocks.generic.RedBarrel;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockLore extends ItemBlockBase {

	public ItemBlockLore(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemstack, player, list, bool);

		if(this.field_150939_a instanceof RedBarrel) {
			list.add(I18nUtil.resolveKey("desc.block.lore.barrel"));
		}

		if(this.field_150939_a == ModBlocks.meteor_battery) {
			list.add(I18nUtil.resolveKey("desc.block.lore.meteor_battery"));
		}

		if(this.field_150939_a instanceof BlockOreFluid) {
			for (String line : I18nUtil.resolveKeyArray("desc.block.lore.ore_fluid")){
				list.add(line);
			}
		}

		if(this.field_150939_a == ModBlocks.gravel_diamond) {
			for (String line : I18nUtil.resolveKeyArray("desc.block.lore.gravel_diamond")){
				list.add(line);
			}
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if(this.field_150939_a == ModBlocks.gravel_diamond)
			return EnumRarity.rare;

		if(this.field_150939_a == ModBlocks.block_euphemium || this.field_150939_a == ModBlocks.block_euphemium_cluster || this.field_150939_a == ModBlocks.plasma)
			return EnumRarity.epic;

		return EnumRarity.common;
	}

}
