public class Hash271 {

    /** Default size for foundation array */
    private static final int DEFAULT_SIZE = 4;

    /** Added a default loadFactor threshhold constant value */
    private static final double LOAD_THRESHOLD = 0.75;

    /** Foundation array of node objects */
    Node[] foundation;

    /** Added a loadFactor field to track ratio of nodes to the array's length  */
    private double loadFactor;

    /* Added a nodeCount field to track the number of nodes in the hash & for easy loadFactor calculation */
    private int nodeCount;

    /** Basic constructor */
    public Hash271(int size) {
        this.foundation = new Node[size];
        nodeCount = 0; // Initializes the number of nodes in the hash to zero
        loadFactor = 0.0; // Initializes the value of loadFactor to zero (since nodes haven't been added yet)
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
            
            if (resizeNeeded()) { // resize hash if necessary
                rehash();
            }
            int destination = computeArrayPosition(node.hashCode()); // use node's hashcode to determine its position in the underlying array
            // If the position in the array is occupied by another node,
            // place that node under the new node we wish to insert
            if (this.foundation[destination] != null) {
                node.setNext(this.foundation[destination]);
                
            }
            this.foundation[destination] = node; // Put the new node to the array position
            nodeCount++;
            updateLoadFactor(); // adjusts loadFactor value for each addition
        }
    } // method put

    /**
     * Checks to see if adding a node would put the loadFactor over the default threshold
     * 
     * @return truth value of whether threshold would be surpassed
     */
    private boolean resizeNeeded() {
        return loadFactor >= LOAD_THRESHOLD; //Returns true if loadFactor is greater than 0.75 (default)
    }

    /* Updates load factor with current load factor ratio */
    private void updateLoadFactor() {
        loadFactor = (double) nodeCount / this.foundation.length; // Updates loadFactor with the ratio of current nodes to the length of the foundation array
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
     * Rehashes with an array double the initial size
     */
    private void rehash() {
        Node[] oldFoundation = this.foundation; // Copies original foundation array to a new variable name
        this.foundation = new Node[oldFoundation.length * 2]; // Initialize a variable to track the progression through each array in foundation
        int iterator = 0; // Initializes a variable to iterate through the oldFoundation indexes
        loadFactor = 0.0; // Resets the loadFactor to zero
        nodeCount = 0; // Resets nodeCount to zero

        while (iterator < oldFoundation.length) {
            Node nodeToAdd = oldFoundation[iterator]; // Sets nodeToAdd variable equal to the node value (or null value) of the iterator specified index
            while ( nodeToAdd != null) {
                Node next = nodeToAdd.getNext(); // Declares a next (node) variable equal to the next node in the LL (could be null)
                this.put(nodeToAdd); // Adds node to new foundation array
                nodeToAdd.setNext(null); // Sets the next value of the node just added to null (to avoid confusion with next values)
                nodeToAdd = next; // Assigns the node to add value equal to the next node in the LL (null if nonexistant)
            }
            iterator++; // Move to next index in oldFoundation array
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
