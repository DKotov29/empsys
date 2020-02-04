package ua.dkotov.empiresystems.handlers;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ua.dkotov.empiresystems.Core;

import java.text.SimpleDateFormat;

public class MainHandler implements Listener {

    private final Core core;
    private final SimpleDateFormat sdf;

    public MainHandler(Core core) {
        this.core = core;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e){
       // core.sendMessageToPlayer(e.getPlayer(), "TEST");
        if (e.getPlayer().getName().equals("CONSOLE")){
            e.getPlayer().disconnect("Пошёл бы ты.");
        }
       /* ResultSet rs = core.getDbm().getSession().execute("SELECT * FROM ProEmpire.eBans" +
                " WHERE banned='" + e.getPlayer().getName() + "';");
        if (!rs.isExhausted()){
            if (rs.one().getLong("unbanTime") > System.currentTimeMillis()){
                e.getPlayer().disconnect(new TextComponent("§6ProEmpire\nВы были забанены\nКем: "
                        + rs.one().getString("giver") + "\nПо причине: " +
                        rs.one().getString("reason")
                        + "\nКогда вы будете разбанены: " +
                        sdf.format(new Date(rs.one().getLong("unbanTime"))) + "\nДата бана: " +
                        sdf.format(rs.one().getDate("date")) + "\nЗа поддержкой вы можете обратится в группу сервера\n" +
                        "https://vk.com/proempire_ru"));
            } else {
                core.getDbm().getSession().execute("DELETE FROM ProEmpire.eBans " +
                        "WHERE banned='" + e.getPlayer().getName() + "' IF EXISTS;");
            }
        } */

        /* если зареган и ип тот же пустим в лобби если не зареган на фейк аус сервер,
        если другой ип - кик */
      /*  if (core.getPm().checkReg(e.getPlayer())) { // если он зареган
            if (core.getPm().checkIp(e.getPlayer())
                    || e.getPlayer().getAddress().getHostName().equals("95.67.89.196")) {
                e.getPlayer().connect(core.getProxy().getServerInfo("lobby-1"));
                return;
            } else {//если чел зареган и его ип не совпадает
                e.getPlayer().disconnect(new TextComponent("§6ProEmpire\n§6Вы не можете войти на" +
                        " сервер\n§6Пожалуйста сообщите администрации"));
                return;
            }
        } */

     //   core.sendMessageToPlayer(e.getPlayer(), "ooops");
    }

