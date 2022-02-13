/// <reference path="../node_modules/@nativescript/types/index.d.ts" />

declare namespace mypackage {
    export class PublicInterface {
        public static get(): mypackage.PublicInterface;
        public isOk(): boolean;
    }
}
