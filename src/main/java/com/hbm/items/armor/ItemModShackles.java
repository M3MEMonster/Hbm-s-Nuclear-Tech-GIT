package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModShackles extends ItemArmorMod {

	public ItemModShackles() {
		super(ArmorModHandler.extra, false, false, true, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		for (String line : I18nUtil.resolveKeyArray("desc.item.shackles")){
			list.add(line);
		}
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		list.add(I18nUtil.format("desc.item.shackles.add", stack.getDisplayName()));
	}
}
