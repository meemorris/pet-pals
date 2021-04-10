<template>
  <div id="list">
    <h1>Playdates</h1>

    <h2 id="species-header">Species</h2>
    <!-- <h2>Energy</h2>
    <h2>Timidity</h2>
    <h2>Curiosity</h2> -->
    <table id="playdateFilter">
      <tr>
        <td>
          <select
            name="species"
            id="species"
            v-model="filter.pet.species"
            v-on:change="toggleOtherSpecies"
          >
            <option value="" default selected>Please select an option</option>
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
        </td>
        <td>
          <!-- <select
            name="energy"
            id="energy"
            v-model="filter.pet.energeticRelaxed"
          >
            <option value="" default selected>Please select an option</option>
            <option value="energetic">Energetic</option>
            <option value="relaxed">Relaxed</option>

          </select> -->
        </td>
      </tr>
    </table>

    <div v-for="playdate in filteredList" v-bind:key="playdate.playdateId">
      <playdate-details v-bind:playdate="playdate" />
    </div>
  </div>
</template>
<script>
import playdateService from "@/services/PlaydateService";
import PlaydateDetails from "@/components/PlaydateDetails.vue";

export default {
  name: "playdates",
  data() {
    return {
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
  },
  computed: {
    filteredList() {
      let filteredPlaydates = this.playdateList;
      if (this.filter.pet.species != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) => playdate.pet.species == this.filter.pet.species
        );
      }
      return filteredPlaydates;
    },
  },
  methods: {
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

#species {
  margin-left: 60px;
  font-size: 1.1rem;
  margin-bottom: 3vh;
  height: 35px;
  border: 1px solid hsla(210, 6%, 67%, 0.6);
  border-radius: 3%;
}

#species-header {
  margin-left: 58px;
  font-size: 1.2rem;
  font-weight: bold;
}

#otherSpecies {
  margin-left: 60px;
}
</style>