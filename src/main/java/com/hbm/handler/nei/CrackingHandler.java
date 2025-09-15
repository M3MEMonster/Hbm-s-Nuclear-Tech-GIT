package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CrackingRecipes;
import com.hbm.util.i18n.I18nUtil;

public class CrackingHandler extends NEIUniversalHandler {

	public CrackingHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.cracking.recipe_name"), ModBlocks.machine_catalytic_cracker, CrackingRecipes.getCrackingRecipesForNEI());
	}

	@Override
	public String getKey() {
		return "ntmCracking";
	}
}
