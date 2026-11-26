package se.fjax26.maskinpark;
public class Hjullastare extends Anlaggningsmaskin {

    private static final double SAKERHETSFAKTOR = 50.0;
    private static final double BRANSLE_PER_TIMME = 13.5;
    private static final double BRANSLE_PER_TON = 0.4;

    private final double skopVolymKbm;
    private final double maxLyftKapacitetTon;
    private boolean harGafflarMonterade;

    public Hjullastare(String id, String modell, double timKostnad,
                       double skopVolymKbm, double maxLyftKapacitetTon) {
        super(id, modell, timKostnad);
        this.skopVolymKbm = skopVolymKbm;
        this.maxLyftKapacitetTon = maxLyftKapacitetTon;
        this.harGafflarMonterade = false;
    }

    public void monteraGafflar() {
        harGafflarMonterade = true;
    }

    public void monteraSkopa() {
        harGafflarMonterade = false;
    }

    public String utforLyftEllerLastning(double timmar, double tonMaterial) {
        if (arOverbelastad(tonMaterial)) {
            return "FEL: Överbelastning! Materialvikt ("
                    + tonMaterial + "t) överstiger säkerhetsgränsen.";
        }

        double bransleForbrukning = beraknaBransleForbrukning(timmar, tonMaterial);

        uppdateraMaskinTillstand(timmar, bransleForbrukning);

        return formateraResultat(timmar, tonMaterial);
    }

    private boolean arOverbelastad(double tonMaterial) {
        return tonMaterial > maxLyftKapacitetTon * SAKERHETSFAKTOR;
    }

    private double beraknaBransleForbrukning(double timmar, double tonMaterial) {
        return timmar * BRANSLE_PER_TIMME + tonMaterial + BRANSLE_PER_TON;
    }

    private void uppdateraMaskinTillstand(double timmar, double bransleForbrukning) {
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNiva(Math.max(0, getBransleNiva() - bransleForbrukning));
    }

    private String hamtaMonteratVerktyg() {
        return harGafflarMonterade
                ? "Pallgafflar"
                : "Skopa (" + skopVolymKbm + "m³)";
    }

    private String formateraResultat(double timmar, double tonMaterial) {
        return "Hjullasting gick bra: " + getId()
                + " hanterade " + tonMaterial
                + " ton på " + timmar
                + " h med " + hamtaMonteratVerktyg()
                + " monterat som verktyg.";
    }

    public double getSkopVolymKbm() {
        return skopVolymKbm;
    }

    public double getMaxLyftKapacitetTon() {
        return maxLyftKapacitetTon;
    }

    public boolean isHarGafflarMonterade() {
        return harGafflarMonterade;
    }
}
