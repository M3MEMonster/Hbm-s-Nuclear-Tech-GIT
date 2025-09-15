package com.hbm.blocks.generic;

import com.hbm.blocks.machine.BlockPillar;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockWriting extends BlockPillar {

	public BlockWriting(Material mat, String top) {
		super(mat, top);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;

		} else if(!player.isSneaking()) {

			ChatStyle red = new ChatStyle().setColor(EnumChatFormatting.RED);
			for (String line : I18nUtil.resolveKeyArray("chat.block.writing")){
				player.addChatMessage(new ChatComponentText(line).setChatStyle(red));
			}
			return true;

		} else {
			return false;
		}
	}
}
