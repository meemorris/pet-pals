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
      this.getDistance(
        Number(this.$store.state.accountInfo.lat),
        Number(this.$store.state.accountInfo.lng),
        Number(element.lat),
        Number(element.lng)
      )
        .then((response) => {
          if (response.status === 200) {
            this.$store.commit(
              "ADD_PLAYDATE_DISTANCE",
              element.playdateId,
              response.data
            );
          }
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Distance could not be calculated. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Distance could not be calculated. Server could not be reached"
            );
          } else {
            alert(
              "Distance could not be calculated. Request could not be created."
            );
          }
        });
    });
  },

  methods: {
    initializeMap() {
      const mapContainer = this.$refs.googleMap;
      this.map = new this.google.maps.Map(mapContainer, this.mapConfig);
    },
    getDistance(LatOrigin, LngOrigin, LatDest, LngDest) {
      const origin = new this.google.maps.LatLng(LatOrigin, LngOrigin);
      const destination = new this.google.maps.LatLng(LatDest, LngDest);
      const service = new this.google.maps.DistanceMatrixService();
      console.log(origin);
      console.log(destination);
      console.log(service);
      return service.getDistanceMatrix({
        origins: [origin],
        destinations: [destination],
        travelMode: "DRIVING",
        unitSystem: this.google.maps.UnitSystem.imperial,
        // avoidHighways: false,
        // avoidTolls: false,
      });
    },
  },
};
</script>

<style scoped>
.google-map {
  width: 100%;
  min-height: 100%;
}
</style>