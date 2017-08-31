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
                            execute_process(directory,divide_command(history.get(history.size()-1)));
                        } else {
                            System.out.println("Usted no introdujo ningún comando previamente");
                        }
                        break;
                    default:
                        //the feature of history !x, tq x is a number of command
                        if (commandLine.charAt(0)=='!' && ((commandLine.charAt(1)-'0')<=history.size())){
                            execute_process(directory,divide_command(history.get((commandLine.charAt(1)-'0')-1)));
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
                commandLine.equals("")||commandLine.charAt(0)=='!')){
            history.remove(0); //remove the oldest command saved
            history.add(commandLine); //insert the new command
        } else if (!(commandLine.equals("history")||commandLine.equals("")||
                commandLine.charAt(0)=='!')){
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
        int i=1;
        while(ite.hasNext()){
            System.out.println(i+"- "+ite.next());//printing in console
            ++i;
        }
    }

    /**
     * function that divide the users commands into tokens
     * @param comando the user command
     * @return Vector that has the tokens
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
     *
     * @param currentDir
     * @return
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

// Desarrollado con fines académicos. Uso, edición y distribución libres. 2017