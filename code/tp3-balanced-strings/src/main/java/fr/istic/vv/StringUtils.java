package fr.istic.vv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {

    private StringUtils() {}

    public static boolean isBalanced(String str) {
        int len = str.length();
        if(len%2 == 1){
            return false;
        }
        LinkedList<Character> filo = new LinkedList<>();
        for(int i=0;i<len;i++) {
            char current = str.charAt(i);
            if(current == '(' || current == '[' || current == '{'){
                filo.addLast(current);
                continue;
            }
            if(filo.isEmpty()){
                return false;
            }
            Character last = filo.peekLast();
            if((last == '(' && current == ')') ||
                (last == '[' && current == ']') ||
                (last == '{' && current == '}')){
                filo.removeLast();
            }else {
                return false;
            }
        }
        return filo.isEmpty();
    }
}
