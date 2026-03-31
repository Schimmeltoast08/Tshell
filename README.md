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
The first line is your Shell prompt. If you set it to user@hostname, it will show your user and hostame. If you set it to anything else, that will be your shell prompt. Leave blank for default
The second line is your Shell prompt addition. It works the same as the first line, but can not be user@hostname
Lines 3-17 are the ascii art shown when running tshell -l. If your image does not fit within those lines and does not take up all of them, it will show the default Ascii art
Line 18 is a flag that, if present, disabels the warning you get if your ascii art does not fit within the box
Line 19 lets you choose the text editor used for tshell -cfg
Line 20 lets you control if the shell should restart after editing the config. Set it to doReloadAfterConfigEdit for true, anything else for false (preferably empty)