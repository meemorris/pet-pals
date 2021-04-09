<template>
  <div id="details">
    <h3>Playdate with {{ playdate.pet.name }}</h3>
    <img class="pet-pic" v-if="playdate.pet.pic" v-bind:src="playdate.pet.pic" />
    <img class="pet-pic" v-else src="@/assets/noPetPic.png" />
    <p>Location: {{playdate.address}}, {{playdate.city}}, {{playdate.state}} {{playdate.zip}}</p>
    <p>Date/Time: {{playdate.date}}</p>
  </div>
</template>

<script>
export default {
  name: "playdateDetails",
  props: ["playdate"],
};
</script>

<style scoped>
img {
    width: 200px;
     max-width: 350px;
    min-width: 150px; 
    border-radius: 2%;
    margin-bottom: 10px;
    margin-left:10px;
    margin-right:10px;
}

h3 {
    margin-top: 10px;
}



</style>