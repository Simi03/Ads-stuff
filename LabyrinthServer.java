package ch.zhaw.ads;


import java.awt.*;

public class LabyrinthServer implements CommandExecutor {
    ServerGraphics serverGraphics = new ServerGraphics();

    public Graph<DijkstraNode, Edge> createGraph(String s) throws Exception {
        Graph<DijkstraNode,Edge> graph = new AdjListGraph<DijkstraNode,Edge>(DijkstraNode.class,Edge.class);
        String [] lines = s.split("\n");

        for (String line : lines) {
            String [] cells = line.split(" ");
            String from = cells[0];
            String to = cells[1];

            try {
                if(graph.findNode(from) == null) graph.addNode(from);
                if(graph.findNode(to) == null) graph.addNode(to);

                graph.addEdge(from, to,1);
                graph.addEdge(to, from,1);

            } catch (Throwable e) {
                throw new Exception(e);
            }
        }
        return graph;
    }

    public void drawLabyrinth(Graph<DijkstraNode, Edge> graph) {
        serverGraphics.setColor(Color.BLACK);
        serverGraphics.fillRect(0,0,10,10);

        serverGraphics.setColor(Color.WHITE);
        for (DijkstraNode<Edge> node : graph.getNodes()) {
            for(Edge e : node.getEdges()) {
                DijkstraNode<Edge> n = (DijkstraNode<Edge>) e.getDest();
                serverGraphics.drawPath(node.getName(), ((DijkstraNode<?>) e.getDest()).getName(), false);
            }
        }
    }

    private boolean search(DijkstraNode<Edge> current, DijkstraNode<Edge> goal) {
        current.setMark(true);
        if(current == goal) {
            return true;
        }

        for(Edge e : current.getEdges()) {
            DijkstraNode<Edge> nextNode = (DijkstraNode<Edge>) e.getDest();
            if (!nextNode.getMark() && search(nextNode, goal)) {
                return true;
            }
        }
        current.setMark(false);

        return false;
    }

    // search and draw result
    public void drawRoute(Graph<DijkstraNode, Edge> graph, String startNode, String zielNode) {
        DijkstraNode<Edge> start = graph.findNode(startNode);
        DijkstraNode<Edge> ziel = graph.findNode(zielNode);

        search(start, ziel);

        for (DijkstraNode<Edge> node : graph.getNodes()) {
            if(!node.getMark()) continue;

            for(Edge e : node.getEdges()) {
                DijkstraNode<Edge> next = (DijkstraNode<Edge>) e.getDest();
                if(!next.getMark()){
                    continue;
                }

                // marked Node
                serverGraphics.drawPath(node.getName(), next.getName(), true);
                break;
            }
        }
    }





    public String execute(String s) throws Exception {
        Graph<DijkstraNode, Edge> graph;
        String labyrinth = "0-6 4-6\n" + "4-6 7-6\n" + "7-6 9-6\n" + "7-6 7-4\n" + "7-4 6-4\n" + "7-4 9-4\n" + "9-4 9-1\n" + "7-4 7-1\n" + "7-1 5-1\n" + "4-6 4-4\n" + "4-4 4-3\n" + "4-4 1-4\n" + "1-4 1-1\n" + "1-1 3-1\n" + "3-1 3-2\n" + "3-1 3-0\n";
        graph = createGraph(labyrinth);
        drawLabyrinth(graph);

        serverGraphics.setColor(Color.RED);
        drawRoute(graph, "0-6", "3-0");
        return serverGraphics.getTrace();
    }

}