package ua.dkotov.empiresystems.admin;

import com.datastax.driver.core.ResultSet;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ua.dkotov.empiresystems.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSystem {

    private Core core;
    private Map<String, Integer> admins;

    public AdminSystem(Core core) {
        this.core = core;
        admins = new HashMap<>();
        ResultSet rs = core.getDbm().getSession().execute("SELECT * FROM ProEmpire.admins;");
        rs.all().stream().forEach(r ->
            admins.put(r.getString("nick"), r.getInt("level"))
        );
           }

    public List<ProxiedPlayer> getAdminsLevelWhichHigher(int level){
        List<ProxiedPlayer> admins1 = new ArrayList<>();
        List<String> lpp = new ArrayList<>();
        core.getProxy().getPlayers()
                .stream()
                .forEach(p -> lpp.add(p.getName()));
        admins.forEach((nick, lvl) -> {
            if (level <= lvl){
                if (lpp.contains(nick)){
                    admins1.add(core.getProxy().getPlayer(nick));
                }
            }
        });
        return admins1;
    }

    public boolean isAdminWhichLevelHigher(String name, int level){
        if (admins.containsKey(name)){
            if (admins.get(name) <= level){
                return true;
            }
        }
        return false;
    }

    public List<ProxiedPlayer> getOnlineAdmins() {
        List<ProxiedPlayer> onlineAdmins = new ArrayList<>();
        List<String> lpp = new ArrayList<>(); // все онлайн игроки
        core.getProxy().getPlayers()
                .stream()
                .forEach(p -> lpp.add(p.getName()));
        admins.forEach((nick, lvl) -> {
            if (lpp.contains(nick)) {
                onlineAdmins.add(core.getProxy().getPlayer(nick));
            }
        });
        return onlineAdmins;
    }

    public String getRuleDesc(String rule){
        ResultSet rs = core.getDbm().getSession().execute("SELECT * FROM " +
                "proempire.rules WHERE id = '" + rule + "';");
        if (rs.isExhausted()){
            return "null";
        }
        return rs.one().getString("description");
    }

}