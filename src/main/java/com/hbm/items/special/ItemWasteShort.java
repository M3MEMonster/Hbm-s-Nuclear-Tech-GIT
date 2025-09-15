package com.hbm.items.special;

import java.util.List;

import com.hbm.main.MainRegistry;

import com.hbm.util.i18n.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemWasteShort extends Item {

	public ItemWasteShort() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.controlTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < WasteClass.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.ITALIC + WasteClass.values()[rectify(stack.getItemDamage())].name);

		super.addInformation(stack, player, list, bool);
	}

	public static int rectify(int meta) {
		return Math.abs(meta) % WasteClass.values().length;
	}

	public enum WasteClass {

		//all decayed versions include lead-types and classic nuclear waste
		URANIUM235(I18nUtil.resolveKey("desc.item.waste_short.URANIUM235"), 0, 100),			//fresh recycling makes iodine, caesium and technetium, depleted turns into neptunium
		URANIUM233(I18nUtil.resolveKey("desc.item.waste_short.URANIUM233"), 50, 100),			//fresh recycling makes iodine, caesium and technetium, depleted turns into u235
		NEPTUNIUM(I18nUtil.resolveKey("desc.item.waste_short.NEPTUNIUM"), 150, 500),		//funny fission fragments + polonium and pu238 and 239 / u235
		PLUTONIUM239(I18nUtil.resolveKey("desc.item.waste_short.PLUTONIUM239"), 250, 1000),	//funny fission fragments + pu240 and 241 / u238 (actually u236 but fuck you)
		PLUTONIUM240(I18nUtil.resolveKey("desc.item.waste_short.PLUTONIUM240"), 350, 1000),	//funny fission fragments + pu241 / u238  + lead
		PLUTONIUM241(I18nUtil.resolveKey("desc.item.waste_short.PLUTONIUM241"), 500, 1000),	//funny fission fragments + am241 / 242 / np237 + bismuth
		AMERICIUM242(I18nUtil.resolveKey("desc.item.waste_short.AMERICIUM242"), 750, 1000),	//funny fission fragments + the same as 242 but with more curium
		AMERICIUM241(I18nUtil.resolveKey("desc.item.waste_short.AMERICIUM241"), 750, 1000),   //funny fission fragments + cm242 / am242 / more curium / pu239
		SCHRABIDIUM(I18nUtil.resolveKey("desc.item.waste_short.SCHRABIDIUM"), 1000, 1000), //funniest fission fragments
		BERKELIUM247(I18nUtil.resolveKey("desc.item.waste_short.BERKELIUM247"), 1000, 1000),  //funny fission fragments + curium / californium / americium
		CURIUM244(I18nUtil.resolveKey("desc.item.waste_short.CURIUM244"), 1000, 1000),	    //californium / lots rare curium isotopes
		CURIUM245(I18nUtil.resolveKey("desc.item.waste_short.CURIUM245"), 1000, 1000);        //more californium

		public String name;
		public int liquid;
		public int gas;

		private WasteClass(String name, int liquid, int gas) {
			this.name = name;
			this.liquid = liquid;
			this.gas = gas;
		}
	}
}
