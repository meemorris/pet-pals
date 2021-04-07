<template>
<div>
  <pet v-bind:id="$route.params.id"></pet>
  <router-link v-bind:to= "{name : 'registerPet'}">Register Pet</router-link>
</div>
</template>
<script>
//import petService from '@/services/PetService';
import Pet from '@/components/Pet';

export default {
  name: 'petProfile',
  // props: {
  //   'petId': Number
  // },
  components: {
    Pet 
  },
  methods: {
      // getPets(){
      //     petService.list().then(response =>{
      //         this.$store.commit("SET_PETS", response.data);
      //     })
      // },
      // getPetProfile(id){
      //     petService.getPet(id).then(response =>{
      //         if(response){
      //           //method to display pet information
      //         }
              
      //     }).catch((error) => {
      //     if (error.response) {
      //       alert(
      //         "Pet could not be found. Response was " +
      //           error.response.statusText
      //       );
      //     } else if (error.request) {
      //       alert("Pet could not be found. Server could not be reached");
      //     } else {
      //       alert("Pet could not be found. Request could not be created.");
      //     }
      //   });
      // }
  }
}
</script>