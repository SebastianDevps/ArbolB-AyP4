
package arbol_b;

import java.util.ArrayList;
import java.util.Scanner;

public class Arbol_B {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BTree tree = new BTree(3);
        
        Scanner teclado = new Scanner(System.in);
        
        int n;
        
        System.out.println("Cuantos datos desea ingresar: ");
        n = teclado.nextInt();
        // Insertar algunos valores de ejemplo
        int[] values = new int[n];
        
        for (int i = 0; i < values.length; i++) {
            System.out.print("Ingrese valores: ");
            values[i] = teclado.nextInt();
        }
        
        ArrayList<Integer> valores = new ArrayList<>();
        
        for (int value : values) {
            tree.insert(value);
            valores.add(value);
            System.out.println("\nDespués de insertar " + value + ":");
            tree.display();
            System.out.println("\nVisualización en árbol:");
            tree.displayTree();
            System.out.println("------------------------");
        }
        
        String opt;
        
        do {
            System.out.println("Ingrese el dato que desea eliminar: ");
            int num = teclado.nextInt();
            if (valores.contains(num)) {
                tree.deleteAndRebuild(num, valores);
                valores.remove(Integer.valueOf(num));
            } else {
                System.out.println("El dato no existe en el árbol");
            }

            System.out.print("Desea eliminar otro? s/n: ");
            opt = teclado.next();
            
        } while (opt.equalsIgnoreCase("s"));
        
        tree.displayTree();
        
        
        
        
        
    }
       
    
    
}
