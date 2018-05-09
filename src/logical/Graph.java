package logical;

import java.util.ArrayList;

public class Graph {

    private int countNode;

    private static final int infinity = 100;

    private int[][] bandwidth;

    private int[][] flow;

    private boolean[] isSeen;

    public Graph(){
    }

    private class Edge{
        private int indexNode;

        private boolean isToFlow;

        public Edge(int indexNode, boolean isToFlow){
            this.indexNode = indexNode;
            this.isToFlow = isToFlow;
        }

        public int getIndexNode() {
            return indexNode;
        }

        public boolean isToFlow() {
            return isToFlow;
        }
    }

    private boolean findPath(int source, int target){

        if (target > countNode){
            target = countNode;
        }

        ArrayList<Edge> edgesFromChain = new ArrayList<Edge>();
        int minFlowFromChain = infinity;
        int thisIndex = source;
        int indexCheck = -1;

        while (thisIndex != target){
            ArrayList<Edge> edgesIncident = new ArrayList<Edge>();
            int indexMax = -1;
            int maxBandwidth = 0;
            for (int i = 0; i < countNode; i++){
                boolean isIEqualsIndexPrevious = false;
                for (Edge edge : edgesFromChain) {
                    if (i == edge.indexNode){
                        isIEqualsIndexPrevious = true;
                    }
                }
                if (!isIEqualsIndexPrevious && i != source && !isSeen[i]){
                    if (bandwidth[thisIndex][i] >= flow[i][thisIndex] && bandwidth[thisIndex][i] != 0){
                        edgesIncident.add(new Edge(i, true));
                        if (bandwidth[thisIndex][i] > maxBandwidth){
                            indexMax = edgesIncident.size() - 1;
                            maxBandwidth = bandwidth[thisIndex][i];
                        }
                    } else if (flow[i][thisIndex] > 0){
                        edgesIncident.add(new Edge(i, false));
                        if (flow[i][thisIndex] > maxBandwidth){
                            indexMax = edgesIncident.size() - 1;
                            maxBandwidth = flow[i][thisIndex];
                        }
                    }
                }
            }
            if (edgesIncident.size() > 1){
                indexCheck = edgesIncident.get(indexMax).indexNode;
            }
            if (indexMax == -1){
                if (indexCheck == -1){
                    int indexIsSeen = 0;
                    if (!edgesFromChain.isEmpty()){
                        indexIsSeen = edgesFromChain.get(0).getIndexNode();
                    }
                    isSeen[indexIsSeen] = true;
                } else {
                    isSeen[indexCheck] = true;
                }
                edgesFromChain.clear();
                break;
            }
            Edge edgeToChain = edgesIncident.get(indexMax);
            edgesFromChain.add(edgeToChain);
            if (maxBandwidth < minFlowFromChain){
                minFlowFromChain = maxBandwidth;
            }
            thisIndex = edgeToChain.indexNode;
        }
        int start = source;
        if (!edgesFromChain.isEmpty()){
            System.out.println("Увеличивающая цепь:");
            for (Edge edge : edgesFromChain) {
                int next = edge.getIndexNode();
                System.out.println("Ребро: " + start + " " + next);
                if (edge.isToFlow()){
                    System.out.println("Текущий поток: " + flow[start][next]);
                    System.out.println("Текущая пропускная способность: " + bandwidth[start][next]);
                    flow[start][next] += minFlowFromChain;
                    bandwidth[start][next] -= minFlowFromChain;
                    System.out.println("Новый поток: " + flow[start][next]);
                    System.out.println("Новая пропускная способность: " + bandwidth[start][next]);
                }
                else {
                    System.out.println("Текущий поток: " + flow[next][start]);
                    System.out.println("Текущая пропускная способность: " + bandwidth[next][start]);
                    flow[next][start] -= minFlowFromChain;
                    bandwidth[next][start] += minFlowFromChain;
                    System.out.println("Новый поток: " + flow[next][start]);
                    System.out.println("Новая пропускная способность: " + bandwidth[next][start]);
                }
                start = next;
            }
            System.out.println();
        }
        return !isSeen[0];
    }

    private int findMaxFlow(int source, int target){
        for (int i = 0; i < isSeen.length; i++){
            isSeen[i] = false;
        }
        int count = 0;
        while (findPath(source, target)){
            count++;
        }
        int maxFlow = 0;
        for (int i = 0; i < countNode; i++){
            maxFlow += flow[0][i];
        }
        return maxFlow;
    }

    public int test1(int source, int target){
        countNode = 6;
        bandwidth = new int[countNode][countNode];
        flow = new int [countNode][countNode];
        isSeen = new boolean[countNode];
        bandwidth[0][1] = 9;
        bandwidth[0][2] = 8;
        bandwidth[1][3] = 6;
        bandwidth[1][4] = 3;
        bandwidth[2][4] = 4;
        bandwidth[3][5] = 10;
        bandwidth[4][3] = 4;
        bandwidth[4][5] = 7;

        return findMaxFlow(source, target);
    }

    public int test2(int source, int target){
        countNode = 4;
        bandwidth = new int[countNode][countNode];
        flow = new int [countNode][countNode];
        isSeen = new boolean[countNode];
        bandwidth[0][1] = 7;
        bandwidth[0][2] = 0;
        bandwidth[1][3] = 0;
        bandwidth[2][1] = 1;
        bandwidth[2][3] = 6;
        flow[0][2] = 8;
        flow[1][3] = 8;
        flow[2][1] = 8;

        return findMaxFlow(source, target);
    }

    public int myVariantBandwidthZero(int source, int target){
        countNode = 6;
        bandwidth = new int[countNode][countNode];
        flow = new int [countNode][countNode];
        isSeen = new boolean[countNode];
        bandwidth[0][1] = 10;
        bandwidth[0][2] = 6;
        bandwidth[0][5] = 7;

        bandwidth[1][2] = 5;
        bandwidth[1][3] = 7;

        bandwidth[2][4] = 8;

        bandwidth[3][1] = 7;
        bandwidth[3][5] = 6;

        bandwidth[4][2] = 8;
        bandwidth[4][3] = 9;
        bandwidth[4][5] = 9;

        return findMaxFlow(source, target);
    }

    public int myVariantBandwidthNotZero(int source, int target){
        countNode = 6;
        bandwidth = new int[countNode][countNode];
        flow = new int [countNode][countNode];
        isSeen = new boolean[countNode];
        bandwidth[0][1] = 8;
        flow[0][1] = 2;
        bandwidth[0][2] = 0;
        flow[0][2] = 6;
        bandwidth[0][5] = 7;
        flow[0][5] = 0;

        bandwidth[1][2] = 3;
        flow[1][2] = 2;
        bandwidth[1][3] = 7;
        flow[1][3] = 0;

        bandwidth[2][4] = 2;
        flow[2][4] = 6;

        bandwidth[3][1] = 7;
        flow[3][1] = 0;
        bandwidth[3][5] = 0;
        flow[3][5] = 6;

        bandwidth[4][2] = 8;
        flow[4][2] = 0;
        bandwidth[4][3] = 3;
        flow[4][3] = 6;
        bandwidth[4][5] = 7;
        flow[4][5] = 2;

        return findMaxFlow(source, target);
    }
}
