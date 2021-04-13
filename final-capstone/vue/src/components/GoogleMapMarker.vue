<template>
  <div class="mapDetails" v-show="true">
    <h1>details!!!</h1>
    <!-- <playdate-details v-bind:playdate="playdate"/> -->
  </div>
</template>

<script>
//import { POINT_MARKER_ICON_CONFIG } from '@/constants/mapSettings'
// import PlaydateDetails from "@/components/PlaydateDetails.vue"

export default {
  // components: {
  //   PlaydateDetails
  // },
  data() {
    return {
      showDetails: false,
    };
  },
  computed: {
    // playdate(){
    //   return this.$store.state.playdateList.find((playdate)=> playdate.playdateId == this.marker.id)
    // }
  },
  props: {
    google: {
      type: Object,
      required: true,
    },
    map: {
      type: Object,
      required: true,
    },
    marker: {
      type: Object,
      required: true,
    },
  },

  mounted() {
    new this.google.maps.Marker({
      position: this.marker.position,
      marker: this.marker,
      map: this.map,
      icon:
        "https://www.gstatic.com/earth/images/stockicons/190201-2016-animal-paw_4x.png",
      //POINT_MARKER_ICON_CONFIG
    });
    // this.google.maps.event.addListener(this.marker,'click', () => {
    //   console.log("clicked");
    //   this.map.setZoom(8);
    //   this.map.setCenter(this.marker.getPosition());
    // });
    // this.$store.state.playdateList.forEach(element => {
    //   const id = element.playdayId
    //   this.displayDetails.id = false})
  },

  methods: {
    toggleDetails() {
      if (this.showDetails === false) {
        this.showDetails = true;
      } else {
        this.showDetails = false;
      }
    },
  },
  // render() {},
};
</script>

<style scoped>
.mapDetails {
  display: block;
  position: absolute;
}
</style>
