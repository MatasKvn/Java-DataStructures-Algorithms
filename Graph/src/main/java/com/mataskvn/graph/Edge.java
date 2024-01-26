package com.mataskvn.graph;

public class Edge<T>
{
    private T vertex;

    private int weight;

    Edge(T node, int weight)
    {
        this.vertex = node;
        this.weight = weight;
    }

    public T getVertex() { return vertex; }
    public int getWeight() { return weight; }

    @Override
    public String toString()
    {
        return "(" + vertex + ", " + weight + ")";
    }
}
