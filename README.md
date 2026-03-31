# Tshell
A customizeable shell written in Java.

# Installation

## Binary (Linux-only)
```
git clone https://github.com/Schimmeltoast08/Tshell.git
cd Tshell/linux-binary
mv tshell /usr/bin/tshell
mkdir ~/.config/tshell
cp config.tscfg ~/.config/tshell

```
The binary version **only works on linux**. It is faster and easier then the source code, but you can not modify it youself
Important note: The binary only supports the major updates (for example 2.0.0), not the minor updates and bugfixes (for example 2.2.0 or 2.2.1)

## Source code 
```
git clone https://github.com/Schimmeltoast08/Tshell.git
cd Tshell/source-code
./install.sh
```
More up-to-date, but slower execution time. If you want to turn it into an executeable yourself, check out my tool called "jbuild" and run jbuild tshell in a directory which only contains the tshell.java file. Do not forget to copy the default config file or make your own! 


## Usage
```
tshell               | start tshell
inside of tshell:
    tshell           | display a friendly message
    tshell -l        | show the logo
    tshell -v        | show the version
    tshell -c        | show the config directory
    tshell -cfg      | edit the config
    tshell --reload  | reloads the shell
    help             | shows helpfull information
    type             | get the type of any command

```

## Configuration

| Line | Setting | Options | Effect |
|------|---------|---------|--------|
|   1  | Shell prompt | Any symbol(s) | Set the prompt |
|   2  | Prompt addition | Any symbol(s) | add to the prompt |
|   3  | Foreground Colour | Most colours | set the prompt foreground colour |
|   4  | Background Colour | Most colours | Set the prompt background colour |
| 5-19 | Ascii logo | Any Ascii logo that fits into 3-17 lines exactly |  Change the output of tshell -l |
|  20  | Disable Warning | ignoreEmptyAsciiArtWarning | Ignore the warning if the ascii art does not fit |
|  21  | Text editor | Any text editor | Sets the text editor used by tshell -cfg |
|  22  | Reload behaviour | doReloadAfterConfigEdit | Setting for if the shell should restart after editing the config |

### Secret functions
1. if you set the second line to "user@host", the prompt will be your username @ your hostname 
2. if you set the second line to "showCWD" (show current working directory) and the first line to anything other then user@host, the prompt will be your working directory
3. if you set the second line to doSlashSeperate and the first line to user@host, your prompt will be username/hostname>