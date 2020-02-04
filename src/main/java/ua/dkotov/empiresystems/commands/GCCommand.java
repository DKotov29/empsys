package ua.dkotov.empiresystems.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import ua.dkotov.empiresystems.Core;

public class GCCommand extends Command {


    public GCCommand(Core core) {
        super("gc", null, "system");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        Runtime r = Runtime.getRuntime();
        commandSender.sendMessage(new TextComponent("§aEmpire §6|§a Memory"));
        commandSender.sendMessage(new TextComponent("§6Free: " + r.freeMemory() / (1024 * 1024) + "MB"));
        commandSender.sendMessage(new TextComponent("§6Using: " + ( r.totalMemory() - r.freeMemory()) / (1024 * 1024) + "MB"));
        commandSender.sendMessage(new TextComponent("§6Max: " + r.maxMemory()/ (1024 * 1024) + "MB"));

        commandSender.sendMessage(new TextComponent("§6Total: " + r.totalMemory()/ (1024 * 1024) + "MB"));

        commandSender.sendMessage(new TextComponent("§aEmpire §6|§a Processors"));
        commandSender.sendMessage(new TextComponent("§6Available :" + r.availableProcessors()));
    }
}
