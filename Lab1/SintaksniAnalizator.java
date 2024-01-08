
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SintaksniAnalizator {
    //varijable
    static ArrayList<Trojka> ulaz=new ArrayList<>();
    static ArrayList<Produkcija> produkcije=new ArrayList<>();
    public static Stack<elementStoga> stog=new Stack<>();
    static int dubina=0;
    static String pocStanje="<program>";
    static String trenutnoStanje;
    public static String[] string;
    public static String temp;
    public static TreeNode<String> root=new TreeNode<>("<program>",0);
    public static StringBuilder sb=new StringBuilder();
    public static ArrayList<String> tablicaZavrsnihZnakova=new ArrayList<>();


    public static void main(String[] args) throws FileNotFoundException {
        trenutnoStanje=pocStanje;
        ucitajProdukcije();
        ucitajZavrsneZnakove();
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()){
            temp=scanner.nextLine();
            string = temp.split(" ");
            ulaz.add(new Trojka(string[0],Integer.parseInt(string[1]),string[2]));
        }
        ulaz.add(new Trojka("#",0,"#"));
        stog.push(new elementStoga(pocStanje,root));

        for(Trojka trojka:ulaz){
            boolean error=false;
            boolean trojkaFound=false;
            if(trenutnoStanje.equals(trojka.leksickaJedinka)){
                buildString(trojka,stog.peek().node.depth);
                stog.pop();
                trenutnoStanje=stog.peek().naziv;
                continue;
            }
            if(!tablicaZavrsnihZnakova.contains(trenutnoStanje)){
                buildString(trenutnoStanje,stog.peek().node.depth);
            }
            while(!trojkaFound && !(trojka.value.equals("#")&&stog.isEmpty()) && !error) {
                error=false;
                if(trenutnoStanje.equals(trojka.leksickaJedinka)){
                    buildString(trojka,stog.peek().node.depth);
                    stog.pop();
                    trenutnoStanje=stog.peek().naziv;
                    break;
                }
                for (Produkcija prod : produkcije) {
                    if (trenutnoStanje.equals(prod.nezavrsniZnak) && prod.getDoseg().contains(trojka.leksickaJedinka)&&!stog.isEmpty()) {
                        for (String produkcija : prod.produkt) {
                            stog.peek().node.addChild(new TreeNode<>(produkcija, stog.peek().node.depth + 1));
                        }
                        elementStoga el = stog.pop();
                        Collections.reverse(el.node.getChildren());
                        for (TreeNode<String> child : el.node.getChildren()) {
                            stog.push(new elementStoga(child.getData(), child));
                        }
                        Collections.reverse(el.node.getChildren());
                        if(stog.peek().naziv.equals("$")){
                            buildString(stog.peek().naziv,stog.peek().node.depth);

                            stog.pop();
                            if(!stog.isEmpty()) {
                                trenutnoStanje = stog.peek().naziv;
                            }
                            if(!tablicaZavrsnihZnakova.contains(trenutnoStanje)&& !stog.isEmpty()) {
                                buildString(stog.peek().naziv, stog.peek().node.depth);
                            }
                            error=false;
                            break;
                        }
                        else if(tablicaZavrsnihZnakova.contains(stog.peek().naziv)){
                            buildString(trojka,stog.peek().node.depth);

                            stog.pop();
                            if(!stog.isEmpty()) {
                                trenutnoStanje = stog.peek().naziv;
                            }
                            error=false;
                            trojkaFound=true;
                            break;
                        }
                        else{
                            trenutnoStanje=stog.peek().naziv;
                            buildString(trenutnoStanje,stog.peek().node.depth);
                            error=false;
                            break;
                        }

                    } else {
                        error=true;
                    }
                }
            }
            if(error){
                sb.setLength(0);
                sb.append("err").append(" ").append(trojka.leksickaJedinka)
                        .append(" ").append(trojka.redak).append(" ").append(trojka.value);
                System.out.println(sb);
                return;
            }

        }
        if(!stog.isEmpty()){
            sb.setLength(0);
            sb.append("err kraj").append("\n");
        }
        System.out.println(sb);
    }

    public static void ucitajProdukcije() throws FileNotFoundException {
        String str = "<program>=<lista_naredbi>={IDN KR_ZA #}\n" +
                "<lista_naredbi>=<naredba> <lista_naredbi>={IDN KR_ZA}\n" +
                "<lista_naredbi>=$={KR_AZ #}\n" +
                "<naredba>=<naredba_pridruzivanja>={IDN}\n" +
                "<naredba>=<za_petlja>={KR_ZA}\n" +
                "<naredba_pridruzivanja>=IDN OP_PRIDRUZI <E>={IDN}\n" +
                "<za_petlja>=KR_ZA IDN KR_OD <E> KR_DO <E> <lista_naredbi> KR_AZ={KR_ZA}\n" +
                "<E>=<T> <E_lista>={IDN BROJ OP_PLUS OP_MINUS L_ZAGRADA}\n" +
                "<E_lista>=OP_PLUS <E>={OP_PLUS}\n" +
                "<E_lista>=OP_MINUS <E>={OP_MINUS}\n" +
                "<E_lista>=$={IDN KR_ZA KR_DO KR_AZ D_ZAGRADA #}\n" +
                "<T>=<P> <T_lista>={IDN BROJ OP_PLUS OP_MINUS L_ZAGRADA}\n" +
                "<T_lista>=OP_PUTA <T>={OP_PUTA}\n" +
                "<T_lista>=OP_DIJELI <T>={OP_DIJELI}\n" +
                "<T_lista>=$={IDN KR_ZA KR_DO KR_AZ OP_PLUS OP_MINUS D_ZAGRADA #}\n" +
                "<P>=OP_PLUS <P>={OP_PLUS}\n" +
                "<P>=OP_MINUS <P>={OP_MINUS}\n" +
                "<P>=L_ZAGRADA <E> D_ZAGRADA={L_ZAGRADA}\n" +
                "<P>=IDN={IDN}\n" +
                "<P>=BROJ={BROJ}";
        try {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
            String line;
            String[] clanovi;
            String[] produkti;
            String[] doseg;

            while ((line = bufferedReader.readLine()) != null) {
                clanovi = line.split("=");
                produkti = clanovi[1].split(" ");
                clanovi[2] = clanovi[2].replace("{", "").replace("}", "");
                doseg = clanovi[2].split(" ");
                produkcije.add(new Produkcija(clanovi[0],
                        new ArrayList<>(Arrays.stream(produkti).collect(Collectors.toList())),
                        new ArrayList<>(Arrays.stream(doseg).collect(Collectors.toList()))));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void buildString(Trojka trojka, int depth){
        for(int i=0;i<depth;i++){
            sb.append(" ");
        }
        sb.append(trojka.leksickaJedinka).append(" ").append(trojka.redak).append(" ").append(trojka.value).append("\n");

    }
    public static void buildString(String naziv,int depth){
        for(int i=0;i<depth;i++){
            sb.append(" ");
        }
        sb.append(naziv).append("\n");
    }
    public static void ucitajZavrsneZnakove(){
        tablicaZavrsnihZnakova.add("IDN");
        tablicaZavrsnihZnakova.add("BROJ");
        tablicaZavrsnihZnakova.add("OP_PRIDRUZI");
        tablicaZavrsnihZnakova.add("OP_PLUS");
        tablicaZavrsnihZnakova.add("OP_MINUS");
        tablicaZavrsnihZnakova.add("OP_PUTA");
        tablicaZavrsnihZnakova.add("OP_DIJELI");
        tablicaZavrsnihZnakova.add("L_ZAGRADA");
        tablicaZavrsnihZnakova.add("D_ZAGRADA");
        tablicaZavrsnihZnakova.add("KR_ZA");
        tablicaZavrsnihZnakova.add("KR_OD");
        tablicaZavrsnihZnakova.add("KR_DO");
        tablicaZavrsnihZnakova.add("KR_AZ");
    }
}

