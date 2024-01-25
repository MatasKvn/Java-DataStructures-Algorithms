package com.mataskvn.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T>
{
    public Map<T, List<Edge<T>>> adjacencyMap;

    public Graph()
    {
        adjacencyMap = new HashMap<T, List<Edge<T>>>();
    }

    public void addVertex(T vertex)
    {
        adjacencyMap.put(vertex, new ArrayList<Edge<T>>());
    }

    public void addEdge(T source, T destination, int weight)
    {
        adjacencyMap.get(source).add(new Edge<>(destination, weight));
    }



    public String getAdjacencyRepresentationString()
    {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<T, List<Edge<T>>> entry : adjacencyMap.entrySet())
        {
            result.append(entry.getKey().toString() + " -> " + entry.getValue().toString() + '\n');
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<Integer>();

        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(5, 6, 0);
        graph.addEdge(5,5, 6);
        graph.addEdge(6,6, 0);

        System.out.println(graph.getAdjacencyRepresentationString());
        System.out.println("aaaaaa");

    }


}

class Edge<T>
{
    private T destination;

    private int weight;

    Edge(T value, int weight)
    {
        this.destination = value;
        this.weight = weight;
    }

    @Override
    public String toString()
    {
        return "{" + destination + " " + weight + "}";
    }
}
