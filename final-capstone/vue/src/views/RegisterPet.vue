<template>
  <div id="register" class="text-center">
    <form class="form-registerPet">
        <h1>Register your pet.</h1>
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
      <select name="species" id = "species">
        <option value="Dog">Dog</option>
        <option value="Cat">Cat</option>
        <option value="Rabbit">Rabbit</option>
        <option value="Hedgehog">Hedgehog</option>
        <option value="Other">Other</option>
      </select>
      
      <label for="breed" class="sr-only">breed</label>
      <input
        type="text"
        id="breed"
        class="form-control"
        placeholder="Breed"
        v-model="pet.breed"
        required
      />
      <label for="weight" class="sr-only">weight</label>
      <input
        type="text"
        id="weight"
        class="form-control"
        placeholder="Weight"
        v-model="pet.weight"
        required
      />
      <label for="birthYear" class="sr-only">BirthYear</label>
      <input
        type="month"
        id="birthYear"
        class="form-control"
        placeholder="Birth Year"
        min="1970-01"
        value=""
        v-model="pet.birthYear"
        required
      />
      <label for="energetic_relaxed" class="sr-only">energetic_relaxed</label>
        <input 
        type="radio" 
        id="energetic" 
        name="energetic_relaxed"
        v-model="pet.energetic_relaxed"
        value="energetic">
        <label for="energetic"> Energetic</label><br>

        <input 
        type="radio" 
        id="relaxed" 
        name="energetic_relaxed"
        v-model="pet.energetic_relaxed" 
        value="relaxed">
        <label for="relaxed"> Relaxed</label><br>

      <label for="shy_friendly" class="sr-only">Shy_Friendly</label>
      <input 
        type="radio" 
        id="shy" 
        name="shy_friendly" 
        v-model="pet.shy_friendly"
        value="shy">
        <label for="shy"> Shy</label><br>
        <input 
        type="radio" 
        id="friendly" 
        name="shy_friendly"
        v-model="pet.shy_friendly" 
        value="friendly">
        <label for="friendly"> Friendly</label><br>
      
      <label for="apathetic_curious" class="sr-only">Shy_Friendly</label>
      <input 
        type="radio" 
        id="apathetic" 
        name="apathetic_curious"
        v-model="pet.apathetic_curious" 
        value="apathetic">
        <label for="apathetic"> Apathetic</label><br>
        <input 
        type="radio" 
        id="curious" 
        name="apathetic_curious" 
        v-model="pet.apathetic_curious" 
        value="curious">
        <label for="curious"> Curious</label><br>

      <label for="bio" class="sr-only">Bio</label>
      <input
        type="text"
        id="Bio"
        class="form-control"
        placeholder="Bio"
        v-model="pet.Bio"
        required
      />
      <button class="btn btn-lg btn-primary" type="submit">
        Register Pet
      </button>
    </form>
  </div>
</template>

<script>
import petService from '../services/PetService';

export default {
  name: 'registerPet',
  data() {
    return {
      pet: {
        name: '',
        species: '',
        breed: '',
        weight: '',
        birthYear: '',
        energetic_relaxed: '',
        shy_firendly: '',
        apathetic_curious: '',
        bio: '',
      },
    };
  },
  methods: {
    register() {
        petService.registerPet(this.pet).then (response => {
            if(response.status===201){
            this.$router.push("/");
            }
        })
        .catch(error => {
            if(error.response){
                alert('Pet could not be registered. Response was ' + error.response.statusText );
            } else if (error.request){
                alert('Pet could not be registered. Server could not be reached');
            }else{
                alert('Pet could not be registered. Request could not be created.');
            }
        })
    }
  },
};
</script>

<style></style>
