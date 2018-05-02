import logical.Graph;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.test2();
        int result = graph.findMaxFlow(0, 3);
        System.out.println(result);
    }
}
