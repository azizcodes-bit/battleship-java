import java.util.Random;
import java.util.Scanner;

public class SchiffeSpielfeld {

    private char[][] meinFeld;
    private char[][] gegnerFeld;

    private Scanner sc;
    private Random random;

    private static final char LEER = ' ';
    private static final char SCHIFF = 'S';
    private static final char WASSER = 'O';
    private static final char TREFFER = '*';

    public SchiffeSpielfeld() {

        this.meinFeld = new char[10][10];
        this.gegnerFeld = new char[10][10];

        this.sc = new Scanner(System.in);
        this.random = new Random();

        initialisiereFeld(meinFeld);
        initialisiereFeld(gegnerFeld);
    }

    private void initialisiereFeld(char[][] feld) {

        for (int i = 0; i < feld.length; i++) {

            for (int j = 0; j < feld[i].length; j++) {

                feld[i][j] = LEER;
            }
        }
    }

    public void printField() {

        System.out.print("   ");

        for (int i = 0; i < meinFeld.length; i++) {

            System.out.print((i + 1) + "  ");
        }

        System.out.println();

        char buchstabe = 'A';

        for (int i = 0; i < meinFeld.length; i++) {

            System.out.print(buchstabe + "  ");

            for (int j = 0; j < meinFeld[i].length; j++) {

                System.out.print(meinFeld[i][j] + "  ");
            }

            System.out.println();

            buchstabe++;
        }
    }

    public void printFieldGeg() {

        System.out.print("   ");

        for (int i = 0; i < gegnerFeld.length; i++) {

            System.out.print((i + 1) + "  ");
        }

        System.out.println();

        char buchstabe = 'A';

        for (int i = 0; i < gegnerFeld.length; i++) {

            System.out.print(buchstabe + "  ");

            for (int j = 0; j < gegnerFeld[i].length; j++) {

                if (gegnerFeld[i][j] == SCHIFF) {

                    System.out.print("   ");

                } else {

                    System.out.print(gegnerFeld[i][j] + "  ");
                }
            }

            System.out.println();

            buchstabe++;
        }
    }

    public void printFields() {

        System.out.println("Gegner:");
        printFieldGeg();

        System.out.println();

        System.out.println("Spieler:");
        printField();
    }

    public boolean setShip(int startReihe,
                           int startSpalte,
                           int endReihe,
                           int endSpalte,
                           int laenge) {

        return setShipAufFeld(
                meinFeld,
                startReihe,
                startSpalte,
                endReihe,
                endSpalte,
                laenge,
                true
        );
    }

    private boolean setShipAufFeld(char[][] feld,
                                   int startReihe,
                                   int startSpalte,
                                   int endReihe,
                                   int endSpalte,
                                   int laenge,
                                   boolean fehlermeldung) {

        if (startReihe < 0 || startReihe >= 10 ||
            endReihe < 0 || endReihe >= 10 ||
            startSpalte < 0 || startSpalte >= 10 ||
            endSpalte < 0 || endSpalte >= 10) {

            if (fehlermeldung) {
                System.out.println("Ungueltige Position!");
            }

            return false;
        }

        if (startReihe != endReihe &&
            startSpalte != endSpalte) {

            if (fehlermeldung) {
                System.out.println(
                        "Schiffe duerfen nicht diagonal gesetzt werden!"
                );
            }

            return false;
        }

        int eingegebeneLaenge;

        if (startReihe == endReihe) {

            eingegebeneLaenge =
                    Math.abs(endSpalte - startSpalte) + 1;

        } else {

            eingegebeneLaenge =
                    Math.abs(endReihe - startReihe) + 1;
        }

        if (eingegebeneLaenge != laenge) {

            if (fehlermeldung) {

                System.out.println(
                        "Das Schiff muss Laenge "
                        + laenge
                        + " haben!"
                );
            }

            return false;
        }

        if (startReihe == endReihe) {

            int von = Math.min(startSpalte, endSpalte);
            int bis = Math.max(startSpalte, endSpalte);

            for (int spalte = von;
                 spalte <= bis;
                 spalte++) {

                if (feld[startReihe][spalte] == SCHIFF) {

                    if (fehlermeldung) {

                        System.out.println(
                                "Hier befindet sich bereits ein Schiff!"
                        );
                    }

                    return false;
                }
            }

            for (int spalte = von;
                 spalte <= bis;
                 spalte++) {

                feld[startReihe][spalte] = SCHIFF;
            }
        }

        else {

            int von = Math.min(startReihe, endReihe);
            int bis = Math.max(startReihe, endReihe);

            for (int reihe = von;
                 reihe <= bis;
                 reihe++) {

                if (feld[reihe][startSpalte] == SCHIFF) {

                    if (fehlermeldung) {

                        System.out.println(
                                "Hier befindet sich bereits ein Schiff!"
                        );
                    }

                    return false;
                }
            }

            for (int reihe = von;
                 reihe <= bis;
                 reihe++) {

                feld[reihe][startSpalte] = SCHIFF;
            }
        }

        return true;
    }

    public void shoot(int reihe, int spalte) {

        shootAufFeld(
                gegnerFeld,
                reihe,
                spalte,
                true
        );
    }

