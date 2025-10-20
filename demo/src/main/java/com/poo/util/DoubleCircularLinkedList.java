package com.poo.util;
import com.poo.entity.CombatEntity;

/**
 * Implementação de uma lista ligada circular para gerenciar participantes em um combate.
 * Cada nó na lista contém um CombatEntity e um ponteiro para o próximo nó.
 * A lista permite adicionar, remover e iterar sobre os participantes de forma circular.
 * Além disso, a lista é ordenada com base na iniciativa dos participantes.
 */
public class DoubleCircularLinkedList {
    private static class Node {
        CombatEntity data;
        Node next;
        Node previous;

    Node(CombatEntity data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
        public CombatEntity getData() {
            return data;
        }
        public Node getPrevious() {
            return previous;
        }
        public Node getNext() {
            return next;
        }
        public void setNext(Node next) {
            this.next = next;
        }
        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
    private Node head; // Aponta para o primeiro nó da lista
    private Node current; // Aponta para o nó atual na iteração
    private int size; // Contagem de elementos
    public DoubleCircularLinkedList() {
        this.head = null;
        this.current = null;
        this.size = 0;
    }
    /**
     *
     * @param combatEntity Adiciona um novo participante ao final da lista circular.
     * Se a lista estiver vazia, o novo nó se torna a cabeça e aponta para si mesmo.
     * Caso contrário, o novo nó é adicionado após o último nó e aponta de volta para a cabeça.
     */
    public void addParticipants(CombatEntity combatEntity) {
        Node newNode = new Node(combatEntity);
        // Caso de lista vazia
        if (head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrevious(head);
            current = head;
        } else {
            // Caso de lista não vazia
            Node last = head.getPrevious();

            last.setNext(newNode);  // O antigo último aponta para o novo nó
            newNode.setNext(head); // O novo último aponta de volta para o primeiro
            newNode.setPrevious(last); // O novo nó aponta para o antigo último
            head.setPrevious(newNode); // A cabeça aponta para o novo último
        }
        this.size++;
    }
    /**
     * Ordena os participantes na lista com base na iniciativa em ordem decrescente.
     * Utiliza um método de inserção ordenada para construir uma nova lista ordenada.
     * Após a ordenação, a cabeça e o ponteiro atual são atualizados para a nova lista ordenada.
     */
    public void sortParticipantsByInitiative() {
        if (size <= 1) return; // Nada para ordenar

        DoubleCircularLinkedList sortedList = new DoubleCircularLinkedList(); // Cria uma nova lista para os elementos ordenados

        Node temp = head;
        int originalSize = size;
        // Percorre a lista original
        for (int i = 0; i < originalSize; i++) {
            // Insere em ordem na nova lista
            sortedList.insertInOrder(temp.getData());
            // Move para o próximo nó da lista original
            temp = temp.getNext();
        }

            this.head = sortedList.head;
            this.current = sortedList.head;
            this.size = sortedList.size;



    }

    /**
     * Insere um CombatEntity na lista de forma ordenada com base na iniciativa.
     * A lista é mantida em ordem decrescente de iniciativa.
     *
     * @param combatEntity O CombatEntity a ser inserido na lista.
     */
    private void insertInOrder(CombatEntity combatEntity) {
        Node newNode = new Node(combatEntity);
        if (head == null) {
            // Caso de lista vazia
            this.head = newNode;
            newNode.setNext(this.head);
            newNode.setPrevious(this.head);
            this.size = 1;
            return;

        }
        // Caso de inserir antes da cabeça
        if (combatEntity.getInitiative() > this.head.getData().getInitiative()) {
            // encontra o ultimo nó e faz apontar para cabeça
            Node last = this.head.getPrevious();
            Node oldHead = this.head;
            newNode.setNext(oldHead); // Novo nó aponta para a antiga cabeça
            oldHead.setPrevious(newNode); // antiga cabeça aponta para o novo nó como seu anterior

            this.head = newNode; // Novo nó se torna a nova cabeça
            newNode.setPrevious(last);   // Novo nó aponta para o último nó
            last.setNext(this.head);
            this.size++;
            return;
        }
        // Inserir no meio ou no fim
        Node current = this.head;
        do {
            if (combatEntity.getInitiative() > current.getNext().getData().getInitiative() || current.getNext() == head) {
                break;  // Posição encontrada
            }
            current = current.getNext();
        }while (current != head);

    newNode.setNext(current.getNext());
    newNode.setPrevious(current);
    current.getNext().setPrevious(newNode);
    current.setNext(newNode);

        this.size++;
    }
    public CombatEntity nextTurn() {
        if (current == null) return null;
        current = current.getNext();
        return current.getData();
    }
    public void removeParticipant(CombatEntity combatEntity) {
        if (head == null) return; // Lista vazia

        Node currentNode = head;

        // Percorre a lista até encontrar o nó ou voltar para o início
        do {
            if (currentNode.getData().equals(combatEntity)) {
                // Encontrou o nó para remover

                if (size == 1) {
                    // Caso: Último nó
                    head = current = null;
                } else {
                    // Caso: Múltiplos nós

                    if (currentNode == head) {
                        // Remoção da Cabeça
                        Node last = head.getPrevious();
                        head = head.getNext(); // Nova cabeça
                        last.setNext(head);
                        head.setPrevious(last);
                    } else {
                        // Remoção do Meio/Fim
                        //Liga o nó anterior ao próximo do nó atual, removendo-o da lista
                        currentNode.previous.setNext(currentNode.getNext());
                        // Liga o próximo nó ao nó anterior do nó atual
                        currentNode.getNext().setPrevious(currentNode.previous);

                    }

                    // Ajusta o ponteiro 'current' se o nó removido era o que estava agindo
                    if (current == currentNode) {
                        current = currentNode.getNext();
                    }
                }

                size--;
                return; // Encerra após a remoção
            }
            currentNode = currentNode.getNext();
        } while (currentNode != head);
    }
    public boolean isCombatOver() {
        return size <= 1;
    }
}

