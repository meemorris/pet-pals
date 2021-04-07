<template>
<div>
  <h1>Home of Your Future Profile Page</h1>
  <router-link v-bind:to= "{name : 'registerPet'}">Register Pet</router-link>
  <router-link v-bind:pet="pet">Pet Profile</router-link>
</div>
</template>

<script>
import petService from '@/services/PetService';

export default {
created() {
  this.retrievePetList();
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
  }
}

}
</script>

<style>

</style>