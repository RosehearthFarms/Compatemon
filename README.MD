# Compatemon
## A Cobblemon Addon
This mod is designed to integrate the wonderful world of Cobblemon with other wonderful minecraft mods.
Each module should be fully independent and work without any of the others being loaded.There _are_ additional integrations between the modules, however!

You may be asking yourself, "What's the point?" The point, my dear friend, is IMMERSION.

## Features
- Not all modules are available for both Forge and Fabric. Development was focused on Forge, but testing did occur to make sure Fabric was supported where available.
- Each module has its own config file. The default values are recommended, of course, but testing has occurred at the fringes (in most cases). Any issues with values outside the default will be considered, but considered less important.
### The Pehkui Module
> Fabric and Forge!
 

Have you ever wanted your minecraft world to feel just a bit more _varied_? With the Pehkui Module, you can add a bit of variety to your life!
Pokemon will spawn in at a default range of 25% of their normal size to 150%! This isn't fully random, however! The distribution of sizes is on a bell curve centered around 100%. These default values are configurable, but be warned, as minecraft AI tends to break when hitboxes and bounding boxes get too large!
### The Sophisticated Module - Sophisticated Storage
> Forge Only

Have you ever wanted your storage controller to link to containers more than 16 blocks away? This fixes that! With a value fully configurable up to 256 blocks, your storage system can grow by leaps and bounds.
- Technically, this feature isn't truly an integration with Cobblemon. HOWEVER! And there is a however!! There will be! Stay tuned!

### The Apotheosis Module
> Forge Only

Have you ever wanted to have Bosses in Cobblemon? With the Apotheosis Module, your dream is a reality! PokeBosses spawn using the logic built in the base mod, but spawns are relative to the player, same as normal pokemon. You can expect the bosses to be a bit stronger than normal pokemon, so look out!
- Note: PokeBosses have more hearts and have their attributes/effects scaled same as Apotheosis bosses. Currently, their being a boss has no bearing on their in battle stats. This _is_ planned however, so stay tuned!

PokeBosses are normally catchable by default, as essentially they're just stronger versions of normal pokemon. There is a catch, though! When a PokeBoss spawns from a boss spawner, it will NOT be catchable. This is due to slightly different logic that causes the pokemon spawned from these to be completely random. From the smallest Joltik to the largest Wailord, to even Moltres and other legendaries already implemented. This can be changed in the config, but as the goal is a natural integration between mods, you'll have to make the decision to enable this yourself!

Currently, this integrates with the Pehkui Module to have adjustable sizing for bosses separately than normal mods. This is configurable, and will not do anything if the Pehkui Module is not enabled (and installed)

This also adds a few extra modded blocks to the enchanting module of Apotheosis. Shouldn't have any effect unless you have the specific mods installed.

### The Compatemon Module
Ah yes, my favorite module. Currently, this module is for any Cobblemon specific or Vanilla related changes that aren't _really_ compatibility or anything else.
As of now, the only thing in this module is a setting for changing the default nickname length. By Cobblemon default, the longest nickname is 12 characters, and that's Just Not Good Enough.

- Yes, I'm aware the GUI doesn't handle nicknames larger than 12 characters very well. I want to make those fields auto-scroll or cut off or something similar, but I haven't gotten to it yet.


### Future Plans
There's a biiiiiig list of mods in the TODO.md file that I've considered for Compatemon intergratabilities. However, I am OPEN to suggestions for ANY mod across the board, Forge or Fabric. I'll prioritize things as what is fun to code! But since that's a really vague standard, I'm more likely to work on something that has a LOT of changes involved, or that I use a lot in my normal modpacks. Also willing to prioritize cross platform mods over single platform ones, and if it's fabric only, good luck? Theoretically, could integrate fabric only mods too using sinytra, but I haven't experimented with that.