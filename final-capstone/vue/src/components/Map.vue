<template>
  <div class="Map">
      <!-- <GmapMap
      :center='center'
      :zoom='12'
      style='width:100%;  height: 400px;'
    /> -->
  </div>
</template>

<script>
// import gmapsInit from "./utils/gmaps";

// export default {
//   name: "Map",
//   async mounted() {
//     try {
//       const google = await gmapsInit();
//       const geocoder = new google.maps.Geocoder();
//       const map = new google.maps.Map(this.$el);
//       const locations = [
//         {
//           position: {
//             lat: 48.16091,
//             lng: 16.38333,
//           },
//         },
//         {
//           position: {
//             lat: 48.17427,
//             lng: 16.32962,
//           },
//         },
//         // ...
//       ];

//       geocoder.geocode({ address: "Austria" }, (results, status) => {
//         if (status !== "OK" || !results[0]) {
//           throw new Error(status);
//         }

//         map.setCenter(results[0].geometry.location);
//         map.fitBounds(results[0].geometry.viewport);
//       });

//       const markers = locations
//       .map(x => new google.maps.Marker({...x, map}));
//     } catch (error) {
//       console.error(error);
//     }
//   },
// };
</script>

<style>
/* html,
body {
  margin: 0;
  padding: 0;
}

.App {
  width: 100vw;
  height: 100vh;
} */
</style>