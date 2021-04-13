<template>
  <div>
    <h3>Scheduled Playdates</h3>
    <div class="playdate-card" v-for="playdate in playdateList" v-bind:key="playdate.playdateId">
      <h4>Playdate hosted by {{ playdate.pet.name }}</h4>
      <p><span class="playdate-header">
        Date/Time:</span>
        {{ moment(playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
      </p>
      <p>
        <span class="playdate-header">Location:</span> {{ playdate.address }}, {{ playdate.city }},
        {{ playdate.state }}
        {{ playdate.zip }}
      </p>
        <h5 v-show="playdate.attendeeList.length != 0">Attendees:</h5>
        <div
          v-for="attendee in playdate.attendeeList"
          v-bind:key="attendee.petId"
          id="attendee-list-block"
        >
          <p id="attendee-list-names">{{ attendee.name }}</p>
        </div>
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

<style scoped>

.playdate-card {
  border: 2px solid hsla(240, 1%, 40%, 0.2);
  border-radius: 1.5%;
  padding: 15px;
  margin-bottom: 10px;
}

.playdate-header {
  font-weight: 500;
}

h4 {
  font-size: 1.1rem;
  font-style: italic;
}

h5 {
  font-size: 1.1rem;
}

#attendee-list-names::before {
  content: "\2022";
  color: #a7acb1;
  font-weight: bold;
  display: inline-block;
  width: 1em;
}

#attendee-list-names {
  margin-left: 1em;
  margin-bottom: 1px;
}

h3 {
  margin-bottom: 20px;
}


</style>