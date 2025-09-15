package com.hbm.handler.nei;

import com.hbm.blocks.generic.BlockToolConversion;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.item.ItemStack;

public class ToolingHandler extends NEIUniversalHandler {
	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModItems.boltgun),
				new ItemStack(ModItems.blowtorch),
				new ItemStack(ModItems.acetylene_torch)};
	}
	public ToolingHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.tooling.recipe_name"), BlockToolConversion.getRecipes(true), BlockToolConversion.getRecipes(false));
	}

	@Override
	public String getKey() {
		return "ntmTooling";
	}
}
