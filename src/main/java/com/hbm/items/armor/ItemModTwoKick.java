package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModTwoKick extends ItemArmorMod {

	public ItemModTwoKick() {
		super(ArmorModHandler.servos, false, true, false, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		for (String line : I18nUtil.resolveKeyArray("desc.item.two_kick")){
			list.add(line);
		}
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(I18nUtil.format("desc.item.two_kick.add",stack.getDisplayName()));
	}
}
