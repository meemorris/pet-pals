<template>
  <form class="form-user" v-on:submit.prevent="sendForm">
    <div id="dateTimePicker">
      <!-- <label for="date-time" class="sr-only">Date and Time</label> -->
      <!-- line 10(value="currentDate") not currently functioning, looking into how to display the current date and time as default value -->
      <input
        type="datetime-local"
        id="date-time"
        name="dateTime"
        value="currentDate"
        class="form-control"
        v-model="playdate.dateTime"
        required
        autofocus
      />
    </div>

    <label for="address" class="sr-only">Address</label>
    <input
      type="text"
      id="address"
      class="form-control"
      placeholder="Street Address"
      v-model="playdate.address"
      required
    />

    <label for="city" class="sr-only">City</label>
    <input
      type="text"
      id="city"
      class="form-control"
      placeholder="City"
      v-model="playdate.city"
      required
    />

    <label for="state" class="sr-only">State</label>
    <input
      type="text"
      id="state"
      class="form-control"
      placeholder="State"
      v-model="playdate.state"
      required
    />

    <label for="zip" class="sr-only">Zip</label>
    <input
      type="text"
      id="zip"
      pattern="[0-9]*"
      class="form-control"
      placeholder="Zip Code"
      v-model="playdate.zip"
      required
    />

    <!-- <label for="petName" class="sr-only">Pet Name</label>
    <input
      type="text"
      id="petName"
      class="form-control"
      placeholder="Pet Name"
      v-model="playdate.petName"
      required
    /> -->

    <label for="petName">Pet Name</label>
    <select name="petName" id="petName" v-model="playdate.name" required>
        <option value="" default>---</option>
        <option v-for="pet in myPetsFilter" v-bind:key="pet.petId"
        >
        {{ pet.name }}
        </option>
    
    </select>

    <button class="btn btn-lg btn-primary" type="submit">
      Schedule Playdate
    </button>
  </form>
</template>

<script>

export default {
  name: "playdateForm",
  data() {
    return {
      playdate: {
        dateTime: "",
        address: "",
        city: "",
        state: "",
        zip: "",
        petName: "",
      },
    };
  },
};
</script>

<style>
.form-user {
  height: 100vh;
  margin-top: 5vh;
}

#date-time {
  width: 350px;
  height: 40px;
  margin-bottom: 8px;
}

#dateTimePicker {
  display: flex;
  justify-content: center;
}
</style>