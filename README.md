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

## Source code 
```
git clone https://github.com/Schimmeltoast08/Tshell.git
cd Tshell/source-code
./install.sh
```
More up-to-date, but slower execution time


## Usage
```
tshell             | start tshell
inside of tshell:
    tshell         | display a friendly message
    tshell -l      | show the logo
    tshell -v      | show the version
    tshell -c      | show the config directory
    help           | shows helpfull information
    type           | get the type of any command

```

## Configuration
The first line is your Shell prompt. If you set it to user@hostname, it will show your user and hostame. If you set it to anything else, that will be your shell prompt. Leave blank for default
The second line is your Shell prompt addition. It works the same as the first line, but can not be user@hostname
Lines 3-17 are the ascii art shown when running tshell -l. If your image does not fit within those lines and does not take up all of them, it will show the default Ascii art
Line 18 is a flag that, if present, disabels the warning you get if your ascii art does not fit within the box
