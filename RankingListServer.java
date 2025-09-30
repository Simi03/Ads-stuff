package ch.zhaw.ads;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class RankingListServer implements CommandExecutor {

    public List<Competitor> createList(String rankingText) {
        List<Competitor> competitors = new ArrayList<>();
        String[] lines = rankingText.split("\\r?\\n");

        for (String line : lines) {
            String[] parts = line.split(";");
            competitors.add(new Competitor(0, parts[0], parts[1]));
        }

        return competitors;
    }

    public String createSortedText(List<Competitor> competitorList) {
        StringBuilder stringBuilder = new StringBuilder();

        competitorList.sort(Comparator.comparing(Competitor::getTime));
        for (int i = 0; i < competitorList.size(); i++) {
            Competitor comp = competitorList.get(i);
            stringBuilder
                    .append(i + 1).append(" ")
                    .append(comp.getName()).append(" ")
                    .append(comp.getTime())
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    public String execute(String rankingList) throws Exception {
        List<Competitor> competitorList = createList(rankingList);
        return "Rangliste (List)\n" + createSortedText(competitorList);
    }

    public static void main(String[] args) throws Exception {
        String rangliste =
                "Mueller Stefan;02:31:14\n" +
                        "Marti Adrian;02:30:09\n" +
                        "Kiptum Daniel;02:11:31\n" +
                        "Ancay Tarcis;02:20:02\n" +
                        "Kreibuhl Christian;02:21:47\n" +
                        "Ott Michael;02:33:48\n" +
                        "Menzi Christoph;02:27:26\n" +
                        "Oliver Ruben;02:32:12\n" +
                        "Elmer Beat;02:33:53\n" +
                        "Kuehni Martin;02:33:36\n";
        RankingListServer server = new RankingListServer();
        System.out.println(server.execute(rangliste));
    }
}