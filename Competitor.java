package ch.zhaw.ads;

import java.util.*;
import java.text.*;

public class Competitor implements Comparable<Competitor> {
    private String name;
    private String time;
    private int rank;

    public Competitor(int rank, String name, String time) {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    private static long parseTime(String s) {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;
    }

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return "" + rank + " " + name + " " + time;
    }

    @Override
    public int compareTo(Competitor o) {
        return Long.compare(parseTime(this.time), parseTime(o.getTime()));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Competitor)) return false;
        Competitor c = (Competitor) o;
        return parseTime(this.time) == parseTime(c.getTime());
    }
}