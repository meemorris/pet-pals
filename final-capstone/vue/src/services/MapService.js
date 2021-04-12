import axios from 'axios';
// const http = axios.create({baseURL: "https://maps.googleapis.com/maps/api/geocode/"});

export default {
    getLatAndLong(address,key){
        return axios.get(`https://maps.googleapis.com/maps/api/geocode/json?address=${address}&key=${key}`);
    }
}