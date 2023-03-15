## Kollective Update
### 0.3.7


## THE HIVE JUST GOT SMART.
- **Wyrms now have the Gestalt System.**
  - Wyrms naturally gravitate to players over time
  - Goes off of "infamy"/"attention" value in world
    - The higher this value is, the faster wyrms will congregate around players
    - The stat naturally goes down faster when it is higher, but goes down slower when lower
    - Once infamy is activated by a value change, it will never drop to 0.
    - Infamy is capped at 100
    - If infamy is capped at first, infamy MUST wait some time before it falls before.
  - When wyrms are close enough, they will aggro onto players regardless of being in direct line of sight or not.

- New configs within AI file (Gestalt category):
  - **"Total Awareness"** (true)
  - **"Total Awareness Range"** (8/16/20)
  - **"Infamy Enabled"** (true)
  - **"Infamy Decay Chance"** (20000/25000/30000)

- Wyrms, if attacked while one is normally attacking, will engage upon an attacker regardless of what type of wyrm they are.
  - Before, if a warrior was attacked, only warriors would pick that target.
  - Now, if a warrior was attacked, rovers, soldiers, and rovers would pick that target.
  - Hive Creep will respond to calls from Nyral Wyrms, and Nyral Wyrms will respond to calls from Hive Creep.

- Wyrms will try to intelligently pathfind through locations
- Wyrms can now follow from 40 blocks away
- Creeped can now follow from 25 blocks away (To avoid massive swarm issues)

### Other Changes:

- Fix for wyrms randomly enraging
- Fixed various casting issues like `java.lang.ClassCastException: [Insert Projectile Here] cannot be cast to net.minecraft.entity.EntityLivingBase`, which would cause attacked aliens to crash the game
- Some updates to first time user dialogue
- Fix for distance check.
- Wyrms command now always outputs regardless if invasions are enabled
- New AI options:
  - **"Rage Enabled"** (true)
- New achievements:
  - **"They Can Do That?"**
- Adjusted achievements:
  - Not Today now requires Californium's Witness
  - A Splitting Migraine now requires Californium's Witness
- Tainted Warrior zaps nearby enemies
- Tainted Warrior applied Taint effect buffed to level 3
- Invasion events now properly block Creeped from showing up at 1st invasion stage
- DRASTICALLY lowered creep pod spawns from invasion events, that was a glitch
- Lowered chances of callous pod spawns.
- Callous pods will never spawn after stage 2 of the invasion
- Default preset set to Classic
- New Warrior variant: Oro
- New sounds for Oro Warrior Variant
- Added Tips mod interaction in the form of 29 new tips! (Thanks for 300 downloads!)
- Wyrms of Nyrus root advancement should now be given upon joining the world
- Large nerf to evolution:
  - Steps increased to 100/95/90
  - Evolution in classic mode has effects reduced by 90%
  - Evolution stats in Death World have been set to the previous values of Classic
  - Evolution stats nerfed in Dark Forest slightly (Health was reduced the most)
- Fixed evolution system having faulty math and making wyrms have 1753868726586735 HP at only 10 or so evolution steps
- Fixed invasion events trying to happen in peaceful mode (Thanks to the MFC MC server for catching this one!)
- Fixed events happening in non-surface dimensions like The Nether (Thanks to Harbinger for finding this!)
- Fixed Warrior reach distance on their special ability reaching too far out.
- New texture variant for Follyflesh block
- Made Unknown Specimens slightly more expensive
- New Creeped Bulb Block

### Technical changes for addon developers:

- Added Addon support (A new configuration folder is created for each preset, place your addon's configuration files in there!)
  - Makes addon development easier and keeps configurations neat & organized
  - Developers: To get preset values (id), reference `ConfigBase.selectedPreset` for `presetInts()`/`presetFloats()`/`presetBools()`/`presetStrings()`
- Added functionalities for addon developers:
  - `presetStrings(String normal, String deathWorld, String darkForest, int id)` - To use different strings across different presets
  - `presetStringArrays(String[] normal, String[] deathWorld, String[] darkForest, int id)` - To use different string arrays across different presets (For example: Creeped transformations)
  - `presetIntArrays(int[] normal, int[] deathWorld, int[] darkForest, int id)` - To use different integer arrays across different presets (for example: dimension ids)
  - `presetFloatArrays(float[] normal, float[] deathWorld, float[] darkForest, int id)` - To use different floating-point value arrays across different presets