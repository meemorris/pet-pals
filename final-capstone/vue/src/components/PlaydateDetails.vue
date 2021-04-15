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
        <span class="bolded">Location: </span
        ><span v-show="$store.state.token != ''">{{ playdate.address }}, </span
        >{{ playdate.city }}, {{ playdate.state }} {{ playdate.zip }}
      </p>
      <p>
        <span class="bolded">Date & Time: </span>
        {{ moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
      </p>
    </div>

    <div>
      <div id="large"  v-show="showLarge" v-if="$store.state.token != ''">
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
          <h3>Playdate with {{ playdate.pet.name }}</h3>
        </div>
        <div id="pet-details">
          <p v-if="playdate.pet.bio">{{ playdate.pet.bio }}</p>
          <p>
            {{ playdate.pet.energeticRelaxed }} |
            {{ playdate.pet.shyFriendly }} |
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
            <span class="bolded">Location: </span>{{ playdate.address }},
            {{ playdate.city }}, {{ playdate.state }} {{ playdate.zip }}
          </p>
          <p>
            <span class="bolded">Date & Time: </span>
            {{
              moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a")
            }}
          </p>
        </div>
        <div id="owner-details">
          <img
            class="owner-pic"
            v-if="playdate.owner.pic"
            v-bind:src="playdate.owner.pic"
          />
          <!-- <img class="owner-pic" v-else src="@/assets/paw-outline-light.png" /> -->
          <img class="owner-pic" v-else src="@/assets/default-user-pic.jpg" />
          <div id="owner-content">
            <h4>Owner</h4>
            <h6>
              {{ playdate.owner.firstName }} {{ playdate.owner.lastName }}
            </h6>
            <p v-if="playdate.owner.bio">{{ playdate.owner.bio }}</p>
          </div>
        </div>
        <div id="join-playdate" v-show="$store.state.token != ''">
          <button
            id="button-join-playdate"
            class="btn btn-primary"
            v-on:click.stop="
              toggleShowForm();
              attemptingJoin();
            "
          >
            Join Playdate
          </button>
        </div>
        </div>
      <div id="not-logged-in" v-else>
        <h4>
          Please log in or register to view more details or join a playdate
        </h4>
        <router-link
          :to="{ name: 'home' }"
          tag="button"
          id="back-to-home"
          class="btn btn-primary"
          >Back To Home</router-link
        >
      </div>
        <div v-if="$store.state.pets.length != 0" v-show="showForm">
          <form
            class="form-user"
            id="form-join-playdate"
            v-on:submit.prevent.stop="addPetToPlaydate"
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
                name="pet-name"
                v-on:change="findPetByName"
                v-bind:value="pet.name"
                v-model="petName"
                required
              />{{ pet.name }}</label
            >

            <div id="cancel-join-playdate-buttons">
              <button id="button-add-pet" type="submit">Join Playdate</button>
              <div id="button-divider"><span>&nbsp;|&nbsp;</span></div>
              <button
                id="button-cancel-add-pet"
                v-on:click="cancelJoinPlaydate"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
        <div
          v-show="preemptiveJoinAttempt"
          class="alert alert-danger playdate-error-message"
          role="alert"
        >
          Please register a pet before joining a playdate.
        </div>
      
    </div>
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";

export default {
  name: "playdateDetails",
  props: ["playdate"],
  components: {},
  created() {},
  data() {
    return {
      errorMsg: "",
      successMsg: "",
      joinPlaydateCancelled: false,
      showSmall: true,
      showLarge: false,
      showForm: false,
      isPetAttendee: false,
      preemptiveJoinAttempt: false,
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
      }
    },
    addPetToPlaydate() {
      if (
        this.playdate.pet.name == this.pet.name &&
        !this.joinPlaydateCancelled
      ) {
        this.errorMsg =
          this.playdate.pet.name + " is already hosting this playdate.";
        this.clearData();
      } else if (
        this.playdate.attendeeList.some(
          (attendee) => attendee.name == this.pet.name
        )
      ) {
        this.errorMsg = this.pet.name + " is already attending this playdate.";
        this.clearData();
      } else if (!this.joinPlaydateCancelled) {
        playdateService
          .joinPlaydate(this.playdate.playdateId, this.pet.petId)
          .then((response) => {
            if (response.status === 201) {
              this.successMsg = "Playdate joined, yay!";
              this.createPlaydatesList();
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
    createPlaydatesList() {
      playdateService.getListOfPlaydates().then((response) => {
        this.$store.commit("SET_PLAYDATE_LIST", response.data);
        this.populateMarkers();
      });
    },
    cancelJoinPlaydate() {
      this.joinPlaydateCancelled = true;
      this.showForm = false;
      this.showSmall = false;
    },
    clearData() {
      this.showForm = false;
      this.petName = "";
      this.pet = {};
      this.toggleDisplay();
    },
    attemptingJoin() {
      if (this.$store.state.pets.length == 0) {
        this.preemptiveJoinAttempt = true;
      }
    },
  },
};
</script>

<style scoped>
#owner-details, #playdate-details {
  margin-right: 30px;
}
#not-logged-in {
  height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 20px;
}

#not-logged-in h4 {
  text-align: center;
}

#back-to-home {
  margin-bottom: 0px;
}

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
    "title title title"
    "pic pet playdate"
    "pic pet owner"
    ". join .";
}
#large p {
  font-size: 1.1rem;
}
#pet-pic {
  grid-area: pic;
  display: flex;
  align-items: flex-start;
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
  justify-content: space-between;
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

h3 {
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

.bolded {
  font-weight: 700;
}

#owner-content h4 {
  text-align: left;
}
</style>