import axios from 'axios';
const http = axios.create({baseURL: "https://maps.googleapis.com/maps/api/geocode/json?"});

export default {
    getLatAndLong(){
        return http.get(`/address=${address}`);
    }
}