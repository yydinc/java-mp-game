# java-mp-game

@author Yusuf Yasin Dinç

This is my first java game.

# ---Prerequests---

+Latest java jdk version required to play this game (java version "1.8.0_271")


# ---The Game---

+We have a character(a rectangle)

+We can move it with arrow keys

+This game is a "Local Multiplayer" game

+Server is going to be open for all the time



# --- How To Play The Game ---

+You have to download "Game.java" file

+Put it in a folder named "cava"

+Open cmd and cd into this directory

+Compile the file by entering "javac Game.java" into cmd

+After that cd into parent directory (Where "cava" folder is located)

+Execute the file by entering "java cava.Game"

+Enjoy the Game :)




# -----------Possible Errors----------


***Error1 : javac is not recognized as an internal or external command***

+This error is given because of "javac.exe" isn't on the Path

+We are going to add "javac.exe"s directory to the Path

+Firstly we have to find where is our "Java_sdk's 'bin' file located" located

+For example mine is at "C:\Program Files\Java\jdk1.8.0_271\bin"

+Continue next steps after you find this location

+Right click on "Computer"

+Click "Properties"

+Click "Advenced System Settings" (At the left of the window)

+Click "Environment Variables" Button (At the bottom of the window)

+Select "Path" from the selection box AT THE BOTTOM

+While "Path" is selected click "Edit" button AT THE BOTTOM

+Click "New" button from recently opened window

+Paste the location you copied 

+Press enter

+Click "Ok" button at the bottom of the screen

+Now you can compile your game at a new cmd window(you must open a new cmd window because we edited the Path)


***Error2: Game starts but nothing happens !***

+Actualy this is not an error

+When you execute the game it connects to server

+And server sends a unique number after accepting your connection

+After your game connects the server it waits for this unique number

+Sometimes server sends that unique number before your game goes into waiting state

+And it waits forever

+I'm gonna fix it as soon as possible









