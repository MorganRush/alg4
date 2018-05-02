import logical.Graph;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        int result = graph.myVariantBandwidthZero(0, 5);
        System.out.println("Максимальный поток: " + result);
        System.out.println();
        result = graph.myVariantBandwidthNotZero(0, 5);
        System.out.println("Максимальный поток: " + result);
    }
}
