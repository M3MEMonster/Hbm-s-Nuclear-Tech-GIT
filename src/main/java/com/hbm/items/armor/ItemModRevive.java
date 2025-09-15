package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModRevive extends ItemArmorMod {

	public ItemModRevive(int durability) {
		super(ArmorModHandler.extra, false, false, true, false);
		this.setMaxDamage(durability);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.scrumpy) {
			for (String line : I18nUtil.resolveKeyArray("desc.item.revive.scrumpy")){
				list.add(line);
			}
		}
		if(this == ModItems.wild_p) {
			list.add(I18nUtil.resolveKey("desc.item.revive.wild_p"));
		}

		/*list.add(EnumChatFormatting.ITALIC + "In the news:");
		list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Man literally too angry to die.");
		list.add("");
		list.add(EnumChatFormatting.ITALIC + "\"I ain't got time to die\" says local");
		list.add(EnumChatFormatting.ITALIC + "man after ripping the physical manifestation");
		list.add(EnumChatFormatting.ITALIC + "of disaster itself in half.");*/

		list.add("");
		list.add(I18nUtil.format("desc.item.revive.common",(stack.getMaxDamage() - stack.getItemDamage())));
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		list.add(I18nUtil.format("desc.item.revive.add",stack.getDisplayName(), (stack.getMaxDamage() - stack.getItemDamage())));
	}
}
