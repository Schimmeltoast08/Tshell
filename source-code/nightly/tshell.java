import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.net.InetAddress;
import javax.swing.JOptionPane;




public class tshell {
    static String currentDirectory = System.getProperty("user.dir");
    static ArrayList<String> history = new ArrayList<>();
    static ArrayList<String> aliases = new ArrayList<>();
    static ArrayList<String> LeftAlias = new ArrayList<>();
    static ArrayList<String> RightAlias = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Boolean doExit = false;
        while (!doExit){
        Scanner scanner = new Scanner(System.in);
        boolean doTry = true;
        boolean doPrintSlogan = true;
        String shellStarterString = "";
        String asciiArtStringFromConfig = """
        """;
        String preferedConfigEditor = "vim";
        String shellStarterAdditionalString = "";
        boolean doReloadAfterConfigEdit = false;
        String promptFGColour = "White";
        String promptBGColour = "";
        String guiDarkMode = "";
        MyFrame myframe = null; // for instanciation issue
        


        ArrayList<String> configFile = new ArrayList<>();
        try{
            String filepath = System.getProperty("user.home") + "/.config/tshell/config.tscfg";
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null){
                configFile.add(line);
            }
            } catch (IOException e){
                IO.println("Exception: Config file not found. Generating new empty config");
            }
            shellStarterAdditionalString = configFile.get(1).trim();
        try{
         shellStarterString = configFile.get(0);
         preferedConfigEditor = configFile.get(20);
         promptFGColour = configFile.get(2);
         promptBGColour = configFile.get(3);
         try {
            if (configFile.get(21).equalsIgnoreCase("doReloadAfterConfigEdit")){
                doReloadAfterConfigEdit = true;
            }
             
         } catch (Exception e) {}
         try { guiDarkMode = configFile.get(22);} catch (Exception e){}
         try {
            String newLine = System.getProperty("line.separator");
            for (int i = 4; i < 18; i++ ){
                
                
                    asciiArtStringFromConfig += configFile.get(i);
                    asciiArtStringFromConfig += newLine;
                
            }
             
         } catch (Exception e) { 
            if(!(configFile.get(19).equalsIgnoreCase("ignoreEmptyAsciiArtWarning"))){
                IO.println("No ascii art in config, using default" + e);
            }
         }
        } catch (Exception e){IO.println("Empty shell config!");}




        try {
            String historyLine;
            BufferedReader historyReader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.tshHistory"));
            while ((historyLine = historyReader.readLine()) != null){
                history.add(historyLine);

            }

            
        } catch (IOException e) {
            IO.println("could not open History file");
        }


        String aliasPath = System.getProperty("user.home") + "/.config/tshell/aliases.tscfg";

        try {
            if (!(new File(aliasPath).exists())){
                new File(aliasPath).createNewFile();
            }
            BufferedReader aliasReader = new BufferedReader(new FileReader(aliasPath));
            String aliasLine;
            while ((aliasLine = aliasReader.readLine()) != null){
                aliases.add(aliasLine);                
            }
            //IO.println(Arrays.toString((aliases.get(0)).split("=")));
            
            for (String s : aliases){
                String[] aliasParts = s.split("=");
                //IO.println(Arrays.toString(aliasParts));
                LeftAlias.add(aliasParts[0]);
                RightAlias.add(aliasParts[1]);
            }


            
            
        } catch (Exception e) {
            IO.println("Error: Could not open alias File at " + aliasPath + ". Press E for full Error ");
            @SuppressWarnings("unused")
            errorTimerThread aliasErrorThread = new errorTimerThread();
            if (scanner.nextLine().toLowerCase().equals("e")){
                IO.println(e);
            }
        }

