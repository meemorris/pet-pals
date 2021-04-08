<template>
  <div class="pet-profile">
    <h1>Pet Profile</h1>

    <h2 id="pet-name">{{ pet.name }}</h2>
    
    <img id="pet-pic" v-bind:src="pet.pic" />
  
    <div id="playdates">
      <h3>Scheduled Playdates</h3>
    </div>

    <div class="profile-card">
     
        <h3 id="bio-header">Bio</h3>
        <p id="bio">{{ pet.bio }}</p>
      

        <h3 id="stats-header">Stats</h3>
        <div class="stats">
        <p><span class="profile-header">Species: </span>{{ pet.species }}</p>
        <p><span class="profile-header">Breed: </span>{{ pet.breed }}</p>
        <p>
          <span class="profile-header">Year of Birth: </span>{{ pet.birthYear }}
        </p>
        <p><span class="profile-header">Weight: </span>{{ pet.weight }} pounds</p>
      </div>

        <h3 id="personality-header">Personality</h3>
        <div class="personality">
        <p>{{ pet.energeticRelaxed }}</p>
        <p>{{ pet.shyFriendly }}</p>
        <p>{{ pet.apatheticCurious }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import petService from "@/services/PetService";

export default {
  name: "pet",
  props: ["id"],
  created() {
    this.retrievePet();
  },
  computed: {
    pet() {
      return this.$store.state.pet;
    },
  },
  methods: {
    retrievePet() {
      petService
        .getPet(this.id)
        .then((response) => {
          this.$store.commit("SET_CURRENT_PET", response.data);
        })
        .catch((error) => {
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
    },
  },
};
</script>

<style scoped>
.pet-profile {
  display: grid;
  grid-template-columns: 1fr, 1fr, 1fr;
  gap: 30px;
  grid-template-areas:
    "page-header page-header page-header"
    "name name name"
    "image image pd"
    "pet-info pet-info pd"
    ". . pd";
  margin: 0 30px;
}

h1 {
  grid-area: page-header;
  margin-left: 5%;
  margin-top: 3%;
}

#pet-name {
  grid-area: name;
  text-align: center;
}

#pet-pic {
  border-radius: 2%;
  grid-area: image;
  max-width: 30vw;
  margin-left: 30%;
}

#playdates {
  display: flex;
  grid-area: pd;
  border: 4px solid #cd704c;
  border-radius: 15px;
  margin: 20px 10px;
  padding: 30px 25px;
}

.profile-card {
  /* display: flex; */
  grid-area: pet-info;
  border: 4px solid #cd704c;
  border-radius: 15px;
  margin: 20px 10px;
  padding: 30px 25px;
}

.profile-card {
  display: grid;
  grid-template-columns: 1fr, 1fr;
  gap: 10px;
  grid-template-areas: 
  "b-header bio-info"
  "s-header stat-info"
  "p-header personality-info";
  align-items: center;
}

#bio-header {
  grid-area: b-header;
  padding-bottom: 5%;
  border-bottom: 3px solid #C4CAD0;
  align-self: initial;
}

#bio {
  grid-area: bio-info;
  margin-bottom: 10%;
}

#stats-header {
  grid-area: s-header;
  padding-bottom: 5%;
  border-bottom: 3px solid #C4CAD0;
  align-self: initial;
}

.stats {
  grid-area: stat-info;
  margin-bottom: 10%;
}

#personality-header {
  grid-area: p-header;
  padding-bottom: 5%;
  border-bottom: 3px solid #C4CAD0;
  margin-bottom: 10%;
  align-self: initial;
}

.personality {
  grid-area: personality-info;
}

</style>