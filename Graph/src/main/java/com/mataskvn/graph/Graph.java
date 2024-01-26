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


    public Path<T> dijkstra(T beginning, T end)
    {
        if (beginning.equals(end))
            return new Graph.Path<T>(beginning);
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

        return new Graph.Path<T>(beginning, minWeightPath);
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


    public boolean containsCycles()
    {

        for (Map.Entry<T, List<Edge<T>>> entry : adjacencyMap.entrySet())
        {
            if (containsCycles_recursive(entry.getKey(), new Stack<T>()))
                return true;
        }

        return false;
    }

    private boolean containsCycles_recursive(T node, Stack<T> visited)
    {
        if (visited.contains(node))
            return true;

        visited.push(node);
        for (Edge<T> edge : adjacencyMap.get(node))
        {
            if (visited.contains(edge.getNode()))
                return true;
            return containsCycles_recursive(edge.getNode(),visited);
        }

        return false;
    }









    // MAIN
    public static void main(String[] args) {
        Graph<String> graph = new Graph<String>();

        graph.addEdge("a","b",0);
//        graph.addEdge("b","a",0);

        graph.addEdge("a","o",0);
        graph.addEdge("a","c",0);
//        graph.addEdge("b","a",0);
        graph.addEdge("b","c",0);
        graph.addEdge("b","n",8);
        graph.addEdge("c","d",0);
        graph.addEdge("d","e", 6);
        graph.addEdge("e","f", 4);
//        graph.addEdge("o","a", 4);

        System.out.println(graph.getAdjacencyRepresentationString());


        var path = graph.dijkstra("a", "f");
        System.out.println("Min path: " + path.toString());

        System.out.println(graph.breadthFirstSearch("b"));
        System.out.println(graph.depthFirstSearch("b"));

        System.out.println("Graph contains cycles: " + graph.containsCycles());
    }


    public static class Path<T>
    {
        private T start;
        private List<Edge<T>> edges;

        public Path(T start)
        {
            this.start = start;
            this.edges = null;
        }
        public Path(T start, List<Edge<T>> edges)
        {
            this.start = start;
            this.edges = edges;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder(start.toString());
            for (Edge<T> edge : edges)
            {
                result.append(" -> ")
                        .append(edge.getNode());
            }
            return result.toString();
        }

        public List<Edge<T>> getEdges()
        {
            return edges;
        }

        public T getStart()
        {
            return start;
        }

        public void setEdges(List<Edge<T>> edges)
        {
            this.edges = edges;
        }

        public void setStart(T start)
        {
            this.start = start;
        }
    }
}

