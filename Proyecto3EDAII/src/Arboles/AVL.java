package Arboles;

import java.util.InputMismatchException;
import java.util.Scanner;

/**Implementación de un árbol binario balanceado AVL
 * <p>Autor: Perez Osorio Luis Eduardo</p>
 * <p>Ultima modificación 04/12/23</p>
 */
public class AVL extends Tree {
    
    public AVL(){
        super();
    }
    
    /**Insercion balanceada
     * 
     * @param data Valor a insertar en el arbol. No puede ser un valor repetido.
     */
    @Override
    public void add(int data){
        if(!search(data)){
            root = add(root,data);
            root.height = maxHeight(root.left,root.right)+1;
        }else{
            System.out.println("NO SE PERMITEN VALORES REPETIDOS");
        }
        
        //root = balance(root);
        
    }
    
    /**Inserción balanceada
     * 
     * @param root Nodo sobre el cual se hace la insercion.
     * @param data Clave a insertar
     * @return nodo actualizado y balanceado despues de la insercion
     */
    private Node add(Node root,int data){
        //System.out.println("a");
        if(root == null){
            //return (new Node(data,height)); //test
            return (new Node(data,1)); 
        }else if(data < root.data){
            root.left = add(root.left,data);
            root.height = maxHeight(root.left,root.right)+1; //test
        }else{
            root.right = add(root.right,data);
            root.height = maxHeight(root.left,root.right)+1; //test
        }
        
        return balance(root,data);
        ////
        ////
        //return root;
        
    }
    
    /** Eliminación balanceada.
     * 
     * @param data valor a eliminar del arbol
     */
    @Override
    public void remove(int data){
        root = remove(root,data);
        
    }
    
    /**Eliminación balanceada
     * 
     * @param node Nodo sobre el cual se hace la eliminación.
     * @param data Clave a eliminar.
     * @return Nodo actualizado y balanceado despues de la eliminación.
     */
    private Node remove(Node node,int data){
        if(node == null){
            return null;
        }
        else if(data<node.data){
            node.left = remove(node.left,data);
        }else if(data>node.data){
            node.right = remove(node.right,data);
        }else{
            if(node.right==null){
                return node.left;
            }else if(node.left == null){
                return node.right;
            }
            Node temp = node;
            int tempH= node.height; //WIP
            node = min(temp.right);
            node.right = removeMin(temp.right);
            node.left = temp.left;
            node.height= tempH;
            
        }
        
        return balance(node);

        //return node;
    }
   
    /**Rotación a la izquierda.
     * <p>El hijo derecho del sub-árbol desbalanceado se cambia a la raíz y se conecta el hijo izquierdo de este al nodo original</p>
     * @param node Nodo pivote / raíz del sub-árbol desbalanceado.
     * @return Raíz despues de la rotación.
     */
    private Node leftRotation(Node node){
        Node r = node.right;
        Node rl = r.left;
        
        r.left = node;
        node.right = rl;
        
        node.height = maxHeight(node.left,node.right)+1;
        if(rl != null){
            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            rl.height = maxHeight(rl.left,rl.right)+1;
        }   
        r.height = maxHeight(r.left,r.right)+1;
        
        return r;
    }
    
    /**Rotación a la derecha.
     * <p>El hijo izquierdo del sub-árbol desbalanceado se cambia a la raíz y se conecta el hijo derecho de este al nodo original</p>
     * @param node Nodo pivote / raíz del sub-árbol desbalanceado.
     * @return Raíz despues de la rotación.
     */
    private Node rightRotation(Node node){
        Node l = node.left;
        Node lr = l.right;
        
        l.right = node;
        node.left = lr;
        
        node.height = maxHeight(node.left,node.right)+1;
        if(lr != null){
            //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            lr.height = maxHeight(lr.left,lr.right)+1;
        }
        l.height = maxHeight(l.left,l.right)+1;
        
        return l;
    }
    
    /** Obtiene el factor de balance de un nodo con la diferencia de la altura de los nodos izquierdo y derecho.
     * 
     * @param node Nodo
     * @return Factor de balance del nodo recibido.
     */
    private int getBalance(Node node){
       if(node == null){
           return 0;
       }else{
           //System.out.println("leftH:"+getHeight(node.left)+"  rightH:"+getHeight(node.right));
           int buffer = getHeight(node.left);
           int l =( buffer != -1 )?(buffer):(0);
           buffer = getHeight(node.right);
           int r =( buffer != -1 )?(buffer):(0);
           
           return (l-r);
            //return getHeight(node.left)-getHeight(node.right);
       }
   }
    
    
    /**Método para balancear la inserción
     * <p>Utiliza rotaciones para rebalancear el árbol despues de una inserción.</p>
     * @param node Nodo a balancear.
     * @param data Valor ingresado en la inserción.
     * @return Nodo balanceado.
     */
    private Node balance(Node node, int data){
       int balance = getBalance(node);
       //System.out.println("data:"+node.data+"  bal:"+balance);
       //this.print(System.out);
       if(Math.abs(balance) <=1 ){
           return node;
       }
       if(balance > 1 && data < node.left.data){
           //System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && data > node.right.data){
           //System.out.println("Left Rotation");
           return leftRotation(node);
           
       }else if(balance > 1 && data > node.left.data){
           //System.out.println("Left Rotation");
           node.left = leftRotation(node.left);
           //System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && data < node.right.data){
           //System.out.println("Right Rotation");
           node.right = rightRotation(node.right);
           //System.out.println("Left Rotation");
           return leftRotation(node);
            
       }
       return node;
   }
   
