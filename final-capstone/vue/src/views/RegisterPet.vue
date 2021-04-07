<template>
  <div id="register" class="text-center">
    <form class="form-registerPet" v-on:submit.prevent="register">
      <h1>Register Your Pet</h1>
      <label for="name" class="sr-only">Pet Name</label>
      <input
        type="text"
        id="name"
        class="form-control"
        placeholder="Name"
        v-model="pet.name"
        required
        autofocus
      />
      <label for="species">Species</label>
      <select
        name="species"
        id="species"
        v-model="pet.species"
        v-on:change="toggleOtherSpecies"
      >
        <option value="" default>---</option>
        <option value="dog">Dog</option>
        <option value="cat">Cat</option>
        <option value="rabbit">Rabbit</option>
        <option value="hedgehog">Hedgehog</option>
        <option value="other">Other (please describe)</option>
      </select>

      <input
        type="text"
        id="otherSpecies"
        class="form-control d-none"
        placeholder="Other Species Type"
        v-model="pet.species"
        required
      />

      <label for="breed" class="sr-only">breed</label>
      <input
        type="text"
        id="breed"
        class="form-control"
        placeholder="Breed"
        v-model="pet.breed"
      />
      <label for="weight" class="sr-only">weight</label>
      <input
        type="number"
        id="weight"
        min="0"
        class="form-control"
        placeholder="Weight in pounds"
        v-model="pet.weight"
        required
      />
      <label for="birthYear" class="sr-only">BirthYear</label>
      <input
        type="number"
        id="birthYear"
        class="form-control"
        placeholder="Birth Year"
        min="1970"
        max="2021"
        value=""
        v-model="pet.birthYear"
        required
      />

      <div class="personality">
        <p>Energy:</p>
        <label for="energetic">
          <input
            type="radio"
            id="energetic"
            name="energetic_relaxed"
            v-model="pet.energeticRelaxed"
            value="energetic"
          />
          Energetic</label
        >

        <label for="relaxed">
          <input
            type="radio"
            id="relaxed"
            name="energetic_relaxed"
            v-model="pet.energeticRelaxed"
            value="relaxed"
          />
          Relaxed</label
        >
      </div>

      <div class="personality">
        <p>Timidity:</p>
        <label for="shy">
          <input
            type="radio"
            id="shy"
            name="shy_friendly"
            v-model="pet.shyFriendly"
            value="shy"
          />
          Shy
        </label>
        <label for="friendly">
          <input
            type="radio"
            id="friendly"
            name="shy_friendly"
            v-model="pet.shyFriendly"
            value="friendly"
          />
          Friendly
        </label>
      </div>

      <div class="personality">
        <p>Curiosity:</p>
        <label for="apathetic">
          <input
            type="radio"
            id="apathetic"
            name="apathetic_curious"
            v-model="pet.apatheticCurious"
            value="apathetic"
          />
          Apathetic</label
        >

        <label for="curious">
          <input
            type="radio"
            id="curious"
            name="apathetic_curious"
            v-model="pet.apatheticCurious"
            value="curious"
          />
          Curious</label
        >
      </div>

      <label for="bio" class="sr-only">Bio</label>
      <input
        type="text"
        id="bio"
        class="form-control"
        placeholder="Bio"
        v-model="pet.bio"
      />

      <label for="pic" class="sr-only">Pic</label>
      <input
        type="url"
        id="pic"
        class="form-control"
        placeholder="Image URL of Pet"
        v-model="pet.pic"
      />

      <button class="btn btn-lg btn-primary" type="submit">Register Pet</button>
    </form>
  </div>
</template>

<script>
import petService from "../services/PetService";

export default {
  name: "registerPet",
  data() {
    return {
      pet: {
        name: "",
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
    };
  },

  methods: {
    register() {
      petService
        .registerPet(this.pet)
        .then((response) => {
          if (response.status === 201) {
            const petId = response.data;
            this.$router.push(`/pets/${petId}`);
          }
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Pet could not be registered. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Pet could not be registered. Server could not be reached");
          } else {
            alert("Pet could not be registered. Request could not be created.");
          }
        });
    },
    toggleOtherSpecies() {
      let otherSpeciesElement = document.getElementById("otherSpecies");
      if (this.pet.species === "other") {
        this.pet.species = "";
        otherSpeciesElement.classList.remove("d-none");
      } else {
        otherSpeciesElement.classList.add("d-none");
      }
    },
  },
};
</script>

<style scoped>
.personality {
  display: block;
}
.personality label {
  display: inline-block;
  margin: 0.5rem;
}

.personality p {
  display: inline-block;
  margin: 0.5rem;
  font-family: "Raleway", sans-serif;
  font-weight: 600;
}

h1 {
  margin-top: 45px;
}
</style>
