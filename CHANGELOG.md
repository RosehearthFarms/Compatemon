# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.5] - 2024-02-20
### Added
- New Logo! Yay!
### Fixed
- Affix Item Drop Rate?
- Entity data for pokemon bosses not being reset properly after the pokemon is reset
  - I think I got most of it fixed too.
- Pokemon Nicknames keep their colors now when set through Apotheosis. Unfortunately, won't work with other ways.
- Hitboxes are getting close!!!
### Changed
- New Config file for purely Cobblemon modifications
- Still trying to fix hitbox problems
- Removed quark logic (for now) and cleaned up files! First true release is upcoming!
### Borked


## [0.2.4] - 2024-02-19
### Added
### Fixed
- Apotheosis things should now properly check if apotheosis compat/config settings are enabled. Pehkui too if those didn't.
### Changed
- Updated config to allow a separated configurable Pokemon Boss Spawn Rate. And updated the logic to go with it.
- Modified a TON of the Pehkui logic and implemented the config for specifically allowing bosses to be larger. May actually make bosses random sized too so may need double checked.
- Made a couple Apotheosis Config values float ranges rather than booleans
### Borked
- Hitbox Problems
- Pokemon are dropping named affix items when they shouldn't be.

## [0.2.3] - 2024-02-15
### Added
### Fixed 
- Config Reloading for Forge, at least. Implemented logic that may work for Fabric as well once I figure out how fabric handles that event.
### Changed
- Added logic to see if we can pass the Rarity back to the BossEvents for making differing text for when a boss spawns
- Trying to resolve pokemon nickname renaming and recoloring problems
### Borked
- Nickname color STILL gets reset when the pokemon is renamed as soon as the name is changed. It actually may be as soon as the pokemon gets focus.


## [0.2.2] - 2024-02-15

### Fixed
- Sophisticated Storage Spam. Also tested it up to 64 blocks.
## [0.2.1] - 2024-02-15
### Added
### Fixed
- Pokemon Bosses now correctly spawn! They only spawn within the spawn range percentage defined within Adventure.cfg
- Potentially will create a new config option for pokemon specific spawn percentage chance.
### Changed
### Borked
- Pokemon Hitbox sizes are still not mapping right. Possibly re-call the pokemon's set eye height? May also be fixed by fixing apotheosis height code?
- Sophisticated Storage plugins _work_ but cause a TON of log spam, so changing the implementation.
- Configs still can't be reloaded and the game has to be restarted :(


## [0.2.0] - 2024-02-14

### Added
- Apotheosis Pokemon Bosses Spawn Properly - kind of. Some pokemon spawn as bosses but aren't supposed to.
- > Pokemon can drop affix items and gems based on config.
- > Boss sizes scale based on config. Unsure if this works with normal bosses.
- Sophisticated Core functionality to make Controller Search Range based on config - UNTESTED
- 
### Fixed
- Apotheosis Issues with pokemon bosses not spawning AS bosses or with items

### Changed
- Updated TODO file! YAY!

## [0.1.3] - 2024-02-09

### Added

- Everything!
- Pehkui Integration for Fabric and Forge
- Apotheosis Integration for Forge
- Pokemon Music Disks: “ENLS’s Pre-Looped Music Library” extending Quark's Ambient Disks (ish)
-

### Fixed



### Changed


### Removed


[unreleased]: https://github.com/olivierlacan/keep-a-changelog/compare/v1.1.1...HEAD