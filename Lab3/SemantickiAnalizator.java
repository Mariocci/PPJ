
import java.util.*;
import java.util.stream.Collectors;

public class SemantickiAnalizator {
    static int razina = 0;
    static String[] tempStr;
    static ArrayList<String> ulaz = new ArrayList<>();
    static ArrayList<Trojka> varijable = new ArrayList<>();
    static Trojka tempTrojka = null;

    public static void main(String[] args) {
        String temp;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            temp = scanner.nextLine();
            ulaz.add(temp);
        }
        boolean pridruzNar = false;
        boolean za = false;
        boolean lijevo = false;
        boolean inZa = false;
        boolean od = false;
        boolean dos = false;

        for (String string : ulaz) {
            String strings = string.trim();
            tempStr = strings.split(" ");
            if (lijevo && tempStr[0].equals("<lista_naredbi>")) {
                lijevo = false;
            }
            if (tempStr[0].equals("<naredba_pridruzivanja>") || (tempStr[0].equals("<naredba_pridruzivanja>") && inZa)) {
                pridruzNar = true;
                continue;
            }
            if (tempStr[0].equals("KR_ZA")) {
                inZa = false;
                od = false;
                dos = false;
                za = true;
                razina++;
                continue;
            }
            if (za && od && !dos && !inZa && tempStr[0].equals("KR_DO")) {
                dos = true;
                continue;
            }
            if (za && od && dos && !inZa && tempStr[0].equals("<lista_naredbi>")) {
                inZa = true;
                tempTrojka = null;
                continue;
            }
            if (tempStr[0].equals("IDN") && pridruzNar && !lijevo) {
                if (!containsTrojka(varijable, new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina))
                    /*&& checkDefinition(ulaz.indexOf(string))*/) {
                    tempTrojka = new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina);
                    varijable.add(tempTrojka);
                }
            } else if (tempStr[0].equals("IDN") && za && !od && !inZa) {
                if (!containsTrojka(varijable, new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina))) {
                    tempTrojka = new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina);
                    varijable.add(tempTrojka);
                }
            } else if (tempStr[0].equals("KR_OD") && za && !od && !inZa) {
                od = true;
                continue;
            }
            if (!lijevo && tempStr[0].equals("OP_PRIDRUZI")) {
                lijevo = true;
                continue;
            }
            if (tempStr[0].equals("IDN") && pridruzNar && lijevo) {
                if (!containsTrojka(varijable, new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina))
                        || (tempTrojka != null && tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)))) {
                    List<Trojka> filteredTrojkaList = varijable.stream()
                            .filter(trojka ->
                                    trojka.getLeksickaJedinka().equals(tempStr[0]) &&
                                            trojka.getValue().equals(tempStr[2]) &&
                                            trojka.getRazina() <= razina)
                            .collect(Collectors.toList());;

                    if (filteredTrojkaList.isEmpty() || (tempTrojka != null && tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)))) {
                        printError(tempStr);
                        break;
                    } else {
                        printUse(tempStr);
                    }
                } else {
                    printUse(tempStr);
                }
                continue;
            }
            if (pridruzNar && tempStr[0].equals("<naredba>")) {
                pridruzNar = false;
                lijevo = false;
                tempTrojka = null;
                continue;
            } else if (za && od && !inZa && !dos && tempStr[0].equals("IDN")) {
                if (!containsTrojka(varijable, new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina))
                        || (tempTrojka != null && tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)))) {
                    List<Trojka> filteredTrojkaList = varijable.stream()
                            .filter(trojka ->
                                    trojka.getLeksickaJedinka().equals(tempStr[0]) &&
                                            trojka.getValue().equals(tempStr[2]) &&
                                            trojka.getRazina() <= razina)
                            .collect(Collectors.toList());

                    if (filteredTrojkaList.isEmpty() || (tempTrojka != null && tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)))) {
                        printError(tempStr);
                        break;
                    } else {
                        printUse(tempStr);
                    }
                } else {
                    printUse(tempStr);
                }
                continue;
            } else if (za && od && dos && !inZa && tempStr[0].equals("IDN")) {
                if (!containsTrojka(varijable, new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)) || (tempTrojka != null && tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina)))) {
                    List<Trojka> filteredTrojkaList = varijable.stream()
                            .filter(trojka ->
                                    trojka.getLeksickaJedinka().equals(tempStr[0]) &&
                                            trojka.getValue().equals(tempStr[2]) &&
                                            trojka.getRazina() <= razina)
                            .collect(Collectors.toList());  // Replace .toList() with .collect(Collectors.toList())

                    if (filteredTrojkaList.isEmpty() || tempTrojka.equals(new Trojka("IDN", Integer.parseInt(tempStr[1]), tempStr[2], razina))) {
                        printError(tempStr);
                        break;
                    } else {
                        printUse(tempStr);
                    }
                } else {
                    printUse(tempStr);
                }
                continue;
            }
            if (za && od && dos && inZa && tempStr[0].equals("KR_AZ")) {
                za = false;
                od = false;
                dos = false;
                inZa = false;
                razina--;
                varijable.removeIf(trojka -> trojka.getRazina() > razina);
            }
        }
    }

    static void printError(String[] strings) {
        StringBuilder sb = new StringBuilder();
        sb.append("err ").append(strings[1]).append(" ").append(strings[2]);
        System.out.println(sb);
    }

    static void printUse(String[] strings) {
        StringBuilder sb = new StringBuilder();
        int redak = findMaxRazina(strings[2], razina);
        sb.append(strings[1]).append(" ").append(redak).append(" ").append(strings[2]);
        System.out.println(sb);
    }

    static int findMaxRazina(String value, int currentRazina) {
        int maxRedak = 0;
        for (Trojka trojka : varijable) {
            if (trojka.getValue().equals(value) && trojka.getRazina() <= currentRazina && !trojka.equals(tempTrojka)) {
                maxRedak = Math.max(maxRedak, trojka.getRedak());
            }
        }
        return maxRedak;
    }

    static boolean containsTrojka(List<Trojka> trojkaList, Trojka trojka) {
        for (Trojka t : trojkaList) {
            if (t.equals(trojka)) {
                return true;
            }
        }
        return false;
    }
}
