package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ModSword extends ItemSword {

	public ModSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.saw)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.saw"));
		if(this == ModItems.bat)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.bat"));
		if(this == ModItems.bat_nail)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.bat_nail"));
		if(this == ModItems.golf_club)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.golf_club"));
		if(this == ModItems.pipe_rusty)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.pipe_rusty"));
		if(this == ModItems.pipe_lead)
			list.add(I18nUtil.resolveKey("desc.weapon.modsword.pipe_lead"));
		if(this == ModItems.reer_graar) {
			for (String line : I18nUtil.resolveKeyArray("desc.weapon.modsword.reer_graar")){
				list.add(line);
			}
		}
	}
}
