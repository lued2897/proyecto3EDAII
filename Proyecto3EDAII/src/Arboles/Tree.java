package Arboles;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

/**Clase que modela el árbol binario de busqueda
 *<p>Autor: Perez Osorio Luis Eduardo</p>
 * <p>Ultima modificación 04/12/23</p>
 */
public class Tree {
    /**Nodo Raíz del árbol.
     * 
     */
    Node root;
    
    /**Constructor nulo, inicializa como nulo el valor de la raíz
     * 
     */
    public Tree(){
        root = null; 
    }
    
    /**Inicializa la raiz con la clave recibida
     * 
     * @param data clave de la raíz
     */
    public Tree(int data){
        root = new Node(data); 
    }
    
    /**Inserta el nodo recibido en la raiz.
     * 
     * @param node Nodo raíz
     */
    public Tree(Node node){
        root=node;
    }
    
    /**Regresa la altura del nodo recibido
     * 
     * @param node Nodo
     * @return Altura del nodo, 0 si es nulo.
     */
    public int getHeight(Node node){
        if(node==null){
            //return -1;
            return 0 ;
        }
        return node.height;
    }
    
    /**Metodo para obtener el nodo mínimo de un sub-arbol.
     * 
     * @param node nodo raiz.
     * @return nodo mínimo del sub-arbol.
     */
    public Node min(Node node){
        if(node.left == null){
            return node;
        }
        else{
            return min(node.left);
        }
    }
    
    /**Compara la altura de dos nodos y regresa el valor mayor.
     * 
     * @param l Nodo 1 a comparar
     * @param r Nodo 2 a comparar
     * @return El valor maximo de entre las 2 alturas.
     */
    public int maxHeight(Node l,Node r){
        int lh = getHeight(l);
        int rh = getHeight(r);
        if(lh>rh){
            return lh;
        }else{
            return rh;
        }
    }
    
    /**Inserción para el árbol binario de busqueda
     * 
     * @param data Clave a añadir
     */
    public void add(int data){
        if(!search(data)){
            root = add(root,data);
            root.height = maxHeight(root.left,root.right)+1;
        }else{
            System.out.println("NO SE PERMITEN VALORES REPETIDOS");
        }
    }
    
    /**
     * 
     * @param root Nodo raiz (nedo actual)
     * @param data Clave a insertar
     * @return El nodo actualizado despues de la inserción
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
        return root;
        
    }
    
    /**Elimina el nodo mínimo del sub árbol indicado
     * 
     * @param node Nodo raiz
     * @return El nodo actualizado despues de la eliminación
     */
    protected Node removeMin(Node node){
        if(node.left == null){
            return node.right;
        }
        node.left = removeMin(node.left);
        //System.out.println("RmoveMin node.data="+node.data+"  left:"+node.left );
        return node;
    }
    
    /**Método para eliminación en BST
     * 
     * @param data Valor de la clave a eliminar
     */
    public void remove(int data){
        root = remove(root,data);
    }
    
    /**Elimina la clave con el valor indicado
     * 
     * @param node Nodo sobre el cual se hace la eliminación.
     * @param data Clave a eliminar
     * @return El nodo actualizado despues de la eliminación
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
            //System.out.println("min= "+node.data);
            node.right = removeMin(temp.right);
            node.left = temp.left;
            node.height= tempH;
            
        }
        return node;
    }
    
    /**Imprime el contenido y altura de un nodo
     * 
     * @param n nodo a mostrar.
     */
    protected void visit(Node n){
        System.out.println(n.data+","+n.height);
    }
    
    /**Algoritmo de busqueda por expansion, imprime el contenido del arbol.
     * 
     */
    public void breadthFrist(){
        Node r = root;
	Queue<Node> queue = new LinkedList();
	if(r!=null){
            queue.add(r);
            while(!queue.isEmpty()){
                r = (Node)queue.poll();
		visit(r);
		if(r.left!=null)
                    queue.add(r.left);
		if(r.right!=null)
		queue.add(r.right);
            }
	}
    }

    /**Construye un string con una representación grafica del árbol
     * 
     * @param sb Objeto StringBuilder que usará
     * @param padding Espacio entre el borde y la impresion
     * @param pointer Caracter para señalar al nodo
     * @param node Nodo sobre el cual se realiza la impresion.
     */
    private void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.data);  
            sb.append(",");
            sb.append(node.height); 
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
    
    /**Imprime una representacion grafica del arbol en pre-orden.
     * 
     * @param os Stream de salida para la impresión.
     */
    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "->", root); 
        os.print(sb.toString());
    }
    
    /**Busqueda en el árbol binario.
     * 
     * @param data Valor que será buscado.
     * @return True si el valor se encuentra en el árbol, False si no es así.
     */
    public boolean search(int data){
       return search(this.root,data);
    }
   
    /**Metodo para buscar recursivamente en el arbol.
     * 
     * @param node Nodo sobre el cual se hace la busqueda.
     * @param data Valor buscado.
     * @return True si el valor se encuentra en el árbol, False si no es así.
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
    
    /**Clase interna para modelar los nodos del arbol binario.
     * 
     */
    class Node{
        /**Altura del nodo, 0 si es nulo, 1 si es hoja.
         * 
         */
        int height;
        /**Clave almacenada en el nodo.
         * 
         */
        int data;
        /**Nodo hijo izquierdo.
         * 
         */
        Node left;
        /**Nodo hijo derecho.
         * 
         */
        Node right;
        
        /**Crea un nuevo nodo con el valor ingresado
         * 
         * @param data Clave del nodo.
         */
        public Node(int data){
            this(data,1,null,null);
        }
        
        /**Crea un nodo con el valor y altura indicados.
         * 
         * @param data Clave del nodo.
         * @param height Altura del nodo.
         */
        public Node(int data,int height){
            this(data,height,null,null);
        }
        
        /**Inicializa un nuevo nodo con el valor, altura e hijos indicados.
         * 
         * @param data Clave del nodo.
         * @param height Altura del nodo.
         * @param left Hijo izquierdo.
         * @param right  Hijo derecho.
         */
        public Node(int data, int height,Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
            this.height= height;
        }
        
    }
    /**Metodo usado para probar la funcionalidad de los metodos de la clase.
    * 
    * @param args 
    */
    public static void main(String[] args) {
        int values[] = {50,30,20,40,70,60,80,15,25,35,45,55,65,75,85,72,78,71,73};
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
        
        Tree tree = new Tree();
        for(int i: values){
            tree.add(i);
        }
        tree.print(System.out);
        tree.remove(70);
        tree.breadthFrist();
        
        //tree.print(System.out);
    }
}
