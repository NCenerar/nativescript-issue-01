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

The factory pattern does not work as expected.

`App_Resources/Android/src/main/java/mypackage/PublicClass.java`
```java
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
```

`./types/references.d.ts`
```typescript
/// <reference path="../node_modules/@nativescript/types/index.d.ts" />

declare namespace mypackage {
    export class PackageProtectedClass extends java.lang.Object implements java.lang.Runnable {
        public constructor(): PackageProtectedClass;
        public run(): void;
    }
    export class PublicClass extends java.lang.Object {
        public constructor(): PublicClass;
        public getIt(): mypackage.PackageProtectedClass;
    }
}
```

Script part of `./app/Components/Home.vue`
```typescript
  import Vue from "nativescript-vue";

  function logObject(name: string, obj: any, indent: number = 0) {
      const prefix = ''.padStart(indent, ' ');
      const proto = Object.getPrototypeOf(obj);
      if (name) console.log(prefix, name, obj);
      console.log(prefix, " >", Object.entries(obj));
      console.log(prefix, " >", Object.getOwnPropertyNames(obj));
      if (obj && proto && obj !== Object.prototype) {
        logObject('', proto, indent + 2);
      }
  }

  export default Vue.extend({
    computed: {
      message() {
        return "Blank {N}-Vue app";
      }
    },
    mounted() {
      logObject("mypackage", mypackage);
      logObject("PublicClass", mypackage.PublicClass);
      logObject("PackageProtectedClass", mypackage.PackageProtectedClass);
      const theFactory = new mypackage.PublicClass();
      const theProblem = theFactory.getIt();
      logObject("theFactory", theFactory);
      logObject("theProblem", theProblem);
      console.log("theProblem.run :", theProblem.run);
      const theClass = theProblem.getClass();
      const theInterfaces = theClass.getInterfaces();
      const theDeclaredMethods = theClass.getDeclaredMethods()
      console.log("theProblem.getClass() :", theClass);
      console.log("theClass.getInterfaces() :", theInterfaces);
      for (let i=0; i<theInterfaces.length; i++) {
        console.log(" > ", i, theInterfaces[i]);
      }
      console.log("theClass.getDeclaredMethods() :", theDeclaredMethods);
      for (let i=0; i<theDeclaredMethods.length; i++) {
        console.log(" > ", i, theDeclaredMethods[i]);
      }
      try {
        theProblem.run();
      } catch (e) {
        console.error(e, (e as Error).stack);
      }
    }
  });
```

# The output:

