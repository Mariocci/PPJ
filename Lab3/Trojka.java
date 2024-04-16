

public class Trojka {
    public String leksickaJedinka;
    public int redak;
    public String value;
    public int razina;

    public Trojka(String leksickaJedinka,int redak,String value, int razina){
        this.leksickaJedinka=leksickaJedinka;
        this.redak=redak;
        this.value=value;
        this.razina=razina;
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

    public int getRazina() {
        return razina;
    }

    public void setRazina(int razina) {
        this.razina = razina;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trojka trojka = (Trojka) obj;
        return this.getLeksickaJedinka().equals(trojka.getLeksickaJedinka()) && this.getValue().equals(trojka.getValue()) && this.getRazina()==trojka.getRazina();
    }
    public boolean comp(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trojka trojka = (Trojka) obj;
        return this.getLeksickaJedinka().equals(trojka.getLeksickaJedinka()) && this.getValue().equals(trojka.getValue()) && this.getRazina()<=trojka.getRazina();
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + leksickaJedinka.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + razina;

        return result;
    }

}
