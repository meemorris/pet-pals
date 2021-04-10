<template>
  <div id="list">
      <h1>Playdates</h1>
    <div v-for="playdate in playdateList" v-bind:key="playdate.playdateId">
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

<style scoped>
#list {
    display: flex;
    flex-direction: column;
}
h1 {
    text-align: center;
    margin-top: 45px;
}
</style>