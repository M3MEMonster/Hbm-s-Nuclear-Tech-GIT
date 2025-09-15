package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AlkylationRecipes;
import com.hbm.util.i18n.I18nUtil;

public class AlkylationHandler extends NEIUniversalHandler {

	public AlkylationHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.alkylation.recipe_name"), ModBlocks.machine_alkylation, AlkylationRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmAlkylation";
	}
}
