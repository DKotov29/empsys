package ua.dkotov.empiresystems.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ua.dkotov.empiresystems.Core;

public class OnlineCommand extends Command {

    private Core core;

    public OnlineCommand(Core core) {
        super("online");
        this.core = core;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage("§aEmpire §6|§a Онлайн: §6" + core.getProxy().getOnlineCount());
        StringBuilder sb = new StringBuilder();
        for(ProxiedPlayer p : core.getProxy().getPlayers()){
            String s = core.getAs().isAdminWhichLevelHigher(commandSender.getName(),
                    1000) ? "[Admin] " + p.getName() + ", " : p.getName() + ", ";
            sb.append(s);
        }
        commandSender.sendMessage(sb.toString());
    }
}
