package ch.zhaw.ads;

import java.util.concurrent.atomic.AtomicInteger;

public class RankingTreeServer implements CommandExecutor {
    TreeNode root = null;

    public Tree<Competitor> createTree(String rankingText) {
        Tree<Competitor> competitorTree = new SortedBinaryTree<>();
        String[] lines = rankingText.split("\\r?\\n");

        for (String line : lines) {
            Competitor c = new Competitor(0, line.split(";")[0], line.split(";")[1]);
            competitorTree.add(c);
        }

        return competitorTree;
    }


    public String createSortedText(Tree<Competitor> competitorTree) {
        Traversal<Competitor> traversal = competitorTree.traversal();
        StringBuilder sb = new StringBuilder();

        AtomicInteger index = new AtomicInteger(0);
        traversal.inorder(visitor -> {
            sb.append(index.incrementAndGet()).append(" ")
                    .append(visitor.getName()).append(" ")
                    .append(visitor.getTime())
                    .append(System.lineSeparator());
        });

        return sb.toString();
    }



    public String execute(String rankingList) throws Exception {
        Tree<Competitor> competitorTree = createTree(rankingList);
        return "Rangliste (Tree)\n" + createSortedText(competitorTree);
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
        RankingTreeServer server = new RankingTreeServer();
        System.out.println(server.execute(rangliste));
    }
}