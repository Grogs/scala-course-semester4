# Lesson 8

Start by checking out the `lesson8` branch

This week we want to display the prices of every hotel to the user.
  
Steps:

1. In `HotelsController`, add the hotel prices into the search page and pass it down to the hotelsTable template.

    Hints:
      - Use the method provided on `HotelPriceService`.
      - Check the new parameter expected from `hotelsTable.scala.html`.
        
2. Update the hotelsTable template so each hotel row has a price.

   Hints:
     - Fix the `HotelsControllerSpec` test.
     
3. So far, only the initial page load will include prices. Now update the interactive search to fetch the new prices. 

   Hints:
     - Update the method `initialiseInteractiveSearch` on `App` when the table is re-populated.
     - You can use the autowire Client again, but now you'll need to call a different service.
