package projet.cardlibrary_amrane_vallipuram.exception;

public class MemberNotFoundException extends Exception {

    public MemberNotFoundException(Long id) {
        super("Membre avec l'ID " + id + " introuvable.");
    }

    public MemberNotFoundException(String username) {
        super("Membre avec le nom " + username + " introuvable.");
    }
}
