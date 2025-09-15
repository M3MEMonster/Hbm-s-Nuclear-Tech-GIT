package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.gui.GUIScreenHolotape;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.EnumUtil;

import com.hbm.util.i18n.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemHolotapeImage extends ItemHoloTape implements IGUIProvider {

	public ItemHolotapeImage() {
		super(EnumHoloImage.class, false, false);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		EnumHoloImage holo = EnumUtil.grabEnumSafely(EnumHoloImage.class, stack.getItemDamage());
		list.add(I18nUtil.format("desc.item.holotape_image.color", holo.colorCode, holo.colorName));
		list.add(I18nUtil.format("desc.item.holotape_image.label", holo.name));
	}

	public static enum EnumHoloImage {
		HOLO_DIGAMMA(		EnumChatFormatting.RED,			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_DIGAMMA.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_DIGAMMA.name"),				I18nUtil.resolveKey("desc.item.holotape_image.HOLO_DIGAMMA.text")),
		HOLO_RESTORED(		EnumChatFormatting.RED,			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_RESTORED.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_RESTORED.name"),				I18nUtil.resolveKey("desc.item.holotape_image.HOLO_RESTORED.text")),
		HOLO_FE_HALL(		EnumChatFormatting.GREEN,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_HALL.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_HALL.name")	,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_HALL.text")),
		HOLO_FE_CORRIDOR(	EnumChatFormatting.GREEN,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_CORRIDOR.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_CORRIDOR.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_CORRIDOR.text")),
		HOLO_FE_SERVER(		EnumChatFormatting.GREEN,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_SERVER.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_SERVER.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FE_SERVER.text")),
		HOLO_FEH_DOME(		EnumChatFormatting.RED,			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_DOME.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_DOME.name"),			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_DOME.text")),
		HOLO_FEH_BOAT(		EnumChatFormatting.RED,			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_BOAT.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_BOAT.name"),			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_BOAT.text")),
		HOLO_FEH_LSC(		EnumChatFormatting.RED,			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_LSC.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_LSC.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_FEH_LSC.text")),
		HOLO_F3_RC(			EnumChatFormatting.DARK_GREEN,	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_RC.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_RC.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_RC.text")),
		HOLO_F3_IV(			EnumChatFormatting.DARK_GREEN,	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_IV.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_IV.name"),			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_IV.text")),
		HOLO_F3_WM(			EnumChatFormatting.DARK_GREEN,	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_WM.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_WM.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_F3_WM.text")),
		HOLO_NV_CRATER(		EnumChatFormatting.GOLD,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_CRATER.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_CRATER.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_CRATER.text")),
		HOLO_NV_DIVIDE(		EnumChatFormatting.GOLD,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_DIVIDE.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_DIVIDE.name"),			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_DIVIDE.text")),
		HOLO_NV_BM(			EnumChatFormatting.GOLD,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_BM.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_BM.name"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_NV_BM.text")),
		HOLO_O_1(			EnumChatFormatting.WHITE,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_1.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_1.name"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_1.text")),
		HOLO_O_2(			EnumChatFormatting.WHITE,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_2.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_2.name"),			I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_2.text")),
		HOLO_O_3(			EnumChatFormatting.WHITE,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_3.color"),	I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_3.name"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_O_3.text")),
		HOLO_CHALLENGE(		EnumChatFormatting.GRAY,		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_CHALLENGE.color"),		I18nUtil.resolveKey("desc.item.holotape_image.HOLO_CHALLENGE.name"),				I18nUtil.resolveKey("desc.item.holotape_image.HOLO_CHALLENGE.text")),
		;

		private String name;
		private String text;
		private String colorName;
		private EnumChatFormatting colorCode;

		private EnumHoloImage(EnumChatFormatting colorCode, String colorName, String name, String text) {
			this.name = name;
			this.text = text;
			this.colorName = colorName;
			this.colorCode = colorCode;
		}

		public String getText() {
			return this.text;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenHolotape();
	}
}
