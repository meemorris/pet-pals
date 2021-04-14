<template>
  <div>
    <form class="form-user" id="form" v-on:submit.prevent="addPetToPlaydate">
      <p>Which pet?</p>
      <label
        v-for="pet in $store.state.pets"
        v-bind:key="pet.petId"
        for="pet-name"
        class="label-pet-name"
      >
        <input
          class="input-pet-name"
          type="radio"
          id="pet-name"
          v-on:change="findPetByName"
          v-bind:value="pet.name"
          v-model="petName"
        />{{ pet.name }}</label
      >
      <button id="button-add-pet" class="btn btn-primary" type="submit">
        Join Playdate
      </button>
    </form>
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";
export default {
  name: "JoinPlaydateForm",
  data() {
    return {
      pet: {
        petId: "",
        name: "",
        userId: "",
        species: "",
        breed: "",
        weight: "",
        birthYear: "",
        energeticRelaxed: "",
        shyFriendly: "",
        apatheticCurious: "",
        bio: "",
        pic: "",
      },
      petName: "",
    };
  },
  methods: {
    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
    },
    addPetToPlaydate() {
      if (this.playdate.pet.name == this.pet.name) {
        this.errorMsg = " is already hosting this playdate.";
        this.showForm = false;
        this.toggleDisplay();
      } else {
        playdateService
          .joinPlaydate(this.playdate.playdateId, this.pet.petId)
          .then((response) => {
            if (response.status === 201) {
              this.successMsg = "Playdate joined, yay!";
            }
            this.showForm = false;
            this.toggleDisplay();
            this.pet = {};
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be joined. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Playdate could not be joined. Server could not be reached"
              );
            } else {
              alert(
                "Playdate could not be joined. Request could not be created."
              );
            }
          });
      }
    },
  },
};
</script>

<style>
</style>