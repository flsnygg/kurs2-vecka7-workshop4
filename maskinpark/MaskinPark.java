package se.fjax26.maskinpark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaskinPark {

    private final List<Anlaggningsmaskin> maskiner;
    private final List<Korning> korningar;

    public MaskinPark() {
        this.maskiner = new ArrayList<>();
        this.korningar = new ArrayList<>();
    }

    public void registreraMaskin(Anlaggningsmaskin maskin) {
        if (maskin != null) {
            maskiner.add(maskin);
        }
    }

    public void registreraKorning(Korning korning) {
        if (korning != null) {
            korningar.add(korning);
        }
    }

    public void skrivUtAllaMaskiner() {
        System.out.println(
                "=== MASKINPARK ÖVERSIKT (" + maskiner.size() + " maskiner) ==="
        );

        for (Anlaggningsmaskin maskin : maskiner) {
            System.out.println(" • " + maskin);
        }
    }

    public void skrivUtAllaKorningar() {
        System.out.println(
                "=== KÖRNINGSRAPPORT (" + korningar.size() + " körpass) ==="
        );

        for (Korning korning : korningar) {
            double slutPris =
                    korning.beraknaSlutprisOchGenereraRapport(true, false);

            System.out.println(String.format(
                    " • [%s] Förare: %s | Maskin: %s (%s) | Tid: %.1fh | Slutpris (inkl moms): %.2f kr",
                    korning.getKorningId(),
                    korning.getForarNamn(),
                    korning.getMaskin().getId(),
                    korning.getMaskin().getModell(),
                    korning.getTimmar(),
                    slutPris
            ));
        }
    }

    public List<Anlaggningsmaskin> getMaskiner() {
        return Collections.unmodifiableList(maskiner);
    }

    public List<Korning> getKorningar() {
        return Collections.unmodifiableList(korningar);
    }
}