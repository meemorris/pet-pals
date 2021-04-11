<template>
  <div id="profile">
    <h1>Welcome!</h1>
    <div id="columns">
      <div id="petColumn">
        <h2>Your Pets</h2>
        <div
          class="preview"
          v-for="pet in $store.state.pets"
          v-bind:key="pet.petId"
          v-on:click="viewPetDetails(pet.petId)"
        >
          <pet-preview v-bind:pet="pet" />
        </div>
        <router-link
          :to="{ name: 'registerPet' }"
          id="registerPet"
          tag="button"
          class="btn btn-primary"
          >Register New Pet</router-link
        >
      </div>

      <div id="accountColumn">
        <account-info></account-info>
      </div>

      <div id="playdateColumn">
        <h2>Current Playdates</h2>
        <div id="playdate-info">
          </div>
          <router-link
            :to="{ name: 'createPlaydate' }"
            id="createPlaydate"
            tag="button"
            class="btn btn-primary"
            >Schedule Playdate</router-link
          >
      </div>
    </div>
  </div>
</template>

<script>
import petService from "@/services/PetService";
import petPreview from "@/components/PetPreview.vue";
import userService from "@/services/UserService";
import accountInfo from "@/components/AccountInfo.vue";

export default {
  created() {
    this.retrievePetList();
    this.retrieveAccountInfo();
  },
  components: {
    petPreview,
    accountInfo,
  },

  methods: {
    retrievePetList() {
      petService
        .getPetsByUserId()
        .then((response) => {
          this.$store.commit("SET_PETS", response.data);
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Pet list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Pet list could not be found. Server could not be reached");
          } else {
            alert("Pet list could not be found. Request could not be created.");
          }
        });
    },
    viewPetDetails(id) {
      this.$router.push(`/pets/${id}`);
    },
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
  },
};
</script>

<style scoped>
#columns {
  display: flex;
  justify-content: space-around;
}

#petColumn,
#accountColumn,
#playdateColumn {
  flex-basis: 25%;
}

#petColumn {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#registerPet {
  margin-bottom: 50px;
}

h1,
h2 {
  text-align: center;
  margin-bottom: 3vh;
}

h1 {
  margin-top: 45px;
  margin-bottom: 45px;
  color: #cd704c;
}

/* .preview:hover {
  background-color: #c4cad0;
  border-radius: 2%;
} */

#playdate-info {
  border: 2px solid #cd704c;
  border-radius: 0.25rem;
  margin: 20px 10px;
  padding: 30px 25px;
  background-color: #fff;
  box-shadow: 1px 3px 7px 0 rgba(134, 130, 130, 0.4),
    0 1px 2px 0 rgba(56, 56, 56, 0.3);
}
</style>