package collections;

import elements.Node;

public class Queue<E> {
    private Node<E> head;

    public void enqueue(E data) {
        if (head == null) {
            head = new Node<>(data);
            return;
        }
        Node<E> currentNode = head;
        while (currentNode.next != null) currentNode = currentNode.next;
        currentNode.next = new Node<>(data);
    }

    public E dequeue() {
        if (head == null) throw new IllegalArgumentException("No value in queue");
        E temp = head.data;
        head = head.next;
        return temp;
    }

    public E peek() {
        if (head == null) throw new IllegalArgumentException("No value in queue");
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}