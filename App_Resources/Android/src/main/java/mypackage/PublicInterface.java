package mypackage;

public interface PublicInterface {
    public static PublicInterface get() {
        return PublicClass.get();
    }
    public boolean isOk();
}
