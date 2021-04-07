<template>
  <div>
    <img v-bind:src="pet.pic" />
    <h2>{{ pet.name }}</h2>
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
    width: 100px;
}
</style>