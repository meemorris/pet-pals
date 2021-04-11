<template>
  <div>
    <div class="outline">
      <h1 class="form-header">Schedule Playdate</h1>
      <playdate-form></playdate-form>
    </div>
  </div>
</template>

<script>
import PlaydateForm from "../components/PlaydateForm.vue";
export default {
  name: "createPlaydate",
  components: {
    PlaydateForm,
  },
};
</script>

<style>
#schedule {
  margin-top: 45px;
  text-align: center;
}
</style>