package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.machine.ItemRTGPellet;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.item.ItemStack;

public class RTGRecipeHandler extends NEIUniversalHandler {

	public RTGRecipeHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.rtg.recipe_name"), new ItemStack[] {
				new ItemStack(ModBlocks.machine_rtg_grey),
				new ItemStack(ModBlocks.machine_difurnace_rtg_off)
			}, ItemRTGPellet.getRecipeMap());
	}

	@Override
	public String getKey() {
		return "ntmRTG";
	}
}
