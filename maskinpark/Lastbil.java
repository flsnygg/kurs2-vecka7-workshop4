package se.fjax26.maskinpark;

public class Lastbil extends Anlaggningsmaskin {

    private double lasteKapacitetTon;
    private int antalAxlar;
    private boolean harSlap;

    private static final double GRUND_FORBRUKNING = 3.2;
    private static final double FORBRUKNING_PER_TON = 0.15;
    private static final double SLAP_EXTRA_FORBRUKNING = 1.2;
    private static final double MEDELHASTIGHET_KMH = 60.0;
    private static final double KM_PER_MIL = 10.0;

    public Lastbil(String id, String modell, double timKostnad, double lasteKapacitetTon, int antalAxlar, boolean harSlap) {
        super(id, modell, timKostnad);
        this.lasteKapacitetTon = lasteKapacitetTon;
        this.antalAxlar = antalAxlar;
        this.harSlap = harSlap;
    }
    private boolean arOverlastad(double lastViktTon) {
        return lastViktTon > getMaxLastKapacitet();
    }
    private double getMaxLastKapacitet() {
        return harSlap ? lasteKapacitetTon * 2.0 : lasteKapacitetTon;
    }
    private String skapaOverlastMeddelande() {
        return "FEL: Lastbil överlastad! Max kapacitet: "
                + lasteKapacitetTon + "t (Släp: " + harSlap + ")";
    }
    private double beraknaBransleForbrukning(double strackaKm, double lastViktTon) {
        double forbrukningPerMil = GRUND_FORBRUKNING
                + (lastViktTon * FORBRUKNING_PER_TON)
                + (harSlap ? SLAP_EXTRA_FORBRUKNING : 0.0);

        return (strackaKm / KM_PER_MIL) * forbrukningPerMil;
    }
    private String skapaTransportMeddelande(double strackaKm,
                                            double lastViktTon,
                                            double totalBransle) {
        return "TRANSPORT FULLFÖRD: " + getId()
                + " kört " + strackaKm + " km med "
                + lastViktTon + " ton last. Förbrukning: "
                + String.format("%.1f", totalBransle) + "L.";
    }
    private void uppdateraDrifttimmar(double strackaKm) {
        setDrifttimmar(getDrifttimmar() + (strackaKm / MEDELHASTIGHET_KMH));
    }

    private void uppdateraBransleNiva(double totalBransle) {
        setBransleNiva(Math.max(0, getBransleNiva() - totalBransle));
    }

    public String utforTransport(double strackaKm, double lastViktTon) {

        if (arOverlastad(lastViktTon)) {
            return skapaOverlastMeddelande();
        }

        double totalBransle = beraknaBransleForbrukning(strackaKm, lastViktTon);

        uppdateraDrifttimmar(strackaKm);
        uppdateraBransleNiva(totalBransle);

        return skapaTransportMeddelande(strackaKm, lastViktTon, totalBransle);
    }

    // Getters och Setters
    public double getLasteKapacitetTon() { return lasteKapacitetTon; }
    public int getAntalAxlar() { return antalAxlar; }
    public boolean isHarSlap() { return harSlap; }
    public void setHarSlap(boolean harSlap) { this.harSlap = harSlap; }
}