package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CryoRecipes;
import com.hbm.util.i18n.I18nUtil;

public class CryoHandler extends NEIUniversalHandler {

	public CryoHandler() {
		super(I18nUtil.resolveKey("desc.handler.nei.cryo.recipe_name"), ModBlocks.machine_cryo_distill, CryoRecipes.getCryoRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCryodistill";
	}

}

