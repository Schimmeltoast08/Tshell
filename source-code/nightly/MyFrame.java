import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.FlowLayout;



/**
 *
 * @author toasti
 */
public class MyFrame extends JFrame implements ActionListener{

    JButton  PromptSettingTextFieldSubmitButton;
    JTextField PromptSettingTextField;
    String promptSetting;




    MyFrame(Boolean doDarkMode, String cfgFilePrompt){
        Color fontColor;
        Color OddPanelColor;
        Color EvenPanelColor;

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

    JPanel PromptSettingPanel = new JPanel();
    PromptSettingPanel.setBackground(OddPanelColor);
    PromptSettingPanel.setBounds(0,0,200,50);
    PromptSettingPanel.setLayout(null);
    

    JPanel PromptAdditionPanel = new JPanel();
    PromptAdditionPanel.setBackground(EvenPanelColor);
    PromptAdditionPanel.setLayout(null);


    JLabel PromptSetting = new JLabel("Prompt: ");
    PromptSetting.setForeground(fontColor);
    PromptSetting.setBounds(0,0,200,50);

    JLabel PromptAddition = new JLabel("Prompt addition:");
    PromptAddition.setForeground(fontColor);
    PromptAddition.setBounds(0,50,200,50); // x y height width

    PromptSettingTextField = new JTextField(cfgFilePrompt);
    PromptSettingTextField.setBounds(700,0,200,50);
    PromptSettingTextField.setCaretColor(Color.black);

    PromptSettingTextFieldSubmitButton = new JButton("Submit");
    PromptSettingTextFieldSubmitButton.setBounds(900,0,100,50);
    PromptSettingTextFieldSubmitButton.addActionListener(this);
    

//          frame block ///////////////////////////////////////
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Tshell config");
    this.setVisible(true);
    this.setSize(1000, 1000);
    this.setResizable(false);
    this.setLayout(null); // for absolute positioning

/////////////////////////////////////////////////////////////////



//          Add stack
    this.add(PromptSetting);
    this.add(PromptAddition);
    this.add(PromptSettingPanel);
    this.add(PromptAdditionPanel);
    this.add(PromptSettingTextField);
    this.add(PromptSettingTextFieldSubmitButton);



///////////////////////////////////
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == PromptSettingTextFieldSubmitButton){
            this.promptSetting = PromptSettingTextField.getText();
            IO.println(this.promptSetting);

            //TODO https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
            // logic: this puts it all down in a temporary config, moves the old config to a backup and renames
            // or copies over the temp config to the config.tshcfg file, aka replaces it. 
            // ArrayList contains FileLines, replace a line in file, save as tmpcfg, replace cfg with tmpcfg
            // when "save changes" button is pressed. Else don't
            // also add an exit button that closes the window but not the shell. 
            // Plan: Monday evening
        }
        
    }



}

