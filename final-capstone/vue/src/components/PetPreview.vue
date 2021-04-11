<template>
  <div id="preview">
    <div class="card">
    <h3 class="card-title">{{ pet.name }}</h3>
    <img class="pet-pic card-img-top" v-if="pet.pic" v-bind:src="pet.pic" />
    <img class="pet-pic card-img-top" v-else src="@/assets/noPetPic.png" />
    </div>
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
    margin-top: 10px;
    width: 90%;
    max-width: 300px;
    margin-bottom: 25px;
}

.card {
  display: flex;
  align-items: center;
}

h3 {
    margin-top: 10px;
    font-size: 1.4rem;
}

#preview {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 3vh;
    background-color: #FAFAFA !important;
}

.card:hover {
  background-color: #c4cad0;
  border-radius: 2%;
  cursor: pointer;
}

</style>