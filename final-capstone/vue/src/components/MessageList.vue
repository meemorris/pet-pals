<template>
<div>
  <div id="list">
    <h1>Messages</h1>

    <p v-show="messageList.length == 0" id="no-results">No results found</p>

    <div id="filters-messages">
        <select
          name="petName"
          id="petName"
          v-model="petName"
          v-on:click="retrievePetNames"
        >
          <option value="" default selected disabled>Pet Hashtag</option>
          <option value="">View All</option>
          <option v-for="name in listOfPets" v-bind:key="name"
          v-bind:value="name">
            {{ name }}
          </option>
        </select>

        <select
          name="user"
          id="user"
          v-model="petName"
          v-on:click="retrievePetNames"
        >
          <option value="" default selected disabled>Pet Hashtag</option>
          <option value="">View All</option>
          <option v-for="name in listOfPets" v-bind:key="name"
          v-bind:value="name">
            {{ name }}
          </option>
        </select>
      </div>
    </div>

    <div id="list-view">
      <div v-for="message in messageList" v-bind:key="message.messageId">
        <message-detail v-bind:message="message" />
      </div>

      <router-link
      :to="{ name: 'writeMessage' }"
      id="writeMessage"
      tag="button"
      class="btn btn-primary"
      >Write Message</router-link
    >
    </div>
  </div>
</template>

<script>
import messageService from "@/services/MessageService";
import MessageDetail from "./MessageDetail.vue";

export default {
  name: "messageforum",
  data() {
    return {
      listOfPets: [],
      petName: "",
      messages: [
        {
          messageId: "",
          userId: "",
          message: "",
          name: "",
          pic: "",
          postedDate: "",
          pet: {},
        },
      ],
    };
  },
  created() {
    this.createMessageList();
  },

  components: { MessageDetail },
  computed: {
    displayTypeIsList() {
      return this.displayType === "List";
    },
    messageList() {
      let listOfMessages = this.$store.state.messageList;
      return listOfMessages;
    },
    // sortedMessageList() {
    //   let listOfMessages = this.$store.state.messageList;
    //   this.listOfMessages.sort((a, b) => (a.messageId > b.messageId) ? 1 : -1)
    //   return listOfMessages;
    // },
  },
  methods: {
    createMessageList() {
      messageService
        .getListOfMessages()
        .then((response) => {
          console.log(response);
          this.$store.commit("SET_MESSAGE_LIST", response.data);
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Messages list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Messages list could not be found. Server could not be reached"
            );
          } else {
            alert(
              "Messages list could not be found. Request could not be created."
            );
          }
        });
    },
    retrievePetNames() {
      this.messageList.forEach((message) => {
        if (message.pet.name && !this.listOfPets.includes(message.pet.name)) {
          this.listOfPets.push(message.pet.name);
        }
      });
    },
  },
};
</script>

<style scoped>
#list {
  height: 100vh;
}

h1 {
  text-align: center;
  margin-top: 45px;
  margin-bottom: 30px;
}

#no-results {
  text-align: center;
  margin-top: 5vh;
}

#list-view {
  display: flex;
  align-items: center;
  flex-direction: column;
}

#writeMessage {
  margin-bottom: 80px;
}
</style>