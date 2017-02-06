# Restaurant scanner

An application that enables you to
- View all restaurants near a given location, using the DD REST API service
- Add restaurants to a favorite list
- Ability to Login and access protected resource

Application Walk-through
------------------------

The application has 3 primary Screens

- Splash Screen: User generally don't see this screen unless a cold-launch that will take a
   noticeable time.
- Landing Screen: Main Screen with a drawer layout so that we can swap in child views for
    - Restaurant List: This screen shows the restaurants retrieved from the service. Primary
          responsibility is to display the items in both a list and grid layout.
    - Favourite Restaurant List: This screen shows the restaurants that are added as favourites,
           from the Details screen. Also provide context menu to clear fav
    - Profile Screen: Access the Consumer details, after Login to the service.
- Restaurant Details: This screen displays the details of the restaurant selected from the restaurant
    list screen. Also provide the ability to add a restaurant to a favourites list.


Design
------

Application

- The application uses MVP pattern.
- All network calls, except Image downloading, are done using Retrofit.
- Dagger is used for maintaining the DI container.
- Asynchronous operations and Threading are taken care off by Rx
- Image download is done using AsynTasks and downloaded Images are cached in an LRU cache
- List(s) use RecycleViews, which uses the ViewHolder pattern and hence better performance
- Retained fragments are used to store the data across orientation changes
- Application is ordered in to self-explanatory packages

UI

- Product List page uses RecycleView. It uses both LinearLayoutManager and GridLayoutManager to provide toggle views. Error screens are given as fallbacks, incase of any unexpected errors. List(s) also support pull to refresh option
- Details Screen layout is based on ViewPager and the PageAdapter loads each restaurants as fragments.
- There is no separate layouts for Orientation changes or device densities. The app ensures that the layout is stable and consistent though.
- The theme is blue and white mostly, with some RED thrown in
- Material style is followed for the profile page

Testing

- Sample placeholder UnitTest(s) ,using Mockito, are written, especially for the network part.
- Yet to put in integration tests, with Expresso and then hook for Firebase

Assumptions
-----------
- The application talks only to a hardcoded service.
- The location is hardcoded
- No pagination is implemented locally, as ideally the REST API should have provision of staged/lazy retrieval
- minSdkVersion 23 and targetSdkVersion 25
- Targeted at hdpi devices (and hence only resources are provided for those)
- Use of possibly copyrighted images.
- Its assumed that product images are not very big.
- The package names and App names are purposefully named to avoid using DD names.

TODO
____

- Add proper JavaDoc comments
- Beef up error handing
- Add more valid tests