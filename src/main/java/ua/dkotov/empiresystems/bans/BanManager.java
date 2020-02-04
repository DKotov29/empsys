package ua.dkotov.empiresystems.bans;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ua.dkotov.empiresystems.Core;
import ua.dkotov.empiresystems.utils.DurationUtil;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BanManager {

    private Core core;

    public BanManager(Core core) {
        this.core = core;
    }

    public void ban(ProxiedPlayer toBan, long time, String reason, ProxiedPlayer giver){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        core.getDbm().getSession().execute("INSERT INTO ProEmpire.eBans(banned, giver," +
                " unbanTime, reason, date) " +
                "VALUES ('" + toBan.getName() + "', '" + giver.getName() + "', " +
                (System.currentTimeMillis() + time) + ", '" + reason + "', '" + sdf.format(
                        new Date()) +"');");
        core.getDbm().getSession().execute("INSERT INTO ProEmpire.eBansHistory(banned, giver," +
                " unbanTime, reason, date) " +
                "VALUES ('" + toBan.getName()+ "', '" + giver.getName() + "', " +
                (System.currentTimeMillis() + time) + ", '" + reason + "', '" + sdf.format(
                        new Date()) +"');");
        toBan.disconnect(new TextComponent("§6ProEmpire\nВы были забанены\nКем: "
                + giver.getName() + "\nПо причине: " + reason + "\nКогда вы будете разбанены: " +
                sdf.format(new Date(System.currentTimeMillis() + time)) + "\nДата бана: " +
                sdf.format(new Date()) + "\nЗа поддержкой вы можете обратится в группу сервера\n" +
                "https://vk.com/proempire_ru"));

        core.getAs().getOnlineAdmins().forEach(pp -> pp.sendMessage("[AdminSystem] " + giver.getName() +
                "забанил " + toBan.getName() + " на " +
                new DurationUtil().formatDuration(Duration.ZERO.plusMillis(time))));
        return;
    }


}
