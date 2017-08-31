/**
 * Universidad de Costa Rica
 * Escuela de Ciencias de la Computación e Informática
 * CI-1310 Sistemas Operativos, Tarea programada 1
 * @author Carlos Alberto Delgado Rojas
 * @email carlos.delgadorojas@ucr.ac.cr
 */

package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleShell {
    /**
     * Allows the execution of the SimpleShell program
     */
    public void start(){
        String commandLine; //to read console line
        BufferedReader console = new BufferedReader (new InputStreamReader(System.in));

        Translator tr = new Translator(); //class that provides some functions
        String directory = System.getProperty("user.dir"); //to implement the "cd" command
        List<String> history = new ArrayList<>(10);//to manage history feature

        Boolean run = true; //loop control
        while (run) {
            try{
                // read what the user entered
                System.out.print("jsh>"); commandLine = console.readLine();

                //history register handler
                history = update_history(history,commandLine);

                switch (commandLine){
                    case "exit":
                        run = false; //implements the exit command
                    case "cd":
                        directory = System.getProperty("user.dir");//the simplest cd command
                        break;
                    case "":
                        continue;// if the user entered a return, just loop again
                    case "history":
                        print_history(history); //prints the last 10 commands
                        break;
                    case "!!": //an specialization of history
                        if (!history.isEmpty()){
                            execute_process(directory,tr.divide_command(history.get(history.size()-1)));
                        } else {
                            System.out.println("Usted no introdujo ningún comando previamente");
                        }
                        break;
                    default:
                        List<String> tokens = tr.divide_command(commandLine); //tokenized input

                        //manage the "cd" command with parameters
                        if (tokens.get(0).equals("cd")){
                            if (tokens.get(1).equals("..")){
                                directory = tr.get_parentDirectory(directory);//move to parent dir
                            } else {//change to a new subdirectory
                                if (new File(directory.concat("/".concat(tokens.get(1)))).exists()){
                                    directory = directory.concat("/".concat(tokens.get(1)));
                                } else{
                                    System.out.println("El nombre del directorio es inválido");
                                }
                            }
                        } else{ //manage the rest of possibles commands
                            execute_process(directory,tokens);
                        }
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un problema al ejecutar el comando");
                System.out.println("Para más información consulte:");
                System.out.println(e.getCause());
            }
        }
    }

    /**
     *
     * @param directory
     * @param tokens
     * @throws Exception
     */
    private void execute_process(String directory, List<String> tokens) throws Exception{
        try{
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
        } catch (Exception e){
            throw e;
        }
    }

    /**
     *
     * @param history
     * @param commandLine
     * @return
     */
    private List<String> update_history(List<String> history, String commandLine){
        if (history.size()==10 && !(commandLine.equals("history")||
                commandLine.equals("")||commandLine.equals("!!"))){
            history.remove(0); //remove the oldest command saved
            history.add(commandLine); //insert the new command
        } else if (!(commandLine.equals("history")||commandLine.equals("")||
                commandLine.equals("!!"))){
            history.add(commandLine); //save the last command in the history
        }
        return history;
    }

    /**
     *
     * @param history
     */
    private void print_history(List<String> history){
        Iterator<String> ite = history.iterator();
        while(ite.hasNext()){
            int i=1;
            System.out.println(i+"- "+ite.next());//printing in console
            ++i;
        }
    }
}

/**
 * Desarrollado con fines académicos.
 * Uso, edición y distribución libres.
 * 2017
 **/