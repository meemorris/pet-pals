<template>
  <div>
    <pet v-bind:id="$route.params.id"></pet>

  </div>
</template>
<script>
//import petService from '@/services/PetService';
import Pet from "@/components/Pet";

export default {
  name: "petProfile",

  components: {
    Pet,
  },
computed: {

}
};
</script>