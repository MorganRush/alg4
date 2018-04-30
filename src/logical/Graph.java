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

    public Graph(){
        countNode = 6;
        bandwidth = new int[countNode][countNode];
        flow = new int [countNode][countNode];
        bandwidth[0][1] = 9;
        bandwidth[0][2] = 8;
        bandwidth[1][3] = 6;
        bandwidth[1][4] = 3;
        bandwidth[2][4] = 4;
        bandwidth[3][5] = 10;
        bandwidth[4][3] = 4;
        bandwidth[4][5] = 7;
    }

    private class Edge{
        private int indexNode;

        boolean isToFlow;

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
        //int indexMinedgesFromChain = -1;

        while (thisIndex != target){
            ArrayList<Edge> edgesIncident = new ArrayList<Edge>();
            int indexMax = -1;
            int maxBandwidth = 0;

            for (int i = 0; i < countNode; i++){
                if (bandwidth[thisIndex][i] != 0 && flow[thisIndex][i] < bandwidth[thisIndex][i]){
                    edgesIncident.add(new Edge(i, true));
                    if (bandwidth[thisIndex][i] - flow[thisIndex][i] > maxBandwidth){
                        indexMax = edgesIncident.size() - 1;
                        maxBandwidth = bandwidth[thisIndex][i] - flow[thisIndex][i];
                    }
                }
                if (bandwidth[i][thisIndex] != 0 && flow[i][thisIndex] > 0){
                    edgesIncident.add(new Edge(i, false));
                    if (bandwidth[i][thisIndex] - flow[thisIndex][i] > maxBandwidth){
                        indexMax = edgesIncident.size() - 1;
                        maxBandwidth = bandwidth[thisIndex][i] - flow[thisIndex][i];
                    }
                }
            }
            if (indexMax == -1){
                break;
            }
            Edge edgeToChain = edgesIncident.get(indexMax);
            edgesFromChain.add(edgeToChain);
            if (maxBandwidth < minFlowFromChain){
                minFlowFromChain = maxBandwidth;
                //indexMinedgesFromChain = edgesFromChain.size() - 1;
            }
            thisIndex = edgeToChain.indexNode;
        }
        int start = source;
        for (Edge edge : edgesFromChain) {
            int next = edge.getIndexNode();
            flow[start][next] +=minFlowFromChain;
            bandwidth[start][next] -= minFlowFromChain;
            start = next;
        }
        return !edgesFromChain.isEmpty();
    }

    public void findMaxFlow(int source, int target){
        int count = 0;
        while (findPath(source, target)){
            count++;
        }
        count = 0;
    }
}
