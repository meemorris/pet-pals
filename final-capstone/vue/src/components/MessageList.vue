<template>
  <div id="list">
    <h1>Messages</h1>

    <p v-show="messageList.length == 0" id="no-results">No results found</p>

    <div id="list-view">
      <div>
        <div v-for="message in messageList" v-bind:key="message.messageId">
          <div id="detail-view" class="card">
            <img
              v-if="message.pic"
              class="profile-pic"
              v-bind:src="message.pic"
              alt="user profile picture"
            />
            <img
              class="default-profile-pic"
              v-else
              src="@/assets/default-user-pic.jpg"
              alt="profile picture"
            />

            <div id="message-container" class="card-body">
              <p class="card-title">
                <span class="message-header">Username: </span>
                {{ message.name }}
              </p>

              <p class="card-text">
                <span class="message-header">Message: </span
                >{{ message.message }}
              </p>
            </div>

            <div id="message-info">
              <p id="date-posted" v-show="message.postedDate">{{ moment(message.postedDate).format("dddd, MMMM Do YYYY, h:mm a") }}</p>
              <p id="pet-tag" v-show="message.pet.name">#{{ message.pet.name }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <router-link
      :to="{ name: 'writeMessage' }"
      id="writeMessage"
      tag="button"
      class="btn btn-primary"
      >Write Message</router-link
    >
  </div>
</template>

<script>
import messageService from "@/services/MessageService";
// import userService from "@/services/UserService";

export default {
  name: "messageforum",
  data() {
    return {
      // displayType: "List",
      messages: [
        {
          messageId: "",
          userId: "",
          message: "",
          name: "",
          pic: "",
        },
      ],
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

#detail-view {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 55vw;
  margin-bottom: 10px;
  border: 2px solid hsla(240, 1%, 40%, 0.2);
}

#detail-view:hover {
  background-color: #fafafa;
}

.message-header {
  font-weight: 600;
}

.default-profile-pic {
  max-width: 100px;
  border-radius: 5%;
  align-self: center;
  padding: 13px;
}

.profile-pic {
  max-width: 100px;
  border-radius: 5%;
  align-self: center;
  padding: 20px;
}

#list-view {
  display: flex;
  align-items: center;
  flex-direction: column;
}

#writeMessage {
  margin-bottom: 30px;
}

#message-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 15px;
  justify-content: space-between;
  margin-top: 5px;
}

#pet-tag {
  color: #cd704c;
}

#date-posted {
  color: hsla(196, 86%, 20%, 0.75);
  font-weight: 600;
}
</style>