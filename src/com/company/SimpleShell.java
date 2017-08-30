/**
 * Universidad de Costa Rica
 * Escuela de Ciencias de la Computación e Informática
 * CI-1310 Sistemas Operativos, Tarea programada 1
 * @author Carlos Alberto Delgado Rojas
 * @email carlos.delgadorojas@ucr.ac.cr
 */

package com.company;
import java.io.*;
import java.util.List;

public class SimpleShell {

    /**
     * Allows the execution of the SimpleShell program
     */
    public void start(){
        String commandLine;
        BufferedReader console = new BufferedReader (new InputStreamReader(System.in));
        Translator tr = new Translator(); //class that provides all functions
        String directory = System.getProperty("user.dir"); //to implement the "cd" command
        Boolean run = true;
        while (run) {
            try{
                // read what the user entered
                System.out.print("jsh>"); commandLine = console.readLine();
                switch (commandLine){
                    case "exit":
                        run = false; //implements the exit command
                    case "cd":
                        directory = System.getProperty("user.dir");//the simplest cd command
                        break;
                    case "":
                        continue;// if the user entered a return, just loop again
                    default:
                        List<String> tokens = tr.divide_command(commandLine); //tokenized input
                        //manage the "cd" command
                        if (tokens.get(0).equals("cd")){
                            if (tokens.get(1).equals("..")){//move to parent dir
                                directory = tr.get_parentDirectory(directory);
                            } else {//change to a new directory
                                if (new File(directory.concat("/".concat(tokens.get(1)))).exists()){
                                    directory = directory.concat("/".concat(tokens.get(1))); //set the current directory
                                } else{
                                    System.out.println("El nombre del directorio es inválido");
                                }
                            }
                            //manage the rest of the possibles commands
                        } else{
                            ProcessBuilder pb = new ProcessBuilder(tokens).directory(new File(directory));
                            Process pr = pb.start();
                            //to printing the output
                            InputStreamReader isr = new InputStreamReader(pr.getInputStream());
                            BufferedReader br = new BufferedReader(isr);
                            String line;
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                            pr.destroy();
                        }
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un problema al ejecutar el comando");
                System.out.println("Para más información consulte:");
                System.out.println(e.getCause());
            }
        }
    }
}

/**
 * Desarrollado con fines académicos.
 * Uso, edición y distribución libres.
 * 2017
 **/