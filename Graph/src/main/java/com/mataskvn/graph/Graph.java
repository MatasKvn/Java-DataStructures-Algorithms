package com.mataskvn.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T>
{
    public Map<Node<T>, List<Node<T>>> adjacencyMap;

    public Graph ()
    {
        adjacencyMap = new HashMap<Node<T>, List<Node<T>>>();
    }

    public void printGraph()
    {
        for (Map.Entry<Node<T>, List<Node<T>>> entry : adjacencyMap.entrySet())
        {
            System.out.println(entry.getKey().toString() + " " + entry.getValue().toString());
        }
    }

    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<Integer>();

        Node<Integer> node1 = new Node<Integer>(75, 1);
        ArrayList<Node<Integer>> adjList1 = new ArrayList<Node<Integer>>();
        adjList1.add(node1);

        graph.adjacencyMap.put(node1,adjList1);

        graph.printGraph();
        System.out.println("aaaaaa");

    }


}

class Node<T>
{
    private T node;

    private int weight;

    Node(T node, int w)
    {
        this.node = node;
        this.weight = weight;
    }

    @Override
    public String toString()
    {
        return "{" + node + " " + weight + "}";
    }
}
