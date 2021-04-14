import axios from 'axios';

export default {

  createMessage(message){
    return axios.post('/message', message)
  },

  getListOfMessages(){
    return axios.get('/messages')
  },
}