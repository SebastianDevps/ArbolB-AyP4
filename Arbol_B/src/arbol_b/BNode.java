
package arbol_b;

public class BNode {
    int[] keys;        // Array de claves
    int minDeg;        // Grado mínimo del árbol B
    BNode[] children;  // Array de referencias a los hijos
    int numKeys;       // Número actual de claves
    boolean isLeaf;    // Indica si es un nodo hoja

    public BNode() {
    }
    
    

    public BNode(int deg, boolean isLeaf) {
        this.minDeg = deg;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * minDeg - 1];     // Máximo número de claves
        this.children = new BNode[2 * minDeg];    // Máximo número de hijos
        this.numKeys = 0;
    }

    // Método para mostrar las claves del nodo
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < numKeys; i++) {
            sb.append(keys[i]);
            if (i < numKeys - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

