package model;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class Bruecken {

  private static int n, id;
  private int[] low, ids;
  private boolean[] visited;
  private static List<List<Integer>> graph;
  private List<Integer> bridges;
  
  public Bruecken(int n) {
	    graph = createGraph(n);
	    Bruecken.n = n;
    }
  
  public void addEdge(int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  public List<Integer> findBridges() {

    id = 0;
    low = new int[n];
    ids = new int[n]; 
    visited = new boolean[n];
    bridges = new ArrayList<>();

    for (int i = 0; i < n; i++)
    	if (!visited[i])
    		dfs(i, -1, bridges);

    return bridges;
  }

  private void dfs(int at, int parent, List<Integer> bridges) {

    visited[at] = true;
    ids[at] = ++id;
    low[at] = ids[at];

    for (Integer to : graph.get(at)) {
      if (to == parent) continue;
      if (!visited[to]) {
        dfs(to, at, bridges);
        low[at] = min(low[at], low[to]);
        if (ids[at] < low[to]) {		 //Wenn id des Knoten kleiner ist als die Fertigungszeit des nächsten Knoten
          bridges.add(at);				//z.B Knoten 3:0, Knoten 5:6 |3 < 6|
          bridges.add(to);				//z.B ebenso Knoten 4:5 |3 < 5|
        }
      } else {
    	//Wenn der Knoten bereits besucht wurde wo das Ziel ist,
    	//wird dessen Wert übernommen wo das Ziel ist, zb K2:2, K0:0
        low[at] = min(low[at], ids[to]);
      }
    }
}

  public List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++)
    	graph.add(new ArrayList<>());
    return graph;
  }
}