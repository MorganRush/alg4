import logical.Graph;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        int result = graph.findMaxFlow(0, 5);
        System.out.println(result);
    }
}
