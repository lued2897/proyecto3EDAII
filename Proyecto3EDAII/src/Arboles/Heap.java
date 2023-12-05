package Arboles;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Implementación de un árbol binario que cumple con la propiedad de heapmax.
 * Permite agregar elementos, eliminar la raíz, imprimir el árbol y realizar
 * recorridos en amplitud y en preorden.
 *
 *
 * <p> Autor: Hernández Sánchez Karla</p>
 * <p>Última modificación: 04/12/2023</p>
 */

public class Heap {
    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }
    /**
     * Realiza un recorrido en amplitud (BFS) por el árbol e imprime los nodos.
     */
    public void breadthFirst() {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.value + " ");

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        System.out.println();
    }
    /**
     * Agrega un nuevo valor al heap.
     *
     * @param value El valor que se va a agregar.
     */
    public void add(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            addN(value);
            ordenar(root);
        }
    }
    /**
     * Agrega un nuevo nodo con el valor especificado al árbol utilizando un enfoque de nivel.
     * Se realiza una inserción en el primer nodo disponible en el nivel actual.
     *
     * @param value El valor que se va a agregar al árbol.
     */
    private void addN(int value) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node nodoActual = queue.poll();

            if (nodoActual.left == null) {
                nodoActual.left = new Node(value);
                break; 
            } else {
                queue.add(nodoActual.left);
            }

            if (nodoActual.right == null) {
                nodoActual.right = new Node(value);
                break;
            } else {
                queue.add(nodoActual.right);
            }
        }
    }
    /**
     * Ordena el subárbol con la raíz especificada de acuerdo con la propiedad del heap.
     * Utiliza un enfoque de recorrido en postorden para acomodar el heap.
     *
     * @param node La raíz del subárbol que se va a ordenar.
     */
    public static void ordenar(Node node) {
        if (node != null) {
            ordenar(node.left);
            ordenar(node.right);
            if (node.left != null && node.left.value > node.value) {
                int temp = node.value;
                node.value = node.left.value;
                node.left.value = temp;
                ordenar(node.left);
            }

            if (node.right != null && node.right.value > node.value) {
                int temp = node.value;
                node.value = node.right.value;
                node.right.value = temp;
                ordenar(node.right);
            }           

        }
    }
    /**
     * Elimina la raíz del árbol.
     *
     * @return El nodo raíz después de la eliminación.
     */    
    public Node eliminarRaiz() {
        if (root == null) {
            return null;
        }

        Node padreUltimoNodo = encontrarPadreDelUltimoNodo();

        if (padreUltimoNodo == null) {
            root = null;
        } else {
            if (padreUltimoNodo.right != null) {
                root.value = padreUltimoNodo.right.value;
                padreUltimoNodo.right = null;
                System.out.println(padreUltimoNodo.value);
            } else if (padreUltimoNodo.left != null) {
                root.value = padreUltimoNodo.left.value;
                padreUltimoNodo.left = null;
                System.out.println(padreUltimoNodo.value);
            }
        }
        ordenar(root);

        return root;
    }
    /**
     * Método privado para encontrar el padre del último nodo en el árbol.
     *
     * @return El nodo padre del último nodo.
     */

    private Node encontrarPadreDelUltimoNodo() {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node ultNodo = null;
        Node padreDelUltNodo = null;

        while (!queue.isEmpty()) {
            int s = queue.size();

            for (int i = 0; i < s; i++) {
                Node current = queue.poll();

                if (current.left == null && current.right == null) {
                    ultNodo = current;
                }

                if (current.left != null) {
                    queue.add(current.left);
                    padreDelUltNodo = current;  
                }
                if (current.right != null) {
                    queue.add(current.right);
                    padreDelUltNodo = current;  
                }
            }
        }

        return padreDelUltNodo;
    }
    /**
     * Método privado para imprimir el árbol en formato de árbol visual.
     *
     * @param os Flujo de salida donde se imprime el árbol.
     */
 private void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.value);
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);

        }
    }
    /**
     * Imprime el árbol en formato de árbol visual.
     *
     * @param os Flujo de salida donde se imprime el árbol.
     */
    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", root);
        os.print(sb.toString());
    }
     /**
     * Realiza un recorrido en preorden por el árbol e imprime los nodos.
     */
    public void preOrden() {
        System.out.println("Preorden:");
        preOrden(root);
        System.out.println();
    }

    private void preOrden(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrden(node.left);
            preOrden(node.right);
        }
    }
    /**
     * Método principal que proporciona un menú interactivo para interactuar con el Heap.
     */
    public static void menuHeap() {

        Scanner scanner = new Scanner(System.in);
        Heap heap = new Heap();

        int opcion;
        do {
            System.out.println("\n-------MENÚ HEAP-------:");
            System.out.println("1. Agregar clave");
            System.out.println("2. Eliminar raiz");
            System.out.println("3. Mostrar árbol");
            System.out.println("4. Salir al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese cuántos valores desea agregar: ");
                        int cantidadValores = scanner.nextInt();

                        for (int i = 0; i < cantidadValores; i++) {
                            try {
                                System.out.print("Ingrese el valor " + (i + 1) + ": ");
                                int valor = scanner.nextInt();
                                heap.add(valor);
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Error: Ingrese un valor válido.");
                                scanner.nextLine();
                                i--; 
                            }
                        }

                        System.out.println("Valores agregados correctamente.");
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Error: Ingrese un número válido.");
                        scanner.nextLine();
                    }
                    break;

                case 2:
                    heap.eliminarRaiz();
                    System.out.println("Se eliminó la raiz con éxito");
                    break;
                case 3:
                    System.out.println("Árbol:");
                    heap.print(System.out);
                    
                    System.out.println("Recorrido BFS:");
                    heap.breadthFirst();

                    heap.preOrden();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }
}
    

