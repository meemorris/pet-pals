<template>
<div>
  <h1>Home of Your Future Profile Page</h1>
  <div id="petColumn">
    <h1>Your Pets</h1> 
      <pet-preview v-for="pet in myPetsFilter" v-bind:key="pet.petId" v-bind:pet="pet" v-on:click="viewPetDetails(pet.petId)" />
      <router-link v-bind:to= "{name : 'registerPet'}">Register Pet</router-link>
    </div>

  <!-- <router-link v-bind:pet="pet">Pet Profile</router-link> -->
</div>
</template>

<script>
import petService from '@/services/PetService';
import PetPreview from '@/components/PetPreview.vue';

export default {
created() {
  this.retrievePetList();
},
components: {
  PetPreview
},
computed: {
  myPetsFilter() {
    return this.$store.state.pets.filter(pet => pet.userId === this.$store.state.user.id)
  }
},
methods: {
  retrievePetList() {
    petService.list()
    .then(response => {
      this.$store.commit("SET_PETS", response.data);
    })
    .catch((error) => {
          if (error.response) {
            alert(
              "Pet list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Pet list could not be found. Server could not be reached");
          } else {
            alert("Pet list could not be found. Request could not be created.");
          }
    });
  },
  viewPetDetails(id) {
    this.$router.push(`/pets/${id}`)
  }
}

}
</script>

<style>

</style>