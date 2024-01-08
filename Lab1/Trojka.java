

public class Trojka {
    String leksickaJedinka;
    int redak;
    String value;
    public Trojka(String leksickaJedinka,int redak,String value){
        this.leksickaJedinka=leksickaJedinka;
        this.redak=redak;
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public int getRedak() {
        return redak;
    }

    public String getLeksickaJedinka() {
        return leksickaJedinka;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLeksickaJedinka(String leksickaJedinka) {
        this.leksickaJedinka = leksickaJedinka;
    }

    public void setRedak(int redak) {
        this.redak = redak;
    }
}
