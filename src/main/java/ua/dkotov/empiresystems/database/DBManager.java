package ua.dkotov.empiresystems.database;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import ua.dkotov.empiresystems.Core;

public class DBManager {

    private Core core;
    private Cluster cluster;
    private Session session;


    public DBManager(Core core) {
        this.core = core;
        connect();
    }

    public void connect() {
        Cluster.Builder b = Cluster.builder().addContactPoint("127.0.0.1");
        b.withPort(9042);
        cluster = b.build();
        session = cluster.connect();
        session.execute("CREATE KEYSPACE IF NOT EXISTS ProEmpire" +
                " WITH replication ={'class' : 'SimpleStrategy', 'replication_factor' : 2};");
        session.execute("CREATE TABLE IF NOT EXISTS ProEmpire.eAuth(" +
                "nick text PRIMARY KEY, pass text, regip inet " +
                ",ip inet, regdate timestamp, lastdate timestamp);");
     /*   session.execute(("CREATE TABLE IF NOT EXISTS ProEmpire.eBans(" +
                "banned text PRIMARY KEY, giver text, unbanTime bigint " +
                ",reason text, date timestamp);"));
        session.execute(("CREATE TABLE IF NOT EXISTS ProEmpire.eBansHistory(" +
                "banned text PRIMARY KEY, giver text, unbanTime bigint " +
                ",reason text, date timestamp);")); */
        session.execute(("CREATE TABLE IF NOT EXISTS ProEmpire.admins(" +
                "nick text PRIMARY KEY, level int);"));
        session.execute(("CREATE TABLE IF NOT EXISTS ProEmpire.rules(" +
                "id text PRIMARY KEY, description text);"));

    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

}
