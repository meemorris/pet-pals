<template>
  <div class="pet-profile">
    <h1>Pet Profile</h1>

    <h2 id="pet-name">{{ pet.name }}</h2>

    <img class="pet-pic" v-if="pet.pic" v-bind:src="pet.pic" />
    <img class="pet-pic" v-else src="@/assets/noPetPic.png" />

    <div id="playdates">
      <h3>Scheduled Playdates</h3>
    </div>

    <div class="profile-card">
      <div class="bio-group">
        <h3 v-if="pet.bio">Bio</h3>
        <div id="bio">
          <p v-if="pet.bio">{{ pet.bio }}</p>
        </div>
      </div>

      <div class="stats-group">
        <h3 v-if="pet.bio" id="stat-with-bio">Stats</h3>
        <h3 v-else id="stat-no-bio">Stats</h3>
        <div id="stats">
          <p><span class="profile-header">Species: </span>{{ pet.species }}</p>
          <p v-if="pet.breed">
            <span class="profile-header">Breed: </span>{{ pet.breed }}
          </p>
          <p>
            <span class="profile-header">Year of Birth: </span
            >{{ pet.birthYear }}
          </p>
          <p>
            <span class="profile-header">Weight: </span>{{ pet.weight }} pound{{
              pet.weight === 1 ? "" : "s"
            }}
          </p>
        </div>
      </div>

      <div class="personality-group">
        <h3>Personality</h3>
        <div id="personality-info">
          <p>{{ pet.energeticRelaxed }}</p>
          <p>{{ pet.shyFriendly }}</p>
          <!-- <i v-if="pet.apatheticCurious='apathetic'" class="fa fa-meh-o" aria-hidden="true"></i> -->
          <p>{{ pet.apatheticCurious }}</p>
        </div>
      </div>
    </div>

    <router-link
      :to="{ name: 'updatePet' }"
      id="updatePet"
      tag="button"
      class="btn btn-primary"
      v-if="checkUser"
      >Update Pet Info</router-link
    >

    <router-link
          :to="{ name: 'createPlaydate' }"
          id="createPlaydate"
          tag="button"
          class="btn btn-primary"
          >Schedule Playdate</router-link
        >

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
    checkUser() {
      return this.$store.state.user.id === this.pet.userId;
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
  margin-left: 1vw;
  margin-top: 5vh;
  font-family: 'Rock Salt', cursive;
  font-size: 1.8rem;
  color: #cd704c;
  background-image: url("../assets/paw-outline-light.png"); 
  background-size: 8%;
  background-repeat: no-repeat;
  background-position: 2% 30%;
  padding-top: 5%;
  padding-left: 9%;
}

h2 {
  font-family: 'Rock Salt', cursive;
  color: #07475f; 
  font-size: 1.8rem;
}

h3 {
  font-family: 'Rock Salt', cursive;
  color: #949494; 
  font-size: 1.4rem;
}

#pet-name {
  grid-area: name;
  text-align: center;
}

.pet-pic {
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
  grid-area: pet-info;
  border: 4px solid #cd704c;
  border-radius: 15px;
  margin: 20px 10px;
  padding: 30px 25px;
}

#updatePet {
  margin-bottom: 20%;
}

/* #createPlaydate {
  margin-bottom: 10%;
} */

#stats p::before, #personality-info p::before {
  content: "\2022"; 
  color: #a7acb1; 
  font-weight: bold; 
  display: inline-block; 
  width: 1em; 
  margin-left: -1em; 
}

.bio-group {
  display: flex;
}

.bio-group h3 {
  flex-basis: 50%;
  border-bottom: 3px solid hsla(240, 1%, 40%, 0.2);
  padding-bottom: 50px;
}

#bio {
  flex-basis: 63%;
}

.stats-group {
  display: flex;
  margin-top: 2%;
}

#stat-with-bio {
  flex-basis: 50%;
  padding-bottom: 140px;
  border-bottom: 3px solid hsla(240, 1%, 40%, 0.2);
}

#stat-no-bio {
  flex-basis: 50%;
  padding-bottom: 100px;
  border-bottom: 3px solid #c4cad0;
}

#stats {
  flex-basis: 60%;
}

.personality-group {
  display: flex;
  margin-top: 2%;
}

.personality-group h3 {
  flex-basis: 50%;
}

#personality-info {
  flex-basis: 60%;
}
</style>