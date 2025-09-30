package ch.zhaw.ads;
import java.util.*;
import java.util.stream.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class RankingStreamServer implements CommandExecutor {

    public Stream<Competitor> createStream(String rankingText) {
        List<Competitor> competitors = new ArrayList<>();
        String[] lines = rankingText.split("\\r?\\n");

        Arrays.stream(lines)
                        .map(line -> line.split(";"))
                        .map(parts -> new Competitor(0, parts[0], parts[1]))
                        .forEach(competitors::add);

        return competitors.stream();
    }

    public String createSortedText(Stream<Competitor> competitorStream) {
        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger atomicInteger = new AtomicInteger(1);

        competitorStream.sorted().forEach( competitor -> stringBuilder
                .append(atomicInteger.getAndIncrement()).append(" ")
                .append(competitor.getName()).append(" ")
                .append(competitor.getTime())
                .append(System.lineSeparator()));

        return stringBuilder.toString();
    }

    public String execute(String rankingList) throws Exception {
        Stream<Competitor> competitorStream = createStream(rankingList);
        return "Rangliste (Stream)\n" + createSortedText(competitorStream);
    }

    public static void main(String[] args) throws Exception {
        String rangliste =
                "Mueller Stefan;02:31:14\n"+
                        "Marti Adrian;02:30:09\n"+
                        "Kiptum Daniel;02:11:31\n"+
                        "Ancay Tarcis;02:20:02\n"+
                        "Kreibuhl Christian;02:21:47\n"+
                        "Ott Michael;02:33:48\n"+
                        "Menzi Christoph;02:27:26\n"+
                        "Oliver Ruben;02:32:12\n"+
                        "Elmer Beat;02:33:53\n"+
                        "Kuehni Martin;02:33:36\n";
        RankingStreamServer server = new RankingStreamServer();
        System.out.println(server.execute(rangliste));
    }
}