# JLC Duper

A Fabric mod for **Minecraft 26.2** that adds a late-game machine capable of duplicating items, powered by the **Ender Dragon's Heart**.

> Double or nothing.

## Author

- **Justine Cancio**
- GitHub: [justinecancio06-hub](https://github.com/justinecancio06-hub)
- Repository: [jlc-duper-mod](https://github.com/justinecancio06-hub/jlc-duper-mod)

## Features

- **JLC Duper** — a 3-slot machine that converts **1 input item into 2** over the course of 30 seconds.
- **Ender Dragon's Heart** — a new item dropped by the Ender Dragon, used as fuel and as a way to avoid Enderman aggression.
- Custom block textures, lit/unlit states, and a unique placement sound.
- A "Double or Nothing" advancement awarded for crafting the machine.
- Full GUI with animated progress flame and arrow, inventory persistence, and hopper-free slot rules.

## The JLC Duper

### Crafting

| | | |
|:--:|:--:|:--:|
| Diamond Block | Diamond Block | Diamond Block |
| | Nether Star | |
| Netherite Ingot | Netherite Ingot | Netherite Ingot |

The recipe is shaped and yields one JLC Duper.

### How it works

The machine has three slots:

| Slot | Position | Accepts |
|:--|:--|:--|
| Input | Top-left | Any item **except** the JLC Duper, Ender Dragon's Heart, and Dragon Egg |
| Fuel | Bottom-left | Ender Dragon's Heart only |
| Output | Right | Read-only (cannot be inserted) |

1. Place the item you want to duplicate in the **input** slot.
2. Place an **Ender Dragon's Heart** in the **fuel** slot.
3. The block lights up (light level 12) and runs for **600 ticks (30 seconds)**.
4. When finished, the input is reduced by 1 and **2 copies** are added to the output.

The operation only continues while:
- the input slot holds a valid, non-forbidden item,
- the fuel slot holds an Ender Dragon's Heart,
- the output is either empty or the same item with room for 2 more.

If any condition stops being met, progress resets to zero.

### Fuel

The **Ender Dragon's Heart** has a durability of **5**, so a single heart powers **5 duplications**. The heart is fire-resistant. When its last durability is used, it is consumed.

### Block properties

- Destroy time: 5.0
- Explosion resistance: 6.0
- Requires the correct tool to drop
- Emits light level 12 while running (`lit=true`)
- Drops its contents when broken
- Plays a custom sound when placed

### Advancements

Crafting a JLC Duper grants the hidden-free advancement **"Double or Nothing"** with a challenge-complete sound.

## Ender Dragon's Heart

When the Ender Dragon dies, an Ender Dragon's Heart is dropped **5–15 blocks** from the island center on the surface heightmap.

While holding a heart in either the main hand or offhand, **Endermen will not become aggressive when you look at them** (implemented with a mixin on `EnderMan`).

The heart is also the only valid fuel for the JLC Duper, making it the key resource for the whole mod.

## Requirements

| Component | Version |
|:--|:--|
| Minecraft | 26.2 |
| Fabric Loader | 0.19.5+ |
| Fabric API | 0.160.0+26.2 |
| Java | 25+ |

## Building

The project uses Gradle with the Fabric Loom plugin.

```bash
# Windows
gradlew.bat build

# Linux / macOS
./gradlew build
```

The finished mod and sources jars are written to `build/libs/`.

### Development

- `gradlew.bat runClient` — launch a development client
- `gradlew.bat runServer` — launch a development server

A GitHub Actions workflow (`.github/workflows/build.yml`) builds the mod on every push and pull request.

## Project Structure

```
src/main/java/com/example/jlcduper/    Core mod, blocks, items, block entity, screen handler, mixins
src/client/java/com/example/jlcduper/  Client entrypoint and GUI screen
src/main/resources/                     Assets, data (recipes, loot, advancement, function), mod metadata
```

## License

This project is available under the **CC0-1.0** license. See [LICENSE](LICENSE) for details.
