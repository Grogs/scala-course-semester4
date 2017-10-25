# Lesson 6

Start by checking out the `lesson6`branch and running `sbt compile`.
 
[//]: # (Review solutions to lesson 5. Look at the code moved to shared.)


### Exercise 1 - Interactive Search
We want to automatically update the search results as the user changes their search. 

To do this, you can go through these steps on the page load. Remember, the code is in the `App` class in the client project. The body of `main` will run on page load.

1. Start by adding event listeners to the destination and distance input boxes.
    * Take a look through all the events available and decide which one to use: https://www.w3schools.com/jsref/dom_obj_event.asp
    * Then you can use the `addEventListener` method on an element to add a new listener.
    * add a println to verify that they work...
2. Make them call a new function for the event handlers to call, and call it with both the destination and distance
    * This will allow us to share the code to refresh the search results.
    * add a println to verify that it's called...
2. Implement to the render function, it should:
    * Fetch the new search results using the Autowire `Client`
        * Use `Client[HotelsService]` to call methods on the HotelsService we implemented a few weeks ago. 
        * See the Autowire documentation: https://github.com/lihaoyi/autowire#minimal-example
    * Generate the new table HTML using the `searchResults` template that was refactored out into the shared project.
    * Replace the previous table with the new table.
4. Remove the Search button, it's not needed anymore!

### Exercise 2 - Autocompletion of destinations
Take a look at `fss.Autocomplete` and hook it up to the destination input
    
### Exercise 3 - Only update search results for valid destinations.  
 At the moment, if you type in Paris, you get intermediate empty search results until you finish typing.  
 Instead, don't update the results if it's empty, or alternatively, show a message saying nothing matches their criteria.