import axios from 'axios';

export default {
    createProfile(user){
        return axios.post('/profile', user)
      },

    updatePet(id, user){
        return axios.put(`/profile/${id}`, user)
    },

    getProfile(id){
        return axios.get(`/profile/${id}`)
      },

}