import axios from 'axios';

export default {

  createMessage(message){
    return axios.post('/messageforum', message)
  },

  getListOfMessages(){
    return axios.get('/messageforum')
  },
}