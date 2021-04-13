<template>
  <div>
    <playdate-list/>
  </div>
</template>

<script>
import PlaydateList from "../components/PlaydateList.vue";
import userService from "@/services/UserService";

export default {
  name: "playdates",
  components: {
    PlaydateList,
  },
  created(){
    this.retrieveAccountInfo;
  },
  methods: {
    retrieveAccountInfo() {
      userService
        .getProfile(this.$store.state.user.id)
        .then((response) => {
          if (response.data.firstName === null) {
            this.$router.push("/profile/create");
          } else {
            this.$store.commit("SET_ACCOUNT_INFO", response.data);
          }
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Account information could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Account information could not be found. Server could not be reached"
            );
          } else {
            alert(
              "Account information could not be found. Request could not be created."
            );
          }
        });
    },
  }
}
</script>

<style scoped>

</style>