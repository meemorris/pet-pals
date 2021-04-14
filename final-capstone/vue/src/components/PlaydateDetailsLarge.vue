<template>
  <div>
    <div id="largeDetail">
      <div id="pet-pic">
        <img
          class="pet-pic"
          v-if="playdate.pet.pic"
          v-bind:src="playdate.pet.pic"
        />
        <img
          class="pet-pic default-pic"
          v-else
          src="@/assets/paws-default-white.png"
        />
      </div>
      <div id="title">
        <h4>Playdate with {{ playdate.pet.name }}</h4>
      </div>
      <div id="pet-details">
        <p v-if="playdate.pet.bio">{{ playdate.pet.bio }}</p>
        <p>
          {{ playdate.pet.energeticRelaxed }} | {{ playdate.pet.shyFriendly }} |
          {{ playdate.pet.apatheticCurious }}
        </p>
        <p>Species: {{ playdate.pet.species }}</p>
        <p v-if="playdate.pet.breed">Breed: {{ playdate.pet.breed }}</p>
        <p>
          Weight: {{ playdate.pet.weight }} pound{{
            playdate.pet.weight === 1 ? "" : "s"
          }}
        </p>
        <p v-if="age === 0">Less Than 1 Year Old</p>
        <p v-else>Age: {{ age }} year{{ age === 1 ? "" : "s" }}</p>
      </div>
      <div id="playdate-details">
        <p>
          Location: {{ playdate.address }}, {{ playdate.city }},
          {{ playdate.state }} {{ playdate.zip }}
        </p>
        <p>
          Date & Time:
          {{ moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
        </p>
      </div>
      <div id="owner-details">
        <img class="owner-pic" v-if="owner.pic" v-bind:src="owner.pic" />
        <img class="owner-pic" v-else src="@/assets/paw-outline-light.png" />
        <div id="owner-content">
          <h5>Owner</h5>
          <h6>{{ owner.firstName }} {{ owner.lastName }}</h6>
          <p v-if="owner.bio">{{ owner.bio }}</p>
        </div>
      </div>
      <div id="join-playdate" v-show="$store.state.token != ''">
        <button
          id="button-join-playdate"
          class="btn btn-primary"
          v-on:click.stop="
            toggleShowForm();
            maintainLargeDisplay();
          "
        >
          Join Playdate
        </button>
      </div>
    </div>
    <div v-show="showForm">
        <join-playdate-form v-bind:playdate="playdate"/>
    </div>
    <div>
    <router-link
      :to="{ name: 'playdates' }"
      id="backToList"
      tag="button"
      class="btn btn-primary"
      >Back to Playdates List</router-link
    >
    </div>
  </div>
</template>

<script>
import userService from '@/services/UserService';
import playdateService from '@/services/PlaydateService';
import JoinPlaydateForm from '@/components/JoinPlaydateForm.vue';

export default {
  name: "PlaydateDetailsLarge",
  components: {
    JoinPlaydateForm,
  },
  created() {
    this.getPlaydate();
  },
  data() {
    return {
        showForm: false,
        owner: [],
        playdate: {}
    };
  },
  computed: {
    age() {
      return new Date().getFullYear() - this.playdate.pet.birthYear;
    },
  },
  methods: {
    toggleShowForm() {
      if (this.showForm) {
        this.showForm = false;
      } else {
        this.showForm = true;
      }
    },
    getPlaydate(){
        playdateService
        .getPlaydate(this.$route.params.id)
        .then((response) => this.playdate = response.data)
        this.getOwner();
    },
    getOwner() {
      userService
        .getProfile(this.playdate.pet.userId)
        .then((response) => {
          this.owner = response.data;
        });
    },
  },
};
</script>

<style>
#largeDetail {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
    "pic title title"
    "pic pet playdate"
    "pic pet owner"
    "pic pet join";
}
#pet-pic {
  grid-area: pic;
  display: flex;
  align-items: center;
  justify-content: center;
}
#title {
  grid-area: title;
  margin-top: 10px;
}
#pet-details {
  grid-area: pet;
}
#playdate-details {
  grid-area: playdate;
}
#owner-details {
  grid-area: owner;
}
#join-playdate {
  grid-area: join;
  margin-bottom: 10px;
}

#largeDetail .pet-pic {
  width: 250px;
  border-radius: 2%;
}

.owner-pic {
  height: 100px;
  width: 100px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 10px;
}

h4 {
  text-align: center;
}

#owner-details {
  display: flex;
}

#owner-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>