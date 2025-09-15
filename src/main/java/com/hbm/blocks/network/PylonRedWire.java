package com.hbm.blocks.network;

import com.hbm.tileentity.network.TileEntityPylon;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class PylonRedWire extends PylonBase {

	public PylonRedWire(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPylon();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(I18nUtil.format("desc.block.network.connection_type",I18nUtil.resolveKey("desc.block.network.type.single")));
		list.add(I18nUtil.format("desc.block.network.connection_range","25m"));
	}
}
