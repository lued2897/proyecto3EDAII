package Arboles;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

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

    Node ultimoNodoPadre = encontrarPadreUltimoNodo(root);

    if (ultimoNodoPadre == null) {
        root = null;
    } else {
        if (ultimoNodoPadre.right != null) {
            root.value = ultimoNodoPadre.left.value;
            ultimoNodoPadre.left = null;
            
        } else {
            root.value = ultimoNodoPadre.right.value;
            ultimoNodoPadre.right = null;
        }
        
    }
    ordenar(root);

    return root;
}

private Node encontrarPadreUltimoNodo(Node node) {
    if (node == null || (node.left == null && node.right == null)) {
        return null;
    }

    if (node.left != null && (node.left.left != null || node.left.right != null)) {
        return encontrarPadreUltimoNodo(node.left);
    } else if (node.right != null && (node.right.left != null || node.right.right != null)) {
        return encontrarPadreUltimoNodo(node.right);
    }

    return node;
}
 private void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.value);
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("?  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "???";
            String pointerForLeft = (node.right != null) ? "???" : "???";

            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);
            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
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

    public static void main(String[] args) {
        Heap heap = new Heap();

        int values[] = {30, 50, 20, 40, 70, 60, 80, 15, 25, 35, 45, 55, 65, 75, 85,105,120,11,2};
        for (int i : values) {
            heap.add(i);
        }

        System.out.println("BFS:");
        heap.breadthFirst();

        System.out.println("Árbol:");
        heap.print(System.out);
        
        heap.preOrden();
        
        System.out.println("Eliminar raiz:");
        heap.eliminarRaiz(); 

        heap.print(System.out);
        heap.preOrden();
    }
}
