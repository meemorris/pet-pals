<template>
  <div id="list">
    <h1>Messages</h1>

    <p v-show="messageList.length == 0" id="no-results">No results found</p>

    <div id="list-view" v-if="displayTypeIsList">
      <div v-for="message in messageList" v-bind:key="message.message_id">
        {{message}}
      </div>
    </div>
  </div>
</template>

<script>
import messageService from "@/services/MessageService";

export default {
  name: "messageforum",
  data() {
    return {
      displayType: "List",
      message: {
        message_id: "",
        user_id: "",
        message: "",
      },
    };
  },
  created() {
    this.createMessageList();
  },

  components: {},
  computed: {
    displayTypeIsList() {
      return this.displayType === "List";
    },
    messageList() {
      let listOfMessages = this.$store.state.messageList;
      return listOfMessages;
    },
  },
  methods: {
    createMessageList() {
      messageService
        .getListofMessages()
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
  },
};
</script>

<style scoped>
#list {
  display: flex;
  flex-direction: column;
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
</style>