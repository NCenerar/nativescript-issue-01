# Quick setup
Clone this project:
```
git clone git@github.com:NCenerar/nativescript-issue-01.git
```

Enter the project and run it on android:
```
tns debug android
```

# The bug

When the code reach a method call on an android object `mypackage.PublicInterface.get().isOk()`, you will get a runtime error:

```
The build result is located at: xxx/platforms/android/app/build/outputs/apk/debug/app-debug.apk
Installing on device XXXX...
Successfully installed on device with identifier 'XXXX'.
Restarting application on device XXXX...
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(Frame)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(Page)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(ActionBar)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(label)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(ActionBar(4), Label(5))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(Page(3), ActionBar(4))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(gridlayout)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(label)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(formattedstring)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(span)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(, Span(9))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> CreateElement(span)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild( , Span(10))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(Label(7),  Blank {N}-Vue app)
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(GridLayout(6), Label(7))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(Page(3), GridLayout(6))
JS: {NSVue (Vue: 2.6.12 | NSVue: 2.9.0)} -> AppendChild(Frame(2), Page(3))
JS: Before bug:
JS:   theProblem seems good                     : mypackage.PublicClass$PrivateInnerClass@12abed3
JS:   theProblem.isOk is undefined but shouldn't: undefined
JS: And this is obviously giving an error:
JS: [Vue warn]: Error in mounted hook: "TypeError: theProblem.isOk is not a function"
JS: 
JS: found in
JS: 
JS: ---> <Home> at app/components/Home.vue
JS:        <Frame>
JS:          <Root>
System.err: An uncaught Exception occurred on "main" thread.
System.err: Unable to start activity ComponentInfo{org.nativescript.issuenativeandroid/com.tns.NativeScriptActivity}: com.tns.NativeScriptException: Calling js method onCreate failed
System.err: TypeError: theProblem.isOk is not a function
System.err: 
System.err: StackTrace:
System.err: java.lang.RuntimeException: Unable to start activity ComponentInfo{org.nativescript.issuenativeandroid/com.tns.NativeScriptActivity}: com.tns.NativeScriptException: Calling js method onCreate failed
System.err: TypeError: theProblem.isOk is not a function
System.err:     at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3271)
System.err:     at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3410)
System.err:     at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:83)
System.err:     at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
System.err:     at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
System.err:     at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2017)
System.err:     at android.os.Handler.dispatchMessage(Handler.java:107)
System.err:     at android.os.Looper.loop(Looper.java:214)
System.err:     at android.app.ActivityThread.main(ActivityThread.java:7397)
System.err:     at java.lang.reflect.Method.invoke(Native Method)
System.err:     at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492)
System.err:     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:935)
System.err: Caused by: com.tns.NativeScriptException: Calling js method onCreate failed
System.err: TypeError: theProblem.isOk is not a function
System.err:     at com.tns.Runtime.callJSMethodNative(Native Method)
System.err:     at com.tns.Runtime.dispatchCallJSMethodNative(Runtime.java:1302)
System.err:     at com.tns.Runtime.callJSMethodImpl(Runtime.java:1188)
System.err:     at com.tns.Runtime.callJSMethod(Runtime.java:1175)
System.err:     at com.tns.Runtime.callJSMethod(Runtime.java:1153)
System.err:     at com.tns.Runtime.callJSMethod(Runtime.java:1149)
System.err:     at com.tns.NativeScriptActivity.onCreate(NativeScriptActivity.java:29)
System.err:     at android.app.Activity.performCreate(Activity.java:7802)
System.err:     at android.app.Activity.performCreate(Activity.java:7791)
System.err:     at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1300)
System.err:     at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3246)
System.err:     ... 11 more
Unable to apply changes on device: XXXX. Error is: The application org.nativescript.issuenativeandroid does not appear to be running on XXXX or is not built with debugging enabled. Try starting the application manually..
```

# Long setup

Initialize the project:
```
tns create issue-native-android --vue --ts
cd issue-native-android
```

Create a java interface `./App_Resources/Android/src/main/java/mypackage/PublicInterface.java`:
```
package mypackage;

public interface PublicInterface {
    public static PublicInterface get() {
        return PublicClass.get();
    }
    public boolean isOk();
}
```

Update the types `./types/references.d.ts`:
```
/// <reference path="../node_modules/@nativescript/types/index.d.ts" />

declare namespace mypackage {
    export class PublicInterface {
        public static get(): mypackage.PublicInterface;
        public isOk(): boolean;
    }
}
```

Create a java class implementing the interface with a singleton pattern based on a private inner class `App_Resources/Android/src/main/java/mypackage/PublicClass.java`:
```
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
```

Update the main Component and add a mounted function `./app/Components/Home.vue`:
```
    mounted() {
      const theProblem = mypackage.PublicInterface.get();
      console.log("Before bug:");
      console.log("  theProblem seems good                     :", theProblem);
      console.log("  theProblem.isOk is undefined but shouldn't:", theProblem.isOk);
      console.log("And this is obviously giving an error:");
      console.log("  theProblem.isOk()                         :", theProblem.isOk());
    }
```

Run on android:
```
tns debug android
```
