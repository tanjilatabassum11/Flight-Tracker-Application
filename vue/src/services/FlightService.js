import axios from 'axios';

export default {

    createFlight(flight, tripId){
        const url = "/flights/"+tripId;
        return axios.post(url, flight );
    }

}