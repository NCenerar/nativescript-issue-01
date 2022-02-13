package mypackage;

public final class PublicClass {
    private static class PrivateInnerClass implements PublicInterface {
        @Override
        public boolean isOk() {
            return true;
        }
    }

    private static PrivateInnerClass singleton;
    public static PrivateInnerClass get() {
        if (PublicClass.singleton == null ) {
            PublicClass.singleton = new PrivateInnerClass();
        }
        return PublicClass.singleton;
    }
}
