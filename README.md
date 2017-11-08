# Lesson 8

Start by checking out the `lesson8` branch

This week we want to display the prices of every hotel to the user.
  
Steps:

1. In `HotelsController`, add the hotel prices in the response.

    Hints:
      - Use the method provided on `HotelPriceService`.
      - Check the new parameter expected from `hotelsTable.scala.html`.
        
2. Include the prices on each row of the hotelsTable, so that, you can see the price of each hotel.

   Hints:
     - Fix the `HotelsControllerSpec` test.
     
3. When we change our search, we want to still display the prices. 

   Hints:
     - Update the method `initialiseInteractiveSearch` on `App` when the table is re-populated.
