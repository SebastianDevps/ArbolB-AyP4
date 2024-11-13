
package arbol_b;

import java.util.ArrayList;

public class BTree {
    private BNode root;
    private int minDeg;

    BNode node = new BNode();
    
    public BTree(int deg) {
        this.root = null;
        this.minDeg = deg;
    }

    // Método principal para mostrar el árbol
    public void display() {
        if (root != null) {
            System.out.println("Árbol B (grado mínimo = " + minDeg + "):");
            displayNode(root, 0);
        } else {
            System.out.println("Árbol vacío");
        }
    }

    // Método auxiliar para mostrar un nodo y sus subárboles
    private void displayNode(BNode node, int level) {
        
        // Crear el indentado según el nivel
        String indent = "  ".repeat(level);
        
        // Mostrar el nodo actual
        System.out.println(indent + "Nivel " + level + ": " + node);

        // Si no es hoja, mostrar los hijos
        if (!node.isLeaf) {
            for (int i = 0; i <= node.numKeys; i++) {
                if (node.children[i] != null) {
                    displayNode(node.children[i], level + 1);
                }
            }
        }
    }

    // Método de búsqueda público
    public boolean search(int key) {
        if (root == null) 
            return false;
        return search(root, key);
    }

    // Método de búsqueda privado recursivo
    private boolean search(BNode node, int key) {
        int i = 0;
        // Buscar la primera clave mayor o igual a key
        while (i < node.numKeys && key > node.keys[i])
            i++;

        // Si encontramos la clave
        if (i < node.numKeys && key == node.keys[i])
            return true;

        // Si no encontramos la clave y es un nodo hoja
        if (node.isLeaf)
            return false;

        // Recorrer el subárbol apropiado
        return search(node.children[i], key);
    }

    // Método de inserción público
    public void insert(int key) {
        // Si el árbol está vacío
        if (root == null) {
            root = new BNode(minDeg, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            // Si el nodo raíz está lleno, el árbol crece en altura
            if (root.numKeys == 2 * minDeg - 1) {
                BNode newRoot = new BNode(minDeg, false);
                newRoot.children[0] = root;
                splitChild(newRoot, 0, root);
                
                // Nueva raíz tiene dos hijos. Decidir cuál va a tener la nueva clave
                int i = 0;
                if (newRoot.keys[0] < key)
                    i++;
                insertNonFull(newRoot.children[i], key);
                root = newRoot;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    // Método para dividir el hijo y de node
    private void splitChild(BNode parent, int index, BNode child) {
        BNode newNode = new BNode(child.minDeg, child.isLeaf);
        newNode.numKeys = minDeg - 1;

        // Copiar las últimas (minDeg-1) claves de child a newNode
        for (int j = 0; j < minDeg - 1; j++)
            newNode.keys[j] = child.keys[j + minDeg];

        // Copiar los últimos minDeg hijos de child a newNode
        if (!child.isLeaf) {
            for (int j = 0; j < minDeg; j++)
                newNode.children[j] = child.children[j + minDeg];
        }

        // Reducir el número de claves en child
        child.numKeys = minDeg - 1;

        // Crear espacio para el nuevo hijo en parent
        for (int j = parent.numKeys; j >= index + 1; j--)
            parent.children[j + 1] = parent.children[j];

        // Enlazar el nuevo hijo con parent
        parent.children[index + 1] = newNode;

        // Mover una clave de child a parent
        for (int j = parent.numKeys - 1; j >= index; j--)
            parent.keys[j + 1] = parent.keys[j];

        parent.keys[index] = child.keys[minDeg - 1];
        parent.numKeys++;
    }

    // Insertar en un nodo que no está lleno
    private void insertNonFull(BNode node, int key) {
        int i = node.numKeys - 1;

        if (node.isLeaf) {
            // Encontrar la posición correcta e insertar
            while (i >= 0 && node.keys[i] > key) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            // Encontrar el hijo donde debemos insertar la clave
            while (i >= 0 && node.keys[i] > key)
                i--;
            i++;

            // Ver si el hijo está lleno
            if (node.children[i].numKeys == 2 * minDeg - 1) {
                splitChild(node, i, node.children[i]);
                if (node.keys[i] < key)
                    i++;
            }
            insertNonFull(node.children[i], key);
        }
    }

    // Método auxiliar para obtener la altura del árbol
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BNode node) {
        if (node == null) return 0;
        if (node.isLeaf) return 1;
        return 1 + getHeight(node.children[0]);
    }

    // Método para mostrar el árbol en forma visual
    public void displayTree() {
        System.out.println("\nEstructura visual del árbol B:");
        displayTreeVisual(root, 0, "");
    }

    private void displayTreeVisual(BNode node, int level, String prefix) {
        if (node == null) return;

        System.out.println(prefix + "+-- " + node);

        if (!node.isLeaf) {
            for (int i = 0; i <= node.numKeys; i++) {
                String childPrefix = prefix + "|   ";
                if (i == node.numKeys) {
                    childPrefix = prefix + "    ";
                }
                if (node.children[i] != null) {
                    displayTreeVisual(node.children[i], level + 1, childPrefix);
                }
            }
        }
    }
    
public void deleteAndRebuild(int key, ArrayList<Integer> keys) {
    if (root == null) {
        System.out.println("El árbol está vacío");
        return;
    }

    // Crear un nuevo árbol B
    BTree newTree = new BTree(minDeg);

    
    // Insertar todas las claves menos la que queremos eliminar
    for (int k : keys) {
        if (k != key) {
            newTree.insert(k);
        }
    }

    // Reemplazar la raíz actual con la del nuevo árbol
    this.root = newTree.root;
}


   
}

