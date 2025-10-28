package ch.zhaw.ads;

import java.util.*;

public class RouteServer implements CommandExecutor {
    /*
    build the graph given a text file with the topology
    */
    public Graph<DijkstraNode, Edge> createGraph(String topo) throws Exception {
        Graph<DijkstraNode,Edge> graph = new AdjListGraph<DijkstraNode,Edge>(DijkstraNode.class,Edge.class);
        List<String> lines = Arrays.asList(topo.split("\n"));

        for(String line : lines) {
            String [] fromTo = line.split(" ");
            String from = fromTo[0];
            String to = fromTo[1];
            int dist = Integer.parseInt(fromTo[2]);

            try {
                if(graph.findNode(from) == null) graph.addNode(from);
                if(graph.findNode(to) == null) graph.addNode(to);

                graph.addEdge(from, to, dist);
                graph.addEdge(to, from, dist);

            } catch (Throwable e) {
                throw new Exception(e);
            }
        }
        return graph;
    }


    /*
    apply the dijkstra algorithm
    */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        DijkstraNode startNode = graph.findNode(from);
        DijkstraNode endNode = graph.findNode(to);
        PriorityQueue<DijkstraNode> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(DijkstraNode::getDist));

        startNode.setDist(0);
        priorityQueue.add(startNode);

        DijkstraNode<Edge> current;

        while(!priorityQueue.isEmpty()) {
            current = priorityQueue.poll();
            current.setMark(true);
            if(current == endNode) return;

            for(Edge e : current.getEdges()) {
                DijkstraNode<Edge> n = (DijkstraNode<Edge>) e.getDest();
                if(!n.getMark()) {
                    double dist = e.getWeight()+ current.getDist();

                    if(n.getPrev() == null || dist < n.getDist()) {
                        priorityQueue.remove(n);
                        n.setDist(dist);
                        n.setPrev(current);
                        priorityQueue.add(n);
                    }
                }
            }
        }

    }

    /*
    find the route in the graph after applied dijkstra
    the route should be returned with the start town first
    */
    public List<DijkstraNode<Edge>> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode<Edge>> route = new LinkedList<>();
        DijkstraNode<Edge> town = graph.findNode(to);
        do {
            route.add(0,town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String topo) throws Exception {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode<Edge>> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuffer buf = new StringBuffer();
        for (DijkstraNode<Edge> rt : route) buf.append(rt+"\n");
        return buf.toString();
    }

    public static void main(String[] args)throws Exception {
        String swiss =
                "Winterthur Zürich 25\n"+
                        "Zürich Bern 126\n"+
                        "Zürich Genf 277\n"+
                        "Zürich Luzern 54\n"+
                        "Zürich Chur 121\n"+
                        "Zürich Berikon 16\n"+
                        "Bern Genf 155\n"+
                        "Genf Lugano 363\n"+
                        "Lugano Luzern 206\n"+
                        "Lugano Chur 152\n"+
                        "Chur Luzern 146\n"+
                        "Luzern Bern 97\n"+
                        "Bern Berikon 102\n"+
                        "Luzern Berikon 41\n";
        RouteServer server = new RouteServer();
        System.out.println(server.execute(swiss));
    }
}