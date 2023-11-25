/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author lalox
 */
public class Tree {
    Node root;
    
    public Tree(){
        root = null; 
    }
    
    public Tree(int data){
        root = new Node(data); 
    }
    
    public Tree(Node node){
        root=node;
    }
    
    public int getHeight(Node node){
        if(node==null){
            return -1;
        }
        return node.height;
    }
    
    public Node min(Node node){
        if(node.left == null){
            return node;
        }
        else{
            return min(node.left);
        }
    }
    
    public int maxHeight(Node l,Node r){
        int lh = getHeight(l);
        int rh = getHeight(r);
        if(lh>rh){
            return lh;
        }else{
            return rh;
        }
    }
    
    public void add(int data){
        root = add(root,data,0);
        root.height = maxHeight(root.left,root.right)+1;
    }
    
    private Node add(Node root,int data,int height){
        //System.out.println("a");
        if(root == null){
            //return (new Node(data,height)); //test
            return (new Node(data,0));
        }else if(data < root.data){
            root.left = add(root.left,data,height+1);
            root.height = maxHeight(root.left,root.right)+1; //test
        }else{
            root.right = add(root.right,data,height+1);
            root.height = maxHeight(root.left,root.right)+1; //test
        }
        return root;
        
    }
    
    private Node removeMin(Node node){
        if(node.left == null){
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }
    
    public void remove(int data){
        root = remove(root,data);
    }
    
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
        return node;
    }
    
    protected void visit(Node n){
        System.out.println(n.data+","+n.height);
    }
    
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

    
    public void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.data);  
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
    
    
    class Node{
        int height;
        int data;
        Node left;
        Node right;
        
        public Node(int data){
            this(data,0,null,null);
        }
        
        public Node(int data,int height){
            this(data,height,null,null);
        }
        
        public Node(int data, int height,Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
            this.height= height;
        }
        
    }
    
    public static void main(String[] args) {
        int values[] = {50,30,20,40,70,60,80,15,25,35,45,55,65,75,85};
        /*
                    50  
               /         \
              30         70
             /  \       /   \
           20   40     60    80
          /  \  / \   /  \   / \
         15 25 35 45 55  65 75  85
        */
        
        Tree tree = new Tree();
        for(int i: values){
            tree.add(i);
        }
        //tree.remove(30);
        tree.breadthFrist();
        tree.print(System.out);
    }
}
