import axios from 'axios';

export default {

  createPlaydate(playdate){
    return axios.post('/playdates', playdate)
  },

  
}