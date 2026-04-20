import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;




public class tshell {
    static String currentDirectory = System.getProperty("user.dir");
    static ArrayList<String> history = new ArrayList<>();
    static ArrayList<String> aliases = new ArrayList<>();
    static ArrayList<String> LeftAlias = new ArrayList<>();
    static ArrayList<String> RightAlias = new ArrayList<>();
    static ArrayList<String> configFile = new ArrayList<>();
    static String lastPrompt = "";
    //static ArrayList<String> configFile = new ArrayList<>();
    static final int MAX_HISTORY_SIZE = 1000; // i hate the name but it's the naming convention :/

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Boolean doExit = false;
        String versionNumber = "3.2.1"; 

        Config config = loadConfig();
        loadHistory();
        loadAliases();
        initConfigIfNotExists();

        boolean doTry = true;
        boolean doPrintSlogan = true;
        MyFrame myframe = null; // for instanciation issue

        while(!doExit){ // for exit command to break whole thing

        if (!(config.startUpCommand.isEmpty())){
            executeCommand(config.startUpCommand);
        }

        while (true){ // repl loop
            boolean isValid = false;
            renderPrompt(config);
            
            String prompt = scanner.nextLine();
            history.add(prompt);

            if (history.size() > MAX_HISTORY_SIZE) { 
                history.remove(0);  // only in memmory aka ArrayList. File can get infinite //byDesign

}
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
                IO.println(currentDirectory);

            }

            if (prompt.startsWith("tshell -v")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                IO.println("Tshell version " + versionNumber);
                
            }

            if (prompt.startsWith("history")){
                for (String s : history){
                    IO.println(s);

                }
                
            }

            if (prompt.startsWith("!!")){
                isValid = true;
                doTry = false;
                executeCommand(lastPrompt + prompt.substring(2));
                // does not work with sudo !! for safety reasons
            }

            if (prompt.contains("|")) {
                doTry = false;
                isValid = true;
                runPipeline(prompt);
                if (prompt.contains("\"") || prompt.contains("\'")){
                    IO.println("Sorry, \" and \' is not implemented yet :(");
                }
            }


            if (prompt.startsWith("tshell --update-linux")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                try{
                executeCommand("sudo rm  -rf /opt/tshell");
                executeCommand("sudo rm /usr/bin/tshell");
                //executeCommand("sudo rm -rf /tmp/tshell-tmp");
                
                //executeCommand("mkdir /tmp/tshell-update-folder-" + versionNumber);
                executeCommand("git clone https://github.com/Schimmeltoast08/Tshell.git /tmp/tshell-tmp");
                //executeCommand("sudo mv /tmp/tshell-tmp /tmp/tshell-update-folder-" + versionNumber);
                executeCommand("sudo /tmp/tshell-tmp/linux-binary/install.sh");
                IO.println("This is still experimental. If it does not work, run 'sduo /tmp/tshell-tmp/linux-binary/install.sh");

                IO.println("test");
            } catch (Exception e){} }

            if (prompt.startsWith("emptyHistory")){
                emptyHistory();
            }
            

            if (prompt.startsWith("tshell --gui")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                Boolean doDarkMode = true;
                if (!(config.guiDarkMode.equals("guiDarkMode"))){
                    doDarkMode = false;
                }
                try{
                myframe = new MyFrame(doDarkMode, ConfigObjectToArrayList(config, configFile));
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Could not open settings window. If you are on wayland, is XWayland running? ");
                    // still throws java error instead of my error. Immidiately terminates for some reason even tho it's in try block
                    // edit 09.04.2026: during testing, I encountered a sitation where this message popped up, so sometimes it works
                }
                while (true){ // halt shell until gui closes
                    if(myframe.doExit){
                        myframe.dispose();
                        break;
                    }
                }
                if (config.doReloadAfterConfigEdit.equalsIgnoreCase("doReloadAfterConfigEdit")){
                    config = loadConfig();
                    printColour("reloading shell", "Green");
                    break;
                }
            }

            if (prompt.startsWith("tshell --reload")){
                printColour("reoloading shell", "Green");
                config = loadConfig();
                         loadHistory();
                         loadAliases();
                         initConfigIfNotExists();
                break;
            }

            if (prompt.startsWith("tshell -c")){
                isValid = true;
                doTry = false;
                doPrintSlogan = false;
                if (prompt.startsWith("tshell -cfg")){
                    try {
                        executeCommand(config.preferedConfigEditor + " " +  System.getProperty("user.home") + "/.config/tshell/config.tscfg");
                        if (config.doReloadAfterConfigEdit.equalsIgnoreCase("doReloadAfterConfigEdit")){
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
                printAscii(config.asciiArtString, config);
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

            if (prompt.startsWith("alias")){
                String alias = prompt.substring(6);
                addAlias(alias + "\n");
            }



            if (prompt.contains("&&")){
                isValid = true;
                doTry = false;
                String[] cmds = prompt.split("&&");
                for (String cmd : cmds){
                    cmd = cmd.trim();
                    executeCommand(cmd);
                }
            }



           if (doTry){
           if (!prompt.isEmpty() && (executeCommand(prompt)) == false && isValid == false){
            try{
                suggest(prompt);
            } catch (Exception e){}

           }
           }
           doTry = true;
           doPrintSlogan = true;
           lastPrompt = prompt;

           try(FileWriter historyWriter = new FileWriter(System.getProperty("user.home") + "/.tshHistory", true)){

               historyWriter.write(prompt + "\n");
               


           } catch (IOException e) {
            IO.println("Could not write to history. Hit E for error code");

                // ignore warning, it does its job and @
             new errorTimerThread(); 
            if (scanner.nextLine().toLowerCase().equals("e")){
                IO.println(e);
            }
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
            String[] builtInCommands = {"type", "echo", "exit", "pwd", "cls", "history", "emptyHistory", "tshell", "alias"}; 
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
                            //ProcessBuilder pb2 = new ProcessBuilder()
                            pb.directory(new File(currentDirectory));
                            pb.inheritIO();
                            Process process = pb.start();


/*
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            StringBuilder builder = new StringBuilder();
                            String line = "";
                            while ( (line = reader.readLine()) != null) {
                                builder.append(line);
                                builder.append(System.getProperty("line.separator"));
                            }
                            cmdOutput = builder.toString();
*/
                            
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
    System.out.print("\033[H\033[2J"); // resets color;
    System.out.flush();
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


static void printAscii(String asciiConfig, Config config){

        if (!(asciiConfig.isEmpty())){
            IO.println(asciiConfig);
        } else{
            IO.println(config.defaultLogo);
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
//////////////////////////////
    ArrayList<String> sanetizedCmds = new ArrayList<>();
    for (String cmd : cmds){
        if (!(sanetizedCmds.contains(cmd))){
            sanetizedCmds.add(cmd);
        }
    }

    return sanetizedCmds;
}


static void emptyHistory() {
    String historyFilePath = (System.getProperty("user.home") + "/.tshHistory");
    try (FileWriter historyWriter = new FileWriter(historyFilePath, false)){
    historyWriter.write("");
    historyWriter.close();
    history.clear();


    } catch (IOException e){
        IO.println("Could not delete history file");
    }
}


static void loadHistory() {
    history.clear(); 

    try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.tshHistory"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            history.add(line);
        }

    } catch (IOException e) {
        IO.println("could not open History file");
    }
}


static void loadAliases(){
            Scanner scanner = new Scanner(System.in);
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

            
            for (String s : aliases){
                String[] aliasParts = s.split("=", 2);

                LeftAlias.add(aliasParts[0]);
                RightAlias.add(aliasParts[1]);
            }

            
            
        } catch (IOException e) {
            IO.println("Error: Could not open alias File at " + aliasPath + ". Press E for full Error ");
            @SuppressWarnings("unused")
            errorTimerThread aliasErrorThread = new errorTimerThread();
            if (scanner.nextLine().toLowerCase().equals("e")){
                IO.println(e);
            }
        }
}

static Config loadConfig() {
    Config config = new Config();

    String filepath = System.getProperty("user.home") + "/.config/tshell/config.tscfg";
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath));) {
        configFile.clear();

        

        String line;

        while ((line = reader.readLine()) != null) {
            configFile.add(line);
        }


        config.shellStarterString = configFile.get(0); // no .trim() in case they want smt like "Toast % Hi >"
        config.shellStarterAdditionalString = configFile.get(1).trim();
        config.promptFGColour = configFile.get(2);
        config.promptBGColour = configFile.get(3);
        config.startUpCommand = configFile.get(19);
        config.preferedConfigEditor = configFile.get(20);
        config.doReloadAfterConfigEdit = configFile.get(21);
        config.guiDarkMode = configFile.get(22);


        


        StringBuilder ascii = new StringBuilder();
        String newLine = System.lineSeparator();
        
        if((config.asciiArtString.isEmpty() || config.asciiArtString.isBlank())){
        for (int i = 4; i < 18; i++) {
            ascii.append(configFile.get(i)).append(newLine);
        }

        config.asciiArtString = ascii.toString();
        }


    } catch (Exception e) {
        IO.println("Error loading config: " + e);
    }

    return config; 
}



static void renderPrompt(Config config){
    try{
    if (!(config.shellStarterString.isEmpty())){
                if (config.shellStarterString.startsWith("user@host")){
                    if (config.shellStarterAdditionalString.equals("doSlashSeperate")){
                        printColour((System.getProperty("user.name") + "/" + InetAddress.getLocalHost().getHostName() + "> "),config.promptFGColour, config.promptBGColour);
                    } else {
                            printColour((System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName() + config.shellStarterAdditionalString +" "),config.promptFGColour,config.promptBGColour);
                        }
                    } else if (config.shellStarterAdditionalString.equals("showCWD")){
                        printColour((currentDirectory + ">" + "\u001B[0m "), config.promptFGColour, config.promptBGColour);
                    } else {
                    printColour((config.shellStarterString + config.shellStarterAdditionalString + " "), config.promptFGColour, config.promptBGColour);
                  }     
            } else{
            System.out.print("<\\>" + config.shellStarterAdditionalString + " "); 
            }
    } catch (UnknownHostException e){
        IO.println("Error fetching Host");
    } catch (Exception e) {
        IO.println("Error rendering prompt");
    }
}

static void initConfigIfNotExists(){
    try{
        File configDir = new File(System.getProperty("user.home") + "/.config/tshell");
        if (!(configDir.exists())){
        configDir.mkdir();
        }

        File config = new File(System.getProperty("user.home") + "/.config/tshell/config.tscfg");
        if (!(config.exists())){
            config.createNewFile();

        }
    } catch (IOException e){
        IO.println("Could not init config file or config directory");
    }
}

static ArrayList<String> ConfigObjectToArrayList(Config config, ArrayList<String> configFile){
    ArrayList<String> ret = new ArrayList<>();
    
    ret.add(config.shellStarterString);
    ret.add(config.shellStarterAdditionalString);
    ret.add(config.promptFGColour);
    ret.add(config.promptBGColour);
    for (int i = 4; i < 19; i++){ // weird cuz easier for config
    ret.add(configFile.get(i));

    }
    ret.add(config.startUpCommand);
    ret.add(config.preferedConfigEditor);
    ret.add(config.doReloadAfterConfigEdit);
    ret.add(config.guiDarkMode);

    return ret;
    

    
}
static void addAlias(String alias){
    try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/.config/tshell/aliases.tscfg", true)){
        writer.write(alias);

    } catch (IOException e){

    }
}
static void runPipeline(String prompt) {
    try {
        String[] commands = prompt.split("\\|");

        List<Process> processes = new ArrayList<>();
        List<List<String>> parsedCommands = new ArrayList<>();

        // parse commands
        for (String cmd : commands) {
            String[] parts = cmd.trim().split(" ");
            parsedCommands.add(new ArrayList<>(List.of(parts)));
        }

        // start processes
        for (List<String> cmd : parsedCommands) {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(new File(currentDirectory));
            processes.add(pb.start());
        }

        // pipe them
        for (int i = 0; i < processes.size() - 1; i++) {
            Process current = processes.get(i);
            Process next = processes.get(i + 1);

            InputStream in = current.getInputStream();
            OutputStream out = next.getOutputStream();

            new Thread(() -> {
                try (in; out) {
                    in.transferTo(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // print final output
        Process last = processes.get(processes.size() - 1);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(last.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // wait for all
        for (Process p : processes) {
            p.waitFor();
        }

    } catch (Exception e) {
        IO.println("Pipeline error: " + e);
    }
}


}