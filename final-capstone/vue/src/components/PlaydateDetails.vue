<template>
  <div id="details" v-on:click="toggleDisplay">
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
        Date/Time:
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
          Date/Time:
          {{ moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
        </p>
      </div>
      <div id="owner-details">
        <img class="owner-pic" v-if="owner.pic" v-bind:src="owner.pic" />
        <img class="owner-pic" v-else src="@/assets/noPetPic.png" />
        <div id="owner-content">
          <h5>Owner</h5>
          <h6>{{ owner.firstName }} {{ owner.lastName }}</h6>
          <p v-if="owner.bio">{{ owner.bio }}</p>
        </div>
      </div>
      <div id="join-playdate">
        <!-- <router-link
          :to="{ name: 'createPlaydate' }"
          id="createPlaydate"
          tag="button"
          class="btn btn-primary"
          >Join Playdate</router-link
        > -->
        <button class="btn btn-primary">
          Join Playdate
        </button>

        <div id="selectPet">
          <div>
            <select
              name="petName"
              id="petName"
              v-on:click="findPetByName"
              v-on:change="togglePetQuestion"
              v-model="petName"
              required
            >
              <option value="" default disabled>Pet Name</option>
              <option v-for="pet in $store.state.pets" v-bind:key="pet.petId">
                {{ pet.name }}
              </option>
            </select>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import userService from "@/services/UserService";
import playdateService from "@/services/PlaydateService";
// import GoogleMapLoader from "./GoogleMapLoader";
export default {
  name: "playdateDetails",
  props: ["playdate"],
  created() {
    this.getOwner();
  },
  data() {
    return {
      owner: {},
      showSmall: true,
      showLarge: false,
      pet: {},
      petName: "",
      // distance: "",
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
    getOwner() {
      return userService
        .getProfile(this.playdate.pet.userId)
        .then((response) => {
          this.owner = response.data;
          // this.getDistance();
        });
    },
    // getDistance(){
    //   return GoogleMapLoader.getDistance(Number(this.$store.state.accountInfo.lat), Number(this.$store.state.accountInfo.lng), Number(this.playdate.lat), Number(this.playdate.lng))
    //   .then((response) => {
    //     this.distance = response.data;
    //   })
    // },
    toggleDisplay() {
      if (this.showSmall) {
        this.showSmall = false;
        this.showLarge = true;
      } else {
        this.showSmall = true;
        this.showLarge = false;
      }
    },
    addPetToPlaydate() {
      playdateService
      .joinPlaydate(this.playdate.playdateId, this.pet.petId)
      .then((response) => {
        if (response.status === 201) {
          this.$router.push('/playdates');
        }
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
    },

    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
      this.petName = '';
    },
    togglePetQuestion() {
      let petQuestionElement = document.getElementById("petName");
      if (this.petName == "") {
        petQuestionElement.classList.remove("d-none");
      } else {
        petQuestionElement.classList.add("d-none");
      }
    }
  },
};
</script>

<style scoped>
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