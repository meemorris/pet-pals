<template>
  <div>
      <h1>Playdates</h1>
    <div v-for="playdate in playdateList" v-bind:key="playdate.playdateId">
    <!-- <h3>Playdate with {{ playdate.pet.name }}</h3>
    <img class="pet-pic" v-if="playdate.pet.pic" v-bind:src="playdate.pet.pic" />
    <img class="pet-pic" v-else src="@/assets/noPetPic.png" />
    <p>Location: {{playdate.address}}, {{playdate.city}}, {{playdate.state}} {{playdate.zip}}</p>
    <p>Date/Time:{{playdate.date}}</p> -->
      <playdate-details v-bind:playdate="playdate"/>
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
    };
  },
  created() {
    this.createPlaydatesList();
  },

  components: {
    PlaydateDetails,
  },
  computed: {},
  methods: {
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