package com.mataskvn.graph;

public class Edge<T>
{
    private T node;

    private int weight;

    Edge(T node, int weight)
    {
        this.node = node;
        this.weight = weight;
    }

    public T getNode() { return node; }
    public int getWeight() { return weight; }

    @Override
    public String toString()
    {
        return "(" + node + ", " + weight + ")";
    }
}
