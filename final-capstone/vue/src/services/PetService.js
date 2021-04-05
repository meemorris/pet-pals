import axios from 'axios';

export default {

  registerPet(pet){
    return axios.post('/pets/register', pet)
  }
}