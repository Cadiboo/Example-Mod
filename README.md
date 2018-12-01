# [Example Mod](https://github.com/Cadiboo/Example-Mod) 
### An example mod created by [Cadiboo](https://github.com/Cadiboo) to try and get new modders to use good code practices
This contains the basic setup for a normal mod, and the included files use good code practices.
### All credits for Forge, FML etc go to their respective owners.
Any code written by me is free for any use at all. Some credit would be nice though :)
Download the repository, follow the directions to install Forge from the README.txt in the root directory and you're ready to go!

## Setting up a repository
1. Download and extract (or Fork) the repository to a folder you will be able to find and write a mod in
2. Open up your command prompt and go to that folder

#### Using Eclipse
3. Run the `setupDecompWorkspace` gradle task
	- On Mac: run `./gradlew setupDecompWorkspace`
	- On Windows: run `gradlew setupDecompWorkspace`
4. Run the `eclipse` gradle task
	- On Mac: run `./gradlew eclipse`
	- On Windows: run `gradlew eclipse`
5. Open eclipse... TODO add instructions

#### Using IntelliJ
3. import the build.gradle file....
TODO add instructions

## Concepts
A Proxy holds code that is run EXCLUSIVELY on the physical client or the physical server. It's only purpose is to handle code that can't be run on the other physical side (i.e. it will crash the game if run on the wrong side). Any common code should be run from literally anywhere else. 

Modding with Minecraft Forge is pretty simple, but it has quite a few conventions & pre-requisites that are unique to it and are actually pretty important and vital to having your mod work properly. I’ve been Modding for a little while, and have been programming & coding for a few years now so I’ve put together an Example Mod to help new modders start Modding (in the correct way, using the right conventions etc) as fast as possible, with as little hassle as possible and with as few problems as possible.
My example mod is meant to be easy to set up, and you can just hop right into Modding in a correct & working framework by editing, modifying and adding to the code.
The example mod starts you off using RegistryEvents (the proper way to register blocks, items etc.), the @ObjectHolder annotation (the proper way to access & use your blocks, items etc.), the SidedProxy system (the proper way to differentiate between DedicatedServer & Client sided code) and some examples of how to use all the systems in the proper way. (Sorry if this is sounding a bit scary, because of the nuances of Minecraft, Forge and Modding in general there are ways in which stuff has to be done that isn’t always the way you would first think of).


### Eclipse Formatter
If you are using eclipse you can go to Preferences > Formatter and import the eclipse formatter file (Cadiboo's Eclipse Formatter.xml)
### Changing your username
You can change your username by launching Minecraft with the `-username` argument. You can do this in eclipse by going to Run > Run Configurations > YOUR CONFIGURATION > Arguments and putting  `-username YOUR USERNAME` into the text box


### See Also
- Jabelar's test mod
- Choonsters test mod
- Draco18s's Harder Ores (called Reasonable Realism now?) mod
- Diesieben07's mods (they have lots of mods)
- the Forge test mods