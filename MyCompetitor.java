package ch.zhaw.ads;

import java.util.*;
import java.text.*;

public class MyCompetitor implements Comparable<MyCompetitor> {
    private String name;
    private String time;
    private int rank;

    public MyCompetitor(int rank, String name, String time)  {
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

    private static long parseTime(String s)  {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {System.err.println(e);}
        return 0;
    }

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return ""+ rank + " "+name+" "+time;
    }

    @Override
    public int compareTo(MyCompetitor o) {
        if(o.rank > this.rank) return -1;
        if(o.rank < this.rank) return 1;

        return 0;
    }

    @Override
    public boolean equals (Object o) {
        MyCompetitor competitor = (MyCompetitor) o;
        return competitor.name.equals(this.name) && competitor.rank == this.rank;
    }

    @Override
    public int hashCode () {
        return Objects.hash(name, rank); // hash by name and rank
    }
}