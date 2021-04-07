<template>
  <div class="pet-profile">
    <h1>{{ pet.name }}</h1>
    <img class="pet-pic" v-bind:src="pet.pic" />
    <p>{{pet.bio}}</p>
    <p>{{pet.species}}</p>
    <p>{{pet.breed}}</p>
    <p>{{pet.birthYear}}</p>
    <p>{{pet.weight}}</p>
    <p>{{pet.energeticRelaxed}}</p>
    <p>{{pet.shyFriendly}}</p>
    <p>{{pet.apatheticCurious}}</p>
    
  </div>
</template>

<script>
import petService from '@/services/PetService';

export default {
  name: "pet",
  props: ["id"],
  created() {
    this.retrievePet();
  },
  // data() {
  //   return {
  //     pet:
  //       {
  //         id: "42",
  //         name: "test",
  //         species: "cat",
  //         breed: " breed",
  //         weight: "pounds",
  //         birthYear: "1984",
  //         energeticRelaxed: "Relaxed",
  //         shyFriendly: "Shy",
  //         apatheticCurious: "Apathetic",
  //         bio: "very quiet",
  //         pic: "https://api.time.com/wp-content/uploads/2019/11/gettyimages-459761948.jpg?w=800&quality=85",
  //       },
  //   };
  // },
  computed: {
    pet() {
      return this.$store.state.pet;
    }
  },
  methods: {
    retrievePet() {
      petService.getPet(this.id)
      .then(response => {
        this.$store.commit("SET_CURRENT_PET", response.data);
      })
     .catch(error => {
          if (error.response && error.response.status === 404) {
            alert(
              "Pet not available. This pet may have been deleted."
            );
            this.$router.push("/profile");
          }
        });
    }
  }
  // methods: {
  //     getPetInfo() {
  //         return
  //     }
  // }
};
</script>

<style scoped>
.pet-profile {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.pet-pic {
  max-width: 30vw;
}

</style>