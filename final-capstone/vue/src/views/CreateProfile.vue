<template>
  <div id="register" class="text-center">
    <h1>Create Profile</h1>
    <user-form v-bind:button="text"></user-form>
  </div>
</template>

<script>
import userForm from "@/components/UserForm";

export default {
  name: "createProfile",
  components: {
    userForm,
  },
  data() {
    return {
      text: "Create",
    };
  },

};
</script>

<style scoped>
h1 {
  margin-top: 45px;
}
</style>