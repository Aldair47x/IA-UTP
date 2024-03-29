import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
 
public class UniformCostSearch
{
    private PriorityQueue<Node> priorityQueue;
    private Set<Integer> settled;
    private int distances[];
    private int numberOfNodes;
    private int adjacencyMatrix[][];
    private LinkedList<Integer> path;
    private int[] parent;
    private int source, destination;
    public static final int MAX_VALUE = 999; 
 
    public UniformCostSearch(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        this.settled = new HashSet<Integer>();
        this.priorityQueue = new PriorityQueue<>(numberOfNodes, new Node());
        this.distances = new int[numberOfNodes + 1];
        this.path = new LinkedList<Integer>();
        this.adjacencyMatrix = new int[numberOfNodes + 1][numberOfNodes + 1]; 
        this.parent = new int[numberOfNodes + 1];
    }
 
    public int uniformCostSearch(int adjacencyMatriz[][], int source, int destination)
    {
        int evaluationNode;
        this.source = source;
        this.destination = destination;
 
        for (int i = 1; i <= numberOfNodes; i++)
        {
            distances[i] = MAX_VALUE;
        }
        
 
       /* for (int sourcevertex = 1; sourcevertex <= numberOfNodes; sourcevertex++)
        {
            for (int destinationvertex = 1; destinationvertex <= numberOfNodes; destinationvertex++)
            {
                this.adjacencyMatrix[sourcevertex][destinationvertex] =
                       adjacencyMatriz [sourcevertex][destinationvertex];
	    }
        }*/
 
        priorityQueue.add(new Node(source, 0));
        distances[source] = 0;
 
        while (!priorityQueue.isEmpty())
        {
            evaluationNode = getNodeWithMinDistanceFromPriorityQueue();
            System.out.println("The eval Node is " + evaluationNode);
            if (evaluationNode == destination)
            {
                return distances[destination];
            } 
            settled.add(evaluationNode);
            addFrontiersToQueue(evaluationNode);
        }
        return distances[destination];
    }
 
    private void addFrontiersToQueue(int evaluationNode)
    {
        for (int destination = 1; destination <= numberOfNodes; destination++)
        {
            if (!settled.contains(destination))
            {
                if (adjacencyMatrix[evaluationNode][destination] != MAX_VALUE)
                {
                    if (distances[destination] > adjacencyMatrix[evaluationNode][destination]  
                                    + distances[evaluationNode])
                    {
                        distances[destination] = adjacencyMatrix[evaluationNode][destination]	
                                               + distances[evaluationNode]; 				 		
                        parent[destination] = evaluationNode;
                    }
 
                    Node node = new Node(destination, distances[destination]);
                    if (priorityQueue.contains(node))
                    {
                        priorityQueue.remove(node);
                    }
                    priorityQueue.add(node);
                }
            }
        }
    }
 
    private int getNodeWithMinDistanceFromPriorityQueue()
    {
        Node node = priorityQueue.remove();
        return node.node;
    }
 
    public void printPath()
    {
        path.add(destination);
        boolean found = false;
        int vertex = destination;
        while (!found)
        {
            if (vertex == source)
            {
                found = true;
                continue;
            }
            path.add(parent[vertex]);
            vertex = parent[vertex];
        }
        System.out.println("\n\n");
        System.out.println("The Path between " + source + " and " + destination+ " is ");
        System.out.println("\n\n");
        Iterator<Integer> iterator = path.descendingIterator();
        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + "\t");
        }
    }
 
 
class Node implements Comparator<Node>
{
    public int node;
    public int cost;
 
    public Node()
    {
 
    }
 
    public Node(int node, int cost)
    {
        this.node = node;
        this.cost = cost;
    }
 
    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        if (node1.node < node2.node)
            return -1;
        return 0;
    }
 
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Node)
        {
            Node node = (Node) obj;
            if (this.node == node.node)
            {
                return true;
            }
        }
        return false;
    }
}

}