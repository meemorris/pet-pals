import axios from 'axios';

export default {

  registerPet(pet){
    return axios.post('/registerPet', pet)
  }
}