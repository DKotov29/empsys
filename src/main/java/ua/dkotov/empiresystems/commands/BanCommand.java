package ua.dkotov.empiresystems.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ua.dkotov.empiresystems.Core;
import ua.dkotov.empiresystems.utils.DurationUtil;

public class BanCommand extends Command {

    private Core core;

    public BanCommand(Core core) {
        super("ban");
        this.core = core;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (core.getAs().isAdminWhichLevelHigher(commandSender.getName(), 20)
                || commandSender.getName().equals("CONSOLE")) {
            if (strings.length != 3) {
                commandSender.sendMessage("/ban time nick rule");
                return;
            }
            ProxiedPlayer p = core.getProxy().getPlayer(strings[1]);
            if (p == null) {
                commandSender.sendMessage("Игрока нет в сети");
                return;
            }
            if (core.getAs().getRuleDesc(strings[2]).equals("null")) {
                commandSender.sendMessage("Такого правила нет -_-");
                return;
            }
            if (commandSender.getName().equals("CONSOLE")) {
                core.getBm().ban(p,
                        new DurationUtil().parse(strings[0]),
                        "По велению консоли",
                        p);
                return;
            }

            core.getBm().ban(p,
                    new DurationUtil().parse(strings[0]),
                    core.getAs().getRuleDesc(strings[1]),
                    core.getProxy().getPlayer(commandSender.getName()));
            return;
        } else {
            commandSender.sendMessage("У тебя нет прав на это D:");
        }
    }
}