<template>
  <GoogleMapLoader
  :mapConfig="mapConfig"
  apiKey="AIzaSyCMDIDFLUZUj0iH6vHHsSb-hFw4ZQmhgus"
  :markers="markers"
  :playdateList="playdateList"
>

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
      //return this.markers[0].position;
      return { lat: Number(this.$store.state.accountInfo.lat), lng: Number(this.$store.state.accountInfo.lng) }
    }
  }
};
</script>

<style>

</style>