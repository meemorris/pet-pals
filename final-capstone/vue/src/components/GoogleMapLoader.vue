<template>
  <div>
    <div class="google-map" ref="googleMap">
      <template v-if="Boolean(this.google) && Boolean(this.map)">
        <slot :google="google" :map="map" />
      </template>
    </div>
  </div>
</template>

<script>
import GoogleMapsApiLoader from "google-maps-api-loader";
import store from '@/store/index'

export default {
  props: {
    mapConfig: Object,
    apiKey: String,
  },
  data() {
    return {
      google: null,
      map: null,
    };
  },
  async mounted() {
    const googleMapApi = await GoogleMapsApiLoader({
      apiKey: this.apiKey,
    });
    this.google = googleMapApi;
    this.initializeMap();
    this.$store.state.playdateList.forEach((element) => {
      
      const LatOrigin = Number(this.$store.state.accountInfo.lat);
      const LngOrigin =  Number(this.$store.state.accountInfo.lng);
      const LatDest = Number(element.lat);
      const LngDest =  Number(element.lng);
      const origin = new this.google.maps.LatLng(LatOrigin, LngOrigin);
      const destination = new this.google.maps.LatLng(LatDest, LngDest);
      const service = new this.google.maps.DistanceMatrixService();
      //  return new Promise((resolve, reject) => {
        service.getDistanceMatrix(
          {
            origins: [origin],
            destinations: [destination],
            travelMode: "DRIVING",
            unitSystem: this.google.maps.UnitSystem.IMPERIAL,
            // avoidHighways: false,
            // avoidTolls: false,
          },
          function (resp, status) {
            if (status == "OK") {
              // var origins = response.originAddresses;
              // var destinations = response.destinationAddresses;
              const resultat = resp.rows[0].elements[0].distance.text;
              console.log("distance : " + resultat);
              const distanceArray = resultat.split(" ");
              const distance = distanceArray[0];
              const id = element.playdateId;
              const data = {
                "id": id,
                "distance": Number(distance)
              }
              store.commit(
                "ADD_PLAYDATE_DISTANCE",
                data
              );
            } 
          }
        );
    // })
    })},
  methods: {
    initializeMap() {
      const mapContainer = this.$refs.googleMap;
      this.map = new this.google.maps.Map(mapContainer, this.mapConfig);
    },
    // async getDistance(LatOrigin, LngOrigin, LatDest, LngDest, element) {
    //   const origin = new this.google.maps.LatLng(LatOrigin, LngOrigin);
    //   const destination = new this.google.maps.LatLng(LatDest, LngDest);
    //   const service = new this.google.maps.DistanceMatrixService();

    //   return new Promise((resolve, reject) => {
    //     let response;
    //     service.getDistanceMatrix(
    //       {
    //         origins: [origin],
    //         destinations: [destination],
    //         travelMode: "DRIVING",
    //         unitSystem: this.google.maps.UnitSystem.IMPERIAL,
    //         // avoidHighways: false,
    //         // avoidTolls: false,
    //       },
    //       function (resp, status) {
    //         if (status == "OK") {
    //           // var origins = response.originAddresses;
    //           // var destinations = response.destinationAddresses;
    //           var resultat = resp.rows[0].elements[0].distance.text;
    //           console.log("distance : " + resultat);
    //           this.$store.commit(
    //             "ADD_PLAYDATE_DISTANCE",
    //             element.playdateId,
    //             resultat
    //           );
    //           // resolve(resp);
    //           response = resultat;
    //         } else {
    //           response = reject(status);
    //         }
    //       }
    //     );
    //     return response;
    //   });
    // },
  },
};
</script>

<style scoped>
.google-map {
  width: 100%;
  min-height: 100%;
}
</style>