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
            MemoryBlock freeBlock = current.block; // Access the block directly
            if (freeBlock.length >= length) { // If the block is large enough
                int baseAddress = freeBlock.baseAddress;
                MemoryBlock allocatedBlock = new MemoryBlock(baseAddress, length);
                allocatedList.addLast(allocatedBlock);

                if (freeBlock.length == length) {
                    freeList.remove(current); // Exact fit, remove the block
                } else {
                    freeBlock.baseAddress += length; // Update the free block
                    freeBlock.length -= length;
                }
                return baseAddress;
            }
            current = current.next;
        }
        return -1; // No suitable block found
    }

    public void free(int address) {
        Node current = allocatedList.getFirst();
        while (current != null) {
            MemoryBlock allocatedBlock = current.block;
            if (allocatedBlock.baseAddress == address) {
                allocatedList.remove(current);
                freeList.addLast(allocatedBlock);
                return;
            }
            current = current.next;
        }
        throw new IllegalArgumentException("Memory block with the given address not found in allocated list");
    }

    public void defrag() {
        if (freeList.getSize() <= 1) return; // Nothing to defragment

        Node current = freeList.getFirst();
        while (current != null && current.next != null) {
            MemoryBlock currentBlock = current.block;
            MemoryBlock nextBlock = current.next.block;

            // Check if current and next blocks are adjacent
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
