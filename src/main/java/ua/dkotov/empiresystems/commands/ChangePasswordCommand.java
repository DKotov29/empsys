package ua.dkotov.empiresystems.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.mindrot.jbcrypt.BCrypt;
import ua.dkotov.empiresystems.Core;

public class ChangePasswordCommand extends Command {

    private Core core;

    public ChangePasswordCommand(Core core) {
        super("chps", null, "changepass","changepassword", "chpsw",
                "changepw", "chp");
        this.core = core;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!core.getProxy().getPlayer(commandSender.getName())
                .getServer().getInfo().getName().equals("auth")){
            if (strings.length != 1){
                commandSender.sendMessage("§aEmpire §6|§a /chps §7[§6Новый Пароль§7]");
            }
            core.getDbm().getSession().execute(
                    "UPDATE proempire.eauth SET pass = '" +
                            BCrypt.hashpw(strings[0], BCrypt.gensalt(15))
                            + "' WHERE nick = '" + commandSender.getName() + "';");
            commandSender.sendMessage(
                    "§aEmpire §6|§a Ваш новый пароль: §6\"" + strings[0] + "\"." );
        }else {
            commandSender.sendMessage("Нет нет, не надо");
        }
    }
}
