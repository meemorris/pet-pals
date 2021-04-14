<template>
  <div id="list">
    <h1>Messages</h1>

    <p v-show="messageList.length == 0" id="no-results">No results found</p>

    <div id="list-view">
      <div>
        <div v-for="message in messageList" v-bind:key="message.messageId">
          <div id="detail-view">
            <!-- Use this one -->
            <!-- <img class="profile-pic" v-bind:src="message.pic" alt="user profile picture" /> -->

            <!-- Test -->
            <img
              class="profile-pic"
              src="@/assets/paws-default.png"
              alt="user profile picture"
            />

            <div id="message-container">
              <!-- Test -->
              <h2><span class="message-header">User Name:</span> Test</h2>

              <!-- Use this one -->
              <!-- <h2><span class="message-header">User Name: </span> {{ message.name }}</h2> -->

              <p>
                <span class="message-header">Message: </span
                >{{ message.message }}
              </p>
              <!-- {{message.userId}}:  {{message.message}} -->
            </div>
          </div>
        </div>
      </div>
    </div>
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
    // retrieveAccountInfo() {
    //   userService
    //     .getProfile(this.message.userId)
    //     .then((response) => {
    //       if (response.data.firstName === null) {
    //         this.$router.push("/profile/create");
    //       } else {
    //         this.$store.commit("SET_ACCOUNT_INFO", response.data);
    //       }
    //     })
    //     .catch((error) => {
    //       if (error.response) {
    //         alert(
    //           "Account information could not be found. Response was " +
    //             error.response.statusText
    //         );
    //       } else if (error.request) {
    //         alert(
    //           "Account information could not be found. Server could not be reached"
    //         );
    //       } else {
    //         alert(
    //           "Account information could not be found. Request could not be created."
    //         );
    //       }
    //     });
    // },
  },
};
</script>

<style scoped>
#list {
  /* display: flex;
  flex-direction: column; */
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

  display: grid;
  grid-template-columns: 1fr, 1fr;
  grid-template-areas:
  "pic body"
  "pic body";


  /* box-sizing: border-box; */
  max-width: 450px;
  clear: both;

  background: #949494;
  background-image: -webkit-gradient(
    linear,
    left bottom,
    left top,
    color-stop(0.15, #bee2ff),
    color-stop(1, #95c2fd)
  );
  background-image: -webkit-linear-gradient(bottom, #d1d5d8 15%, #97a3b4 100%);
  background-image: -moz-linear-gradient(bottom, #d1d5d8 15%, #97a3b4 100%);
  background-image: -ms-linear-gradient(bottom, #d1d5d8 15%, #97a3b4 100%);
  background-image: -o-linear-gradient(bottom, #d1d5d8 15%, #97a3b4 100%);
  background-image: linear-gradient(to top, #d1d5d8 15%, #97a3b4 100%);
  filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#97a3b4', endColorstr='#d1d5d8');

  border: solid 1px rgba(0, 0, 0, 0.5);
  -webkit-border-radius: 20px;
  -moz-border-radius: 20px;
  border-radius: 20px;

  -webkit-box-shadow: inset 0 8px 5px rgba(255, 255, 255, 0.65),
    0 1px 2px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: inset 0 8px 5px rgba(255, 255, 255, 0.65),
    0 1px 2px rgba(0, 0, 0, 0.2);
  box-shadow: inset 0 8px 5px rgba(255, 255, 255, 0.65),
    0 1px 2px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
  padding: 6px 20px;
  color: #000;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.8);
  word-wrap: break-word;
}

h2 {
  font-size: 1.2rem;
}

.message-header {
  font-weight: 600;
}

.profile-pic {
  max-width: 100px;
  /* min-width: 150px; */
  border-radius: 5%;
  align-self: center;
  grid-area: pic;
}

#message-container {
  /* display: flex;
  align-items: flex-start;
  flex-direction: column; */
  grid-area: body;
}
</style>