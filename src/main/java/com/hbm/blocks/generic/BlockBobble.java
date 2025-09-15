package com.hbm.blocks.generic;

import com.hbm.inventory.gui.GUIScreenBobble;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.nbt.INBTTileEntityTransformable;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockBobble extends BlockContainer implements IGUIProvider, INBTBlockTransformable {

	public BlockBobble() {
		super(Material.iron);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {

		TileEntityBobble entity = (TileEntityBobble) world.getTileEntity(x, y, z);

		if(entity != null) {
			return new ItemStack(this, 1, entity.type.ordinal());
		}

		return super.getPickBlock(target, world, x, y, z, player);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			if(!world.isRemote) {
				TileEntityBobble entity = (TileEntityBobble) world.getTileEntity(x, y, z);
				if(entity != null) {
					EntityItem item = new EntityItem(world, x + 0.5, y, z + 0.5, new ItemStack(this, 1, entity.type.ordinal()));
					item.motionX = 0;
					item.motionY = 0;
					item.motionZ = 0;
					world.spawnEntityInWorld(item);
				}
			}
			harvesters.set(null);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;

		} else {
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for(int i = 1; i < BobbleType.values().length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int meta = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);

		TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(x, y, z);
		bobble.type = BobbleType.values()[Math.abs(stack.getItemDamage()) % BobbleType.values().length];
		bobble.markDirty();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(5.5F * f, 0.0F, 5.5F * f, 1.0F - 5.5F * f, 0.625F, 1.0F - 5.5F * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return (meta + coordBaseMode * 4) % 16;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBobble();
	}

	public static class TileEntityBobble extends TileEntity implements INBTTileEntityTransformable {

		public BobbleType type = BobbleType.NONE;

		@Override
		public boolean canUpdate() {
			return false;
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.type = BobbleType.values()[Math.abs(nbt.getByte("type")) % BobbleType.values().length];
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setByte("type", (byte) type.ordinal());
		}

		@Override
		public void transformTE(World world, int coordBaseMode) {
			type = BobbleType.values()[world.rand.nextInt(BobbleType.values().length - 1) + 1];
		}
	}

	public static enum BobbleType {

		NONE(I18nUtil.resolveKey("desc.block.bobble.NONE.name"),								I18nUtil.resolveKey("desc.block.bobble.NONE.label"),			null,														null,																								false,	ScrapType.BOARD_BLANK),
		STRENGTH(		I18nUtil.resolveKey("desc.block.bobble.STRENGTH.name"),							I18nUtil.resolveKey("desc.block.bobble.STRENGTH.label"),		null,														I18nUtil.resolveKey("desc.block.bobble.STRENGTH.inscription"),													false,	ScrapType.BRIDGE_BIOS),
		PERCEPTION(		I18nUtil.resolveKey("desc.block.bobble.PERCEPTION.name"),						I18nUtil.resolveKey("desc.block.bobble.PERCEPTION.label"),	null,														I18nUtil.resolveKey("desc.block.bobble.PERCEPTION.inscription"),												false,	ScrapType.BRIDGE_NORTH),
		ENDURANCE(		I18nUtil.resolveKey("desc.block.bobble.ENDURANCE.name"),						I18nUtil.resolveKey("desc.block.bobble.ENDURANCE.label"),	null,														I18nUtil.resolveKey("desc.block.bobble.ENDURANCE.inscription"),														false,	ScrapType.BRIDGE_SOUTH),
		CHARISMA(		I18nUtil.resolveKey("desc.block.bobble.CHARISMA.name"),							I18nUtil.resolveKey("desc.block.bobble.CHARISMA.label"),		null,														I18nUtil.resolveKey("desc.block.bobble.CHARISMA.inscription"),														false,	ScrapType.BRIDGE_IO),
		INTELLIGENCE(	I18nUtil.resolveKey("desc.block.bobble.INTELLIGENCE.name"),						I18nUtil.resolveKey("desc.block.bobble.INTELLIGENCE.label"),	null,														I18nUtil.resolveKey("desc.block.bobble.INTELLIGENCE.inscription"),						false,	ScrapType.BRIDGE_BUS),
		AGILITY(		I18nUtil.resolveKey("desc.block.bobble.AGILITY.name"),							I18nUtil.resolveKey("desc.block.bobble.AGILITY.label"),		null,														I18nUtil.resolveKey("desc.block.bobble.AGILITY.inscription"),													false,	ScrapType.BRIDGE_CHIPSET),
		LUCK(			I18nUtil.resolveKey("desc.block.bobble.LUCK.name"),								I18nUtil.resolveKey("desc.block.bobble.LUCK.label"),			null,														I18nUtil.resolveKey("desc.block.bobble.LUCK.inscription"),																false,	ScrapType.BRIDGE_CMOS),
		BOB(			I18nUtil.resolveKey("desc.block.bobble.BOB.name"),	                            I18nUtil.resolveKey("desc.block.bobble.BOB.label"),	I18nUtil.resolveKey("desc.block.bobble.BOB.contribution"),									I18nUtil.format("desc.block.bobble.BOB.inscription", System.getProperty("user.name")),										false,	ScrapType.CPU_SOCKET),
		FRIZZLE(		I18nUtil.resolveKey("desc.block.bobble.FRIZZLE.name"),							I18nUtil.resolveKey("desc.block.bobble.FRIZZLE.label"),		I18nUtil.resolveKey("desc.block.bobble.FRIZZLE.contribution"),											I18nUtil.resolveKey("desc.block.bobble.FRIZZLE.inscription"),																					true,	ScrapType.CPU_CLOCK),
		PU238(			I18nUtil.resolveKey("desc.block.bobble.PU238.name"),							I18nUtil.resolveKey("desc.block.bobble.PU238.label"),		I18nUtil.resolveKey("desc.block.bobble.PU238.contribution"),							null,																								false,	ScrapType.CPU_REGISTER),
		VT(				I18nUtil.resolveKey("desc.block.bobble.VT.name"),							I18nUtil.resolveKey("desc.block.bobble.VT.label"),		I18nUtil.resolveKey("desc.block.bobble.VT.contribution"),			I18nUtil.resolveKey("desc.block.bobble.VT.inscription"),																		true,	ScrapType.CPU_EXT),
		DOC(			I18nUtil.resolveKey("desc.block.bobble.DOC.name"),						I18nUtil.resolveKey("desc.block.bobble.DOC.label"),	I18nUtil.resolveKey("desc.block.bobble.DOC.contribution"),						I18nUtil.resolveKey("desc.block.bobble.DOC.inscription"),														true,	ScrapType.CPU_CACHE),
		BLUEHAT(		I18nUtil.resolveKey("desc.block.bobble.BLUEHAT.name"),						I18nUtil.resolveKey("desc.block.bobble.BLUEHAT.label"),	I18nUtil.resolveKey("desc.block.bobble.BLUEHAT.contribution"),													I18nUtil.resolveKey("desc.block.bobble.BLUEHAT.inscription"),													true,	ScrapType.MEM_16K_A),
		PHEO(			I18nUtil.resolveKey("desc.block.bobble.PHEO.name"),								I18nUtil.resolveKey("desc.block.bobble.PHEO.label"),		I18nUtil.resolveKey("desc.block.bobble.PHEO.contribution"),	I18nUtil.resolveKey("desc.block.bobble.PHEO.inscription"),						true,	ScrapType.MEM_16K_B),
		ADAM29(			I18nUtil.resolveKey("desc.block.bobble.ADAM29.name"),							I18nUtil.resolveKey("desc.block.bobble.ADAM29.label"),		I18nUtil.resolveKey("desc.block.bobble.ADAM29.contribution"),							I18nUtil.resolveKey("desc.block.bobble.ADAM29.inscription"),	true,	ScrapType.MEM_16K_C),
		UFFR(			I18nUtil.resolveKey("desc.block.bobble.UFFR.name"),								I18nUtil.resolveKey("desc.block.bobble.UFFR.label"),			I18nUtil.resolveKey("desc.block.bobble.UFFR.contribution"),							I18nUtil.resolveKey("desc.block.bobble.UFFR.inscription"),																						false,	ScrapType.MEM_SOCKET),
		VAER(			I18nUtil.resolveKey("desc.block.bobble.VAER.name"),								I18nUtil.resolveKey("desc.block.bobble.VAER.label"),			I18nUtil.resolveKey("desc.block.bobble.VAER.contribution"),													I18nUtil.resolveKey("desc.block.bobble.VAER.inscription"),											true,	ScrapType.MEM_16K_D),
		NOS(			I18nUtil.resolveKey("desc.block.bobble.NOS.name"),						I18nUtil.resolveKey("desc.block.bobble.NOS.label"),	I18nUtil.resolveKey("desc.block.bobble.NOS.contribution"),									I18nUtil.resolveKey("desc.block.bobble.NOS.inscription"),					true,	ScrapType.BOARD_TRANSISTOR),
		DRILLGON(		I18nUtil.resolveKey("desc.block.bobble.DRILLGON.name"),						I18nUtil.resolveKey("desc.block.bobble.DRILLGON.label"),	I18nUtil.resolveKey("desc.block.bobble.DRILLGON.contribution"),												null,																								false,	ScrapType.CPU_LOGIC),
		CIRNO(			I18nUtil.resolveKey("desc.block.bobble.CIRNO.name"),							I18nUtil.resolveKey("desc.block.bobble.CIRNO.label"),		I18nUtil.resolveKey("desc.block.bobble.CIRNO.contribution"),						I18nUtil.resolveKey("desc.block.bobble.CIRNO.inscription"),																			true,	ScrapType.BOARD_BLANK),
		GWEN(			I18nUtil.resolveKey("desc.block.bobble.GWEN.name"),								I18nUtil.resolveKey("desc.block.bobble.GWEN.label"),			I18nUtil.resolveKey("desc.block.bobble.GWEN.contribution"),											I18nUtil.resolveKey("desc.block.bobble.GWEN.inscription"),																					true,	ScrapType.BOARD_BLANK),
		JUICE(			I18nUtil.resolveKey("desc.block.bobble.JUICE.name"),						I18nUtil.resolveKey("desc.block.bobble.JUICE.label"),	I18nUtil.resolveKey("desc.block.bobble.JUICE.contribution"),					I18nUtil.resolveKey("desc.block.bobble.JUICE.inscription"),								true,	ScrapType.BOARD_BLANK),
		JAMESH_2(		I18nUtil.resolveKey("desc.block.bobble.JAMESH_2.name"),							I18nUtil.resolveKey("desc.block.bobble.JAMESH_2.label"),		I18nUtil.resolveKey("desc.block.bobble.JAMESH_2.contribution"),										I18nUtil.resolveKey("desc.block.bobble.JAMESH_2.inscription"),																						true,	ScrapType.BOARD_BLANK),
		PEEP(			I18nUtil.resolveKey("desc.block.bobble.PEEP.name"),								I18nUtil.resolveKey("desc.block.bobble.PEEP.label"),	I18nUtil.resolveKey("desc.block.bobble.PEEP.contribution"),											I18nUtil.resolveKey("desc.block.bobble.PEEP.inscription"),											true,	ScrapType.CPU_CLOCK),
		MICROWAVE(		I18nUtil.resolveKey("desc.block.bobble.MICROWAVE.name"),						I18nUtil.resolveKey("desc.block.bobble.MICROWAVE.label"),		I18nUtil.resolveKey("desc.block.bobble.MICROWAVE.contribution"),		I18nUtil.resolveKey("desc.block.bobble.MICROWAVE.inscription"),                                                                    true, ScrapType.BRIDGE_BIOS),
		MELLOW(			I18nUtil.resolveKey("desc.block.bobble.MELLOW.name"),				I18nUtil.resolveKey("desc.block.bobble.MELLOW.label"),			I18nUtil.resolveKey("desc.block.bobble.MELLOW.contribution"),						I18nUtil.resolveKey("desc.block.bobble.MELLOW.inscription"),												true,	ScrapType.CARD_PROCESSOR),
		MRKIMKIMORA(	I18nUtil.resolveKey("desc.block.bobble.MRKIMKIMORA.name"),						I18nUtil.resolveKey("desc.block.bobble.MRKIMKIMORA.label"),		I18nUtil.resolveKey("desc.block.bobble.MRKIMKIMORA.contribution"),						I18nUtil.resolveKey("desc.block.bobble.MRKIMKIMORA.inscription"),												false,	ScrapType.BOARD_BLANK),
		ABEL(			I18nUtil.resolveKey("desc.block.bobble.ABEL.name"), 						I18nUtil.resolveKey("desc.block.bobble.ABEL.label"), 		I18nUtil.resolveKey("desc.block.bobble.ABEL.contribution"), 	I18nUtil.resolveKey("desc.block.bobble.ABEL.inscription"),																				true,	ScrapType.CPU_REGISTER);

		public String name;			//the title of the tooltip
		public String label;		//the name engraved in the socket
		public String contribution;	//what contributions this person has made, if applicable
		public String inscription;	//the flavor text
		public boolean skinLayers;
		public ScrapType scrap;

		private BobbleType(String name, String label, String contribution, String inscription, boolean layers, ScrapType scrap) {
			this.name = name;
			this.label = label;
			this.contribution = contribution;
			this.inscription = inscription;
			this.skinLayers = layers;
			this.scrap = scrap;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenBobble((TileEntityBobble) world.getTileEntity(x, y, z));
	}
}