```log
JS:  mypackage {}
JS:   > [[PublicClass, function () { [native code] }], [PackageProtectedClass, function () { [native code] }]]
JS:   > [PublicClass, PackageProtectedClass]
JS:     > []
JS:     > [constructor, __defineGetter__, __defineSetter__, hasOwnProperty, __lookupGetter__, __lookupSetter__, isPrototypeOf, propertyIsEnumerable, toString, valueOf, __proto__, toLocaleString]
JS:  PublicClass function () { [native code] }
JS:   > [[extend, function () { [native code] }], [null, function () { [native code] }], [class, class mypackage.PublicClass]]
JS:   > [length, name, arguments, caller, prototype, extend, null, class, valueOf]
JS:     > [[extend, function () { [native code] }], [null, function () { [native code] }], [class, class java.lang.Object]]
JS:     > [length, name, arguments, caller, prototype, extend, null, class, valueOf]
JS:       > []
JS:       > [length, name, arguments, caller, constructor, apply, bind, call, toString]
JS:         > []
JS:         > [constructor, __defineGetter__, __defineSetter__, hasOwnProperty, __lookupGetter__, __lookupSetter__, isPrototypeOf, propertyIsEnumerable, toString, valueOf, __proto__, toLocaleString]
JS:  PackageProtectedClass function () { [native code] }
JS:   > [[extend, function () { [native code] }], [null, function () { [native code] }], [class, class mypackage.PackageProtectedClass]]
JS:   > [length, name, arguments, caller, prototype, extend, null, class, valueOf]
JS:     > [[extend, function () { [native code] }], [null, function () { [native code] }], [class, class java.lang.Object], [valueOf, function () { [native code] }]]
JS:     > [length, name, arguments, caller, prototype, extend, null, class, valueOf]
JS:       > []
JS:       > [length, name, arguments, caller, constructor, apply, bind, call, toString]
JS:         > []
JS:         > [constructor, __defineGetter__, __defineSetter__, hasOwnProperty, __lookupGetter__, __lookupSetter__, isPrototypeOf, propertyIsEnumerable, toString, valueOf, __proto__, toLocaleString]
JS:  theFactory mypackage.PublicClass@b67070e
JS:   > []
JS:   > []
JS:     > [[<init>, function <init>() { [native code] }], [getIt, function getIt() { [native code] }]]
JS:     > [<init>, getIt, constructor]
JS:       > [[<init>, function <init>() { [native code] }], [clone, function clone() { [native code] }], [equals, function equals() { [native code] }], [finalize, function finalize() { [native code] }], [getClass, function getClass() { [native code] }], [hashCode, function hashCode() { [native code] }], [notify, function notify() { [native code] }], [notifyAll, function notifyAll() { [native code] }], [toString, function toString() { [native code] }], [wait, function wait() { [native code] }]]
JS:       > [<init>, clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, constructor]
JS:         > []
JS:         > [constructor, __defineGetter__, __defineSetter__, hasOwnProperty, __lookupGetter__, __lookupSetter__, isPrototypeOf, propertyIsEnumerable, toString, valueOf, __proto__, toLocaleString]
JS:  theProblem mypackage.PackageProtectedClass@1d8752f
JS:   > [[constructor, function () { [native code] }]]
JS:   > [constructor]
JS:     > []
JS:     > [constructor]
JS:       > [[<init>, function <init>() { [native code] }], [clone, function clone() { [native code] }], [equals, function equals() { [native code] }], [finalize, function finalize() { [native code] }], [getClass, function getClass() { [native code] }], [hashCode, function hashCode() { [native code] }], [notify, function notify() { [native code] }], [notifyAll, function notifyAll() { [native code] }], [toString, function toString() { [native code] }], [wait, function wait() { [native code] }]]
JS:       > [<init>, clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, constructor]
JS:         > []
JS:         > [constructor, __defineGetter__, __defineSetter__, hasOwnProperty, __lookupGetter__, __lookupSetter__, isPrototypeOf, propertyIsEnumerable, toString, valueOf, __proto__, toLocaleString]
JS: theProblem.run : undefined
JS: theProblem.getClass() : class mypackage.PackageProtectedClass
JS: theClass.getInterfaces() : [Ljava.lang.Class;@e38673c
JS:  >  0 interface java.lang.Runnable
JS: theClass.getDeclaredMethods() : [Ljava.lang.reflect.Method;@1e06cc5
JS:  >  0 public void mypackage.PackageProtectedClass.run()
JS: TypeError: theProblem.run is not a function TypeError: theProblem.run is not a function
JS:     at VueComponent.mounted (file: app/webpack:/issue-native-android/app/components/Home.vue?3a7c:60:17)
JS:     at invokeWithErrorHandling (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:1862:0)
JS:     at callHook$1 (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:5097:0)
JS:     at Object.insert (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:4019:0)
JS:     at invokeInsertHook (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:5680:0)
JS:     at Vue.patch [as __patch__] (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:5899:0)
JS:     at Vue._update (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:4823:0)
JS:     at Vue.updateComponent (file: app/webpack:/issue-native-android/node_modules/nativescript-vue/dist/index.js:4944:0)
JS:     at Watcher.get (file:///data/data/org.nativescript.issuen...
```

# Notable output
The difference between the working object `theFactory` and the non working object `theProblem`:
```
JS:  theFactory mypackage.PublicClass@b67070e
JS:   > []
JS:   > []
JS:     > [[<init>, function <init>() { [native code] }], [getIt, function getIt() { [native code] }]]
JS:     > [<init>, getIt, constructor]

JS:  theProblem mypackage.PackageProtectedClass@1d8752f
JS:   > [[constructor, function () { [native code] }]]
JS:   > [constructor]
JS:     > []
JS:     > [constructor]
```

Each of them seems to have a correct reference but `theProblem` does not seems to be correctly binded.

It is a very simple factory pattern and I wonder how NativeScript is supposed to handle object instance coming from a native librarie.
