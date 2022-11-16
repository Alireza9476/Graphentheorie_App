package model;

import java.util.*;
import java.util.LinkedList;

public class Artikulation
{
	private int V;
	private LinkedList<Integer> adj[];
	int time = 0;
	static final int NIL = -1;

	public Artikulation(int v)
	{
		V = v;
		adj = new LinkedList[v];
		for (int i=0; i<v; ++i)
			adj[i] = new LinkedList();
	}
	
	public void addEdge(int v, int w)
	{
		adj[v].add(w); 
		adj[w].add(v); 
	}

	public String getArtikulationen(){
		ArrayList<Integer> ergebnis = new ArrayList<Integer>();
		
		boolean visited[] = new boolean[V];
		int disc[] = new int[V];
		int low[] = new int[V];
		int parent[] = new int[V];
		boolean ap[] = new boolean[V]; 

		for (int i = 0; i < V; i++)
			parent[i] = NIL;			//Standardwerte eintragen, hierbei -1

		//Wenn das Knoten nicht besucht wurde, dann ausführen
		for (int i = 0; i < V; i++)	
			if (visited[i] == false)
				dfs(i, visited, disc, low, parent, ap);

		//Die Ausgabe der Artikulationen
		for (int i = 0; i < V; i++)
			if (ap[i] == true)
				ergebnis.add(i);
		
		return ergebnis.toString();
	}
	
	//u = der Knoten was besucht wird
	void dfs(int u, boolean visited[], int disc[], int low[], int parent[], boolean ap[]){
		int children = 0;
		visited[u] = true;				//Aktuell besuchte Knoten wird auf true gesetzt
		low[u] = ++time;
		disc[u] = low[u];		//disc -> Entdeckungszeiten speichern, der Wert der Zeit wird direkt addiert und dann weitergegeben		

		Iterator<Integer> i = adj[u].iterator();	//iterieren 
		while (i.hasNext())							
		{
			int v = i.next();						
			if (!visited[v])					
			{
				children++;
				parent[v] = u;								//ParentKnoten bekommt den Wert von u also der Knoten bei dem wir sind.
				dfs(v, visited, disc, low, parent, ap);		
				low[u] = Math.min(low[u], low[v]);		 //das kleiner von den wird in low gepeichert

				if (parent[u] == NIL && children > 1) {
					System.out.println("-------------------");
					System.out.println("Parent[u]: " + parent[u] + " children: " + children);
					System.out.println("ap[u]: " + ap[u] + " U: " + u); 
					System.out.println("-------------------");
					ap[u] = true;
				}

				if (parent[u] != NIL && low[v] >= disc[u]) {
					System.out.println("Okey");
					System.out.println("parent[u]" + parent[u]);
					System.out.println("low[v]:" + low[v]);	
					
					ap[u] = true;
				}
			}
			else if (v != parent[u]) {
				low[u] = Math.min(low[u], disc[v]);
				System.out.println("U: " + u);
				System.out.println("V: " + v);
				
				System.out.println("parent[u]: " + parent[u]);
				System.out.println("low[u]: " + low[u] + "\n");
			}
		}
	}
}
