# Tshell
A customizeable shell with graphical settings written in Java.

# Installation

## Binary (Linux-only)
```
git clone https://github.com/Schimmeltoast08/Tshell.git
cd Tshell/linux-binary
mkdir ~/.config/tshell
cp config.tscfg ~/.config/tshell
sudo ./install.sh

```
The binary version **only works on linux**. It is faster and easier then the source code, but you can not modify it youself
Important note: The binary only supports the major updates (for example 2.0.0), not the minor updates and bugfixes (for example 2.2.0 or 2.2.1)

## Portable Source code 
```
git clone https://github.com/Schimmeltoast08/Tshell.git
cd Tshell/source-code
mkdir ~/.config/tshell
cp config.tscfg ~/.config/tshell/
./tshell.java
```
More up-to-date, but slower execution time. This version is portable, meaning everything needed to run it stay in the folder it is in **except for the configuration**.  
#### Nightly
in the source code, there is a nightly folder. In this folder is the unstable testing code. Its update frequency may be down to minutes, it is the very most up-to-date as one can be. Perfect for bleeding-edge enthusiasts, yet it is very little tested.
## Usage
```
Binary: tshell               | start tshell
Portable: java tshell.java   | starts tshell


inside of tshell:
    tshell           | display a friendly message
    tshell -l        | show the logo
    tshell -v        | show the version
    tshell -c        | show the config directory
    tshell -cfg      | edit the config in cli
    tshell --gui     | edit the config in a graphical environment
    tshell --reload  | reloads the shell
    help             | shows helpfull information
    type             | get the type of any command

```

## Configuration for cli

| Line | Setting | Options | Effect |
|------|---------|---------|--------|
|   1  | Shell prompt | Any symbol(s) | Set the prompt |
|   2  | Prompt addition | Any symbol(s) | add to the prompt |
|   3  | Foreground Colour | Most colours | set the prompt foreground colour |
|   4  | Background Colour | Most colours | Set the prompt background colour |
| 5-19 | Ascii logo | Any Ascii logo that fits into 14 lines exactly |  Change the output of tshell -l |
|  20  | Disable Warning | ignoreEmptyAsciiArtWarning | Ignore the warning if the ascii art does not fit |
|  21  | Text editor | Any text editor | Sets the text editor used by tshell -cfg |
|  22  | Reload behaviour | doReloadAfterConfigEdit | Setting for if the shell should restart after editing the config |

## Aliases
to create an Alias, put it in ~/.config/tshell/aliases.tscfg
Syntax:
```
command=command2
```
each new statement must be on a new line. In this example, if you type command it will execute command2. 

### Secret functions
1. if you set the second line to "user@host", the prompt will be your username @ your hostname 
2. if you set the second line to "showCWD" (show current working directory) and the first line to anything other then user@host, the prompt will be your working directory
3. if you set the second line to doSlashSeperate and the first line to user@host, your prompt will be username/hostname>

## Uninstall
```
sudo rm -r /opt/tshell
sudo rm /usr/bin/tshell
```
