# `ridable-stands`

I've pretty much been stuck in time since most of the 
people I work with use Bukkit version 1.8.8, so I decided
to find a way to learn to use a bit of Bukkit version 1.14
by writing a plugin which spawns WASD-controllable armor
stands. I figure that this might come in handy if I ever
end up actually working for someone who isn't stuck in time
like I was, but whatever.

This plugin is also a pretty good demo project for how to
use Guice and maven modules to abstract away 
version-dependent code.

# Usage

Boot up with the plugin loaded and use the `/mountstand` 
command as a player to spawn a new armor stand and mount
it.

# Building

``` shell
git clone https://github.com/caojohnny/ridable-stands.git
cd ridable-stands
mvn clean install
```

Jar is in `plugin/target`.

# Credits

Built with [IntelliJ IDEA](https://www.jetbrains.com/idea/)

Uses [Guice](https://github.com/google/guice)
