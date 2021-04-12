<template>
  <div id="list">
    <h1>Playdates</h1>
    <button class="btn btn-primary" v-on:click="toggleDisplayType">
      View as {{ displayType === "List" ? "Map" : "List" }}
    </button>

    <div id="filters">
      <div id="species-group">
        <select
          name="species"
          id="species"
          v-model="filter.pet.species"
          v-on:change="toggleOtherSpecies"
        >
          <option value="" default selected disabled>Species</option>
          <option value="">View All</option>
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
          v-model="filter.pet.species"
          required
        />
      </div>

      <div id="energy-group">
        <select name="energy" id="energy" v-model="filter.pet.energeticRelaxed">
          <option value="" default selected disabled>Energy</option>
          <option value="">View All</option>
          <option value="energetic">Energetic</option>
          <option value="relaxed">Relaxed</option>
        </select>
      </div>

      <div id="timidity-group">
        <select name="timidity" id="timidity" v-model="filter.pet.shyFriendly">
          <option value="" default selected disabled>Timidity</option>
          <option value="">View All</option>
          <option value="shy">Shy</option>
          <option value="friendly">Friendly</option>
        </select>
      </div>

      <div id="curiosity-group">
        <select
          name="curiosity"
          id="curiosity"
          v-model="filter.pet.apatheticCurious"
        >
          <option value="" default selected disabled>Curiosity</option>
          <option value="">View All</option>
          <option value="apathetic">Apathetic</option>
          <option value="curious">Curious</option>
        </select>
      </div>

      <div id="distance-group">
        <select name="distance" id="distance">
          <option value="" default selected disabled>Max Distance</option>
          <option value="">Any Distance</option>
          <option value="fiveMiles">5 miles</option>
          <option value="tenMiles">10 miles</option>
          <option value="fifteenMiles">15 miles</option>
          <option value="twentyMiles">20 miles</option>
          <option value="twentyFiveMiles">25 miles</option>
          <option value="fiftyMiles">50 miles</option>
        </select>
      </div>
    </div>

    <p v-show="filteredList.length == 0" id="no-results">No results found</p>

    <div id="list-view" v-if="displayTypeIsList">
      <div v-for="playdate in filteredList" v-bind:key="playdate.playdateId">
        <playdate-details v-bind:playdate="playdate" />
      </div>
    </div>
    <TravelMap v-else class="travel-map" />
  </div>
</template>
<script>
import playdateService from "@/services/PlaydateService";
import PlaydateDetails from "@/components/PlaydateDetails.vue";
import TravelMap from "@/components/TravelMap";

export default {
  name: "playdates",
  data() {
    return {
      displayType: "List",
      playdateList: [],
      filter: {
        playdate_id: "",
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
        address: "",
        city: "",
        state: "",
        zip: "",
        date: "",
      },
    };
  },
  created() {
    this.createPlaydatesList();
  },

  components: {
    PlaydateDetails,
    TravelMap,
  },
  computed: {
    displayTypeIsList() {
      return this.displayType === "List";
    },
    filteredList() {
      let filteredPlaydates = this.playdateList;
      const isListedOption =
        this.filter.pet.species === "dog" ||
        this.filter.pet.species === "cat" ||
        this.filter.pet.species === "rabbit" ||
        this.filter.pet.species === "hedgehog";
      if (this.filter.pet.species != "" && !isListedOption) {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.species.toLowerCase() ==
            this.filter.pet.species.toLowerCase()
        );
      } else if (this.filter.pet.species != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) => playdate.pet.species == this.filter.pet.species
        );
      }

      if (this.filter.pet.energeticRelaxed != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.energeticRelaxed == this.filter.pet.energeticRelaxed
        );
      }

      if (this.filter.pet.shyFriendly != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) => playdate.pet.shyFriendly == this.filter.pet.shyFriendly
        );
      }

      if (this.filter.pet.apatheticCurious != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.apatheticCurious == this.filter.pet.apatheticCurious
        );
      }

      return filteredPlaydates;
    },
  },
  methods: {
    toggleDisplayType() {
      if (this.displayType === "List") {
        this.displayType = "Map";
      } else {
        this.displayType = "List";
      }
    },
    toggleOtherSpecies() {
      let otherSpeciesElement = document.getElementById("otherSpecies");
      if (this.filter.pet.species === "other") {
        this.filter.pet.species = "";
        otherSpeciesElement.classList.remove("d-none");
      } else {
        otherSpeciesElement.classList.add("d-none");
      }
    },
    createPlaydatesList() {
      playdateService
        .getListOfPlaydates()
        .then((response) => {
          this.playdateList = response.data;
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Playdates list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Playdates list could not be found. Server could not be reached"
            );
          } else {
            alert(
              "Playdates list could not be found. Request could not be created."
            );
          }
        });
    },
  },
};
</script>

<style scoped>
.travel-map {
  height: 400px;
}
#list {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

h1 {
  text-align: center;
  margin-top: 45px;
  margin-bottom: 30px;
}

#filters {
  display: flex;
  justify-content: space-around;
  margin-bottom: 3vh;
  font-size: 1.1rem;
  margin-top: 3vh;
}

#species {
  border: 1px solid hsla(210, 6%, 67%, 0.6);
  border-radius: 3%;
  height: 35px;
  width: 18vw;
  max-width: 250px;
  padding-left: 5px;
}

#energy,
#timidity,
#curiosity,
#distance {
  border: 1px solid hsla(210, 6%, 67%, 0.6);
  border-radius: 3%;
  height: 35px;
  width: 18vw;
  max-width: 250px;
  padding-left: 5px;
}

#otherSpecies {
  margin-top: 2vh;
  width: 18vw;
  max-width: 250px;
}

#no-results {
  text-align: center;
  margin-top: 5vh;
}
</style>