<template>
<div>
  <h1>Home of Your Future Profile Page</h1>
  <router-link v-bind:to= "{name : 'registerPet'}">Register Pet</router-link>
  <router-link v-bind:pet="pet">Pet Profile</router-link>
</div>
</template>

<script>
export default {

}
</script>

<style>

</style>