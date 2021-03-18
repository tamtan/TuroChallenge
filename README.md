# TuroChallenge
1. In this challenge I follow MVVM design pattern.
2. Some third party libraries are:
- Retrofit to make api calls
- Glide to load image
- Coroutines to switch between thread such as background thread to UI thread and viceverse
- LifeCycle to keep track of the application lifecycle as wel as activity lifecycle
3. How it works:
- First launch the app, it will fetch data of the current location. "Dallas" is hard coded here.
- 20 items is fetched per request.
- Scroll down until the end, it will fetch more data and show to the list
- You can input the items and location to search 
- location can not be empty, ortherwise, current location is set for the search

4. Future features:
- Save the search history for auto completion when user input next time
- Get current location based on GPS to fetch data while location inputted is missing
- Click on the item will show the map of the location
