import axios from 'axios';

export default {

  createPlaydate(playdate){
    return axios.post('/playdates', playdate)
  },

  getListOfPlaydates(){
    return axios.get('/playdates')
  }

  
}