<template>
<div>
    <h1 id="schedule">Schedule Playdate</h1>
    <playdate-form></playdate-form>
    </div>
</template>

<script>
import PlaydateForm from '../components/PlaydateForm.vue'
export default {
    name: "createPlaydate",
    components: {
        PlaydateForm
    }
}

</script>

<style>
#schedule {
  margin-top: 45px;
  text-align: center;
}
</style>