
import java.util.HashMap;
import java.util.Scanner;

public class LeksickiAnalizator {
    public static int line=0;
    public static HashMap<String,String> lexiconTable=new HashMap<>();
    public static String[] string;
    public static String temp;
    public static void main(String[] args) {
        //FILLING TABLE
        lexiconTable.put("IDN","IDN");
        lexiconTable.put("BROJ","BROJ");
        lexiconTable.put("=","OP_PRIDRUZI");
        lexiconTable.put("+","OP_PLUS");
        lexiconTable.put("-","OP_MINUS");
        lexiconTable.put("*","OP_PUTA");
        lexiconTable.put("/","OP_DIJELI");
        lexiconTable.put("(","L_ZAGRADA");
        lexiconTable.put(")","D_ZAGRADA");
        lexiconTable.put("za","KR_ZA");
        lexiconTable.put("od","KR_OD");
        lexiconTable.put("do","KR_DO");
        lexiconTable.put("az","KR_AZ");

        //Read input
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()){
            line++;
            temp=scanner.nextLine();
            string = temp.split("[ \t]+");
            processLine();
        }
    }

    //process the whole line
    public static void processLine(){
        int counter=0;
        while(counter<string.length){
            if(lexiconTable.containsKey(string[counter])){
                print(string[counter++]);
                continue;
            }

            char[] chars=string[counter].toCharArray();
            int newCounter=0;
            while(newCounter<chars.length) {
                if(newCounter+1<chars.length) {
                    if ((chars[newCounter] == '/' && chars[newCounter + 1] == '/')&&temp.contains("//")) {
                        return;
                    }
                }
                while (newCounter<chars.length && lexiconTable.containsKey(String.valueOf(chars[newCounter]))) {
                    print(String.valueOf(chars[newCounter++]));
                }

                if (newCounter<chars.length && 48 <= chars[newCounter] && chars[newCounter] <= 57) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(chars[newCounter++]);
                    while (newCounter<chars.length && ('0' <= chars[newCounter] && chars[newCounter] <= '9')) {
                        sb.append(chars[newCounter++]);
                    }
                    print(sb.toString(), "BROJ");
                } else if(newCounter<chars.length && ('a' <= chars[newCounter] && chars[newCounter] <= 'z'
                        || 'A' <= chars[newCounter] && chars[newCounter] <= 'Z')){
                    StringBuilder sb = new StringBuilder();
                    sb.append(chars[newCounter++]);
                    while (newCounter<chars.length && (('a' <= chars[newCounter] && chars[newCounter] <= 'z'
                            || 'A' <= chars[newCounter] && chars[newCounter] <= 'Z') || ('0' <= chars[newCounter] && chars[newCounter] <= '9'))){
                        sb.append(chars[newCounter++]);
                    }
                    print(sb.toString(), "IDN");
                }else if(newCounter+1<chars.length) {
                    if ((chars[newCounter] == '/' && chars[newCounter + 1] == '/')&&temp.contains("//")) {
                        return;
                    }
                } else{
                    continue;
                }
            }
            counter++;
        }
    }

    //print functions
    public static void print(String string){
        StringBuilder sb=new StringBuilder();
        sb.append(lexiconTable.get(string)).append(" ").append(line).append(" ").append(string);
        System.out.println(sb);
        sb.setLength(0);
    }
    public static void print(String string,String type){
        StringBuilder sb=new StringBuilder();
        sb.append(lexiconTable.get(type)).append(" ").append(line).append(" ").append(string);
        System.out.println(sb);
        sb.setLength(0);
    }

}