                                                    // CFG
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!(new File(System.getProperty("user.home") + "/.config/tshell").exists())){
        new File(System.getProperty("user.home") + "/.config/tshell").mkdir();
        }
        if (!(new File(System.getProperty("user.home") + "/.config/tshell/config.tscfg").exists())){
            new File(System.getProperty("user.home") + "/.config/tshell/config.tscfg").createNewFile();

        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


        while (true){
            boolean isValid = false;
            if (!(shellStarterString.isEmpty())){
                if (shellStarterString.startsWith("user@host")){
                    if (shellStarterAdditionalString.equals("doSlashSeperate")){
                        printColour((System.getProperty("user.name") + "/" + InetAddress.getLocalHost().getHostName() + "> "),promptFGColour, promptBGColour);
                    } else {
                            printColour((System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName() + shellStarterAdditionalString +" "),promptFGColour,promptBGColour);
                        }
                    } else if (shellStarterAdditionalString.equals("showCWD")){
                        printColour((currentDirectory + ">" + "\u001B[0m "), promptFGColour, promptBGColour);
                    } else {
                    printColour((shellStarterString + shellStarterAdditionalString + " "), promptFGColour, promptBGColour);
                  }     
            } else{
            System.out.print("<\\>" + shellStarterAdditionalString + " "); 
            }
            String prompt = scanner.nextLine();
            history.add(prompt);
            if (prompt.equals("exit")){
                doExit = true;
                try{myframe.dispose();} catch (NullPointerException e){/* no panel to kill*/}
                break;
            }
            
            if (prompt.startsWith("echo")){
                try {
                    IO.println(prompt.substring(5));
                } catch (StringIndexOutOfBoundsException e){}
                isValid = true;
                doTry = false;
            } // echo is a binary file for me


            if (prompt.startsWith("type")){
                isValid = true;
                doTry = false;
                isShellBuiltin(prompt);
            }

            if (prompt.startsWith("pwd")){
                isValid = true;
                doTry = false;
                //IO.println(getCurrentWorkingDirectory());
                IO.println(currentDirectory);

            }

            if (prompt.startsWith("tshell -v")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                IO.println("Tshell version 3.0.0");
                
            }

            if (prompt.startsWith("history")){
                for (String s : history){
                    IO.println(s);

                }
                
            }
            

            if (prompt.startsWith("tshell --gui")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                Boolean doDarkMode = true;
                if (!(guiDarkMode.equals("guiDarkMode"))){
                    doDarkMode = false;
                }
                try{
                myframe = new MyFrame(doDarkMode, configFile);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Could not open settings window. If you are on wayland, is XWayland running? ");
                    // still throws java error instead of my error. Immidiately terminates for some reason even tho it's in try block
                }
                while (true){
                    if(myframe.doExit){
                        myframe.dispose();
                        break;
                    }
                }
                if (doReloadAfterConfigEdit){
                    printColour("reloading shell", "Green");
                    break;
                }
            }

            if (prompt.startsWith("tshell --reload")){
                printColour("reoloading shell", "Green");
                break;
            }

            if (prompt.startsWith("tshell -c")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                if (prompt.startsWith("tshell -cfg")){
                    try {
                        executeCommand(preferedConfigEditor + " " +  System.getProperty("user.home") + "/.config/tshell/config.tscfg");
                        if (doReloadAfterConfigEdit){
                            printColour("Auto reloading shell after config edit", "Green");
                            break;
                        }
                    } catch (Exception e) {
                        IO.println("Could not open config file. Run tshell -c to locate it and try manually");
                    }

                } else {
                    IO.println("the config is in " + System.getProperty("user.home") + "/.config/tshell/config.tscfg");
                }
            }


            if (prompt.startsWith("tshell -l")){
                printAscii(asciiArtStringFromConfig);
                doTry = false;
                isValid = true;
                doPrintSlogan = false;
            }

            if (prompt.startsWith("help")){
                IO.println("Tshell, your own personal Shell written in Java");
                IO.println("options:\n  -l   display the tshell logo\n  -v   show the tshell version\n  -c   show the config file\n  exit   exit tshell");
                doTry = false;
                isValid = true;
            }

            if (doPrintSlogan == true){
                if (prompt.startsWith("tshell")){
                    printColour("Tshell: Your own personal Shell written in java", "Green");
                    doTry = false;
                    isValid = true;
                }
            }

            if (prompt.startsWith("cd")){
                isValid = true;
                doTry = false;
                changeCurrentWorkingDirectory(prompt);
            }

            if (prompt.startsWith("cls") || prompt.startsWith("clear")){
                clearScreen();
                doTry = false;
                isValid = true;
            }




           if (doTry){
           if (!prompt.isEmpty() && (executeCommand(prompt)) == false && isValid == false){
            try{
                suggest(prompt);
            } catch (Exception e){}
            //IO.println(prompt + ": command not found");
           }
           }
           doTry = true;
           doPrintSlogan = true;

           try(FileWriter historyWriter = new FileWriter(System.getProperty("user.home") + "/.tshHistory")){
               
               String historyString = "";
               for (String s : history){
                historyString += s + "\n";
               }
               historyWriter.write(historyString);
               


           } catch (IOException e) {
            IO.println("Could not write to history. Hit E for error code");
           }
            

        }
        
    }
    }

    static void isShellBuiltin(String input){
            String pathSeparator = File.pathSeparator;
            String path = System.getenv("PATH");
        try{
            boolean shellBuiltIn = false;
            String prompt = input.substring(5);
            String[] builtInCommands = {"type", "echo", "exit", "pwd", "cls", "history"}; 
            for (String str : builtInCommands){
                if (str.contains(prompt)){
                    IO.println(prompt + " is a shell builtin");
                    shellBuiltIn = true;
                
                }
            }
            if (!shellBuiltIn){
                String[] possiblePaths = path.split(pathSeparator);
                boolean commandExists = false;
                for (String currentPath : possiblePaths){
                    try {
                        currentPath += "/" + prompt;
                        File file = new File(currentPath);
                        if (file.exists()){
                            if (file.canExecute()){
                                IO.println(prompt + " is " + currentPath);
                                commandExists = true;
                                break;
                            } 
                            
                        }
                    } catch (Exception e) {IO.println("Could not resolve command: " + e);}

                }
                    if (!commandExists){
                        IO.println(prompt + ": not found");
                    }  
            }



            
        } catch (Exception e){IO.println("Could not resolve command: " + e);}

        

    }


 static boolean executeCommand(String prompt){

        String pathSeparator = File.pathSeparator;
        String path = System.getenv("PATH");
        String[] possiblePaths = path.split(pathSeparator);
        String[] shortPrompt = prompt.split(" ");
        ArrayList<String> argStr = new ArrayList<>();

        for (int i = 1; i < shortPrompt.length; i++){
            argStr.add(shortPrompt[i]);
        }


        prompt = shortPrompt[0];
        for (String currentPath : possiblePaths){
                try {
                    currentPath += "/" + prompt;
                    File file = new File(currentPath);
                    if (file.exists()){
                        if (file.canExecute()){
                            ArrayList<String> commandAndArgs = new ArrayList<>();
                            commandAndArgs.add(prompt);
                            commandAndArgs.addAll(argStr);
                            ProcessBuilder pb = new ProcessBuilder(commandAndArgs);
                            pb.directory(new File(currentDirectory));
                            pb.inheritIO();
                            Process process = pb.start();
                            
                            @SuppressWarnings("unused")
                            int exitCode = process.waitFor(); // so it waits for finish + if i delete it a random error appears. Idk why


                            return true; // for commandNotFound 
                            

                            
                        } 
                        
                    } 
                }   catch (IOException | InterruptedException e) {
                        IO.println("Could not execute command: " + e);
                    }

                    
        } 
        if (LeftAlias.contains(prompt)) {
            int idx = LeftAlias.indexOf(prompt);
            executeCommand(RightAlias.get(idx));
                        
                    }
        return false;

    }


