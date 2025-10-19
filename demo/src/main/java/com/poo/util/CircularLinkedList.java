package com.poo.util;
import com.poo.entity.CombatEntity;

public class CircularLinkedList {
    private class Node {
        CombatEntity data;
        Node next;

        Node(CombatEntity data) {
            this.data = data;
            this.next = null;
        }
        public CombatEntity getData() {
            return data;
        }
        public Node getNext() {
            return next;
        }
        public void setNext(Node next) {
            this.next = next;
        }
    }
    private Node head; // Aponta para o primeiro nó da lista
    private Node current; // Aponta para o nó atual na iteração
    private int size; // Contagem de elementos
    public CircularLinkedList() {
        this.head = null;
        this.current = null;
        this.size = 0;
    }
    public void addParticipants(CombatEntity combatEntity) {
        Node newNode = new Node(combatEntity);
        if (head == null) {
            // Se a lista está vazia, o novo nó é a cabeça e aponta para si mesmo.
            head = newNode;
            newNode.setNext(head);
            current = head;
        } else {
            // Encontra o último nó (o que aponta para 'head')
            Node last = head;
            while (last.getNext() != head) {
                last = last.getNext();
            }

            last.setNext(newNode);  // O antigo último aponta para o novo nó
            newNode.setNext(head); // O novo último aponta de volta para o primeiro (círculo)
        }
        this.size++;
    }
}
