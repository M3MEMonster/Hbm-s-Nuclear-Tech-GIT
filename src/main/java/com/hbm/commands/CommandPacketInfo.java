package com.hbm.commands;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.concurrent.TimeUnit;

import static com.hbm.handler.threading.PacketThreading.totalCnt;

public class CommandPacketInfo extends CommandBase {
	@Override
	public String getCommandName() {
		return "ntmpackets";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "/ntmpackets [info/resetState/toggleThreadingStatus/forceLock/forceUnlock]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		if (args.length > 0) {
			switch (args[0]) {
				case "resetState":
					PacketThreading.hasTriggered = false;
					PacketThreading.clearCnt = 0;
					return;
				case "toggleThreadingStatus":
					GeneralConfig.enablePacketThreading = !GeneralConfig.enablePacketThreading; // Force toggle.
					PacketThreading.init(); // Reinit threads.
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + I18nUtil.format("chat.command.packet.thread", GeneralConfig.enablePacketThreading)));
					return;
				case "forceLock":
					PacketThreading.lock.lock(); // oh my fucking god never do this please unless you really have to
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18nUtil.resolveKey("chat.command.packet.thread.lock")));
					MainRegistry.logger.error("Packet thread lock acquired by {}, this may freeze the main thread!", sender.getCommandSenderName());
					return;
				case "forceUnlock":
					PacketThreading.lock.unlock();
					MainRegistry.logger.warn("Packet thread lock released by {}.", sender.getCommandSenderName());
					return;
				case "info":
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + I18nUtil.resolveKey("chat.command.packet.info")));

					if (PacketThreading.isTriggered() && GeneralConfig.enablePacketThreading)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18nUtil.resolveKey("chat.command.packet.thread.error")));
					else if (GeneralConfig.enablePacketThreading)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + I18nUtil.resolveKey("chat.command.packet.thread.active")));
					else
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18nUtil.resolveKey("chat.command.packet.thread.inactive")));

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("chat.command.packet.thread.pool")));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.total", PacketThreading.threadPool.getPoolSize())));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.core", PacketThreading.threadPool.getCorePoolSize())));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.idle", (PacketThreading.threadPool.getPoolSize() - PacketThreading.threadPool.getActiveCount()))));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.max", PacketThreading.threadPool.getMaximumPoolSize())));

					for (ThreadInfo thread : ManagementFactory.getThreadMXBean().dumpAllThreads(false, false))
						if (thread.getThreadName().startsWith(PacketThreading.threadPrefix)) {
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + I18nUtil.format("chat.command.packet.thread.name", thread.getThreadName())));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.id", thread.getThreadId())));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.state", thread.getThreadState())));
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.if_lock", (thread.getLockOwnerName() == null ? I18nUtil.resolveKey("desc.common.none") : thread.getLockName()))));
						}

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + I18nUtil.resolveKey("chat.command.packet.thread.packet")));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.amount.total", totalCnt)));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.amount.remain", PacketThreading.threadPool.getQueue().size())));

					if (totalCnt != 0)
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.process_remain", BobMathUtil.roundDecimal(((double) PacketThreading.threadPool.getQueue().size() / totalCnt) * 100, 2))));

					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + I18nUtil.format("chat.command.packet.thread.process_time", BobMathUtil.roundDecimal(TimeUnit.MILLISECONDS.convert(PacketThreading.nanoTimeWaited, TimeUnit.NANOSECONDS), 4))));
					return;
			}
		}
		sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
	}
}
