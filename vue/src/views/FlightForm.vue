<template>
  <h1>Please Enter Flight Details</h1>
  <form v-on:submit.prevent="createFlight()">
      <div>
          <label>Flight Number</label>
          <input type="text" v-model="flight.flightNumber" />
      </div>
      <div>
          <label>Destination</label>
          <input type="text" v-model="flight.destination" />
      </div>
      <div>
          <label>Airline</label>
          <input type="text" v-model="flight.airline" />
      </div>
      <div>
          <label>Departure Time</label>
          <input type="time" v-model="flight.departureTime" />
      </div>
      <div>
          <label>Depature Date</label>
          <input type="date" v-model="flight.departureDate" />
      </div>
      <div>
          <label>Expected Arrival Time</label>
          <input type="time" v-model="flight.expectedArrivalTime" />
      </div>
      <div>
          <label>Expected Arrival Date</label>
          <input type="date" v-model="flight.expectedArrivalDate" />
      </div>
      <div>
          <label>Cost</label>
          <input type="number" v-model="flight.cost" />
      </div>
      <input type="submit" />
  </form>
</template>

<script>
import FlightService from '../services/FlightService.js'
export default {
    data(){
        return {
            flight: {}
        }
    },
    methods: {
        createFlight(){
            FlightService.createFlight(this.flight, this.$route.params.tripId)
                         .then(
                             (response) => {
                                 this.$router.push({name: "home"})
                             }
                         )
        }
    }
}
</script>

<style>

</style>