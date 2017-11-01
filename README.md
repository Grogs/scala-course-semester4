# Lesson 7

Start by checking out the `lesson7`branch

This week we want to allow the user to display the search results on a map.
  
----  


new google.maps.Map(document.getElementById('map'), {
          center: {lat: -34.397, lng: 150.644},
          zoom: 8
        });
        
        will become
        
        
new google.maps.Map(document.getElementById('map'), new MapOption(
          center = new LatLng(lat = -34.397, lng = 150.644),
          zoom = 8
        ));

----
Steps:


1. Add a button to open a bootstrap modal, where we'll display the map. 
    * Documentation: https://getbootstrap.com/docs/3.3/javascript/#modals
    * Fix the failing test in `services.hotels.Lesson7`.  

2. Add an empty Map inside the modal.
    * Put the map related code in `Client.onMapOpen' which runs every time the user opens the Map.
    * See https://developers.google.com/maps/documentation/javascript/examples/map-simple

3. Add a Marker for each Hotel. 
    * See https://developers.google.com/maps/documentation/javascript/examples/marker-simple
    * You'll have to call the backend for the hotels again, in the same way we did last week using the Autowire Client.

4. Update the Map's LatLngBounds, so the Map focuses on the right area. 
    * See https://coderwall.com/p/hojgtq/auto-center-and-auto-zoom-a-google-map

5. Add InfoWindows to display a Hotel's description when a marker is clicked on. 
    * See https://developers.google.com/maps/documentation/javascript/examples/infowindow-simple
 