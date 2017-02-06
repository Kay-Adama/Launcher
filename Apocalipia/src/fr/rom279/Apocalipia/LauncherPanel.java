package fr.rom279.Apocalipia;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.launcher.util.UsernameSaver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

import static fr.theshark34.swinger.Swinger.*;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {

    private Image background = Swinger.getResource("background.png");

    private UsernameSaver saver = new UsernameSaver(Launcher.AP_INFOS);

    private JTextField usernameField = new JTextField(saver.getUsername(""));
    private JPasswordField passwordField = new JPasswordField();

    private STexturedButton playBoutton = new STexturedButton(Swinger.getResource("play.png"));
    private STexturedButton quitBoutton = new STexturedButton(Swinger.getResource("quit.png"));
    private STexturedButton hideBoutton = new STexturedButton(Swinger.getResource("reduire.jpg"));

    private SColoredBar progressBar = new SColoredBar(getTransparentWhite(100), getTransparentWhite(175));
    private JLabel infoLabel = new JLabel("Clique sur Jouer !", SwingerConstants.CENTER);
    
	private Component playButton;

    public <passwordField> LauncherPanel(){
        this.setLayout(null);
        
        usernameField.setForeground(Color.CYAN);
        usernameField.setFont(usernameField.getFont().deriveFont(22F));
        usernameField.setCaretColor(Color.GREEN);
        usernameField.setOpaque(true);
        usernameField.setBorder(null);
        usernameField.setBounds(566,254,262,39);
        this.add(usernameField);
        
        passwordField.setForeground(Color.CYAN);
        passwordField.setFont(usernameField.getFont().deriveFont(22F));
        passwordField.setCaretColor(Color.GREEN);
        passwordField.setOpaque(true);
        passwordField.setBorder(null);
        passwordField.setBounds(566,375,262,39);
        this.add(passwordField);


        playBoutton.setBounds(562 , 464);
        playBoutton.addEventListener(this); 
        this.add(playBoutton);

        quitBoutton.setBounds(835 , 5);
        quitBoutton.addEventListener(this);
        this.add(quitBoutton);

        hideBoutton.setBounds(825 , 5);
        hideBoutton.addEventListener(this);
        this.add(hideBoutton);
        
        progressBar.setBounds(12,593,961,20);
        this.add(progressBar);
        
        infoLabel.setForeground(Color.ORANGE);
        infoLabel.setFont(usernameField.getFont());
        infoLabel.setBounds(12,593,951,20);
        this.add(infoLabel);
        
    }
    

    @SuppressWarnings("deprecation")
	@Override
    public void onEvent(SwingerEvent event){
          if(event.getSource() == playBoutton){
        	  setFieldEnabled(false);
        	  
        	  if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0);
        	  		JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo et un mot de passe valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        	  		setFieldEnabled(true);
        	  		return;
          }
          

        		  try {
        			  Launcher.update();
        		  } catch (Exception event1) {
        			  JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de se connecter :" + event1.getErrorModel().getErrorMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        			  setFieldEnabled(true);
        			  return;
        		  }
        		  
        		  @SuppressWarnings("deprecation")
				public void run() {
            		  try {
            			  Launcher.auth(usernameField.getText(), passwordField.getText());
            		  } catch (AuthenticationException event) {
            			  Launcher.interruptThread();
            			  JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de mettre le jeux a jour : " + event, "Erreur", JOptionPane.ERROR_MESSAGE);
            			  setFieldEnabled(true);
            			  return;
        	  }
          	};		  
          	t.start(); 
    	  	} else if(event.getSource() == quitBoutton){
    	  		System.exit(0);
    	  	} else if(event.getSource() == hideBoutton){
    	  		LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
        	  
    	  	}

      @Override
     public void paintComponent(Graphics graphics) {
     super.paintComponent(graphics);
     Swinger.drawFullsizedImage(graphics, this, background);
       
    }
    
    private void setFieldEnabled(boolean enabled) {
    	usernameField.setEnabled(enabled);
    	passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
    }
    
    public SColoredBar getProgressBar() {
    	return progressBar;
    }
    
    public void setInfoText(String text) {
    	infoLabel.setText(text);
    }