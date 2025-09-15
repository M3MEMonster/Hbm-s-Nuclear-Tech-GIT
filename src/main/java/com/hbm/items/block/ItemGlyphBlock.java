package com.hbm.items.block;

import java.util.List;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemGlyphBlock extends ItemBlock {

	public ItemGlyphBlock(Block block) {
		super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		switch(itemstack.getItemDamage()) {
		case 0: list.add(I18nUtil.resolveKey("desc.block.glyph.hourglass")); break;
		case 1: list.add(I18nUtil.resolveKey("desc.block.glyph.eye")); break;
		case 2: list.add(I18nUtil.resolveKey("desc.block.glyph.pillar")); break;
		case 3: list.add(I18nUtil.resolveKey("desc.block.glyph.ioi")); break;
		case 4: list.add(I18nUtil.resolveKey("desc.block.glyph.delta")); break;
		case 5: list.add(I18nUtil.resolveKey("desc.block.glyph.vtpc")); break;
		case 6: list.add(I18nUtil.resolveKey("desc.block.glyph.cools")); break;
		case 7: list.add(I18nUtil.resolveKey("desc.block.glyph.trefoil")); break;
		case 8: list.add(I18nUtil.resolveKey("desc.block.glyph.pony")); break;
		case 9: list.add(I18nUtil.resolveKey("desc.block.glyph.spark")); break;
		case 10: list.add(I18nUtil.resolveKey("desc.block.glyph.pip")); break;
		case 11: list.add(I18nUtil.resolveKey("desc.block.glyph.triangle")); break;
		case 12: list.add(I18nUtil.resolveKey("desc.block.glyph.linux")); break;
		case 13: list.add(I18nUtil.resolveKey("desc.block.glyph.thirteen")); break;
		case 14: list.add(I18nUtil.resolveKey("desc.block.glyph.digamma")); break;
		case 15: list.add(I18nUtil.resolveKey("desc.block.glyph.celestial_altar")); break;
		}
	}

    public int getMetadata(int meta)
    {
        return meta;
    }
}
