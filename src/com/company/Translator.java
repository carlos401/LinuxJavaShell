/**
 * UNIVERSIDAD DE COSTA RICA
 * ESCUELA DE CIENCIAS DE LA COMPUTACION E INFORMATICA
 * LABORATORIO #3
 * CARLOS DELGADO ROJAS (B52368)
 */

package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Translator {
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
