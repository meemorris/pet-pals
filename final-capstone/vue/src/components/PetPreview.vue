<template>
  <div id="preview">
    <h3>{{ pet.name }}</h3>
    <img class="pet-pic" v-if="pet.pic" v-bind:src="pet.pic" />
    <img class="pet-pic" v-else src="@/assets/noPetPic.png" />
  </div>
</template>

<script>
export default {
  name: "petPreview",
  props: ["pet"],
};
</script>

<style scoped>
img {
    width: 90%;
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

#preview {
    display: flex;
    flex-direction: column;
    align-items: center;
}


</style>