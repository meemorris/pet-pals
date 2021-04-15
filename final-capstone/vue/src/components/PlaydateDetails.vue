<template>
  <div id="details">
    <div id="small">
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
      <b-button v-b-toggle="playdateCollapse" class="m-1">More Details</b-button>
    </div>

    <b-collapse :id="playdateCollapse">
      <div id="large" v-if="$store.state.token != ''">
        <div id="pet-details">
          <p class="italic" v-if="playdate.pet.bio">{{ playdate.pet.bio }}</p>
          <p>
            {{ playdate.pet.energeticRelaxed }} |
            {{ playdate.pet.shyFriendly }} |
            {{ playdate.pet.apatheticCurious }}
          </p>
          <div id="pet-stats">
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
            <h5>
              {{ playdate.owner.firstName }} {{ playdate.owner.lastName }}
            </h5>
            <p class="italic" v-if="playdate.owner.bio">
              {{ playdate.owner.bio }}
            </p>
          </div>
        </div>
        <div id="join-playdate" v-show="$store.state.token != ''">
          <b-button id="button-join-playdate" v-b-toggle="formCollapse" class="m-1">
            Join Playdate
          </b-button>
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
      <b-collapse :id="formCollapse" class="join-form">
        <div v-if="$store.state.pets.length != 0">
          <form id="form-join-playdate" v-on:submit.prevent="addPetToPlaydate">
            <p>Which pet?</p>
            <div>
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
            </div>
          
            <div id="cancel-join-playdate-buttons">
              <button id="button-add-pet" type="submit">Join Playdate</button>
              <div id="button-divider"><span>&nbsp;|&nbsp;</span></div>
              <b-button
                v-b-toggle="formCollapse"
                id="button-cancel-add-pet"
                v-on:click="cancelJoin"
              >
                Cancel
              </b-button>
            </div>
          </form>
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
        </div>
        <div
          v-else
          class="alert alert-danger playdate-error-message"
          role="alert"
        >
          Please register a pet before joining a playdate.
        </div>
      </b-collapse>
    </b-collapse>
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
      showForm: false,
      isPetAttendee: false,
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
    playdateCollapse() {
      return "collapse-playdate" + this.playdate.playdateId;
    },
    formCollapse() {
      return "collapse-form" + this.playdate.playdateId;
    }
  },
  methods: {
    // maintainHighlightedBackground() {

    // },

    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
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
    cancelJoin() {
      this.errorMsg = "";
      this.clearData();
    },
    clearData() {
      this.petName = "";
      this.pet = {};
    },
  },
};
</script>

<style scoped>
.playdate-error-message {
  margin-top: 20px;
  margin-bottom: 0px;
}
#owner-details,
#playdate-details {
  margin-right: 30px;
}
#not-logged-in {
  height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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

.join-form {
  display: flex;
  justify-content: center;
}

#join-form p {
  text-align: center;
  margin-bottom: 0;
  font-family: "Raleway", sans-serif;
  font-size: 1.3rem;
}

#form-join-playdate {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.label-pet-name,
.input-pet-name {
  display: inline-block;
  margin: 0.5rem;
}

#large {
  display: grid;
  grid-template-columns: 3fr 2fr;
  grid-template-areas:
    "pet owner"
    "pet join";
}
#large p {
  font-size: 1.1rem;
}

#pet-details {
  grid-area: pet;
  margin-left: auto;
  margin-right: auto;
}

#owner-details {
  grid-area: owner;
  margin-left: auto;
  margin-right: auto;
}
#join-playdate {
  grid-area: join;
  align-self: center;
  margin-bottom: 0;
  justify-self: center;
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
  height: 120px;
}

.owner-pic {
  height: 100px;
  width: 100px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 10px;
  margin-top: auto;
  margin-bottom: auto;
}

#details:hover {
  background-color: #c4cad0;
  border-radius: 2%;
  cursor: pointer;
}

#details {
  padding: 20px;
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
  justify-content: center;
}

#button-divider {
  align-self: center;
}

.bolded {
  font-weight: 700;
}

.italic {
  font-style: italic;
}

#owner-content h4 {
  text-align: left;
}

#pet-stats p {
  margin: 0px;
}
</style>