    private boolean shootAufFeld(char[][] feld,
                                 int reihe,
                                 int spalte,
                                 boolean ausgabe) {

        if (reihe < 0 || reihe >= 10 ||
            spalte < 0 || spalte >= 10) {

            if (ausgabe) {
                System.out.println("Ungueltiges Feld!");
            }

            return false;
        }

        if (feld[reihe][spalte] == WASSER ||
            feld[reihe][spalte] == TREFFER) {

            if (ausgabe) {

                System.out.println(
                        "Dieses Feld wurde bereits beschossen!"
                );
            }

            return false;
        }

        if (feld[reihe][spalte] == SCHIFF) {

            feld[reihe][spalte] = TREFFER;

            if (ausgabe) {
                System.out.println("Treffer!");
            }

        } else {

            feld[reihe][spalte] = WASSER;

            if (ausgabe) {
                System.out.println("Wasser!");
            }
        }

        return true;
    }

    public void start() {

        int[] schiffLaengen = {
                2, 2, 2,
                3, 3,
                4,
                5
        };

        System.out.println("=== SCHIFFE SETZEN ===");

        for (int i = 0;
             i < schiffLaengen.length;
             i++) {

            int laenge = schiffLaengen[i];

            boolean gesetzt = false;

            while (!gesetzt) {

                System.out.println();

                System.out.println(
                        "Schiff der Laenge "
                        + laenge
                        + " setzen:"
                );

                int startReihe =
                        leseReihe("Startreihe (A-J): ");

                int startSpalte =
                        leseSpalte("Startspalte (1-10): ");

                int endReihe =
                        leseReihe("Endreihe (A-J): ");

                int endSpalte =
                        leseSpalte("Endspalte (1-10): ");

                gesetzt =
                        setShip(
                                startReihe,
                                startSpalte,
                                endReihe,
                                endSpalte,
                                laenge
                        );

                if (gesetzt) {

                    System.out.println();
                    printField();
                }
            }
        }

        setzeGegnerSchiffe();

        System.out.println();
        System.out.println(
                "Alle Schiffe wurden gesetzt!"
        );
    }

    public void update() {

        while (hatNochSchiffe(meinFeld) &&
               hatNochSchiffe(gegnerFeld)) {

            System.out.println();
            printFields();

            System.out.println();
            System.out.println("=== DEIN ZUG ===");

            boolean gueltigerSchuss = false;

            while (!gueltigerSchuss) {

                int reihe =
                        leseReihe("Reihe (A-J): ");

                int spalte =
                        leseSpalte("Spalte (1-10): ");

                gueltigerSchuss =
                        shootAufFeld(
                                gegnerFeld,
                                reihe,
                                spalte,
                                true
                        );
            }

            if (!hatNochSchiffe(gegnerFeld)) {

                System.out.println();
                printFields();

                System.out.println();
                System.out.println("Du hast gewonnen!");

                return;
            }

            System.out.println();
            System.out.println("=== GEGNER ===");

            gegnerSchiesst();

            if (!hatNochSchiffe(meinFeld)) {

                System.out.println();
                printFields();

                System.out.println();
                System.out.println(
                        "Der Gegner hat gewonnen!"
                );

                return;
            }
        }
    }

    private void setzeGegnerSchiffe() {

        int[] schiffLaengen = {
                2, 2, 2,
                3, 3,
                4,
                5
        };

        for (int i = 0;
             i < schiffLaengen.length;
             i++) {

            int laenge = schiffLaengen[i];

            boolean gesetzt = false;

            while (!gesetzt) {

                int startReihe =
                        random.nextInt(10);

                int startSpalte =
                        random.nextInt(10);

                boolean horizontal =
                        random.nextBoolean();

                int endReihe = startReihe;
                int endSpalte = startSpalte;

                if (horizontal) {

                    endSpalte =
                            startSpalte
                            + laenge
                            - 1;

                } else {

                    endReihe =
                            startReihe
                            + laenge
                            - 1;
                }

                gesetzt =
                        setShipAufFeld(
                                gegnerFeld,
                                startReihe,
                                startSpalte,
                                endReihe,
                                endSpalte,
                                laenge,
                                false
                        );
            }
        }
    }

    private void gegnerSchiesst() {

        int reihe;
        int spalte;

        do {

            reihe = random.nextInt(10);
            spalte = random.nextInt(10);

        } while (
                meinFeld[reihe][spalte] == WASSER ||
                meinFeld[reihe][spalte] == TREFFER
        );

        char vorher = meinFeld[reihe][spalte];

        shootAufFeld(
                meinFeld,
                reihe,
                spalte,
                false
        );

        char buchstabe =
                (char) ('A' + reihe);

        System.out.println(
                "Gegner schiesst auf "
                + buchstabe
                + (spalte + 1)
        );

        if (vorher == SCHIFF) {

            System.out.println(
                    "Der Gegner hat getroffen!"
            );

        } else {

            System.out.println(
                    "Der Gegner trifft Wasser."
            );
        }
    }

    private boolean hatNochSchiffe(char[][] feld) {

        for (int i = 0;
             i < feld.length;
             i++) {

            for (int j = 0;
                 j < feld[i].length;
                 j++) {

                if (feld[i][j] == SCHIFF) {

                    return true;
                }
            }
        }

        return false;
    }

    private int leseReihe(String text) {

        System.out.print(text);

        String eingabe = sc.next();

        char buchstabe =
                Character.toUpperCase(
                        eingabe.charAt(0)
                );

        return buchstabe - 'A';
    }

    private int leseSpalte(String text) {

        System.out.print(text);

        return sc.nextInt() - 1;
    }
}