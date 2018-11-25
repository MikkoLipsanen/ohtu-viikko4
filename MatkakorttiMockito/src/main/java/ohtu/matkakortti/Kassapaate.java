package ohtu.matkakortti;

public class Kassapaate {
    private int myytyjaLounaita;
    public static final int HINTA = 5;
    private Matkakortti kortti;

    public Kassapaate(Matkakortti kortti) {
        this.kortti = kortti;
        this.myytyjaLounaita = 0;
    }
    
    public void lataa(Matkakortti kortti, int summa){
        if(summa > 0){
            kortti.lataa(summa);
        }
    }
    
    public void ostaLounas() {
        if(kortti.getSaldo() >= HINTA){
            kortti.osta(HINTA);
            myytyjaLounaita++;
        }
    }

    public int getMyytyjaLounaita() {
        return myytyjaLounaita;
    }
}
