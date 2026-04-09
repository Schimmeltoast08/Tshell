import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;






/**
 *
 * @author toasti
 */
public class MyFrame extends JFrame implements ActionListener{

    JButton PromptSettingTextFieldSubmitButton;
    JButton PromptAdditionTextFieldSubmitButton;
    JButton SettingsSubmitButton;
    JButton ForegroundColorTextFieldSubmitButton;
    JButton BackgroundColorTextFieldSubmitButton;
    JButton StartingCommandSubmitButton;
    JButton ExitButton;
    JButton TxtEditSubmitButton;
    JButton ReloadConfEditSubmitButton;
    JButton DarkmodeSubmitButton;

    JTextField PromptSettingTextField;
    JTextField PromptAdditionTextField;
    JTextField ForegroundColorTextField;
    JTextField BackgroundColorTextField;
    JTextField TxtEditTextField;
    JTextField StartingCommandTextField;


    JCheckBox ReloadConfEditCheckbox;
    JCheckBox DarkmodeCheckbox;


    ArrayList<String> bufferConfigFile;
    Boolean doExit = false;


    MyFrame(Boolean doDarkMode, ArrayList<String> configFile){
        Color fontColor;
        Color OddPanelColor;
        Color EvenPanelColor;
        this.bufferConfigFile = configFile;


    if (doDarkMode){
        this.getContentPane().setBackground(new Color(20,20,20));
        fontColor = new Color(60,60,60);
        OddPanelColor = new Color(128,128,128);
        EvenPanelColor = new Color(255,255,255);
    } else {
        this.getContentPane().setBackground(new Color(255,255,255));
        fontColor = new Color(0,0,0);
        OddPanelColor = new Color(128,128,128);
        EvenPanelColor = new Color(220,220,220);

    }



/// promptsetting \\\

    JPanel PromptSettingPanel = new JPanel();
    PromptSettingPanel.setBackground(OddPanelColor);
    PromptSettingPanel.setBounds(0,0,1000,50);
    PromptSettingPanel.setLayout(null);

    JLabel PromptSetting = new JLabel("Prompt: ");
    PromptSetting.setForeground(fontColor);
    PromptSetting.setBounds(0,0,200,50);

    PromptSettingTextField = new JTextField(configFile.get(0));
    PromptSettingTextField.setBounds(700,0,200,50);
    PromptSettingTextField.setCaretColor(fontColor);

    PromptSettingTextFieldSubmitButton = new JButton("Submit");
    PromptSettingTextFieldSubmitButton.setBounds(900,0,100,50);
    PromptSettingTextFieldSubmitButton.addActionListener(this);

/// promotsetting \\\




/// promptaddition \\\
    JLabel PromptAddition = new JLabel("Prompt addition:");
    PromptAddition.setForeground(fontColor);
    PromptAddition.setBounds(0,50,200,50); // x y height width


    JPanel PromptAdditionPanel = new JPanel();
    PromptAdditionPanel.setBackground(EvenPanelColor);
    PromptAdditionPanel.setLayout(null);
    PromptAdditionPanel.setBounds(0,50,1000,50);

    

    PromptAdditionTextField = new JTextField(configFile.get(1));
    PromptAdditionTextField.setBounds(700,50,200,50);
    PromptAdditionTextField.setCaretColor(fontColor);

    PromptAdditionTextFieldSubmitButton = new JButton("Submit");
    PromptAdditionTextFieldSubmitButton.setBounds(900,50,100,50);
    PromptAdditionTextFieldSubmitButton.addActionListener(this);

/// promptaddition \\\

/// FG COL \\\

    JPanel ForegroundColorPanel = new JPanel();
    ForegroundColorPanel.setBackground(OddPanelColor);
    ForegroundColorPanel.setBounds(0,100,1000,50);
    ForegroundColorPanel.setLayout(null);

    JLabel ForegroundColor = new JLabel("Foreground Color: ");
    ForegroundColor.setForeground(fontColor);
    ForegroundColor.setBounds(0,100,200,50);

    ForegroundColorTextField = new JTextField(configFile.get(2));
    ForegroundColorTextField.setBounds(700,100,200,50);
    ForegroundColorTextField.setCaretColor(fontColor);

    ForegroundColorTextFieldSubmitButton = new JButton("Submit");
    ForegroundColorTextFieldSubmitButton.setBounds(900,100,100,50);
    ForegroundColorTextFieldSubmitButton.addActionListener(this);

/// FG COL \\\




/// BG COL \\\

    JPanel BackgroundColorPanel = new JPanel();
    BackgroundColorPanel.setBackground(EvenPanelColor);
    BackgroundColorPanel.setBounds(0,150,1000,50);
    BackgroundColorPanel.setLayout(null);

    JLabel BackgroundColor = new JLabel("Background Color: ");
    BackgroundColor.setForeground(fontColor);
    BackgroundColor.setBounds(0,150,200,50);

    BackgroundColorTextField = new JTextField(configFile.get(3));
    BackgroundColorTextField.setBounds(700,150,200,50);
    BackgroundColorTextField.setCaretColor(fontColor);

    BackgroundColorTextFieldSubmitButton = new JButton("Submit");
    BackgroundColorTextFieldSubmitButton.setBounds(900,150,100,50);
    BackgroundColorTextFieldSubmitButton.addActionListener(this);

/// BG COL \\\


/// StartingCommand \\\

    JLabel StartingCommand = new JLabel("Starting Command: ");
    StartingCommand.setForeground(fontColor);
    StartingCommand.setBounds(0,200,300,50);

    JPanel StartingCommandPanel = new JPanel();
    StartingCommandPanel.setBackground(OddPanelColor);
    StartingCommandPanel.setBounds(0,200,1000,50);
    StartingCommandPanel.setLayout(null);

    StartingCommandTextField = new JTextField();
    StartingCommandTextField.setText(configFile.get(19));
    StartingCommandTextField.setBounds(700,200,200,50);

    StartingCommandSubmitButton = new JButton("Submit");
    StartingCommandSubmitButton.setBounds(900,200,100,50);
    StartingCommandSubmitButton.addActionListener(this);


    /*if (configFile.get(19).equals("ignoreEmptyAsciiArtWarning")) {
        StartingCommandTextField.setSelected(true);
    } else {
        StartingCommandTextField.setSelected(false);
    }*/





/// StartingCommand \\\



/// txtedit \\\

    JPanel TxtEditPanel = new JPanel();
    TxtEditPanel.setBackground(EvenPanelColor);
    TxtEditPanel.setBounds(0,250,1000,50);
    TxtEditPanel.setLayout(null);

    JLabel TxtEdit = new JLabel("Default text editor:");
    TxtEdit.setForeground(fontColor);
    TxtEdit.setBounds(0,250,200,50);

    TxtEditTextField = new JTextField(configFile.get(20));
    TxtEditTextField.setBounds(700,250,200,50);
    TxtEditTextField.setCaretColor(fontColor);

    TxtEditSubmitButton = new JButton("Submit");
    TxtEditSubmitButton.setBounds(900,250,100,50);
    TxtEditSubmitButton.addActionListener(this);

/// txtedit \\\


/// ReloadConfEdit \\\

    JPanel ReloadConfEditPanel = new JPanel();
    ReloadConfEditPanel.setBackground(OddPanelColor);
    ReloadConfEditPanel.setBounds(0,300,1000,50);
    ReloadConfEditPanel.setLayout(null);

    JLabel ReloadConfEdit = new JLabel("Reload after config edit:");
    ReloadConfEdit.setForeground(fontColor);
    ReloadConfEdit.setBounds(0,300,200,50);


    ReloadConfEditCheckbox = new JCheckBox();
    ReloadConfEditCheckbox.setBounds(700,300,200,50);

    ReloadConfEditSubmitButton = new JButton("Submit");
    ReloadConfEditSubmitButton.setBounds(900,300,100,50);
    ReloadConfEditSubmitButton.addActionListener(this);

    if (configFile.get(21).equals("doReloadAfterConfigEdit")) {
        ReloadConfEditCheckbox.setSelected(true);
    } else {
        ReloadConfEditCheckbox.setSelected(false);
    }

/// ReloadConfEdit \\\


/// Darkmode \\\

    JPanel DarkmodePanel = new JPanel();
    DarkmodePanel.setBackground(EvenPanelColor);
    DarkmodePanel.setBounds(0,350,1000,50);
    DarkmodePanel.setLayout(null);

    JLabel Darkmode = new JLabel("Darkmode:");
    Darkmode.setForeground(fontColor);
    Darkmode.setBounds(0,350,200,50);


    DarkmodeCheckbox = new JCheckBox();
    DarkmodeCheckbox.setBounds(700,350,200,50);

    DarkmodeSubmitButton = new JButton("Submit");
    DarkmodeSubmitButton.setBounds(900,350,100,50);
    DarkmodeSubmitButton.addActionListener(this);

    if (configFile.get(22).equals("guiDarkMode")) {
        DarkmodeCheckbox.setSelected(true);
    } else {
        DarkmodeCheckbox.setSelected(false);
    }

/// Darkmode \\\





/// submit settings \\\
    JPanel SettingsSubmitButtonPanel = new JPanel();
    SettingsSubmitButtonPanel.setBackground(OddPanelColor);
    SettingsSubmitButtonPanel.setLayout(null);
    SettingsSubmitButtonPanel.setBounds(0,905,200,50);


    SettingsSubmitButton = new JButton("Save changes");
    SettingsSubmitButton.setBackground(OddPanelColor);
    SettingsSubmitButton.setBounds(0,905,200,50);
    SettingsSubmitButton.addActionListener(this);
/// submit settings \\\





/// Exit button \\\
    JPanel ExitPanel = new JPanel();
    ExitPanel.setBackground(OddPanelColor);
    ExitPanel.setLayout(null);
    ExitPanel.setBounds(800,905,200,50);


    ExitButton = new JButton("Exit");
    ExitButton.setBackground(OddPanelColor);
    ExitButton.setBounds(800,905,200,50);
    ExitButton.addActionListener(this);
/// submit settings \\\










//          frame block ///////////////////////////////////////
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Tshell config");
    this.setVisible(true);
    this.setSize(1000, 1000);
    this.setResizable(false);
    this.setLayout(null); // for absolute positioning

/////////////////////////////////////////////////////////////////



//          Add stack          \\
    this.add(PromptSetting);
    this.add(PromptAddition);
    this.add(ForegroundColor);
    this.add(BackgroundColor);
    this.add(StartingCommand);
    this.add(TxtEdit);
    this.add(ReloadConfEdit);
    this.add(Darkmode);

    this.add(PromptSettingTextField);
    this.add(PromptAdditionTextField);
    this.add(ForegroundColorTextField);
    this.add(BackgroundColorTextField);
    this.add(TxtEditTextField);

    this.add(PromptSettingTextFieldSubmitButton);
    this.add(PromptAdditionTextFieldSubmitButton);
    this.add(ForegroundColorTextFieldSubmitButton);
    this.add(BackgroundColorTextFieldSubmitButton);
    this.add(StartingCommandSubmitButton);
    this.add(TxtEditSubmitButton);
    this.add(ReloadConfEditSubmitButton);
    this.add(DarkmodeSubmitButton);

    this.add(StartingCommandTextField);
    this.add(ReloadConfEditCheckbox);
    this.add(DarkmodeCheckbox);

    this.add(PromptSettingPanel);
    this.add(PromptAdditionPanel);
    this.add(ForegroundColorPanel);
    this.add(BackgroundColorPanel);
    this.add(StartingCommandPanel);
    this.add(TxtEditPanel);
    this.add(ReloadConfEditPanel);
    this.add(DarkmodePanel);



    this.add(SettingsSubmitButton);
    this.add(SettingsSubmitButtonPanel);

    this.add(ExitButton);
    this.add(ExitPanel);



///////////////////////////////////
    String filepath = System.getProperty("user.home") + "/.config/tshell/config.tscfg";
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath));) {

        
        ArrayList<String> configFile1 = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {                // this is redundant and I hate my life, but when I try to add the ascii logo to the regular config, it throws
                    configFile1.add(line);                          // a NP Exception and when I just pass "" for those, it overrides the logo in the config
        }

    for (int i = 4; i <= 18; i++ ){
        bufferConfigFile.set(i, configFile1.get(i));
    }
    } catch (IOException e){;}
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ///         Button events          \\\
        
        if (e.getSource() == PromptSettingTextFieldSubmitButton){
            bufferConfigFile.set(0,PromptSettingTextField.getText());

        }

        if (e.getSource() == PromptAdditionTextFieldSubmitButton){
            bufferConfigFile.set(1, PromptAdditionTextField.getText());
        }

        if (e.getSource() == ForegroundColorTextFieldSubmitButton){
            bufferConfigFile.set(2, ForegroundColorTextField.getText());
        }

        if (e.getSource() == BackgroundColorTextFieldSubmitButton){
            bufferConfigFile.set(3, BackgroundColorTextField.getText());
        }




       if (e.getSource() == StartingCommandSubmitButton){
        bufferConfigFile.set(19, StartingCommandTextField.getText());
       }

        if (e.getSource() == TxtEditSubmitButton){
            bufferConfigFile.set(20, TxtEditTextField.getText());
        }


        if (e.getSource() == ReloadConfEditSubmitButton){
            if (!(ReloadConfEditCheckbox.isSelected())){
                bufferConfigFile.set(21, "-doReloadAfterConfigEdit");
            } else {
                bufferConfigFile.set(21, "doReloadAfterConfigEdit");
            }
        }

        if (e.getSource() == DarkmodeSubmitButton){
            if (!(DarkmodeCheckbox.isSelected())){
                bufferConfigFile.set(22, "-guiDarkMode");
            } else {
                bufferConfigFile.set(22, "guiDarkMode");
            }
        }


        if (e.getSource() == ExitButton){
            this.doExit = true;

        }

        if (e.getSource() == SettingsSubmitButton){
            try(FileWriter writer = new FileWriter(System.getProperty("user.home") + "/.config/tshell/config.tscfg")){
                String cfg = "";
                StringBuilder sb = new StringBuilder();
                for(String str : bufferConfigFile){
                    //cfg += str + "\n";
                    sb.append(str);
                    sb.append("\n");
                }
                cfg = sb.toString();
                //IO.println("Settings saved Successfully");
                JOptionPane.showMessageDialog(null, "Settings saved successfully");
                writer.write(cfg);
                writer.close();


                
            } catch (Exception f){
                JOptionPane.showMessageDialog(null, "Error encountered while saving changes. Aborting");
            }
        }
        
    }



}

