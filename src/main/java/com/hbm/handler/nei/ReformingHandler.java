package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ReformingRecipes;
import com.hbm.util.i18n.I18nUtil;

public class ReformingHandler extends NEIUniversalHandler {

	public ReformingHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.reforming.recipe_name"), ModBlocks.machine_catalytic_reformer, ReformingRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmReforming";
	}
}
