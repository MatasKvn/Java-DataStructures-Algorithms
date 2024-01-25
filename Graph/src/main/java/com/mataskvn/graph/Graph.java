package com.mataskvn.graph;

import java.util.*;

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
            result.append(entry.getKey().toString())
                  .append(" -> ")
                  .append(entry.getValue().toString()).append('\n');
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    public List<Edge<T>> dijkstra(T beginning, T end)
    {

        List<List<Edge<T>>> paths = dijkstra_recursive(
                beginning, end,0,new Stack<>(), 0, new ArrayList<List<Edge<T>>>(), new ArrayList<Edge<T>>()
                );
        if (paths == null)
            return null;

        int minWeight = Integer.MAX_VALUE;
        List<Edge<T>> minWeightPath = null;

        for (List<Edge<T>> path : paths)
        {
            int pathWeight = calcPathSum(path);
            if (pathWeight < minWeight)
            {
                minWeight = pathWeight;
                minWeightPath = path;
            }
        }
        return minWeightPath;
    }

    //
    private List<List<Edge<T>>> dijkstra_recursive(T begin, T end, int recursionLevel,
                                                  Stack<T> visitedStack, int pathWeight, List<List<Edge<T>>> paths, List<Edge<T>> path)
    {
        if (begin.equals(end))
        {
//            System.out.println("Found path with weight: " + pathWeight);
//            System.out.println("Path: " + path.toString());
            paths.add(new ArrayList<>(path));
        }
        if (adjacencyMap.isEmpty())
        {
            System.out.println("Graph is empty");
            return null;
        }

        visitedStack.push(begin);
        for (Edge<T> edge : adjacencyMap.get(begin))
        {
            if (visitedStack.contains(edge.getValue()))
                continue;

////             Debug
//            String indentation = "";
//            for (int i = 0; i < recursionLevel; ++i)
//                indentation += " ";
//            System.out.println(indentation + "Chose edge: " + edge);

            path.add(edge);
            dijkstra_recursive(edge.getValue(), end, recursionLevel+1, visitedStack, pathWeight+edge.getWeight(), paths, path);
            path.remove(edge);

        }
        visitedStack.pop();

        return paths;
    }
    private int calcPathSum(List<Edge<T>> path)
    {
        if (path == null || path.isEmpty())
            return Integer.MAX_VALUE;
        int[] sum = new int[]{0};
        path.forEach(x -> sum[0] = sum[0] + x.getWeight());
        return sum[0];
    }




    // MAIN
    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<Integer>();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1,2,0);
        graph.addEdge(2,3,0);
        graph.addEdge(1,3, 6);

        System.out.println(graph.getAdjacencyRepresentationString());

        System.out.println("Min path: " + graph.dijkstra(1,3));

        System.out.println("aaaaaa");

    }


}

class Edge<T>
{
    private T value;

    private int weight;

    Edge(T value, int weight)
    {
        this.value = value;
        this.weight = weight;
    }

    public T getValue() { return value; }
    public int getWeight() { return weight; }

    @Override
    public String toString()
    {
        return "{" + value + " " + weight + "}";
    }
}
