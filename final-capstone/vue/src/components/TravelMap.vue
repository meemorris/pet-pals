<template>
  <GoogleMapLoader
  :mapConfig="mapConfig"
  apiKey="AIzaSyCMDIDFLUZUj0iH6vHHsSb-hFw4ZQmhgus"
>
  <template slot-scope="{ google, map }">
    <GoogleMapMarker
      v-for="marker in markers"
      :key="marker.id"
      :marker="marker"
      :google="google"
      :map="map"
    />
    <!-- <GoogleMapLine
      v-for="line in lines"
      :key="line.id"
      :path.sync="line.path"
      :google="google"
      :map="map"
    /> -->
  </template>
</GoogleMapLoader>
</template>

<script>
import GoogleMapLoader from "./GoogleMapLoader";
import GoogleMapMarker from "./GoogleMapMarker";
// import GoogleMapLine from "./GoogleMapLine";
//import MapService from "@/services/MapService"

import { mapSettings } from "@/constants/mapSettings";

export default {
  name: "travelMap",
  components: {
    GoogleMapLoader,
    GoogleMapMarker,
    // GoogleMapLine
  },
  props: ["playdateList", "mapMarkers"],
  // created(){
  //   this.populateMarkers();
  // },
  methods:{
    // populateMarkers(){
    //   this.playdateList.forEach(playdate => {
    //     const addressString = playdate.address + " " + playdate.city + " " + playdate.state;
    //     const replaced = addressString.split(' ').join('%20');
    //     let marker = {
    //       id : addressString,
    //       position: { lat: "", lng: "" }
    //     };
    //     MapService.getLatAndLong(replaced)
    //     .then((response) => {
    //       marker.position.lat = response.geometry.location.lat;
    //       marker.position.lng = response.geometry.location.lng;
    //     })
    //     this.markers.push(marker);
    //   })
    // }
  },

  data() {
    return {
      // markers: [
      //   {
      //     id: "a",
      //     position: { lat: 39.3686391, lng: -84.346541 }
      //   },
      //   {
      //     id: "b",
      //     position: { lat: 39.3090199, lng: -84.3074929 }
      //   },
        // {
        //   id: "c",
        //   position: { lat: 39.2519331, lng: -84.38789640000002 }
        // }
      // ],
      // lines: [
      //   {
      //     id: "1",
      //     path: [{ lat: 3, lng: 101 }, { lat: 5, lng: 99 }]
      //   },
      //   {
      //     id: "2",
      //     path: [{ lat: 5, lng: 99 }, { lat: 6, lng: 97 }]
      //   }
      // ]
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