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
        if (!adjacencyMap.containsKey(source))
            addVertex(source);
        if (!adjacencyMap.containsKey(destination))
            addVertex(destination);
        adjacencyMap.get(source).add(new Edge<>(destination, weight));
    }

    public boolean containsVertex(T vertex)
    {
        return adjacencyMap.containsKey(vertex);
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

    public static<T> String getPathRepresentationString(T beginning, List<Edge<T>> path)
    {
        StringBuilder result = new StringBuilder(beginning.toString());
        for (Edge<T> edge : path)
        {
            result.append(" -> ")
                    .append(edge.getValue());
        }
        return result.toString();
    }


    public List<Edge<T>> dijkstra(T beginning, T end)
    {
        List<List<Edge<T>>> paths = dijkstra_recursive(
                beginning, end,0,new Stack<>(), 0, new ArrayList<List<Edge<T>>>(), new ArrayList<Edge<T>>()
                );

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
            paths.add(new ArrayList<>(path));
        }
        if (adjacencyMap.isEmpty())
            return paths;

        visitedStack.push(begin);
        for (Edge<T> edge : adjacencyMap.get(begin))
        {
            if (visitedStack.contains(edge.getValue()))
                continue;

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
        Graph<String> graph = new Graph<String>();

//        graph.addVertex("Joe");
//        graph.addVertex("Peter");
//        graph.addVertex("Anton");
        graph.addEdge("Joe","Peter",0);
        graph.addEdge("Peter","Anton",0);
        graph.addEdge("Joe","Anton", 6);
        graph.addEdge("a","b",999);

        System.out.println(graph.getAdjacencyRepresentationString());

        System.out.println("Min path: " + Graph.getPathRepresentationString("Joe" , graph.dijkstra("Joe", "Anton")));

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
