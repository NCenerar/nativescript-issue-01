<template>
  <Page>
    <ActionBar>
      <Label text="Home"/>
    </ActionBar>

    <GridLayout>
      <Label class="info">
        <FormattedString>
          <Span class="fas" text.decode="&#xf135; "/>
          <Span :text="message"/>
        </FormattedString>
      </Label>
    </GridLayout>
  </Page>
</template>

<script lang="ts">
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
</script>

<style scoped lang="scss">
  @import '@nativescript/theme/scss/variables/blue';

  // Custom styles
  .fas {
    @include colorize($color: accent);
  }

  .info {
    font-size: 20;
    horizontal-align: center;
    vertical-align: center;
  }
</style>
