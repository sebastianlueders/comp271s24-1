public class Hash271 {

    /** Default size for foundation array */
    private static final int DEFAULT_SIZE = 4;

    /** Added a default loadFactor threshhold constant value */
    private static final double LOAD_THRESHOLD = 0.75;

    /** Foundation array of node objects */
    Node[] foundation;

    /** Added a loadFactor field to track ratio of nodes to the array's length  */
    private double loadFactor;

    /** Basic constructor */
    public Hash271(int size) {
        this.foundation = new Node[size];
        loadFactor = 0; // Initializes the value of loadFactor to zero (since nodes haven't been added yet)
    } // basic constructor

    /** Default constructor */
    public Hash271() {
        this(DEFAULT_SIZE);
    } // default constructor

    /**
     * Map an integer number to one of the positions of the underlying array. This
     * will come handy we need to find the place to chain a node.
     * 
     * @param value int to map to one of the array positions
     * @return int with the integer division remainder between the input value and
     *         the length of the array
     */
    private int computeArrayPosition(int value) {
        return value % this.foundation.length;
    } // method computeArrayPosition

    /**
     * Chain a node to the underlying array
     * 
     * @param node Node to chain to the underlying array
     */
    public void put(Node node) {
        // Operate only is node is not null
        if (node != null) {
            // Use the node's hashcode to determine is position in
            // the underlying array
            while (resizeNeeded()) {
                rehash();
            }
            int destination = computeArrayPosition(node.hashCode());
            // If the position in the array is occupied by another node,
            // place that node under the new node we wish to insert
            if (this.foundation[destination] != null) {
                node.setNext(this.foundation[destination]);
            }
            // Put the new node to the array position
            this.foundation[destination] = node;
        }
    } // method put

    /**
     * Checks to see if adding a node would put the loadFactor over the default threshold
     * 
     * @return truth value of whether threshold would be surpassed
     */
    private boolean resizeNeeded() {
        return loadFactor + (double) 1/foundation.length >= LOAD_THRESHOLD; //Returns true if adding a node would result in a loadFactor greater than 0.75 (default)
    }


    /**
     * Wrapper for put(Node). Accepts a string, creates a Node object and passes it
     * to the put(Node) method.
     * 
     * @param string String to create a node for, then chain that node to the
     *               underlying array.
     */
    public void put(String string) {
        if (string != null && string.length() > 0) {
            Node node = new Node(string);
            this.put(node);
        }
    } // method put

    /** String representation of this object */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.foundation.length; i++) {
            sb.append(String.format("[ %03d ]: ", i));
            Node current = this.foundation[i];
            while (current != null) {
                sb.append(String.format("<%s> ", current.toString()));
                current = current.getNext();
            }
            sb.append("\n");
        }
        return sb.toString();
    } // method toString

    /*
     * 
     */
    private void rehash() {
        Node[] allNodes = new Node[Math.ceil((int) 0.75 * foundation.length)]; // Creates a new array with enough space for every Node in the original hash
        int iterator = 0;
        int indexTracker = 0;

        while (iterator < foundation.length) {
            if (foundation[iterator] != 0) {
                Node nodeToAdd = foundation[iterator];
                allNodes[indexTracker++] = nodeToAdd;
                while (nodeToAdd.hasNext()) {
                    nodeToAdd = nodeToAdd.getNext();
                    allNodes[indexTracker++] = nodeToAdd;
                }

            }
            iterator++;
        }

        iterator = 0;
        foundation = new Node[foundation.length * 2];

        while (iterator < allNodes.length) {
            this.put(allNodes[iterator++]);
        }
    }

    /** Driver code */
    public static void main(String[] args) {
        Hash271 h = new Hash271();
        h.put(new Node("Java"));
        h.put(new Node("Python"));
        h.put(new Node("Lisp"));
        h.put(new Node("Fortran"));
        h.put(new Node("Prolog"));
        h.put(new Node("Cobol"));
        h.put(new Node("C++"));
        h.put(new Node("C"));
        h.put(new Node("C#"));
        System.out.println(h);
    }
}
