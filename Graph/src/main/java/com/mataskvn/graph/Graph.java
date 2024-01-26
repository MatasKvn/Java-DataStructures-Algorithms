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
                    .append(edge.getNode());
        }
        return result.toString();
    }


    public List<Edge<T>> dijkstra(T beginning, T end)
    {
        if (beginning.equals(end))
            return new ArrayList<>();
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
            if (visitedStack.contains(edge.getNode()))
                continue;

            path.add(edge);
            dijkstra_recursive(edge.getNode(), end, recursionLevel+1, visitedStack, pathWeight+edge.getWeight(), paths, path);
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


    public List<T> breadthFirstSearch(T node)
    {
        Stack<T> visitedStack = new Stack<T>();
        Queue<T> queue = new PriorityQueue<T>();

        visitedStack.add(node);
        queue.add(node);
        while (!queue.isEmpty())
        {
            node = queue.remove();

            for (Edge<T> edge : adjacencyMap.get(node))
            {
                if (!visitedStack.contains(edge.getNode()))
                {
                    visitedStack.push(edge.getNode());
                    queue.add(edge.getNode());
                }
            }
        }
        return visitedStack;
    }


    private List<T> depthFirstSearch_recursive(T node, Stack<T> visitedStack)
    {
        visitedStack.push(node);
        for (Edge<T> edge : adjacencyMap.get(node))
        {
            if (visitedStack.contains(edge.getNode()))
                continue;

            depthFirstSearch_recursive(edge.getNode(), visitedStack);
        }

        return visitedStack;
    }

    public List<T> depthFirstSearch(T node)
    {
        return depthFirstSearch_recursive(node, new Stack<T>());
    }

    // MAIN
    public static void main(String[] args) {
        Graph<String> graph = new Graph<String>();

        graph.addEdge("a","b",0);
        graph.addEdge("a","o",0);
        graph.addEdge("a","c",0);
        graph.addEdge("b","a",0);
        graph.addEdge("b","c",0);
        graph.addEdge("b","n",8);
        graph.addEdge("c","d",0);
        graph.addEdge("d","e", 6);
        graph.addEdge("e","f", 4);
        graph.addEdge("o","a", 4);

        System.out.println(graph.getAdjacencyRepresentationString());


        var path = graph.dijkstra("a", "f");
        System.out.println("Min path: " + Graph.<String>getPathRepresentationString("a", path));

        System.out.println(graph.breadthFirstSearch("b"));
        System.out.println(graph.depthFirstSearch("b"));
    }
}

