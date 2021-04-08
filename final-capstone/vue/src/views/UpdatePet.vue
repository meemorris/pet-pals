<template>
  <div id="update" class="text-center">
    <h1>Update Your Pet</h1>
    <pet-form v-bind:button="text"></pet-form>
  </div>
</template>

<script>
import petForm from "@/components/PetForm";

export default {
  name: "updatePet",
  components: {
    petForm,
  },
  data() {
    return {
      text: "Update",
    };
  },
};
</script>

<style scoped>
h1 {
  margin-top: 45px;
}
</style>