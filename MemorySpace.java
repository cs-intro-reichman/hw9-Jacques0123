public class MemorySpace {

    // A list of the memory blocks that are presently allocated
    private LinkedList allocatedList;

    // A list of memory blocks that are presently free
    private LinkedList freeList;

    public MemorySpace(int maxSize) {
        // Initializes an empty list of allocated blocks.
        allocatedList = new LinkedList();
        // Initializes a free list containing a single block which represents
        // the entire memory. The base address of this single initial block is
        // zero, and its length is the given memory size.
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    public int malloc(int length) {
        ListIterator iterator = freeList.iterator();
        while (iterator.hasNext()) {
            Node currentNode = iterator.next();
            MemoryBlock freeBlock = currentNode.getBlock();
            if (freeBlock.getLength() >= length) {
                int baseAddress = freeBlock.getBaseAddress();
                MemoryBlock allocatedBlock = new MemoryBlock(baseAddress, length);
                allocatedList.addLast(allocatedBlock);

                if (freeBlock.getLength() == length) {
                    freeList.remove(currentNode);
                } else {
                    freeBlock.setBaseAddress(baseAddress + length);
                    freeBlock.setLength(freeBlock.getLength() - length);
                }
                return baseAddress;
            }
        }
        return -1; // No suitable block found
    }

    public void free(int address) {
        ListIterator iterator = allocatedList.iterator();
        while (iterator.hasNext()) {
            Node currentNode = iterator.next();
            MemoryBlock allocatedBlock = currentNode.getBlock();
            if (allocatedBlock.getBaseAddress() == address) {
                allocatedList.remove(currentNode);
                freeList.addLast(allocatedBlock);
                return;
            }
        }
        throw new IllegalArgumentException("Memory block with the given address not found in allocated list");
    }

    public String toString() {
        return "Free List:\n" + freeList.toString() + "\nAllocated List:\n" + allocatedList.toString();
    }

    public void defrag() {
        freeList = mergeAdjacentBlocks(freeList);
    }

    private LinkedList mergeAdjacentBlocks(LinkedList list) {
        if (list.getSize() <= 1) {
            return list;
        }

        LinkedList mergedList = new LinkedList();
        ListIterator iterator = list.iterator();

        Node currentNode = iterator.next();
        MemoryBlock currentBlock = currentNode.getBlock();

        while (iterator.hasNext()) {
            Node nextNode = iterator.next();
            MemoryBlock nextBlock = nextNode.getBlock();

            if (currentBlock.getBaseAddress() + currentBlock.getLength() == nextBlock.getBaseAddress()) {
                // Merge adjacent blocks
                currentBlock.setLength(currentBlock.getLength() + nextBlock.getLength());
            } else {
                mergedList.addLast(new MemoryBlock(currentBlock.getBaseAddress(), currentBlock.getLength()));
                currentBlock = nextBlock;
            }
        }
        // Add the last block
        mergedList.addLast(new MemoryBlock(currentBlock.getBaseAddress(), currentBlock.getLength()));

        return mergedList;
    }
}
