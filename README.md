# [Example Mod](https://github.com/Cadiboo/Example-Mod) 
### An example mod created by [Cadiboo](https://github.com/Cadiboo) to try and get new modders to use good code practices
This contains the basic setup for a normal mod, and the included files use good code practices.
### All credits for Forge, FML etc go to their respective owners.
Any code written by me is free for any use at all. Some credit would be nice though :)
Download the repository, follow the directions to install Forge from the README.txt in the root directory and you're ready to go!

## Setting up a repository
1. Download and extract (or Clone/Fork) the repository to a folder you will be able to find and write a mod in

#### Using Eclipse
2. Open up your command prompt and go to that folder
3. Run the `setupDecompWorkspace` gradle task
	- On Mac: run `./gradlew setupDecompWorkspace`
	- On Windows: run `gradlew setupDecompWorkspace`
4. Run the `eclipse` gradle task
	- On Mac: run `./gradlew eclipse`
	- On Windows: run `gradlew eclipse`
5. Open eclipse... TODO add instructions

#### Using IntelliJ
2. Open IntelliJ Idea
3. Open the workspace as a gradle project(File -> Open -> Navigate to the MDK directory). It will have a gradle icon next to it
4. In the pop-up window check "Create separate module per source set" and select `Use default gradle wrapper`. This will import the project as a gradle project.
5. Wait for the importing to be done and then open the gradle sub-menu. This can be done by mousing over the square icon in the bottom left of IDEA and selecting Gradle in the popup.
6. In the open window expand Tasks -> forgegradle and double-click the `setupDecompWorkspace` task.
7. Run the genIntellijRuns task. This will generate the runs for your workspace.
8. Click the `Refresh all gradle projects` button.

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
- [Cubicoder's tutorials](https://cubicoder.wordpress.com) (VERY good practices)
- [Jabelar's test mod](https://github.com/jabelar/ExampleMod-1.12)
- [Choonsters test mod](https://github.com/Choonster-Minecraft-Mods/TestMod3)
- [Draco18s's Reasonable Realism](https://github.com/Draco18s/ReasonableRealism)
- [Diesieben07's mods](https://github.com/diesieben07)
- [Forge test mods](https://github.com/MinecraftForge/MinecraftForge/tree/1.12.x/src/test)
- [V0idWalk3r's mods](https://github.com/V0idWa1k3r)
