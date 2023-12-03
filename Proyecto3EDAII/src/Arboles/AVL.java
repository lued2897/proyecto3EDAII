/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

/**
 *
 * @author lalox
 */
public class AVL extends Tree {
    
    public AVL(){
        super();
    }
    
    @Override
    public void add(int data){
        root = add(root,data,1);
        root.height = maxHeight(root.left,root.right)+1;
        //root = balance(root);
        
    }
    
    private Node add(Node root,int data,int height){
        //System.out.println("a");
        if(root == null){
            //return (new Node(data,height)); //test
            return (new Node(data,1));
        }else if(data < root.data){
            root.left = add(root.left,data,height+1);
            root.height = maxHeight(root.left,root.right)+1; //test
        }else{
            root.right = add(root.right,data,height+1);
            root.height = maxHeight(root.left,root.right)+1; //test
        }
        
        return balance(root,data);
        ////
        ////
        //return root;
        
    }
    
    @Override
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
        
        return balance(node);

        //return node;
    }
   
    private Node leftRotation(Node node){
        Node r = node.right;
        Node rl = r.left;
        
        r.left = node;
        node.right = rl;
        
        node.height = maxHeight(node.left,node.right)+1;
        if(rl != null){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            rl.height = maxHeight(rl.left,rl.right)+1;
        }   
        r.height = maxHeight(r.left,r.right)+1;
        
        return r;
    }
    
    private Node rightRotation(Node node){
        Node l = node.left;
        Node lr = l.right;
        
        l.right = node;
        node.left = lr;
        
        node.height = maxHeight(node.left,node.right)+1;
        if(lr != null){
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            lr.height = maxHeight(lr.left,lr.right)+1;
        }
        l.height = maxHeight(l.left,l.right)+1;
        
        return l;
    }
    
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
    //Matenme
   //Metodo para balancear la insercion
   private Node balance(Node node, int data){
       int balance = getBalance(node);
       //System.out.println("data:"+node.data+"  bal:"+balance);
       //this.print(System.out);
       if(Math.abs(balance) <=1 ){
           return node;
       }
       if(balance > 1 && data < node.left.data){
           System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && data > node.right.data){
           System.out.println("Left Rotation");
           return leftRotation(node);
           
       }else if(balance > 1 && data > node.left.data){
           System.out.println("Left Rotation");
           node.left = leftRotation(node.left);
           System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && data < node.right.data){
           System.out.println("Right Rotation");
           node.right = rightRotation(node.right);
           System.out.println("Left Rotation");
           return leftRotation(node);
            
       }
       return node;
   }
   
   private Node balance(Node node){
       int balance = getBalance(node);
       //System.out.println("data:"+node.data+"  bal:"+balance);
       //this.print(System.out);
       if(Math.abs(balance) <=1 ){
           return node;
       }
       if(balance > 1 && getBalance(node.left) >=0){
           System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && getBalance(node.right) <= 0){
           System.out.println("Left Rotation");
           return leftRotation(node);
           
       }else if(balance > 1 && getBalance(node.left) < 0){
           System.out.println("Left Rotation");
           node.left = leftRotation(node.left);
           System.out.println("Right Rotation");
           return rightRotation(node);
           
       }else if(balance < -1 && getBalance(node.right) > 0){
           System.out.println("Right Rotation");
           node.right = rightRotation(node.right);
           System.out.println("Left Rotation");
           return leftRotation(node);
            
       }
       return node;
   }
   
   public boolean search(int data){
       return search(this.root,data);
   }
   
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
   
   //Balanceo para la eliminacion
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
        tree.print(System.out);
        System.out.println("ñgdsgds");
        System.out.println(tree.search(78));
        System.out.println(tree.search(25));
        
        tree.remove(15);
        tree.remove(25);
        tree.remove(20);
        System.out.println(tree.search(78));
        System.out.println(tree.search(25));
        tree.print(System.out);
    
        
    }
}
