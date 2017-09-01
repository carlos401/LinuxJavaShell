/**
 * Universidad de Costa Rica
 * Escuela de Ciencias de la Computación e Informática
 * CI-1310 Sistemas Operativos, Tarea programada 1
 * @author Carlos Alberto Delgado Rojas
 * @email carlos.delgadorojas@ucr.ac.cr
 */

package com.company;
import java.io.*;
import java.util.*;

public class SimpleShell {
    /**
     * Allows the execution of the SimpleShell program
     */
    public void start(){
        String commandLine; //to read console line
        BufferedReader console = new BufferedReader (new InputStreamReader(System.in));

        String directory = System.getProperty("user.dir"); //to implement the "cd" command
        List<String> history = new ArrayList<>();//to manage history feature

        Boolean run = true; //loop control
        while (run) {
            try{
                // read what the user entered
                System.out.print("jsh>"); commandLine = console.readLine();

                //history register handler
                history = update_history(history,commandLine);

                switch (commandLine){
                    case "exit":
                        System.out.println("Cerrando la consola...");
                        run = false; //implements the exit command
                    case "cd":
                        directory = System.getProperty("user.dir");//the simplest cd command
                        break;
                    case "":
                        continue;// if the user entered a return, just loop again
                    case "history":
                        if (!history.isEmpty()){
                            print_history(history); //prints the last 10 commands
                        } else{
                            System.out.println("Usted no introdujo ningún comando previamente");
                        }
                        break;
                    case "!!": //an specialization of history
                        if (!history.isEmpty()){
                            execute_process(directory,divide_command(history.get(history.size()-1)));
                        } else {
                            System.out.println("Usted no introdujo ningún comando previamente");
                        }
                        break;
                    default:
                        //the feature of history !x, tq x is a number of command
                        if (commandLine.charAt(0)=='!'){
                            if (Integer.parseInt(commandLine.substring(1))<=history.size()){
                                execute_process(directory,divide_command(history.get((Integer.parseInt(commandLine.substring(1)))-1)));
                            } else if(history.isEmpty()){
                                System.out.println("Usted no introdujo ningún comando previamente");
                            }
                            else {
                                System.out.println("No se encontró el "+(Integer.parseInt(commandLine.substring(1)))+"-ésimo comando");
                            }
                        } else{
                            List<String> tokens = divide_command(commandLine); //tokenized input

                            //manage the "cd" command with parameters
                            if (tokens.get(0).equals("cd")){
                                if (tokens.get(1).equals("..")){
                                    directory = get_parentDirectory(directory);//move to parent dir
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
                }
            } catch (Exception e) {
                System.out.println("El comando introducido es inválido");
                System.out.println("Para más información consulte el error:");
                System.out.println(e.getCause());
            }
        }
    }

    /**
     * Allows create new processes using ProcessBuilder
     * @param directory where execute the process
     * @param tokens The list of commands and its parameters
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
     * Update the command history handling some exceptions
     * @param history the current list of commands
     * @param commandLine the last command introduced
     * @return an updated list of commands
     */
    private List<String> update_history(List<String> history, String commandLine){
        if (!(commandLine.equals("history")||commandLine.equals("")||
                commandLine.charAt(0)=='!')){
            history.add(commandLine); //save the last command in the history
        }
        return history;
    }

    /**
     * Prints in console the list of commands
     * @param history the commands saved
     */
    private void print_history(List<String> history){
        Iterator<String> ite = history.iterator();
        int i=1;
        while(ite.hasNext()){
            System.out.println(i+"- "+ite.next());//printing in console
            ++i;
        }
    }

    /**
     * Function that divide the users commands into tokens
     * @param comando the user command
     * @return A list that has the tokens
     */
    public List<String> divide_command(String comando){
        StringTokenizer st = new StringTokenizer(comando," ");
        List<String> comands = new LinkedList<>(); //this is the answered List
        while (st.hasMoreElements()){
            comands.add(st.nextToken()); //and adding the parameters
        }
        return comands;
    }

    /**
     * Allows to know the name of the parent directory given a current
     * @param currentDir the name of the current directory
     * @return the name of the parent directory
     */
    public String get_parentDirectory (String currentDir){
        StringTokenizer st = new StringTokenizer(currentDir,"/");
        if (st.countTokens() == 1){
            return currentDir; //do not exist parent directory
        } else{
            String result="";
            while(st.countTokens() > 1){
                result = result.concat("/".concat(st.nextToken()));
            }
            return result;
        }
    }
}