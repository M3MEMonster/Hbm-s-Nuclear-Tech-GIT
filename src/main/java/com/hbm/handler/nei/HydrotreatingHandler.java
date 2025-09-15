package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.HydrotreatingRecipes;
import com.hbm.util.i18n.I18nUtil;

public class HydrotreatingHandler extends NEIUniversalHandler {

	public HydrotreatingHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.hydrotreating.recipe_name"), ModBlocks.machine_hydrotreater, HydrotreatingRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmHydrotreating";
	}
}
