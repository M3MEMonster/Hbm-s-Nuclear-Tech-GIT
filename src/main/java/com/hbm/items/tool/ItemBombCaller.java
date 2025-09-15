package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.lib.Library;

import com.hbm.util.i18n.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.hbm.world.WorldUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBombCaller extends Item {

	public ItemBombCaller() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18nUtil.resolveKey("desc.item.bomb_caller"));

		switch (stack.getItemDamage()) {
			case 0: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.0")); break;
			case 1: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.1")); break;
			case 2: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.2")); break;
			case 3: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.3")); break;
			case 4: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.4")); break;
			case 5: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.5")); break;
			case 6: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.6")); break;
			case 7: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.7")); break;
			case 8: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.8")); break;
			default: list.add(I18nUtil.resolveKey("desc.item.bomb_caller.type.error"));

		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;

		boolean b = false;
		boolean b2 = false;

	    if(!world.isRemote)
		{
			EntityBomber bomber;
			switch(stack.getItemDamage()) {

				case 1: bomber = EntityBomber.statFacNapalm(world, x, y, z); break;
				case 2: bomber = EntityBomber.statFacChlorine(world, x, y, z); break;
				case 3: bomber = EntityBomber.statFacOrange(world, x, y, z); break;
				case 4: bomber = EntityBomber.statFacABomb(world, x, y, z); break;
				case 5: bomber = EntityBomber.statFacStinger(world, x, y, z); break;
				case 6: bomber = EntityBomber.statFacBoxcar(world, x, y, z); break;
				case 7: bomber = EntityBomber.statFacPC(world, x, y, z); break;
				case 8: bomber = EntityBomber.statFacCV(world, x, y, z);
				b2 = true; break;
				default: bomber = EntityBomber.statFacCarpet(world, x, y, z);

			}
			WorldUtil.loadAndSpawnEntityInWorld(bomber);
			player.addChatMessage(new ChatComponentText(I18nUtil.resolveKey("chat.item.bomb_caller.1")));
			world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);

			if(b2){
		    	player.addChatMessage(new ChatComponentText(I18nUtil.resolveKey("chat.item.bomb_caller.2")));
		        world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
			}

		}

		stack.stackSize -= 1;

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 2));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 3));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 4));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 8));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_)
	{
		return p_77636_1_.getItemDamage() >= 4;
	}
}
