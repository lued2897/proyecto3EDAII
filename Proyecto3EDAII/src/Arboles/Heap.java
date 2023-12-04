package Arboles;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

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

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            addN(value);
            ordenar(root);
        }
    }

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
    /*
        private void add(Node node, int value) {
        if (node.left == null) {
            node.left = new Node(value);
        } else if (node.right == null) {
            node.right = new Node(value);
        } else if (countNodes(node.left) <= countNodes(node.right)) {
            add(node.left, value);
        } else {
            add(node.right, value);
        }
    }*/

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

    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", root);
        os.print(sb.toString());
    }
 
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
    

