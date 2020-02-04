package ua.dkotov.empiresystems.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class DurationUtil {

    public Map<String, Duration> instances;

    public DurationUtil(){
        instances = new HashMap<>();
        instances.put("s", ChronoUnit.SECONDS.getDuration());
        instances.put("m", ChronoUnit.MINUTES.getDuration());
        instances.put("h", ChronoUnit.HOURS.getDuration());
        instances.put("d", ChronoUnit.DAYS.getDuration());
        instances.put("w", ChronoUnit.WEEKS.getDuration());
        instances.put("M", ChronoUnit.MONTHS.getDuration());
        instances.put("y", ChronoUnit.YEARS.getDuration());
        instances.put("D", ChronoUnit.DECADES.getDuration()); // 10y
        instances.put("c", ChronoUnit.CENTURIES.getDuration()); // 100y
        instances.put("T", ChronoUnit.MILLENNIA.getDuration()); // MILLENNIA - 1000y
        instances.put("e", ChronoUnit.ERAS.getDuration()); // era -  1 000 000 000 y
    }

    public long parse(String time){
        long timeM = 0;
        try {
            for (String t : instances.keySet()) {
                if (!time.endsWith(t)) {
                    timeM = instances.get(t).toMillis() *
                            Long.parseLong(time.substring(0, time.length() - 1));
                    return timeM;
                }
            }
        }catch (Exception e){
            return 0;
        }
        return timeM;
    }

    public String formatDuration(Duration d){
        return "";
    }

}
