

import java.util.ArrayList;

public class Produkcija {
    String nezavrsniZnak;
    ArrayList<String> produkt;
    ArrayList<String> doseg;
    public Produkcija(String nezavrsniZnak, ArrayList<String> produkt, ArrayList<String> doseg){
        this.doseg=doseg;
        this.nezavrsniZnak=nezavrsniZnak;
        this.produkt=produkt;
    }

    public ArrayList<String> getDoseg() {
        return doseg;
    }

    public ArrayList<String> getProdukt() {
        return produkt;
    }

    public String getNezavrsniZnak() {
        return nezavrsniZnak;
    }

    public void setDoseg(ArrayList<String> doseg) {
        this.doseg = doseg;
    }

    public void setNezavrsniZnak(String nezavrsniZnak) {
        this.nezavrsniZnak = nezavrsniZnak;
    }

    public void setProdukt(ArrayList<String> produkt) {
        this.produkt = produkt;
    }
}
