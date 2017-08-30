/**
 * UNIVERSIDAD DE COSTA RICA
 * ESCUELA DE CIENCIAS DE LA COMPUTACION E INFORMATICA
 * TAREA PROGRAMADA #1
 * @author CARLOS DELGADO ROJAS (B52368)
 */

package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleShell {

    public static void main(String[] args) throws java.io.IOException {
        String commandLine;
        BufferedReader console = new BufferedReader (new InputStreamReader(System.in));
        Translator tr = new Translator(); //class that provides all functions
        String directory = System.getProperty("user.dir"); //to implement the "cd" command
        while (true) {
            // read what the user entered
            System.out.print("jsh>"); commandLine = console.readLine();

            if (commandLine.equals("exit")){
                break; //implements the exit command
            } else if(commandLine.equals("cd")){
                directory = System.getProperty("user.dir");//the simplest cd command
            } else if (commandLine.equals("")){
                continue;// if the user entered a return, just loop again
            } else{
                try {
                    List<String> tokens = tr.divide_command(commandLine); //tokenized input
                    //manage the "cd" command
                    if (tokens.get(0).equals("cd")){
                        if (tokens.get(1).equals("..")){//move to parent dir
                            directory = tr.get_parentDirectory(directory);
                        } else {//change to a new directory
                            directory = directory.concat("/".concat(tokens.get(1))); //set the current directory
                        }
                        //manage the rest of the possibles comands
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
                } catch (Exception e) {
                    System.out.println("Ocurrió un problema al ejecutar el comando");
                    System.out.println("Para más información consulte:");
                    System.out.println(e.getCause());
                }
            }
        }
    }
}