    /**Método para balancear la eliminacion
     * <p>Utiliza rotaciones para rebalancear el árbol despues de una eliminacion.</p>
     * @param node Nodo a balancear..
     * @return Nodo balanceado.
     */
    private Node balance(Node node){
       int balance = getBalance(node);
       //System.out.println("data:"+node.data+"  bal:"+balance);
       //this.print(System.out);
       if(Math.abs(balance) <=1 ){
           return node;
       }
       if(balance > 1 && getBalance(node.left) >=0){
           //System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && getBalance(node.right) <= 0){
           //System.out.println("Left Rotation");
           return leftRotation(node);
           
       }else if(balance > 1 && getBalance(node.left) < 0){
           //System.out.println("Left Rotation");
           node.left = leftRotation(node.left);
           //System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && getBalance(node.right) > 0){
           //System.out.println("Right Rotation");
           node.right = rightRotation(node.right);
           //System.out.println("Left Rotation");
           return leftRotation(node);
            
       }
       return node;
   }
   
    /**Busqueda en BST.
     * 
     * @param data Valor buscado.
     * @return true si encuentra el valor, false si no se encuentra.
     */
    public boolean search(int data){
       return search(this.root,data);
    }
   
    /**Busqueda en BST.
     * 
     * @param node Nodo sobre el cual se realiza la busqueda.
     * @param data Valor buscado.
     * @return true si encuentra el valor, false si no se encuentra.
     */
    private boolean search(Node node,int data){
       if(node == null){
            return false;
        }else if(data == node.data){
            return true;
        }else if(data < node.data){
            return search(node.left,data);
            //root.height = maxHeight(root.left,root.right)+1; //test
        }else{
            return search(node.right,data);
            //root.height = maxHeight(root.left,root.right)+1; //test
        }
    }
   
    /**Menu de usuario para crear un árbol balanceado ademas de ingresar y eliminar valores de este.
     * 
     */
    public static void menu(){
        AVL tree = new AVL();
        int opt=0;
        int input;
        while(opt !=-1){
            opciones();
            System.out.print("Seleccione una opcion: ");
            opt = readInt();
            System.out.println();
            
            switch(opt){
                case 0:
                    opciones();
                    break;
                    
                case 1:
                    System.out.print("Ingrese el valor de la nueva clave: ");
                    input = readInt();
                    System.out.println();
                    tree.add(input);
                    break;
                
                case 2:
                    System.out.print("Ingrese el valor de la clave a eliminar: ");
                    input = readInt();
                    System.out.println();
                    tree.remove(input);
                    break;    
                
                case 3:
                    System.out.print("Ingrese el valor de la clave: ");
                    input = readInt();
                    System.out.println();
                    if(tree.search(input)){
                        System.out.println(input+" Se encuentar en el arbol");
                    }else{
                        System.out.println(input+" NO se encuentra en el arbol");
                    }
                    break; 
                    
                case 4:
                    System.out.println("Arbol AVL:\n");
                    tree.print(System.out);
                    break;
                    
                case 5:
                    tree = new AVL();
                    break;
            }
        }
    }
    
    /**Opciones del menu de usuario.
     * 
     */
    private static void opciones(){
        System.out.println("(0) Mostrar opciones");
        System.out.println("(1) Agregar clave");
        System.out.println("(2) Eliminar clave");
        System.out.println("(3) Buscar clave");
        System.out.println("(4) Mostrar arbol");
        System.out.println("(5) Nuevo arbol");
        System.out.println("(-1) Salir");
    }
    
    /**Método para asegurar que los datos ingresados sean enteros.
     * 
     * @return valor entero ingresado.
     */
    private static int readInt(){
        int val;
        while(true){
            try{
                Scanner scan = new Scanner(System.in);
                val = scan.nextInt();
                return val;
            }catch(InputMismatchException e){
                //System.out.println("e.getMessage;");
                System.out.println("Solo se permiten enteros");
            }    
        } 
    }
    
    
    /**Método para probar la funcionalidad del arbol AVL.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        int values[] = {50,30,20,40,70,60,80,15,25,35,45,55,65,75,85,72,78,71,73};
        //int values[]={50,30,20};
        /*
                    50  
               /         \
              30         70
             /  \       /   \
           20   40     60    80
          /  \  / \   /  \   / \
         15 25 35 45 55  65 75  85
                            /\
                           72 78
                          / \
                         71  73
   
        */
        
        AVL tree = new AVL();
        for(int i: values){
            tree.add(i);
            System.out.print("added "+i+"\n");
            tree.print(System.out);
            System.out.print("\n\n\n");
        }
        //tree.remove(30);
        tree.breadthFrist();
        
        System.out.println("Arbol final");
        tree.print(System.out);
        
        //System.out.println("Arbol final");
        //System.out.println(tree.search(78));
        //System.out.println(tree.search(25));
        
        
        System.out.println("Eliminacion de 15,25,20");
        tree.remove(15);
        tree.remove(25);
        tree.remove(20);
        //System.out.println(tree.search(78));
        //System.out.println(tree.search(25));
        tree.print(System.out);
        
        menu();
    
        
    }
}
