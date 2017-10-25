# Lesson 4 & 5

Today we will review the backend services we implemented last week.  
Then we will create a basic search results page.

For lesson5 please add this to the view you created:

        <link rel='stylesheet' href='@routes.WebJarAssets.at(webJarAssets.locate("css/bootstrap.css"))'>
        <script src="@routes.WebJarAssets.at(webJarAssets.locate("jquery.js"))" type="text/javascript"></script>
        <script src="@routes.WebJarAssets.at(webJarAssets.locate("js/bootstrap.js"))" type="text/javascript"></script>



Tasks
* Checkout the `lesson4`branch
* Run `sbt test` to see the failing tests.
* Fix the failing tests in `HotelsControllerSpec`.
    * To do this, you will have to build a page to show search results
    * You can check your progress by running `./sbt run` and then browsing to http://localhost:9000/hotels/search?destination=london&distance=1.2
    * Hints:
        * We have a `HotelsController` with the `HotelsService` injected. You can add an endpoint here.
        * You will need to add a route in the `routes` file.
            * You can see an example of extracting a path parameter there:  
              `GET     /assets/*file               controllers.Assets.versioned(path="/public", file: Asset)`  
              If we called `/assets/picture.png`, then `picture.png` would be passed into the `file` parameter
            * You will also need to extract a query parameter. Here's an example:  
              Given the route `GET   /comments                     controllers.Application.comments(page: Int)`  
              If we called `/comments?page=1`, then `1` would be passed into the  `page` parameter
            * For more info, see docs: https://www.playframework.com/documentation/2.5.x/ScalaRouting
        * You will need to add a template in the `views` package.
            * Docs for Play's templates: https://www.playframework.com/documentation/2.5.x/ScalaTemplates
