package com.mycompany.searchenginetest;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import java.time.format.DateTimeFormatter;

public class AmadeusExample {
    public static void main(String[] args) throws ResponseException {
        // Reemplaza con tus claves de API de Amadeus
        String clientId = "HtJkLKMsWgfZCwVKSdx9eTKA1ZuSAJr8";
        String clientSecret = "qg7m1fGLXXljDRdZ";

        Amadeus amadeus = Amadeus.builder(clientId, clientSecret).build();

        // Ejemplo de búsqueda de ofertas de vuelo
        FlightOfferSearch[] flightOffers = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", "LHR")
                      .and("destinationLocationCode", "MAD")
                      .and("departureDate", "2024-02-15")
                      .and("returnDate", "2024-02-20")
                        
                        .and("travelClass", "ECONOMY")
                        .and("currencyCode", "EUR")
                        .and("nonStop", true)
                        
                      
                        
                      .and("adults", 1)
        );

        // Imprimir resultados
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (FlightOfferSearch offer : flightOffers) {
            System.out.println("Type: " + offer.getType());
            System.out.println("ID: " + offer.getId());
            System.out.println("Source: " + offer.getSource());
            System.out.println("Last Ticketing Date: " + offer.getLastTicketingDate());

            // Iterar sobre los itinerarios
            for (FlightOfferSearch.Itinerary itinerary : offer.getItineraries()) {
                System.out.println(" - Itinerary Duration: " + itinerary.getDuration());

                // Iterar sobre los segmentos
                for (FlightOfferSearch.SearchSegment segment : itinerary.getSegments()) 
                {
                    System.out.println("   - Flight Number: " +segment.getCarrierCode()+""+segment.getNumber());

                    System.out.println("   - Segment:");
                    System.out.println("     - Departure: " + segment.getDeparture().getIataCode()
                            + " at " + segment.getDeparture().getAt());
                    System.out.println("     - Arrival: " + segment.getArrival().getIataCode()
                            + " at " + segment.getArrival().getAt());
                }
            }

            // Imprimir información de precios
            System.out.println(" - Price: Currency: " + offer.getPrice().getCurrency()
                    + ", Total: " + offer.getPrice().getTotal());
            System.out.println("******************************************");
        }
    }
}
