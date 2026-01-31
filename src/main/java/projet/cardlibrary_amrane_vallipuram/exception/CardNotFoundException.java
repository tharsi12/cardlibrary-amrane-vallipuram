package projet.cardlibrary_amrane_vallipuram.exception;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String name) {
        super("Carte introuvable : " + name);
    }
}
