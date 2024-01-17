import axios from 'axios';

export default {

    getTrips(){
        return axios.get('/trips');
    },
    createTrip(name){
        return axios.post("/trips/" + name);
    }

}