    @EventHandler
    public void onResponse(ProxyPingEvent e){
        ServerPing sp = e.getResponse();
        sp.setVersion(new ServerPing.Protocol("EmpireSystem",
                sp.getVersion().getProtocol()));
        sp.setDescriptionComponent(new TextComponent( getCenter("§6Pro§aEmpire§7(§4Просим заходить с 1.12.2§7)") + "\n"
                + getCenter("§6Pro§aEmpire")));
        ServerPing.PlayerInfo[] players = { new ServerPing.PlayerInfo("vk.com/proempire_ru", "1"),
                new ServerPing.PlayerInfo("vk.com/proempire_ru", "2")};
        sp.setPlayers(new ServerPing.Players(core.getProxy().getOnlineCount() + 1,
                core.getProxy().getOnlineCount(), players));
        sp.setFavicon(Favicon.create("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAAB" +
                "ACAYAAACqaXHeAAAACXBIWXMAAAsTAAALEwEAmpwYAAAGYWlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAA" +
                "AAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPH" +
                "g6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmU" +
                "gNS42LWMxNDIgNzkuMTYwOTI0LCAyMDE3LzA3LzEzLTAxOjA2OjM5ICAgICAgICAiPiA8cmRmOlJE" +
                "RiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiP" +
                "iA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS" +
                "5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0" +
                "vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNl" +
                "RXZlbnQjIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuM" +
                "C8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1wOkNyZWF0b3JUb2" +
                "9sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDE4LTE" +
                "xLTE1V" +
                "DE5OjE4OjUzKzAyOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDE4LTExLTE1VDE5OjE4OjUzKzAyOj" +
                "AwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAxOC0xMS0xNVQxOToxODo1MyswMjowMCIgeG1wTU06SW5zdGFuY2VJR" +
                "D0ieG1wLmlpZDoyZWQwODU0MS01ODg4LTQxNDMtYmVkNy0yYzljZjM5ZDcyNTMiIHhtcE1NOkRvY3VtZW50SU" +
                "Q9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo5NTA4YTkzYy03" +
                "MTQ2LTdhNGMtYmFhNC04NmY4ZDVjZjY4MDgiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowN" +
                "zQ5ZTQ3Yi1kN2IxLTFiNDUtYTA2YS0xMWE0OWFkYWUxZDAiIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIGRjOmZv" +
                "cm1hdD0iaW1hZ2UvcG5nIj4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj" +
                "0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDowNzQ5ZTQ3Yi1kN2IxLTFiNDUtYTA2YS0xMWE0O" +
                "WFkYWUxZDAiIHN0RXZ0OndoZW49IjIwMTgtMTEtMTVUMTk6MTg6NTMrMDI6" +
                "MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE4IChXaW5kb3dzKSIvPiA8c" +
                "mRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6MmVkMDg1NDEtNTg" +
                "4OC00MTQzLWJlZDctMmM5Y2YzOWQ3MjUzIiBzdEV2dDp3aGVuPSIyMDE4LTExLTE1VDE5OjE4OjUzKzAyOjAwI" +
                "iBzdEV2dDpzb" +
                "2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8" +
                "iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDxwaG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDxyZ" +
                "GY6QmFnPiA8cmRmOmxpPnhtcC5kaWQ6NzEyNGQyMWYtMGViZi1jNTQ5LWJlYWQtZjk5MDMyYjJlMmQyPC9yZGY6b" +
                "Gk+IDwvcmRmOkJhZz4gPC9waG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDwvcmRmOkRlc2NyaXB0aW9uPiA8" +
                "L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+htHE0gAAEthJREFUeJztm3mUXFWdxz/" +
                "3rbX2Vr13kk4CshpDghGTQEQQJagBlH1V5zCCh8VdziCj46jgNoPAAJFBFkFBj+wgBAGDBAiEQEgIhCRk67Wqu6t" +
                "rr7fe+aOqO70l1U3w5JyR3zmvq1/Xfff9vt937+9+f/f3Wkgp+Wc2ZX87sL/tAwL2twP72z4gYH87sL/tAwL2twP7" +
                "27RKDURV46jzhrCBIyVI0BWBoQg6Cw4NpoquCPKeZDDn0BAx5g/Y3qme4y01Ta25wVRPBl6VEixfoghejuecQ5BSI" +
                "ia4sS9FIKB79ab6bV9ymwLXdxSdL2F7oIjd4kUCQoCAgKGKekONu778ONAH0N3Vs" +
                "W8ETNEiwCnAxYmCs/gjtUHmxYKs6s2xNWf/pS2ktwAe8P2U4y/43kExZkYNCo4/qhNVgKIqLN+WZHPGvjwW0B7" +
                "vLjiXLW2K8Lm2KJbl7W4sQHoS04cnB/I8M5CPRnV1IkontPeTgCu60vZVQlcazptdy9nTqvhEXZBwTYDXOtIsW" +
                "Lm9od9y768ztPP6CvY3lzRGuPZjbaUn6Pnje4sYzDJUPvfirg8Pasoruqpwx0eaaGyJQsHZ3U4RYHngSro3uDz" +
                "Sk3knqquJyTr9fhBwOHBnImsfefrMGr51YIyjmsKAgIKDPVBgXlsVvz2ihQtXdywr6Oo6XFl7eXsN+JJsykJM8L" +
                "yCBZfPtkaZWx9S1u1MtZ15aAONNQGsRA53RDtFFciiR9D2eT1rg6b09Vju+A73YPsaBC/qTBU3zAzoR963uJ0/L" +
                "p7BUY1hihmbbNoi5/rYgJ0qcsHBMc49sI6B7tS" +
                "sOQ1hvjijGjfnTAgeoOD6ENA4r7UKfMlXZlQD4IxJXaSEANBre7xbdNBVpUdXFYaOSrYvI+DHiZx91Rdm1HDbk" +
                "S3UVJk4KQvLl6WYVAYmAMvzMSyX2z7SRNDzOWdmDaiCYsFD7IEBKaCYs7loRhVH1h7EktoghZw9IWEK0ON4xG2" +
                "PoCI6pwLivRJwx2DOvvCHc5r4wfwWKLpkBgooipjYQSHIFFyihsqtR7eD5ZLJ2CjKnmOVABzXp0pT+WRzBKvg4Pm" +
                "M7l/AUDab8nxyUhISonsqQN4LAVcO5uwLrzyskR/Ma4GsTcbx9goGQFEEOddHyVjI8vle25fbWL4PBR8pS/Fu5Aw" +
                "QlKYACAZdn6IniepKz1TATJWA4xKJ3DVf/nAT1yxoxc/a5BwPZU8TeQIbGe8lJaAhVQFDAUVhWBOUtUYJYWmdZ4g" +
                "0OeJ72wPPp9/1oaQveqcCq" +
                "CIB06MmAAXP55sH1BlF1+ffDopBwZ0y+CGTEsKaQAR1API5h/XxHJ" +
                "tSeXoLFr15i0TRIet4+CgYukosGqE+qNOoq8R0lXpDpS2k0awq1GkqGa9EgIB/2BT42PltVVdMa43CQIFMwak4j" +
                "MeaLyVRTYWIDgWPh3akeHBHghUd/XQVfQiGIRxBD0cJB4MEakx8z6NoWaR3xqFQzKCrfQgxE1WImK7SaGq0BzQS" +
                "lkddQAP4h0yBmX1pa/VPtwxwU12QnOdPCbyUoAkIV5ngSP7nzTjXrd/JlmQeGlo4bO4CFs5opbWhgWgkQjBgou" +
                "s6hmmQTqXo6+vnL0+uoL/fembQ45TGoD7dR86wfDljS87+0FuZ4qyAph5cr6txV8r3dwrkfB/gEiWoc1ZLBNwJVNt" +
                "ezPclUVODkM6KXSkuXbWZzckMdQcfxnnL5nNAeztV1VUIoZSXTwUpJb7nlae/wHYcCoUiqqqubQ7q+D67gF3Aqik5" +
                "M4FVJMAUgu6Cc+6nmiIsaYrgTkFl+b4kGtJBUfj6C9v59bothGYdwr+efgYHzJ6NpmvYto0QgmKxQG9vgo7OHrq6" +
                "+kim8liWg+d7uI7FQLJAKKRFgGg8YWcaGwwyCZeWVoPuvP2PIwA4FMdv+0RdEEyVYtHdo3obBz5sAIKjn9jIqo44" +
                "n1r6WZYefywgyOZyVBkGhUKB5/6+" +
                "mudfWE9msKRh6hqCNNcHaao1QAiyeR9NC7NzZ+o7eM6/oIaeAn4HPAYQlSr5tA+NKkz++UyOAFdypDBUjqwOgCc" +
                "nB15C2NRAVThpxdus6ohz1tlncszixfTG44RCIQKBAH99ZiWPPf" +
                "oskGbholmcecqnWDS/lQPbq6mtNsBUQYIsuvT0F3l76wArV22te/TxzWe+uj5zJiLwEvB94Omhe6uTTwQBEJXqA" +
                "uHG1p/HTO07qxfPoCWgknMqxwBNgFkV4Gsv7ODmdZs548yzOOboRfT09BKrqyOeSLD8f+8j2beNEz9zGD+6agkL" +
                "FrWCaoBrQcYG2we/fC9VgYAGVcHSeaKTe/7wKtfesIUNWzyiNYHr8wX/ipZGnbFwOnbt2ruvlcBYvpwW0xWqNDF" +
                "axezBpJSYVQGe2pHi5nWbOWrJsRyzeBG9vXEaGmJs3LiJ3/xmOfXVIR5/8EssPfkgQEJnGttO7xY7o8yHggsDBU" +
                "DBqG7i3MtP5JxTN/LjX63j33/df7lqqvOA04B4ZS93W8V0SZb0WXnnpXKHQUMlm3e4YNVmoq0zOfXEExhIJqmpqe" +
                "HNN0vg5x3azJZN32DpyYdDZwrr3SS26+8B/AgTAoTETqWxtjuIurlcfd1CVtw2DSE5pjfurAH0yQAfsspbYuWBOJk" +
                "CmpSgBnXuejNBTyrF+aecTCAUws/lSSYHuPXW25jRFmPtyxdDRMfa0oPQFUSFeasqAtVUwVBLkllTQfqlJTl7ICd8" +
                "xeMRS7D0a7umZ3PKnw1dLJtsLKhIgCHoGHR88r4kUqHPgCrA9rj93V6MlnYOnDWLwcEU1dVV3Hn3nwCXFX86GyIm9" +
                "tY+hL6HASglQijodQGIBsB3IZ5noDtHvL9IMl1Ki2PVOg0NtdTE2jnxEo8HPJVTL9" +
                "v++YZG40vAHe8LAWFN2bArb7MuVeSEtirYSxDUDJWNySJrOgdYeNyxRKuieJ7PmjVr2fTW69x0zTIOXjgTZ1sPa" +
                "BOAlxLQMFoiYEr6N/fzh1ve4L6HtrNh0yDZvI3vuyB9JAIhBKqiEYlUcczHDb5+epjjjq7h+Vczt9TVaL8HKgqEi" +
                "gToQmzybI/X0jYnzFD2Hgp0hReTeRAqh7RPQ0qJ67qsePplGhuqueSiOZBO4Qsxpg+J9FXMSAgaPPo3beX7127gl" +
                "ju2AHnapkX5wvE1zD+0hgNaTGoiKhJIZlw27SryyptZnliZ5OGHPQhq1IVVU1XEGcDd+0yAhDcRwtlecHSkRGEv8U" +
                "DC+v4MxBp" +
                "ob5+Bpml0dXWT6NnOZRctgFgEd3v/mF0giZQBzAYNgj3c/ou1fOW7rwNwwekz+cYFzRwxNwxVamkVsnxwZekpqIK" +
                "TTKUUypMezz0zwPJ7EjyyNo+i+ie/LwQAWXRl7eup4lGy6BJQBQVvPAVaGdTWnIUWiQICKSEeTwA2S45qAyQeo5++" +
                "lEHMJhcyb3PeWS9yz2O9LPzodO78+Sw+tCgKgy70OthxZ49DT0jQIypLTqhhydFhzr50J/f+NTW3ubnyglCRgIIv" +
                "qdbVZ19PW0dtTNscXhcAb7TeFEKAL5Fpm7xi4A+m+d3d92CaAdLpLLpZy8zpUfBGboJKpAhj1qShZwPHfnEdK1/L" +
                "csMP53Lpt9og7+Guz+MN7S+OAS8Ew6JHCrCLPjLpYUYl8w40ufcpORtogr1vkFQkIFRam/+ayjtXPp8scHhTGFl0R" +
                "/sjwJES05Z8pjbMM4kcfYn+mxzP3e554uRQOLQ4ZGqloVsGjxpCV1JgbeSk895i5Ws5HrppLsu+2gxv57FyHkItjR" +
                "bpg6kLiGkQVktk+IAuIOViddmgCIQCeGCWUKlA+z4TULbngMzfk4XoV6VEZ3TOIX2Joing+5xQbfKfAR0FfQO57M1m" +
                "IJBV8BcL5O7goRjgWCgt27jz59385YU+fnzZISy7uBn/jRyOKxFqaQppukCdZkLRZ/2aLK+9mWdHj43j+LiO5IwTa" +
                "zniqChO3Cl1rwuSKQ9wyBS0itp1sgQ4wYB2+7N9+csTg0UaQjqu7Y1r" +
                "5EtJm6HSbmq8k3e" +
                "WBfXgzUC9lGJE4JRIYWAG3iX7RoKLr+3hyLlNXHX1NOQ7hRJ4SsNaCyioEYWXnhzgp7cnePKFNHbOHQTZCRTAPe" +
                "T2x5KRzmfnoMd0rB4LL+Fw6rFVbO51l+/qdzdUAlZRCg/t8dfqyl1dqSJ3dmUgoI1bCYQoLbrVqkKTruL4sr60e" +
                "YlXmi+CkrA2Ue0khPq4+sYkxUKRP14zCwyBnSqn2ppABVTTZ/kNnSw85y0eeap/TdAQpzS3mO3Ah4EFLW2RY3u6" +
                "C/zkxm5o1FEleGmPuXNCnLQoctfz6/PFfSZghL2KpvQ8lciD7RGYIC+WlGoAmgCQw30LRuznKxoafXjb0tz6cIo5h" +
                "8WYPTeM3GEhNDHMk2ZKVj8ywMW/" +
                "7EYPG48CC4CHgPRIn8yQsfq6+/rIvlVAq9Mwphtcf1cf5/1gxwqgeZ8JyLhy+Iia2gMvDhbYOlhEM9Rx6BXA9ku" +
                "yGSGG2NcYrhQp4NkQttj4jkMulef0T9dAVMUZUpiagm57ELe49Ld9UApinx/rV0tbAIC6WvWHA4kC9zw2AG0m+" +
                "BAfdAEl3BzTaveZgIgqRh6PZvIOK5PFUlIyGj8GMOj5JBwPQxmu0GhClGQrQgHfAt3mnU4XkMyZHSjJ63KNH08id" +
                "Mnq1/Ks2VSkrlG/PhpWqGs0h+/V0BwYeesnNEPbet29fbjvFqBaI2AolHVIfp8JkKOP1Ugp12eKIMfrEoGgy/LpsD" +
                "zCithUvkbbXSsUID3AJ5HyAYX6WhXsckQRokSGCi+9W9oUMTXxoKmVlsNU0qKn393tWGly0dBgXPj2xgz/tbwH2ow" +
                "hfSApvYuwbwSMsX5U5d3OgguuP1wUEQJ8r1S72lR0yDkepiJeLLNW0jFiRF1LgltWk4pgxPJIKcW1PLZ22aWLobs" +
                "77jCRObsTs1VaQH/rhgeT0GkzvVGjpB4qb+FUXAZ7CmN2GYXYmXH9A/AkqgBPlsBJzwMpWJO1wJcZAY8PXcG45Gd" +
                "0BWzYfEBXIKJSXa0CDl1d/ko07RHgAWDNeA9LHTTE1P/oS3v3nnLBVgYdSW2drjKJEfBeiqP+uExAQEgIOtIWf+7P" +
                "EzDUR0tw5NDX5R" +
                "9yeGfJHzHqh0eAJ0FTcKXC977Wxtx5Uf72anbOy+tzc155Pf9vwFrgEUr1gKcBP5f3CYcUgPvqosq8h17INmshhe" +
                "Y6bZvryr59JqA5OLpJT8GpMhVRLk2Xnr7rSoIIHk4W6MhYtESMO9mNq4R/bAY4xN2Ycje+xMu6RCIqp53fyGnnN" +
                "yJ7bP72SpZH/56e/9Sq9Pz1G3OA2FnfqD8AfL1oSQKmALiyEp4pEzCuvS9n1+oqqAqe4yEUgW6XUtT7+/OgKlkk" +
                "T42CJUYGTDniJ4yfHCBUgV3wYWsRAehBhU8ureWTp8awtlusXJnk/ieSM5Y/nrqiqlptA053HDDew3ieahBsQxJ" +
                "rDWigivL7TZKArvJ0ssDT/XliAe2XjA4+YlgIjZg7sjwH9lpnELszPXuHhbOpgGkKPn1OE7fcOIuffbmedNI5Dfg" +
                "V5Zu2tRlTAjRVAg5CwOxQKc" +
                "/2hSCCYCBtc8nmPlTBtoDgR4qATM4ZWjsnhDjMxWTrGENkpD2sHRbkBN/9TivfPaOeZJ/1TeDbQ02bajVcd1ykm" +
                "tAqEuDIUcd8RVc5NGyAhIgQYPmctrGXzWmL5oC+jFHPuey7GP+k5cgAMRUTIDyJ5UiQKj+7upWLltbRF7d+AVw6" +
                "1e4qEpB2vZHHwgNCBnNrykqs4HHu+m6e7c/TFDEuAybKvsSoj7INEaCMmRqTsiESLEDT+c1PpvHpj0bo6bJuAI6" +
                "bSl" +
                "cVCYiV38io1VUcyztmUSxIJBairzfH8a928vvuDI0h/dvAjUPXpLKjhIsoS4FRJvdyNlkTno9VlBAxeOja6cydH" +
                "aSzw3qSUrY4KZtK3Fwc1JW646tMfr++l+9tjNNhufHmiHGZL/njyIb+aKUgANpnRCFYizHLAhGgrkoFfGa2mNAW" +
                "wDBHPIup8qEIAjNNXn9Y5zNf3qY980b+pfo6bS6wtdKlkyVAAN9oCOlct2OQtYNFFF29py1kXO4hB0Y2zBU9jNG" +
                "ZYh3ArXe/ReO0bpxUGiMSZ9W6PGZA554nkszeVsRLucNLhRghsff8KYYF1tAy0xxTOOMTUVa9XQg7nnwOaKsIrFJ" +
                "1uKV1GkBYQMYHmfHk/VFN/e+E7b7QYqh4SPxyYqQJge2O0+1Xqqr2o57ubLl4r0rQhRnWRKxG" +
                "k129Nrh+qei" +
                "3m+whCTkyQoz+XYxqz9B3NbUakZCC47AdOKCne+9vi1ckoL62BkAxwlVHKEIkk7a7LaqpJGyXSRJAIt7bwOh4I" +
                "0Z8yjHnI9uMPd/T38eCUCjtI+Qr4atIwP93+6f/j5EPCNjfDuxv+4CA/e3A/rYPCNjfDuxv+z9xCDBnJaJmRAAA" +
                "AABJRU5ErkJggg=="));
        e.setResponse(sp);
        return;
    }

    public String getCenter(String fromWhat){
        StringBuilder sb = new StringBuilder();
        int a = 67 - fromWhat.replace("§6", "")
                .replace("§7", "")
                .replace("§a", "").length();
        for (int i = 0; i < a / 2; i++) {
            sb.append(" ");
        }
        sb.append(fromWhat);
        for (int i = 0; i < a / 2; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

}
