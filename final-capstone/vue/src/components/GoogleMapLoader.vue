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
// import store from '@/store/index'

export default {
  props: {
    mapConfig: Object,
    apiKey: String,
    markers: Array,
    playdateList: Array,
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
  },
  methods: {
    initializeMap() {
      const mapContainer = this.$refs.googleMap;
      this.map = new this.google.maps.Map(mapContainer, this.mapConfig);

      this.markers.forEach((element) => {
        const playdate = this.playdateList.find(playdate => {
          return playdate.playdateId=element.id
        })
        const contentString = "<p>Playdate with " + playdate.pet.name + "</p>";
        const infoWindow = new this.google.maps.InfoWindow({
          size: new this.google.maps.Size(150, 50),
          content: contentString,
        });
        const marker = new this.google.maps.Marker({
          position: element.position,
          marker: element,
          map: this.map,
          icon:
            "https://www.gstatic.com/earth/images/stockicons/190201-2016-animal-paw_4x.png",
          title: "Playdate " + element.id
        });
        marker.addListener("click", () => {
          infoWindow.open(this.map, marker);
        })
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