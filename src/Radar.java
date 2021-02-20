import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;

import fr.irit.automaton.Automaton;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JList;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JSeparator;

public class Radar {
	private JButton WxonBtn ;
	private JButton TstBtn;
	private JButton WxaBtn;
	private JButton StdbyBtn;
	private JButton OffBtn;
	private JLayeredPane layeredPane;
	private JButton ModeBtn;
	private JButton angleBtn;
	private JFrame frame;
	private JButton ManualBtn;
	private JSlider slider ;
	private JButton AutoBtn;
	private JPanel anglePanel ;
	private JPanel ModePanel ;
	private ItemListener itemListener;
	private int state;
	
	 private JLabel labelAngle;
	private JButton btnOn ;
	private JButton btnOff;

	
	private enum PossibleState{
		S1, S2, S3, S4, S5
	}
	
	private enum Modes {
		WXON, TST, WXA, STDBY, OFF
	}
	
	private enum TiltState{
		S1, S2, S3, S4, S5
	}
	
	private enum Tilt {
		CB1, CB2, CB3, CB4, CB5
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Radar window = new Radar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private final Automaton<Modes, PossibleState> autoMode;
    private final Map<Modes, JComponent> eventSourcesM;
    
    private final Automaton<Tilt, TiltState> automatonTilt;
    private final Map<Tilt, JComponent> eventSourcesTilt;

	/**
	 * Create the application.
	 */
	public Radar() {
		initialize();
		
		autoMode = new Automaton<>(Modes.values(), PossibleState.values());
		eventSourcesM = new HashMap<>(Modes.values().length);
		eventSourcesM.put(Modes.WXON, WxonBtn);
		eventSourcesM.put(Modes.OFF, OffBtn);
		eventSourcesM.put(Modes.STDBY, StdbyBtn);
		eventSourcesM.put(Modes.TST, TstBtn);
		eventSourcesM.put(Modes.WXA, WxaBtn);
		
		
		// Set the labels to be painted on the slider
		slider.setPaintLabels(true);
		         
		// Add positions label in the slider
		Hashtable position = new Hashtable();
		position.put(-45, new JLabel("-45"));
		position.put(-30, new JLabel("-30"));
		position.put(-15, new JLabel("-15"));
		position.put(0, new JLabel("0"));
		position.put(15, new JLabel("15"));
		position.put(30, new JLabel("30"));
		position.put(45, new JLabel("45"));
		
		
		slider.setMaximum(45);
		slider.setMinimum(-45);

		         
		// Set the label to be drawn
		slider.setLabelTable(position); 
		
		 for (Modes mode : Modes.values()) {
			 autoMode.addPropertyChangeListener(
	                    mode.toString() + autoMode.ENABLED_SUFFIX, (e) -> {
	                    	eventSourcesM.get(mode).setEnabled((Boolean) e.getNewValue());
	            });
	        }
		 
		 for(int i=0 ; i < PossibleState.values().length; i++)
	        {
	            for(int j=0 ; j < PossibleState.values().length; j++)
	            {
	                if(PossibleState.values()[i] != PossibleState.values()[j])
	                {
	                	autoMode.registerTransition(PossibleState.values()[i], Modes.values()[j], PossibleState.values()[j]);
	                }
	            }
	        }
		   
        autoMode.registerInitialization(PossibleState.S5);

		autoMode.initialize();
		
		automatonTilt = new Automaton<>(Tilt.values(), TiltState.values());
		eventSourcesTilt = new HashMap<>(Tilt.values().length);
		eventSourcesTilt.put(Tilt.CB1, ManualBtn);
		eventSourcesTilt.put(Tilt.CB2, AutoBtn);
		eventSourcesTilt.put(Tilt.CB3, btnOn);
		eventSourcesTilt.put(Tilt.CB4, btnOff);
		eventSourcesTilt.put(Tilt.CB5, slider);
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(anglePanel, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE)
				.addComponent(ModePanel, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(anglePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(ModePanel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
		);
		layeredPane.setLayout(gl_layeredPane);

		   for (Tilt event : Tilt.values()) {
	            automatonTilt.addPropertyChangeListener(
	                    event.toString() + Automaton.ENABLED_SUFFIX, (e) -> {
	                eventSourcesTilt.get(event).setEnabled((Boolean) e.getNewValue());
	            });
	        } 
        automatonTilt.registerInitialization(TiltState.S2);
        
        automatonTilt.registerTransition(TiltState.S2, Tilt.CB1, TiltState.S1);
        
        automatonTilt.registerTransition(TiltState.S1, Tilt.CB2, TiltState.S2);
        

        
        automatonTilt.registerTransition(TiltState.S1, Tilt.CB4, TiltState.S4);
        

        
        automatonTilt.registerTransition(TiltState.S4, Tilt.CB3, TiltState.S3);
        

        
        automatonTilt.registerTransition(TiltState.S4, Tilt.CB5, TiltState.S5);
        
        

        
        automatonTilt.registerTransition(TiltState.S3, Tilt.CB2, TiltState.S2);
        

        
        automatonTilt.registerTransition(TiltState.S3, Tilt.CB4, TiltState.S4);
        

        
        automatonTilt.initialize();
        
        
        
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 layeredPane = new JLayeredPane();
		
		 angleBtn = new JButton("Angle");
		angleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angleBtn.setPressedIcon(null) ;
				switchPanel(anglePanel);
			}
		});
		
