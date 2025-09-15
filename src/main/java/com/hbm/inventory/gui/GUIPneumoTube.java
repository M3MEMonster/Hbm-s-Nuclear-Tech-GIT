package com.hbm.inventory.gui;

import java.util.Arrays;

import com.hbm.util.i18n.I18nUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoTube;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.uninos.networkproviders.PneumaticNetwork;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoTube extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_pipe.png");
	public TileEntityPneumoTube tube;

	public GUIPneumoTube(InventoryPlayer invPlayer, TileEntityPneumoTube tedf) {
		super(new ContainerPneumoTube(invPlayer, tedf));
		this.tube = tedf;

		this.xSize = 176;
		this.ySize = 185;
	}

	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		tube.compair.renderTankInfo(this, x, y, guiLeft + 7, guiTop + 16, 18, 18);

		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 52, 18, 18, x, y, (tube.redstone ? (EnumChatFormatting.GREEN + I18nUtil.resolveKey("desc.gui.pneumo_tube.on")) : (EnumChatFormatting.RED + I18nUtil.resolveKey("desc.gui.pneumo_tube.off"))) + EnumChatFormatting.RESET + I18nUtil.resolveKey("desc.gui.pneumo_tube.redstone"));
		this.drawCustomInfoStat(x, y, guiLeft + 6, guiTop + 36, 20, 8, x, y, I18nUtil.resolveKey("desc.gui.pneumo_tube.compressor", tube.compair.getPressure()), I18nUtil.resolveKey("desc.gui.pneumo_tube.max_range",tube.getRangeFromPressure(tube.compair.getPressure())));

		this.drawCustomInfoStat(x, y, guiLeft + 151, guiTop + 16, 18, 18, x, y, EnumChatFormatting.YELLOW + I18nUtil.resolveKey("desc.gui.pneumo_tube.receive"), tube.receiveOrder == PneumaticNetwork.RECEIVE_ROBIN ? I18nUtil.resolveKey("desc.gui.pneumo_tube.round") : I18nUtil.resolveKey("desc.gui.pneumo_tube.random"));
		this.drawCustomInfoStat(x, y, guiLeft + 151, guiTop + 52, 18, 18, x, y, EnumChatFormatting.YELLOW + I18nUtil.resolveKey("desc.gui.pneumo_tube.slot_order"), tube.sendOrder == PneumaticNetwork.SEND_FIRST ? I18nUtil.resolveKey("desc.gui.pneumo_tube.slot_order.1") : tube.sendOrder == PneumaticNetwork.SEND_LAST ? I18nUtil.resolveKey("desc.gui.pneumo_tube.slot_order.2") : I18nUtil.resolveKey("desc.gui.pneumo_tube.slot_order.3"));


		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 15; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);

				if(this.isMouseOverSlot(slot, x, y) && tube.pattern.modes[i] != null) {
					this.func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + I18nUtil.resolveKey("desc.gui.pneumo_tube.click"), ModulePatternMatcher.getLabel(tube.pattern.modes[i]) }), x, y - 30);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		click(x, y, 7, 52, 18, 18, "redstone");
		click(x, y, 6, 36, 20, 8, "pressure");
		click(x, y, 128, 30, 14, 26, "whitelist");
		click(x, y, 151, 16, 18, 18, "receive");
		click(x, y, 151, 52, 18, 18, "send");
	}

	public void click(int x, int y, int left, int top, int sizeX, int sizeY, String name) {
		if(checkClick(x, y, left, top, sizeX, sizeY)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean(name, true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, tube.xCoord, tube.yCoord, tube.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tube.hasCustomInventoryName() ? this.tube.getInventoryName() : I18n.format(this.tube.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(tube.redstone) drawTexturedModalRect(guiLeft + 7, guiTop + 52, 179, 0, 18, 18);

		if(tube.whitelist) {
			drawTexturedModalRect(guiLeft + 139, guiTop + 33, 176, 0, 3, 6);
		} else {
			drawTexturedModalRect(guiLeft + 139, guiTop + 47, 176, 0, 3, 6);
		}

		drawTexturedModalRect(guiLeft + 151, guiTop + 16, 197, 18 * tube.receiveOrder, 18, 18);
		drawTexturedModalRect(guiLeft + 151, guiTop + 52, 215, 18 * tube.sendOrder, 18, 18);

		drawTexturedModalRect(guiLeft + 6 + 4 * (tube.compair.getPressure() - 1), guiTop + 36, 179, 18, 4, 8);
		GaugeUtil.drawSmoothGauge(guiLeft + 16, guiTop + 25, this.zLevel, (double) tube.compair.getFill() / (double) tube.compair.getMaxFill(), 5, 2, 1, 0xCA6C43, 0xAB4223);
	}
}
