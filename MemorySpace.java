public class MemorySpace {

    private LinkedList allocatedList;
    private LinkedList freeList;

    public MemorySpace(int maxSize) {
        allocatedList = new LinkedList();
        freeList = new LinkedList();
        // Initialize freeList with a single memory block spanning the whole memory space
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    public int malloc(int length) {
        Node current = freeList.getFirst();
        while (current != null) {
            MemoryBlock freeBlock = current.block; // Directly access the `block` field
            if (freeBlock.length >= length) {     // Directly access the `length` field
                int baseAddress = freeBlock.baseAddress; // Directly access the `baseAddress` field
                MemoryBlock allocatedBlock = new MemoryBlock(baseAddress, length);
                allocatedList.addLast(allocatedBlock);

                if (freeBlock.length == length) {
                    // Exact fit: Remove the free block
                    freeList.remove(current);
                } else {
                    // Update the free block
                    freeBlock.baseAddress += length;
                    freeBlock.length -= length;
                }
                return baseAddress;
            }
            current = current.next; // Access the `next` field directly
        }
        return -1; // No suitable block found
    }

    public void free(int address) {
        Node current = allocatedList.getFirst();
        while (current != null) {
            MemoryBlock allocatedBlock = current.block; // Directly access the `block` field
            if (allocatedBlock.baseAddress == address) { // Directly access the `baseAddress` field
                allocatedList.remove(current);
                freeList.addLast(allocatedBlock);
                return;
            }
            current = current.next; // Access the `next` field directly
        }
        throw new IllegalArgumentException("Memory block with the given address not found in allocated list");
    }

    public void defrag() {
        if (freeList.getSize() <= 1) {
            return; // Nothing to defragment
        }

        Node current = freeList.getFirst();
        while (current != null && current.next != null) {
            MemoryBlock currentBlock = current.block;
            MemoryBlock nextBlock = current.next.block;

            // Check if current block and next block are adjacent
            if (currentBlock.baseAddress + currentBlock.length == nextBlock.baseAddress) {
                // Merge the blocks
                currentBlock.length += nextBlock.length;
                freeList.remove(current.next);
            } else {
                current = current.next;
            }
        }
    }

    public String toString() {
        return "Free List:\n" + freeList + "\nAllocated List:\n" + allocatedList;
    }
}