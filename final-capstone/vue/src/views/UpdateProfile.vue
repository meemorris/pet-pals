<template>
  <div id="update" class="text-center">
    <h1>Update Account Information</h1>
    <user-form v-bind:button="text"></user-form>
  </div>
</template>

<script>
import userForm from "@/components/UserForm";

export default {
  name: "updateProfile",
  components: {
    userForm,
  },
  data() {
    return {
      text: "Update",
    };
  },
};
</script>

<style scoped>
h1 {
  margin-top: 45px;
}
</style>