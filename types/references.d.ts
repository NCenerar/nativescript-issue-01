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
