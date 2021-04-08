<template>
  <div id="preview">
    <h3>{{ pet.name }}</h3>
    <img v-bind:src="pet.pic" />
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
    width: 20vw;
    max-width: 350px;
    min-width: 100px;
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