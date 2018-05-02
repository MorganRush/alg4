package logical;

import com.sun.javafx.geom.Edge;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private int countNode;

    private static final int infinity = 100;
    //пропускная способность
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

        ArrayList<Edge> edgesFromChain = new ArrayList<Edge>();
        int minFlowFromChain = infinity;
        int thisIndex = source;

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
                    if (bandwidth[thisIndex][i] != 0){
                        edgesIncident.add(new Edge(i, true));
                        if (bandwidth[thisIndex][i] > maxBandwidth){
                            indexMax = edgesIncident.size() - 1;
                            maxBandwidth = bandwidth[thisIndex][i];
                        }
                    }
                    if (flow[i][thisIndex] > 0){
                        edgesIncident.add(new Edge(i, false));
                        if (flow[i][thisIndex] > maxBandwidth){
                            indexMax = edgesIncident.size() - 1;
                            maxBandwidth = flow[i][thisIndex];
                        }
                    }
                }
            }
            if (indexMax == -1){
                int indexIsSeen = 0;
                if (!edgesFromChain.isEmpty()){
                    indexIsSeen = edgesFromChain.get(0).getIndexNode();
                    edgesFromChain.clear();
                }
                isSeen[indexIsSeen] = true;
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
        for (Edge edge : edgesFromChain) {
            int next = edge.getIndexNode();
            if (edge.isToFlow()){
                flow[start][next] += minFlowFromChain;
                bandwidth[start][next] -= minFlowFromChain;
                start = next;
            }
            else {
                flow[next][start] -= minFlowFromChain;
                bandwidth[next][start] += minFlowFromChain;
                start = next;
            }
        }
        return !isSeen[0];
    }

    public int findMaxFlow(int source, int target){
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

    public void test1(){
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
    }

    public void test2(){
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
    }
}
