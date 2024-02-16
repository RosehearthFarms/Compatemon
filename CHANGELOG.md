# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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