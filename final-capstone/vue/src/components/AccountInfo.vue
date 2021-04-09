<template>
  <div id="accountInfo">
    <img v-bind:src="account.pic" />
    <h3>{{ account.firstName }} {{ account.lastName }}</h3>
    <h4>{{account.bio}}</h4>
    <p>{{ account.email }}</p>
    <p>{{ account.phone }}</p>
    <p>{{ account.address }}</p>
    <p>{{ account.city }}, {{ account.state }} {{ account.zip }}</p>
    <router-link
      :to="{ name: 'updateProfile' }"
      id="updateProfile"
      tag="button"
      class="btn btn-primary"
      >Update Account Info</router-link
    >
  </div>
</template>

<script>
export default {
  name: "accountInfo",
  computed: {
    account() {
      return this.$store.state.accountInfo;
    },
  },
};
</script>

<style scoped>
img {
  width: 75%;
  max-width: 250px;
  min-width: 150px;
  border-radius: 2%;
  margin-bottom: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
h4 {
    font-style: italic;
}
#accountInfo {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>