package ua.dkotov.empiresystems.servers;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.ServerLoginHandler;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.ServerClosedEvent;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ua.dkotov.empiresystems.Core;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuthServerManager {

    private Core core;
    private Server authServer;

    //значение булин = нужно ли провести (авторизацию/регистрацию)
    private Map<ProxiedPlayer, Boolean> regQueue;
    private Map<ProxiedPlayer, Boolean> authQueue;
    private Map<ProxiedPlayer, String> regP;

//    private Map< Integer, Future> tasks;
    private ScheduledExecutorService es;

    public AuthServerManager(Core core) {
        this.core = core;
        startServer();
        es = Executors.newSingleThreadScheduledExecutor();
        regQueue = new HashMap<>();
        authQueue = new HashMap<>();
        regP = new HashMap<>();
    //    tasks = new HashMap<>();
    }

    public void startServer(){
        authServer = new Server("127.0.0.1",
                1111, MinecraftProtocol.class, new TcpSessionFactory(Proxy.NO_PROXY));
        authServer.setGlobalFlag("auth-proxy", Proxy.NO_PROXY);
        authServer.setGlobalFlag("verify-users", false);
        authServer.setGlobalFlag("server-ping-time-handler", 0);
        authServer.setGlobalFlag("access-token", false);
        authServer.setGlobalFlag("compression-threshold", 100);
        authServer.setGlobalFlag("login-handler", new ServerLoginHandler() {
            @Override
            public void loggedIn(Session session) {
                GameProfile profile = session.getFlag("profile");
                ProxiedPlayer pp = core.getProxy().getPlayer(profile.getName());
                if (pp == null){
                    return;
                }
           /*     BossBar bb = core.getViaApi().createBossBar(
                        "ЧИТАЙ ЧАТ", // BossBar title
                        0.5F, // Boss health (Float between 0 and 1)
                        BossColor.GREEN,  // BossBar color
                        BossStyle.SEGMENTED_20); // BossBar Style
                bb.addPlayer(pp.getUniqueId()); */
                es.schedule(() -> {
                    if (pp.isConnected() && pp.getServer().getInfo()
                            .getName().equals("auth")) {
                        pp.disconnect(new TextComponent("§6ProEmpire\nВы не успели авторизоватся D:"));
                    }
                }, 2,TimeUnit.MINUTES );
                if (core.getPm().checkReg(pp)) { // если он зареган
                    if(pp.getAddress().getHostName().equals("")){
                        pp.connect(core.getProxy().getServerInfo("lobby-1"));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        core.getDbm().getSession().execute(
                                "UPDATE proempire.eauth SET lastdate = '" + sdf.format(new Date())
                                        + "' WHERE nick = '" + pp.getName()+ "';");

                        return;
                    }
                    if (core.getPm().checkIp(pp)) {
                        pp.connect(core.getProxy().getServerInfo("lobby-1"));
                        return;
                    }
                }

                session.send(new ServerJoinGamePacket(0, false, GameMode.SURVIVAL,
                        0, Difficulty.PEACEFUL, 1000, WorldType.DEFAULT,
                        false));
                session.send(new ServerSpawnPositionPacket(new Position(0, 0, 0)));
                session.send(new ServerPlayerPositionRotationPacket(0.0, 0.0, 0.0, 0.0f, 0.0f,0 , null ));
                if (core.getPm().checkReg(pp)){//  если зареган просим пароль
                    authQueue.put(pp, true);
                    pp.sendMessage("§aEmpire §6|§a " +
                            "Мы заметили что ваш IP адрес не совпадает с тем с которого вы заходили прежде"
                   + "\n§aEmpire §6|§a " +
                            "Пожалуйте, введите свой пароль.");
                } else {//  регистрация
                    regQueue.put(pp, true);
                    pp.sendMessage("§fEmpire §a| §eЗдравствуйте \n" +
                            "§fEmpire §a| §eВы находитесь на сервере авторизации \n" +
                            "§fEmpire §a| §eВведите пароль, который будете использовать в дальнейшем");
                }
            }
        });
        authServer.addListener(new ServerAdapter() {
            @Override
            public void sessionAdded(SessionAddedEvent event) {
                event.getSession().addListener(new SessionAdapter() {
                    @Override
                    public void packetReceived(PacketReceivedEvent event) {
                        if(event.getPacket() instanceof ClientChatPacket) {
                            ClientChatPacket packet = event.getPacket();
                            GameProfile profile = event.getSession().getFlag("profile");
                            ProxiedPlayer pp = core.getProxy().getPlayer(profile.getName());
                            if (authQueue.containsKey(pp)){
                                
                                if (core.getPm().checkPass(pp, packet.getMessage())){

                                    pp.sendMessage("§aEmpire §6|§a " +
                                                "Пароль верный. Перемещаем в лобби...");
                                 //   core.sendMessageToPlayer(pp, "§aEmpire §6|§a " +
                                    //        "Пароль верный. Перемещаем в лобби...");
                                    es.schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (pp.isConnected()) {

                                                pp.connect(core.getProxy().getServerInfo("lobby-1"));
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                                core.getDbm().getSession().execute(
                                                        "UPDATE proempire.eauth SET ip = '" +
                                                                pp.getAddress().getHostName()
                                                                + "', lastdate = '" + sdf.format(new Date())
                                                        + "' WHERE nick = '" + pp.getName()+ "';");
                                                authQueue.remove(pp);
                                            }
                                        }
                                    }, 5, TimeUnit.SECONDS);
                                } else {
                                    pp.disconnect(new TextComponent("§aEmpire §6|§a " +
                                            "Пароль не верный, попробуйте позже"));
                                    authQueue.remove(pp);
                                }
                                return;
                            }
                            if (regQueue.containsKey(pp)){
                                String pass = packet.getMessage();
                                if (!(pass.length() >=6 && pass.length() <=30)){
                                    pp.sendMessage("§aEmpire §6|§a Пароль должен быть > 6 символов и < 30. Введите новый.");
                                    return;
                                }
                                if (!checkPass(packet.getMessage())){
                                    pp.sendMessage("§aEmpire §6|§a В пароле недопустимые символы. Введите новый.");
                                    return;
                                }
                                regP.put(pp, packet.getMessage());
                                regQueue.remove(pp);
                                TextComponent tc = new TextComponent("§aEmpire §6|§a Введите еще раз.(Наведите что бы узнать пароль)");
                                        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                new ComponentBuilder(packet.getMessage()).create()));
                                pp.sendMessage( tc);
                               return;
                            }
                            if (regP.containsKey(pp)){
                                if (regP.get(pp).equals(packet.getMessage())){
                                    TextComponent tc = new TextComponent("§aEmpire §6|§a Наведите что бы узнать пароль");
                                            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                    new ComponentBuilder(packet.getMessage()).create()));
                                    pp.sendMessage(tc);
                                    pp.sendMessage(
                                            "§aEmpire §6|§a Сейчас мы переместим вас в лобби");
                                    core.getPm().firstAddToDB(pp, packet.getMessage());
                                    pp.connect(core.getProxy().getServerInfo("lobby-1"));
                                    return;
                                } else {
                                    pp.sendMessage("§aEmpire §6|§a Пароль что вы ввели прежде не совпадает " +
                                            "с тем что ввели только что\n§aEmpire §6|§a Регистрация сброшена." +
                                            " Введите пароль что вы будете использовать.");
                                    regP.remove(pp);
                                    regQueue.put(pp, true);
                                    return;
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void sessionRemoved(SessionRemovedEvent event) {
                GameProfile profile = event.getSession().getFlag("profile");
                ProxiedPlayer pp = core.getProxy().getPlayer(profile.getName());
                if (authQueue.containsKey(pp)) authQueue.remove(pp);
                if (regQueue.containsKey(pp)) regQueue.remove(pp);
                if(regP.containsKey(pp)) regP.remove(pp);

            }

            @Override
            public void serverClosed(ServerClosedEvent event) {
                authServer.getSessions().forEach(s -> s.disconnect("Упс, сервер выключаеться."));
                core.getProxy().getLogger().info("Сервер А/Р выключен.");
            }
        });

        authServer.bind();
        core.getProxy().getLogger().info("Включаем сервер А/Р.");
    }

    public void stop(){
        authServer.close();
        es.shutdown();
        authQueue.clear();
        regQueue.clear();
        regP.clear();
    }

    public boolean checkPass(String pass){
        String allowed = "01234567890!@#$%^&*_-=+qwertyuiopasdfghjklzxcvbnmASDFGHJKLQWERTYUIOPZXCVBNM";
        for (char a : pass.toCharArray()){
            if (!allowed.contains(String.valueOf(a))){
                return false;
            }
        }
        return true;
    }
}
