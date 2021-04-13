<template>
  <div>
    <div v-for="playdate in playdateList" v-bind:key="playdate.playdateId">
      <p>
        Address: {{ playdate.address }}, {{ playdate.city }},
        {{ playdate.state }}
        {{ playdate.zip }}
      </p>
    </div>
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";
export default {
  name: "scheduledPlaydates",
  props: ["petId"],
  data() {
    return {
      playdateList: [],
    };
  },
  created() {
    this.getPlaydates();
  },
  methods: {
    getPlaydates() {
      return playdateService
        .getScheduledPlaydates(this.petId)
        .then((response) => {
          this.playdateList = response.data;
        });
    },
  },
};
</script>

<style>
</style>