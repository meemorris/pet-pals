import axios from 'axios';

export default {

  createPlaydate(playdate){
    return axios.post('/playdates', playdate)
  },

  getListOfPlaydates(){
    return axios.get('/playdates')
  },

  joinPlaydate(petId, playdateId) {
    return axios.post(`${petId}/join/playdate/${playdateId}`)
  },

  getScheduledPlaydates(petId) {
    return axios.get(`${petId}/schedule/playdates`)
  },

  getAttendees(playdateId) {
    return axios.get(`/schedule/playdates/${playdateId}/attendees`)
  }
}