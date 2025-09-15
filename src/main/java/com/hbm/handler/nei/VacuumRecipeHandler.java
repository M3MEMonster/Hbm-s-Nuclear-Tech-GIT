package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.util.i18n.I18nUtil;

public class VacuumRecipeHandler extends NEIUniversalHandler {

	public VacuumRecipeHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.vacuum.recipe_name"), ModBlocks.machine_vacuum_distill, RefineryRecipes.getVacuumRecipe());
	}

	@Override
	public String getKey() {
		return "ntmVacuum";
	}
}
