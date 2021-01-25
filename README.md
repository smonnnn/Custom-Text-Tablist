# Fabric Tab List

<a href='https://www.curseforge.com/minecraft/mc-mods/fabric-api'><img src='https://i.imgur.com/Ol1Tcf8.png' width="150"></a>

**Note: this mod is server side only and won't work on clients**

A Fabric mod that lets you customize the player list (tab list)

The config file is located in the config directory (`config/color.json`) and looks like this:

```JSON
{
  "configVersion": 1,
  "header": "&7&l&o2BUILDERS#N2TOOLS       #N#N&610 years&f#N",
  "footer": "#N&b#TPS tps - #MSPT mspt - #PLAYERCOUNT joueurs - #UPTIME uptime#N",
  "motd": "&7&oWelcome to the server.&f",
  "enableColor": true,
  "enableTablistFormatting": true
}
```

## Formatting

```
0 is black
1 is dark blue
2 is dark green
3 is dark aqua
4 is dark red
5 is dark purple
6 is gold
7 is gray
8 is dark gray
9 is blue
a is green
b is aqua
c is red
d is light purple
e is yellow
f is white
----------------------------------------------------
k is obfuscated
l is bold
m is strikethrough
n is underline
o is italic
----------------------------------------------------
#N next line
#UPTIME show uptime
#TPS show ticks per second
#MSPT ms per tick
#PLAYERCOUNT show how many players are online
```


