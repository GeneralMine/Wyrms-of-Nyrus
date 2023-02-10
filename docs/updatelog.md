## Dark Skies Update
### 0.2.7

Reading these (x/y/z) statements are the default config values that are different across the new preset systems. X = Classic, Y = Death World, and Z = Dark Forest.

- Split Radiogenics Config into 4 categories:
  - Wyrms
  - Creeped
  - Follies
  - Global (Affects Wyrms, Creeped, and Follies alike)
- New config options for Radiogenics:
  - Wyrms:
    - **Drop pods cause block damage** (false/true/true)
  - Creeped:
    - **Immune to Explosions** (false/true/true)
    - **Immune to Cacti** (false/false/false)
    - **Immune to Falling** (false/false/true)
  - Global:
    - **Mob Easter Eggs** (true/true/true)
- Wyrms in classic mode are now only immune to falling
- Creeped mobs made into a unified class (That is technical speak for "they now have their own properties")
- Drop pods can now destroy blocks upon landing in Death World and above difficulties
- Unified more drop pod code for consistency
- Visitor-originating Callous Landing Pods now spawn rovers and soldiers instead of probers
- Moved more rendering code to be client-side only
- Evolution decays ~66% faster (but still respects minimum evolution rules)
- Removed some unused code
- Visitor no longer gets pushed by its own drop pods
- Visitor drops pods further away from each other