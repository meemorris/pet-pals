<template>
  <GoogleMapLoader
  :mapConfig="mapConfig"
  apiKey="AIzaSyCMDIDFLUZUj0iH6vHHsSb-hFw4ZQmhgus"
  :markers="markers"
  :playdateList="playdateList"
>
  <!-- <template slot-scope="{ google, map }">
    <GoogleMapMarker
      v-for="marker in markers"
      :key="marker.id"
      :marker="marker"
      :google="google"
      :map="map"
    
    />
  </template> -->
</GoogleMapLoader>
</template>

<script>
import GoogleMapLoader from "./GoogleMapLoader";
// import GoogleMapMarker from "./GoogleMapMarker";


import { mapSettings } from "@/constants/mapSettings";

export default {
  name: "travelMap",
  components: {
    GoogleMapLoader,
    // GoogleMapMarker,

  },
  props: ["playdateList", "markers"],

  methods:{

   
  },

  data() {
    return {
     
    };
  },

  computed: {
    mapConfig() {
      return {
        ...mapSettings,
        center: this.mapCenter
      };
    },

    mapCenter() {
      return this.markers[1].position;
    }
  }
};
</script>

<style>

</style>