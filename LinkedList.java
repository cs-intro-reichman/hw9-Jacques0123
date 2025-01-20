public class LinkedList {

    private Node first; // pointer to the first element of this list
    private Node last;  // pointer to the last element of this list
    private int size;   // number of elements in this list

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    public Node getFirst() {
        return this.first;
    }

    public Node getLast() {
        return this.last;
    }

    public int getSize() {
        return this.size;
    }

    public Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index must be between 0 and size - 1");
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index must be between 0 and size");
        }
        Node newNode = new Node(block);
        if (index == 0) {
            newNode.setNext(first);
            first = newNode;
            if (size == 0) {
                last = newNode;
            }
        } else if (index == size) {
            last.setNext(newNode);
            last = newNode;
        } else {
            Node previous = getNode(index - 1);
            newNode.setNext(previous.getNext());
            previous.setNext(newNode);
        }
        size++;
    }

    public void addLast(MemoryBlock block) {
        add(size, block);
    }

    public void addFirst(MemoryBlock block) {
        add(0, block);
    }

    public MemoryBlock getBlock(int index) {
        return getNode(index).getBlock();
    }

    public int indexOf(MemoryBlock block) {
        Node current = first;
        for (int i = 0; i < size; i++) {
            if (current.getBlock().equals(block)) {
                return i;
            }
            current = current.getNext();
        }
        return -1;
    }

    public void remove(Node node) {
        if (node == null) {
            return;
        }
        if (node == first) {
            first = first.getNext();
            if (first == null) {
                last = null;
            }
        } else {
            Node previous = first;
            while (previous != null && previous.getNext() != node) {
                previous = previous.getNext();
            }
            if (previous != null) {
                previous.setNext(node.getNext());
                if (node == last) {
                    last = previous;
                }
            }
        }
        size--;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index must be between 0 and size - 1");
        }
        Node nodeToRemove = getNode(index);
        remove(nodeToRemove);
    }

    public void remove(MemoryBlock block) {
        Node current = first;
        while (current != null) {
            if (current.getBlock().equals(block)) {
                remove(current);
                return;
            }
            current = current.getNext();
        }
        throw new IllegalArgumentException("MemoryBlock not found in the list");
    }

    public ListIterator iterator() {
        return new ListIterator(first);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = first;
        while (current != null) {
            sb.append(current.getBlock().toString()).append(" -> ");
            current = current.getNext();
        }
        sb.append("null");
        return sb.toString();
    }
}