public static void clearScreen() {  
    System.out.print("\033[H\033[2J");
    System.out.flush();
} 


public static String getCurrentWorkingDirectory(){
     return System.getProperty("user.dir");
}


public static void changeCurrentWorkingDirectory(String input){
    try {
        String path = input.substring(3).trim();

        if (path.isEmpty()) {
            return;
        }

        String newPath;

        if (path.startsWith("~")) {
            newPath = System.getProperty("user.home") + path.substring(1);
        }
        else if (path.startsWith("/")) {
            newPath = path;
        }
        else {
            newPath = currentDirectory + "/" + path;
        }

        File dir = new File(newPath);

        String canonicalPath = dir.getCanonicalPath();
        dir = new File(canonicalPath);

        if (dir.exists() && dir.isDirectory()) {
            currentDirectory = canonicalPath;
        } else {
            IO.println("cd: " + path + ": No such file or directory");
        }

    } catch (IOException e) {
        IO.println("cd: " + input.substring(3).trim() + ": No such file or directory");
    }
}


static void printAscii(String asciiConfig){
    String defaultLogo = """

⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠛⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠓⢦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡀⠀⠀⠀⠘⠿⠟⠀⢀⡀⠀⢀⡀⠀⠻⠿⠇⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣦⠀⠀⠀⠀⠀⠀⠀⠛⠶⠾⠃⠀⠀⠀⠀⠀⠀⠀⣴⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣷⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣾⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                
                """;
    


        if (!(asciiConfig.isEmpty())){
            IO.println(asciiConfig);
        } else{
            IO.println(defaultLogo);
        }

    }

    public static void printColour(String str, String foregroundColour, String backgroundColour){
        final String ANSIRESET = "\u001B[0m";
        String AnsiColNumFG;
        String AnsiColNumBG;
        switch(foregroundColour) {
            case "Black" -> AnsiColNumFG = "\u001B[30m";
            case "Red" -> AnsiColNumFG = "\u001B[31m";
            case "Green" -> AnsiColNumFG = "\u001B[32m";
            case "Yellow" -> AnsiColNumFG = "\u001B[33m";
            case "Blue" -> AnsiColNumFG = "\u001B[34m";
            case "Purple" -> AnsiColNumFG = "\u001B[35m";
            case "Cyan" -> AnsiColNumFG = "\u001B[36m";
            case "White" -> AnsiColNumFG = "\u001B[37m";
            default -> AnsiColNumFG = "\u001B[37m";
            
        }

        switch(backgroundColour) {
            case "Black" -> AnsiColNumBG = "\u001B[40m";
            case "Red" -> AnsiColNumBG = "\u001B[41m";
            case "Green" -> AnsiColNumBG = "\u001B[42m";
            case "Yellow" -> AnsiColNumBG = "\u001B[43m";
            case "Blue" -> AnsiColNumBG = "\u001B[44m";
            case "Purple" -> AnsiColNumBG = "\u001B[45m";
            case "Cyan" -> AnsiColNumBG = "\u001B[46m";
            case "White" -> AnsiColNumBG = "\u001B[47m";
            default -> AnsiColNumBG = "";//"\u001B[40m";
            
        }

        IO.print(AnsiColNumFG + AnsiColNumBG + str + ANSIRESET);

    }

    public static void printColour(String str, String foregroundColour){ // method overload
        final String ANSIRESET = "\u001B[0m";
        String AnsiColNumFG;
        switch(foregroundColour) {
            case "Black" -> AnsiColNumFG = "\u001B[30m";
            case "Red" -> AnsiColNumFG = "\u001B[31m";
            case "Green" -> AnsiColNumFG = "\u001B[32m";
            case "Yellow" -> AnsiColNumFG = "\u001B[33m";
            case "Blue" -> AnsiColNumFG = "\u001B[34m";
            case "Purple" -> AnsiColNumFG = "\u001B[35m";
            case "Cyan" -> AnsiColNumFG = "\u001B[36m";
            case "White" -> AnsiColNumFG = "\u001B[37m";
            default -> AnsiColNumFG = "\u001B[37m";
            
        }


        IO.println(AnsiColNumFG + str + ANSIRESET);

    }

                    //Bad code, //TODO: make better
///////////////////////////////////////////
static void suggest(String input) {
    for (String cmd : getAllCommands()) {
        if (cmd.startsWith(input)) {
            System.out.println("  " + cmd);
        } 
    
    }
}

static List<String> getAllCommands() {
    List<String> cmds = new ArrayList<>();

    // builtins
    cmds.addAll(List.of("cd", "pwd", "exit", "echo", "clear", "help", "tshell"));

    // PATH executables
    String path = System.getenv("PATH");
    String[] paths = path.split(File.pathSeparator);

    for (String p : paths) {
        File dir = new File(p);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.canExecute()) {
                    cmds.add(f.getName());
                }
            }
        }
    }
    ArrayList<String> sanetizedCmds = new ArrayList<>();
    for (String cmd : cmds){
        if (!(sanetizedCmds.contains(cmd))){
            sanetizedCmds.add(cmd);
        }
    }

    return sanetizedCmds;
}
//////////////////////////////

}

