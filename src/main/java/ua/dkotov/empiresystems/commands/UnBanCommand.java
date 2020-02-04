package ua.dkotov.empiresystems.commands;

import com.datastax.driver.core.ResultSet;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import ua.dkotov.empiresystems.Core;

public class UnBanCommand extends Command {

    private Core core;

    public UnBanCommand(Core core) {
        super("unban", null, "ub");
        this.core = core;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        if(!core.getAs().isAdminWhichLevelHigher(commandSender.getName(), 20) ){
            commandSender.sendMessage("У тебя нет прав на это D:");
            return;
        }
        if (strings.length != 1){
            commandSender.sendMessage("/unban nick");
            return;
        }
        ResultSet rs = core.getDbm().getSession().execute("SELECT * FROM ProEmpire.eBans" +
                " WHERE banned='" + strings[0] + "';");
        if (rs.isExhausted()){
            commandSender.sendMessage("Игрок не забанен");
            return;
        }
        core.getDbm().getSession().execute("DELETE FROM ProEmpire.eBans " +
                "WHERE banned = '" + strings[0] + "';");
    }
}
