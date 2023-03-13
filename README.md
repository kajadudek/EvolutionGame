# EvolutionGame
Project for Object-Oriented Programming course at AGH UST 2022/23

Simulator of a world, where numbers of creatures try to survive based on predefined map parameters. 

# How to start
At first, you can choose your own configuration of settings thanks to multiple implemented variants of:
 - map: GlobeMap, NetherMap
 - grass growth: ToxicFields, GreenBelt
 - animal behavior: Predestination, Crazy
 - genotype mutation: Random, Small
 
 and then set other fields values.
 
 # Animals
 The animal color, shown on the map, corresponds to its own energy level, that decreases every day and raises after eating grass. Making a move happens everyday and depends on animal's genotype.
 When two (or more) creatures step on the same field, they can procreate, but only the strongest ones are able to do so.
 By clicking on the certain animal, you can track its statistic until it dies. Map allows you to highlight animals with dominan genotype and track active genes.
 
 # Game
 Program allows you to pause and resume running simulation. It's also possible to run multiple maps with different settings at once and write statistics to csv files.
 [More details on the simulator's functionality can be found here](https://github.com/apohllo/obiektowe-lab/tree/master/proj1)
 
 # Author
 Kaja Dudek
