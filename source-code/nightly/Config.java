
/**
 *
 * @author toasti
 */
public class Config {
    
    String shellStarterString = "";
    String asciiArtString = """
    """;
    String preferedConfigEditor = "vim";
    String shellStarterAdditionalString = "";
    boolean doReloadAfterConfigEdit = false;
    String promptFGColour = "White";
    String promptBGColour = "";
    String guiDarkMode = "";
    String startUpCommand = "";

    /////////////////////// 
    // WHEN ADDING SMT NEW, REMEMBER configObjectToArrayList

static Config loadConfig() {
    Config cfg = new Config();

    cfg.preferedConfigEditor = "vim";
    cfg.promptFGColour = "White";

    return cfg;

}


}
