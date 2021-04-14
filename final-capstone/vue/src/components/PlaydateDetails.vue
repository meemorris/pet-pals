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
      <!-- <div v-show="hostNameSelected">{{ hostNameSelected }}</div>
      <div v-show="petAlreadyAttending">{{ petAlreadyAttending }}</div> -->
      {{ errorMsg }}
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
        <!-- <img class="owner-pic" v-else src="@/assets/paw-outline-light.png" /> -->
        <img class="owner-pic" v-else src="@/assets/default-user-pic.jpg" />
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
          v-on:click="toggleShowForm"
        >
          Join Playdate
        </button>
      </div>
    </div>
    <div v-show="showForm">
      <form
        class="form-user"
        id="form-join-playdate"
        v-on:submit.prevent="addPetToPlaydate"
      >
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
            v-on:change="
              findPetByName();
              isAttendee();
              hostSelected();
            "
            v-bind:value="pet.name"
            v-model="petName"
            required
          />{{ pet.name }}</label
        >

        <div id="cancel-join-playdate-buttons">
          <button id="button-add-pet" type="submit">Join Playdate</button>
          <div id="button-divider"><span>&nbsp;|&nbsp;</span></div>
          <button id="button-cancel-add-pet" v-on:click="cancelJoinPlaydate">
            Cancel
          </button>
        </div>
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
      joinPlaydateCancelled: false,
      owner: {},
      showSmall: true,
      showLarge: false,
      showForm: false,
      isPetAttendee: false,
      petAlreadyAttending: "",
      hostNameSelected: "",
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
    // maintainHighlightedBackground() {

    // },

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
      return userService
        .getProfile(this.playdate.pet.userId)
        .then((response) => {
          this.owner = response.data;
        });
    },
    toggleDisplay() {
      if (this.showSmall && !this.showForm) {
        this.showSmall = false;
        this.showLarge = true;
        this.joinPlaydateCancelled = false;
      } else {
        this.showSmall = true;
        this.showLarge = false;
        this.errorMsg = "";
        this.successMsg = "";
        // this.petAlreadyAttending = "";
        // this.hostNameSelected = "";

      }
    },
    addPetToPlaydate() {
      if (
        this.playdate.pet.name == this.pet.name &&
        !this.joinPlaydateCancelled
      ) {
        this.errorMsg = " is already hosting this playdate.";
        this.clearData();
      } else if (this.isPetAttendee && !this.joinPlaydateCancelled) {
        this.errorMsg = " is already attending this playdate.";
        this.clearData();
      } else if (!this.joinPlaydateCancelled) {
        playdateService
          .joinPlaydate(this.playdate.playdateId, this.pet.petId)
          .then((response) => {
            if (response.status === 201) {
              this.successMsg = "Playdate joined, yay!";
            }
            this.clearData();
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
    cancelJoinPlaydate() {
      this.joinPlaydateCancelled = true;
      this.showForm = false;
      this.showSmall = false;
    },
    isAttendee() {
      this.playdate.attendeeList.forEach((attendee) => {
        if (attendee.name == this.petName) {
          this.petAlreadyAttending = attendee.name;
          this.isPetAttendee = true;
        }
      });
    },
    hostSelected() {
      if (this.playdate.pet.name == this.petName) {
        this.hostNameSelected = this.petName;
      }
    },
    clearData() {
      this.showForm = false;
      this.petName = "";
      this.pet = {};
      this.toggleDisplay();
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

#button-add-pet,
#button-cancel-add-pet {
  background-color: transparent;
  border: none;
  color: #07475f;
  font-weight: bold;
}

#button-add-pet:hover,
#button-cancel-add-pet:hover {
  color: #cd704c;
}

#cancel-join-playdate-buttons {
  display: flex;
  margin-left: 35px;
}

#button-divider {
  align-self: center;
}
</style>