import java.util.*; 
import java.lang.*; 
import java.io.*; 

class Graph 
{ 
	class Edge implements Comparable<Edge> 
	{ 
		int src, dest, weight;  
		@Override
		public int compareTo(Edge compareEdge) 
		{ 
			return this.weight - compareEdge.weight; 
		} 
	}; 

	class Subset 
	{ 
		int parent, rank; 
	}; 

	int V, E; 
	Edge[] edge; 

	// Constructor
	Graph(int v, int e) 
	{ 
		V = v; 
		E = e; 
		edge = new Edge[E]; 
		for (int i = 0; i < e; ++i) 
			edge[i] = new Edge(); 
	} 

	// Find with path compression
	int find(Subset subsets[], int i) 
	{ 
		if (subsets[i].parent != i) 
			subsets[i].parent = find(subsets, subsets[i].parent); 
		return subsets[i].parent; 
	} 

	// Union by rank
	void Union(Subset subsets[], int x, int y) 
	{ 
		int xroot = find(subsets, x); 
		int yroot = find(subsets, y); 

		if (subsets[xroot].rank < subsets[yroot].rank) 
			subsets[xroot].parent = yroot; 
		else if (subsets[xroot].rank > subsets[yroot].rank) 
			subsets[yroot].parent = xroot; 
		else
		{ 
			subsets[yroot].parent = xroot; 
			subsets[xroot].rank++; 
		} 
	} 

	// Kruskal's MST
	void KruskalMST() 
	{ 
		Edge[] result = new Edge[V - 1]; // Store V-1 edges for the MST
		int e = 0; // Index for result[]
		int i = 0; // Index for sorted edges
		
		Arrays.sort(edge); // Sort edges by weight

		Subset[] subsets = new Subset[V]; 
		for (int v = 0; v < V; ++v) 
		{ 
			subsets[v] = new Subset(); 
			subsets[v].parent = v; 
			subsets[v].rank = 0; 
		} 

		// Iterate through edges until MST has V-1 edges
		while (e < V - 1 && i < E) 
		{ 
			Edge nextEdge = edge[i++]; 
			int x = find(subsets, nextEdge.src); 
			int y = find(subsets, nextEdge.dest); 

			if (x != y) 
			{ 
				result[e++] = nextEdge; 
				Union(subsets, x, y); 
			} 
		} 

		// Print the constructed MST
		System.out.println("Following are the edges in the constructed MST"); 
		for (i = 0; i < e; ++i) 
			System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight); 
	} 

	// Driver code
	public static void main (String[] args) 
	{ 
		int V = 4;  
		int E = 5;  
		Graph graph = new Graph(V, E); 

		// add edge 0-1 
		graph.edge[0].src = 0; 
		graph.edge[0].dest = 1; 
		graph.edge[0].weight = 10; 

		// add edge 0-2 
		graph.edge[1].src = 0; 
		graph.edge[1].dest = 2; 
		graph.edge[1].weight = 6; 

		// add edge 0-3 
		graph.edge[2].src = 0; 
		graph.edge[2].dest = 3; 
		graph.edge[2].weight = 5; 

		// add edge 1-3 
		graph.edge[3].src = 1; 
		graph.edge[3].dest = 3; 
		graph.edge[3].weight = 15; 

		// add edge 2-3 
		graph.edge[4].src = 2; 
		graph.edge[4].dest = 3; 
		graph.edge[4].weight = 4; 

		// Execute Kruskal's algorithm
		graph.KruskalMST(); 
	} 
} 
