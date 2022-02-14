package mypackage;

import android.util.Log;

class PackageProtectedClass implements Runnable {
    @Override
    public void run() {
        Log.d("JS", String.format("Run:  I am %s %s", this.getClass(), this));
    }
}

public class PublicClass {
    public PackageProtectedClass getIt() {
        return new PackageProtectedClass();
    }
}
