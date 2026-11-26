package se.fjax26.maskinpark;

public class Valt extends Anlaggningsmaskin {

    private double viktITon;
    private double trummBreddMeter;
    private boolean vibrationsLägeAktivt;

    private static final double BRANSLE_VIBRATION = 16.5;
    private static final double BRANSLE_UTAN_VIBRATION = 11.0;

    public Valt(String id, String modell, double timKostnad, double viktITon, double trummBreddMeter) {
        super(id, modell, timKostnad);
        this.viktITon = viktITon;
        this.trummBreddMeter = trummBreddMeter;
        this.vibrationsLägeAktivt = false;
    }

    public void aktiveraVibration() {
        this.vibrationsLägeAktivt = true;
    }

    public void inaktiveraVibration() {
        this.vibrationsLägeAktivt = false;
    }

    public String utforVältning(double timmar, String underlag) {

        if (timmar <= 0) {
            return "FEL: Ogiltig tid";
        }

        double bransleForbrukning = beraknaBransleForbrukning(timmar);

        uppdateraDrifttimmar(timmar);
        uppdateraBransleNiva(bransleForbrukning);

        return skapaValtningMeddelande(timmar, underlag);
    }

    private double beraknaBransleForbrukning(double timmar) {
        double forbrukningPerTimme = vibrationsLägeAktivt
                ? BRANSLE_VIBRATION
                : BRANSLE_UTAN_VIBRATION;

        return timmar * forbrukningPerTimme;
    }

    private void uppdateraDrifttimmar(double timmar) {
        setDrifttimmar(getDrifttimmar() + timmar);
    }

    private void uppdateraBransleNiva(double bransleForbrukning) {
        setBransleNiva(
                Math.max(0, getBransleNiva() - bransleForbrukning)
        );
    }
    private String skapaValtningMeddelande(double timmar, String underlag) {
        String vibrationsStatus = vibrationsLägeAktivt ? "JA" : "NEJ";

        return "VÄLTNING UTFÖRD: " + getId()
                + " (" + getModell() + ") packade "
                + underlag + " i " + timmar
                + "h. Vibration: " + vibrationsStatus;
    }
    // Getters och Setters
    public double getViktITon() {
        return viktITon;
    }

    public double getTrummBreddMeter() {
        return trummBreddMeter;
    }

    public boolean isVibrationsLägeAktivt() {
        return vibrationsLägeAktivt;
    }


}