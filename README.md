# Chekyshka (Minecraft 1.21.11 / Fabric)

First development build: registers the Chekushka XXL item, gives it to players who have not finished the intro, and locks basic movement while the bottle remains in the inventory. Right-click while holding the bottle to drink it.

Build with Java 21: `./gradlew build` (Windows: `gradlew.bat build`). The distributable remapped JAR is in `build/libs/` and is the JAR without `-sources` or `-dev` in its name.

This is an early source milestone. The next pass should harden inventory slot/drop prevention and add the wobble / hallucination effects.
