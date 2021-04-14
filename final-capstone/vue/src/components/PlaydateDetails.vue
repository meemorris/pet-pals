<template>
  <div id="details" v-on:click="toggleDisplay">
    <div
      v-show="successMsg"
      class="alert alert-success playdate-joined-message"
      role="alert"
    >
      {{ successMsg }}
    </div>

    <div
      v-show="errorMsg"
      class="alert alert-danger playdate-error-message"
      role="alert"
    >
      {{ playdate.pet.name + errorMsg }}
    </div>
    <div id="small" v-show="showSmall">
      <img
        class="pet-pic"
        v-if="playdate.pet.pic"
        v-bind:src="playdate.pet.pic"
      />
      <img
        class="pet-pic default-pic"
        v-else
        src="@/assets/paws-default-white.png"
      />

      <h4>Playdate with {{ playdate.pet.name }}</h4>

      <p>
        Location: {{ playdate.address }}, {{ playdate.city }},
        {{ playdate.state }} {{ playdate.zip }}
      </p>
      <p>
        Date & Time:
        {{ moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
      </p>
    </div>

    <div id="large" v-show="showLarge">
      <div id="pet-pic">
        <img
          class="pet-pic"
          v-if="playdate.pet.pic"
          v-bind:src="playdate.pet.pic"
        />
        <img
          class="pet-pic default-pic"
          v-else
          src="@/assets/paws-default-white.png"
        />
      </div>
      <div id="title">
        <h4>Playdate with {{ playdate.pet.name }}</h4>
      </div>
      <div id="pet-details">
        <p v-if="playdate.pet.bio">{{ playdate.pet.bio }}</p>
        <p>
          {{ playdate.pet.energeticRelaxed }} | {{ playdate.pet.shyFriendly }} |
          {{ playdate.pet.apatheticCurious }}
        </p>
        <p>Species: {{ playdate.pet.species }}</p>
        <p v-if="playdate.pet.breed">Breed: {{ playdate.pet.breed }}</p>
        <p>
          Weight: {{ playdate.pet.weight }} pound{{
            playdate.pet.weight === 1 ? "" : "s"
          }}
        </p>
        <p v-if="age === 0">Less Than 1 Year Old</p>
        <p v-else>Age: {{ age }} year{{ age === 1 ? "" : "s" }}</p>
      </div>
      <div id="playdate-details">
        <p>
          Location: {{ playdate.address }}, {{ playdate.city }},
          {{ playdate.state }} {{ playdate.zip }}
        </p>
        <p>
          Date & Time:
          {{ moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
        </p>
      </div>
      <div id="owner-details">
        <img class="owner-pic" v-if="owner.pic" v-bind:src="owner.pic" />
        <img class="owner-pic" v-else src="@/assets/paw-outline-light.png" />
        <div id="owner-content">
          <h5>Owner</h5>
          <h6>{{ owner.firstName }} {{ owner.lastName }}</h6>
          <p v-if="owner.bio">{{ owner.bio }}</p>
        </div>
      </div>
      <div id="join-playdate" v-show="$store.state.token != ''">
        <button
          id="button-join-playdate"
          class="btn btn-primary"
          v-on:click="
            toggleShowForm();
            maintainLargeDisplay();
          "
        >
          Join Playdate
        </button>
      </div>
    </div>
    <div v-show="showForm">
      <form class="form-user" id="form" v-on:submit.prevent="addPetToPlaydate">
        <p>Which pet?</p>
        <label
          v-for="pet in $store.state.pets"
          v-bind:key="pet.petId"
          for="pet-name"
          class="label-pet-name"
        >
          <input
            class="input-pet-name"
            type="radio"
            id="pet-name"
            v-on:change="findPetByName"
            v-bind:value="pet.name"
            v-model="petName"
          />{{ pet.name }}</label
        >
        <button id="button-add-pet" class="btn btn-primary" type="submit">
          Join Playdate
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import userService from "@/services/UserService";
import playdateService from "@/services/PlaydateService";

export default {
  name: "playdateDetails",
  props: ["playdate"],
  components: {},
  created() {
    this.getOwner();
  },
  data() {
    return {
      errorMsg: "",
      successMsg: "",
      owner: {},
      showSmall: true,
      showLarge: false,
      showForm: false,
      pet: {
        petId: "",
        name: "",
        userId: "",
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
      petName: "",
    };
  },
  computed: {
    age() {
      return new Date().getFullYear() - this.playdate.pet.birthYear;
    },
  },
  methods: {
    maintainLargeDisplay() {
      this.showSmall = false;
    },

    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
    },

    toggleShowForm() {
      if (this.showForm) {
        this.showForm = false;
      } else {
        this.showForm = true;
      }
    },
    getOwner() {
      userService.getProfile(this.playdate.pet.userId).then((response) => {
        this.owner = response.data;
      });
    },
    toggleDisplay() {
      if (this.showSmall && !this.showForm) {
        this.showSmall = false;
        this.showLarge = true;
      } else {
        this.showSmall = true;
        this.showLarge = false;
        this.errorMsg = "";
        this.successMsg = "";
      }
    },
    addPetToPlaydate() {
      if (this.playdate.pet.name == this.pet.name) {
        this.errorMsg = " is already hosting this playdate.";
        this.showForm = false;
        this.toggleDisplay();
      } else {
        playdateService
          .joinPlaydate(this.playdate.playdateId, this.pet.petId)
          .then((response) => {
            if (response.status === 201) {
              this.successMsg = "Playdate joined, yay!";
            }
            this.showForm = false;
            this.toggleDisplay();
            this.pet = {};
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be joined. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Playdate could not be joined. Server could not be reached"
              );
            } else {
              alert(
                "Playdate could not be joined. Request could not be created."
              );
            }
          });
      }
    },
  },
};
</script>

<style scoped>
.playdate-joined-message {
  max-width: 200px;
}

.playdate-error-message {
  max-width: 350px;
}

#form {
  display: flex;
  justify-content: flex-end;
  margin-right: 10vw;
}

.label-pet-name,
.input-pet-name {
  display: inline-block;
  margin: 0.5rem;
}

#large {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
    "pic title title"
    "pic pet playdate"
    "pic pet owner"
    "pic pet join";
}
#pet-pic {
  grid-area: pic;
  display: flex;
  align-items: center;
  justify-content: center;
}
#title {
  grid-area: title;
  margin-top: 10px;
}
#pet-details {
  grid-area: pet;
}
#playdate-details {
  grid-area: playdate;
}
#owner-details {
  grid-area: owner;
}
#join-playdate {
  grid-area: join;
  margin-bottom: 10px;
}

#small .pet-pic {
  width: 100px;
  border-radius: 2%;
  margin: 10px;
}

#large .pet-pic {
  width: 250px;
  border-radius: 2%;
}

#small {
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin: 20px;
  height: 120px;
}

.owner-pic {
  height: 100px;
  width: 100px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 10px;
}

#details:hover {
  background-color: #c4cad0;
  border-radius: 2%;
  cursor: pointer;
}

h4 {
  text-align: center;
}

#owner-details {
  display: flex;
}

#owner-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>