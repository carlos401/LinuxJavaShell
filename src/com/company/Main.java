/**
 * Universidad de Costa Rica
 * Escuela de Ciencias de la Computación e Informática
 * CI-1310 Sistemas Operativos, Tarea programada 1
 * @author Carlos Alberto Delgado Rojas
 * @email carlos.delgadorojas@ucr.ac.cr
 */

package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SimpleShell shell = new SimpleShell();
        try{
            shell.start();
        } catch (Exception e){
            //Exception handle is on SimpleShell
        }
    }
}

/**
  * Desarrollado con fines académicos.
  * Uso, edición y distribución libres.
  * 2017
 **/