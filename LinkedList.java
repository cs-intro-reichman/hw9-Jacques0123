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
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next; // Access the `next` field directly
        }
        return current;
    }

    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node newNode = new Node(block);
        if (index == 0) {
            newNode.next = first; // Set the `next` field directly
            first = newNode;
            if (size == 0) {
                last = newNode;
            }
        } else if (index == size) {
            last.next = newNode; // Set the `next` field directly
            last = newNode;
        } else {
            Node previous = getNode(index - 1);
            newNode.next = previous.next; // Set the `next` field directly
            previous.next = newNode;
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
        return getNode(index).block; // Access the `block` field directly
    }

    public int indexOf(MemoryBlock block) {
        Node current = first;
        for (int i = 0; i < size; i++) {
            if (current.block.equals(block)) { // Access the `block` field directly
                return i;
            }
            current = current.next; // Access the `next` field directly
        }
        return -1;
    }

    public void remove(Node node) {
        if (node == null) {
            return;
        }
        if (node == first) {
            first = first.next; // Access the `next` field directly
            if (first == null) {
                last = null;
            }
        } else {
            Node previous = first;
            while (previous != null && previous.next != node) {
                previous = previous.next; // Access the `next` field directly
            }
            if (previous != null) {
                previous.next = node.next; // Access the `next` field directly
                if (node == last) {
                    last = previous;
                }
            }
        }
        size--;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node nodeToRemove = getNode(index);
        remove(nodeToRemove);
    }

    public void remove(MemoryBlock block) {
        Node current = first;
        while (current != null) {
            if (current.block.equals(block)) { // Access the `block` field directly
                remove(current);
                return;
            }
            current = current.next; // Access the `next` field directly
        }
        throw new IllegalArgumentException("index must be between 0 and size");
    }

    public ListIterator iterator() {
        return new ListIterator(first);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = first;
        while (current != null) {
            sb.append(current.block.toString()).append(" -> "); // Access the `block` field directly
            current = current.next; // Access the `next` field directly
        }
        sb.append("null");
        return sb.toString();
    }
}