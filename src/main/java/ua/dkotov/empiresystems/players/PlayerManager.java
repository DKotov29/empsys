package ua.dkotov.empiresystems.players;

import com.datastax.driver.core.ResultSet;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.mindrot.jbcrypt.BCrypt;
import ua.dkotov.empiresystems.database.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerManager {

    private DBManager dbm;

    public PlayerManager(DBManager dbm){
        this.dbm = dbm;
    }

    public void firstAddToDB(ProxiedPlayer pp, String pass){
        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt(15));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dbm.getSession().execute("INSERT INTO ProEmpire.eAuth(nick, pass, regip, ip," +
                "regdate, lastdate)" +
                " VALUES('"+ pp.getName() + "', '" + hashed + "', '" + pp.getAddress().getHostName() + "', '"
                +  pp.getAddress().getHostName() + "', '" + sdf.format(new Date()) + "', '"+
                sdf.format(new Date()) + "');");
    }

    public boolean checkReg(ProxiedPlayer pp){
        if (!pp.isConnected()) return false;
        ResultSet rs = dbm.getSession().execute(
                        "SELECT * FROM ProEmpire.eAuth WHERE nick='" + pp.getName() + "';");

        if (rs.isExhausted()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkIp(ProxiedPlayer pp){
        ResultSet rs = dbm.getSession().execute(
                "SELECT * FROM ProEmpire.eAuth WHERE nick='" + pp.getName() + "';");
        if (rs.one().getInet("ip").getHostName().equals(pp.getAddress().getHostName())){
            return true;
        }
        return false;
    }

    public boolean checkPass(ProxiedPlayer pp,String pass){
        ResultSet rs = dbm.getSession().execute(
                "SELECT * FROM ProEmpire.eAuth WHERE nick='" + pp.getName() + "';");
        String hashed = rs.one().getString("pass");
        return BCrypt.checkpw(pass, hashed);
    }

}
