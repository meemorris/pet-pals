<template>
<div>
  <h1>{{pet.name}}</h1>
  <router-link v-bind:to= "{name : 'registerPet'}">Register Pet</router-link>
</div>
</template>
<script>
import petService from '@/services/PetService';
export default {
  name: 'registerPet',
  props: {
    'petId': Number
  },
  methods: {
      getPets(){
          petService.list().then(response =>{
              this.$store.commit("SET_PETS", response.data);
          })
      },
      getPetProfile(id){
          petService.petProfile(id).then(response =>{
              if(response){
                //method to display pet information
              }
              
          }).catch((error) => {
          if (error.response) {
            alert(
              "Pet could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Pet could not be found. Server could not be reached");
          } else {
            alert("Pet could not be found. Request could not be created.");
          }
        });
      }
  }
}
</script>