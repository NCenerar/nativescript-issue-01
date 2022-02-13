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

  export default Vue.extend({
    computed: {
      message() {
        return "Blank {N}-Vue app";
      }
    },
    mounted() {
      const theProblem = mypackage.PublicInterface.get();
      console.log("Before bug:");
      console.log("  theProblem seems good                     :", theProblem);
      console.log("  theProblem.isOk is undefined but shouldn't:", theProblem.isOk);
      console.log("And this is obviously giving an error:");
      console.log("  theProblem.isOk()                         :", theProblem.isOk());
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
