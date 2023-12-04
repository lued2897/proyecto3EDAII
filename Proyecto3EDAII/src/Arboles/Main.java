/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author lalox
 */
public class Main {
    public static void main(String[] args) {
        int opt = 0;
        System.out.println("Bienvenido al proyecto de arboles binarios");
        System.out.println("Creado por:"
                + "\n   *Perez Osorio Luis Eduardo"
                + "\n   *Hernández Sánchez Karla "
                + "\n   *Ruiz Cervantes Karla Patricia");
        
        while(opt != -1){
            System.out.println("\n\nSeleccione el arbol que quiere utilizar:");
            System.out.println("Opciones:");
            System.out.println("(0)  Arbol balanceado AVL");
            System.out.println("(1)  Arbol de expresion aritmetica");
            System.out.println("(2)  Heap");
            System.out.println("(-1) Salir");
            opt = readInt();
            switch(opt){
                case 0:
                    System.out.println("\n\n**********Menu de arbol balanceado (AVL)**********");
                    AVL.menu();
                    break;
                    
                case 1:
                    System.out.println("\n\n**********Menu de arbol de expresion aritmetica**********");
                    Aritmetico.menu();
                    break;
                    
                case 2:
                    System.out.println("\n\n**********Menu de Heap**********");
                    Heap.menuHeap();
                    break;
                    
            }
        }
    }
    
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
}
