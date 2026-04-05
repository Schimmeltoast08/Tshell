import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

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

    JTextField PromptSettingTextField;
    JTextField PromptAdditionTextField;


    ArrayList<String> bufferConfigFile;


    MyFrame(Boolean doDarkMode, ArrayList<String> configFile){
        Color fontColor;
        Color OddPanelColor;
        Color EvenPanelColor;
        this.bufferConfigFile = configFile;


    if (doDarkMode){
        this.getContentPane().setBackground(new Color(20,20,20));
        fontColor = new Color(255,255,255);
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
    PromptSettingTextField.setCaretColor(Color.black);

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
    PromptAdditionTextField.setCaretColor(Color.black);

    PromptAdditionTextFieldSubmitButton = new JButton("Submit");
    PromptAdditionTextFieldSubmitButton.setBounds(900,50,100,50);
    PromptAdditionTextFieldSubmitButton.addActionListener(this);

/// promptaddition \\\




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
    this.add(PromptSettingTextField);
    this.add(PromptAdditionTextField);
    this.add(PromptSettingTextFieldSubmitButton);
    this.add(PromptAdditionTextFieldSubmitButton);
    this.add(PromptSettingPanel);
    this.add(PromptAdditionPanel);
    this.add(SettingsSubmitButton);
    this.add(SettingsSubmitButtonPanel);




///////////////////////////////////
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == PromptSettingTextFieldSubmitButton){
            bufferConfigFile.set(0,PromptSettingTextField.getText());
        }

        if (e.getSource() == PromptAdditionTextFieldSubmitButton){
            bufferConfigFile.set(1, PromptAdditionTextField.getText());
        }

        if (e.getSource() == SettingsSubmitButton){
            IO.println("Test");
            try(FileWriter writer = new FileWriter(System.getProperty("user.home") + "/.config/tshell/config.tscfg")){
                String cfg = "";
                for(String str : bufferConfigFile){
                    cfg += str + "\n";
                }
                IO.println("\n\n\n\n\n" + cfg);
                writer.write(cfg);
                writer.close();
                
            } catch (Exception f){
                JOptionPane.showMessageDialog(null, "Error encountered while saving changes. Aborting");
            }
        }
        
    }



}

