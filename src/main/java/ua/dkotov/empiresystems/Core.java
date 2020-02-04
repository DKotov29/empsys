package ua.dkotov.empiresystems;

import net.md_5.bungee.api.plugin.Plugin;
import ua.dkotov.empiresystems.admin.AdminSystem;
import ua.dkotov.empiresystems.bans.BanManager;
import ua.dkotov.empiresystems.commands.ChangePasswordCommand;
import ua.dkotov.empiresystems.commands.GCCommand;
import ua.dkotov.empiresystems.commands.OnlineCommand;
import ua.dkotov.empiresystems.database.DBManager;
import ua.dkotov.empiresystems.filter.LogFilter;
import ua.dkotov.empiresystems.handlers.MainHandler;
import ua.dkotov.empiresystems.players.PlayerManager;
import ua.dkotov.empiresystems.servers.AuthServerManager;
import us.myles.ViaVersion.api.ViaAPI;

public class Core extends Plugin {

    private PlayerManager pm;
    private DBManager dbm;
    private AuthServerManager asm;
    private BanManager bm;
    private AdminSystem as;
    private ViaAPI viaApi;

    @Override
    public void onEnable() {
       // viaApi = Via.getAPI(); // Get the Via API
        getLogger().setFilter(new LogFilter());
        getProxy().getLogger().setFilter(new LogFilter());
        dbm = new DBManager(this);
        pm = new PlayerManager(dbm);
        asm = new AuthServerManager(this);
        getProxy().getPluginManager().registerListener(this, new MainHandler(this));
    //    bm = new BanManager(this);
        as = new AdminSystem(this);
     //   getProxy().getPluginManager().registerCommand(this, new BanCommand(this));
        getProxy().getPluginManager().registerCommand(this, new GCCommand(this));
    //    getProxy().getPluginManager().registerCommand(this, new UnBanCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ChangePasswordCommand(this));
        getProxy().getPluginManager().registerCommand(this, new OnlineCommand(this));
        getLogger().info("Успешно включен!");
    }

    @Override
    public void onDisable() {
        asm.stop();
        dbm.close();
    }

    /*public void sendMessageToPlayer(ProxiedPlayer pp, String msg){
        ByteBuf bb = Unpooled.buffer();
        try {
         //   PacketWrapper pw = new PacketWrapper();
            Type.VAR_INT.write(bb, 0x0E); // Packet ID in hex
            Type.OPTIONAL_CHAT.write(bb, msg);
            Type.BYTE.write(bb, (byte) 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        getViaApi().sendRawPacket(pp, bb);
    } */

    public DBManager getDbm() {
        return dbm;
    }

    public PlayerManager getPm() {
        return pm;
    }

    public BanManager getBm() {
        return bm;
    }

    public AdminSystem getAs() {
        return as;
    }

    public ViaAPI getViaApi() {
        return viaApi;
    }
}