		 ModeBtn = new JButton("Mode");
		 ModeBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				switchPanel(ModePanel);

		 	}
		 });
		
		 
	  
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(angleBtn)
					.addGap(18)
					.addComponent(ModeBtn)
					.addGap(268))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(angleBtn)
						.addComponent(ModeBtn))
					.addGap(27)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		          
		           anglePanel = new JPanel();
		          
		           AutoBtn = new JButton("Auto");
		          AutoBtn.addActionListener(new ActionListener() {
		          	public void actionPerformed(ActionEvent e) {
		          		AutoActionPerformed(e);
		          	}
		          });
		          
		           ManualBtn = new JButton("Manual");
		           
		           ManualBtn.addActionListener(new ActionListener() {
			          	public void actionPerformed(ActionEvent e) {
			          		ManualActionPerformed(e);
			          	}
			          });
		           
			          JLabel labelAngle = new JLabel("");

		          
		           slider = new JSlider();
		           
		           slider.addChangeListener(new ChangeListener() {
		              
					@Override
					public void stateChanged(ChangeEvent e) {
		                   System.out.println();
		                   
		                   int v = ((JSlider) e.getSource()).getValue();

		                   
							labelAngle.setText(String.valueOf(v));

						
					}
		           });
		          
		          JLabel lblNewLabel = new JLabel("RADAR ANGLE :");
		          
		          JLabel lblStabilisation = new JLabel("STABILISATION");
		           
		          
		           btnOn = new JButton("ON");
		           btnOn.addActionListener(new ActionListener() {
		           	public void actionPerformed(ActionEvent e) {
		           		stabilizationOnActionPerformed(e);
		           		
		           		
		           	}
		           });
		          
		           btnOff = new JButton("OFF");
		           btnOff.addActionListener(new ActionListener() {
		           	public void actionPerformed(ActionEvent e) {
		           		stabilizationOffActionPerformed(e);
		           	}
		           });
		          
		          JSeparator separator = new JSeparator();
		          
		          GroupLayout gl_anglePanel = new GroupLayout(anglePanel);
		          gl_anglePanel.setHorizontalGroup(
		          	gl_anglePanel.createParallelGroup(Alignment.TRAILING)
		          		.addGroup(gl_anglePanel.createSequentialGroup()
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addContainerGap()
		          					.addComponent(AutoBtn))
		          				.addComponent(ManualBtn))
		          			.addPreferredGap(ComponentPlacement.RELATED)
		          			.addComponent(separator, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
		          			.addGap(21)
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
		          							.addComponent(lblStabilisation, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
		          						.addComponent(btnOn))
		          					.addPreferredGap(ComponentPlacement.UNRELATED, 15, Short.MAX_VALUE)
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.TRAILING)
		          						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addGroup(gl_anglePanel.createParallelGroup(Alignment.TRAILING)
		          								.addComponent(labelAngle)
		          								.addComponent(lblNewLabel))
		          							.addGap(57))))
		          				.addComponent(btnOff))
		          			.addContainerGap())
		          );
		          gl_anglePanel.setVerticalGroup(
		          	gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          		.addGroup(gl_anglePanel.createSequentialGroup()
		          			.addContainerGap()
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addGap(39)
		          							.addGroup(gl_anglePanel.createParallelGroup(Alignment.BASELINE)
		          								.addComponent(AutoBtn)
		          								.addComponent(lblStabilisation)))
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addGap(25)
		          							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addGap(5)
		          							.addComponent(lblNewLabel)
		          							.addGap(9)
		          							.addComponent(ManualBtn))
		          						.addGroup(gl_anglePanel.createSequentialGroup()
		          							.addGap(12)
		          							.addComponent(btnOn)
		          							.addPreferredGap(ComponentPlacement.RELATED)
		          							.addGroup(gl_anglePanel.createParallelGroup(Alignment.BASELINE)
		          								.addComponent(btnOff)
		          								.addComponent(labelAngle)))))
		          				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
		          			.addContainerGap())
		          );
		          anglePanel.setLayout(gl_anglePanel);
		          
		          ModePanel = new JPanel();
		          
		           WxaBtn = new JButton("WXA");
		           
		            OffBtn = new JButton("OFF");
		            OffBtn.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		offActionPerformed(e);
		            	}
		            });
		            
		             StdbyBtn = new JButton("STDBY");
		             
		              TstBtn = new JButton("TST");
		              
		               WxonBtn = new JButton("WXON");
		               GroupLayout gl_ModePanel = new GroupLayout(ModePanel);
		               gl_ModePanel.setHorizontalGroup(
		               	gl_ModePanel.createParallelGroup(Alignment.LEADING)
		               		.addGroup(gl_ModePanel.createSequentialGroup()
		               			.addGroup(gl_ModePanel.createParallelGroup(Alignment.LEADING)
		               				.addGroup(gl_ModePanel.createSequentialGroup()
		               					.addGap(35)
		               					.addComponent(WxaBtn)
		               					.addGap(18)
		               					.addComponent(StdbyBtn)
		               					.addGap(18)
		               					.addComponent(TstBtn)
		               					.addGap(15)
		               					.addComponent(WxonBtn))
		               				.addGroup(gl_ModePanel.createSequentialGroup()
		               					.addGap(176)
		               					.addComponent(OffBtn)))
		               			.addContainerGap(80, Short.MAX_VALUE))
		               );
		               gl_ModePanel.setVerticalGroup(
		               	gl_ModePanel.createParallelGroup(Alignment.LEADING)
		               		.addGroup(Alignment.TRAILING, gl_ModePanel.createSequentialGroup()
		               			.addContainerGap(20, Short.MAX_VALUE)
		               			.addGroup(gl_ModePanel.createParallelGroup(Alignment.BASELINE)
		               				.addComponent(StdbyBtn)
		               				.addComponent(WxonBtn)
		               				.addComponent(WxaBtn)
		               				.addComponent(TstBtn))
		               			.addGap(18)
		               			.addComponent(OffBtn)
		               			.addGap(64))
		               );
		               ModePanel.setLayout(gl_ModePanel);
		               WxonBtn.addActionListener(new ActionListener() {
		               	public void actionPerformed(ActionEvent e) {
		               		wxonActionPerformed(e);
		               	}
		               });
		               TstBtn.addActionListener(new ActionListener() {
		               	public void actionPerformed(ActionEvent e) {
		               		txtActionPerformed(e);
		               		
		               	}
		               });
		               StdbyBtn.addActionListener(new ActionListener() {
		               	public void actionPerformed(ActionEvent e) {
		               		stdbyActionPerformed(e);
		               	}
		               });
		               WxaBtn.addActionListener(new ActionListener() {
		               	public void actionPerformed(ActionEvent e) {
		               		wxaActionPerformed(e);
		               	}
		               });
		frame.getContentPane().setLayout(groupLayout);
	}
	private void wxonActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.WXON);
	}
	private void wxaActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.WXA);
	}
	private void txtActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.TST);
	}
	private void stdbyActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.STDBY);		
	}
	private void offActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.OFF);
		
	}
	
	private void ManualActionPerformed(ActionEvent e) {
		automatonTilt.acceptEvent(Tilt.CB1);
		
	}
	
	private void AutoActionPerformed(ActionEvent e) {
		automatonTilt.acceptEvent(Tilt.CB2);
		
	}
	
	
	private void stabilizationOnActionPerformed(ActionEvent e) {
		automatonTilt.acceptEvent(Tilt.CB3);
		
	}
	private void stabilizationOffActionPerformed(ActionEvent e) {
		automatonTilt.acceptEvent(Tilt.CB4);
		
	}
	

	
//	private void angleActionPerformed(ActionEvent e) {
//		automatonTilt.acceptEvent(Tilt.CB5);
//		
//	}
	
	public void switchPanel(JPanel panel) {
		
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
		
	}
}
