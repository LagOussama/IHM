import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
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

	
	private enum PossibleState{
		S1, S2, S3, S4, S5
	}
	
	private enum Modes {
		WXON, TST, WXA, STDBY, OFF
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
		position.put(0, new JLabel("0"));
		position.put(25, new JLabel("25"));
		position.put(50, new JLabel("50"));
		position.put(75, new JLabel("75"));
		position.put(100, new JLabel("100"));
		         
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
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(angleBtn)
							.addGap(18)
							.addComponent(ModeBtn)
							.addGap(257))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
							.addGap(15))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(angleBtn)
						.addComponent(ModeBtn))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addGap(29))
		);
		          
		           anglePanel = new JPanel();
		          anglePanel.setBounds(0, 0, 429, 171);
		          layeredPane.add(anglePanel);
		          
		           AutoBtn = new JButton("Auto");
		          AutoBtn.addActionListener(new ActionListener() {
		          	public void actionPerformed(ActionEvent e) {
		          	}
		          });
		          
		           ManualBtn = new JButton("Manual");
		          
		           slider = new JSlider();
		          
		          JLabel lblNewLabel = new JLabel("RADAR ANGLE :");
		          
		          JLabel lblStabilisation = new JLabel("STABILISATION");
		          
		          JToggleButton tglbtnOn = new JToggleButton("ON");
		          GroupLayout gl_anglePanel = new GroupLayout(anglePanel);
		          gl_anglePanel.setHorizontalGroup(
		          	gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          		.addGroup(Alignment.TRAILING, gl_anglePanel.createSequentialGroup()
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addContainerGap()
		          					.addComponent(AutoBtn))
		          				.addComponent(ManualBtn))
		          			.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(6)
		          					.addComponent(tglbtnOn))
		          				.addComponent(lblStabilisation, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(51)
		          					.addComponent(lblNewLabel))
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(18)
		          					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		          			.addGap(10))
		          );
		          gl_anglePanel.setVerticalGroup(
		          	gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          		.addGroup(gl_anglePanel.createSequentialGroup()
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(45)
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.BASELINE)
		          						.addComponent(AutoBtn)
		          						.addComponent(lblStabilisation)))
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(25)
		          					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		          			.addGroup(gl_anglePanel.createParallelGroup(Alignment.LEADING)
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addGap(30)
		          					.addComponent(ManualBtn))
		          				.addGroup(gl_anglePanel.createSequentialGroup()
		          					.addPreferredGap(ComponentPlacement.RELATED)
		          					.addGroup(gl_anglePanel.createParallelGroup(Alignment.TRAILING)
		          						.addComponent(lblNewLabel)
		          						.addComponent(tglbtnOn))))
		          			.addContainerGap(38, Short.MAX_VALUE))
		          );
		          anglePanel.setLayout(gl_anglePanel);
		          
		          ModePanel = new JPanel();
		          ModePanel.setBounds(0, 0, 429, 171);
		          layeredPane.add(ModePanel);
		          
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
	
	public void switchPanel(JPanel panel) {
		
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
		
	}
}
