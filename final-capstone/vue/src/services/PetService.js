import axios from 'axios';

export default {

  registerPet(pet){
    return axios.post('/pets/register', pet)
  },

  list(){
    return axios.get('/pets')
  },
  
  petProfile(id){
    return axios.get(`/pets/${id}`)
  }
}