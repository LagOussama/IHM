import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

public class Radar {
	
	private JButton WxonBtn ;
	private JButton TxtBtn;
	private JButton WxaBtn;
	private JButton StdbyBtn;
	private JButton OffBtn;

	private JFrame frame;
	
	private enum PossibleState{
		S1, S2, S3, S4, S5
	}
	
	private enum Modes {
		WXON, TXT, WXA, STDBY, OFF
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
		eventSourcesM.put(Modes.TXT, TxtBtn);
		eventSourcesM.put(Modes.WXA, WxaBtn);


			
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 WxonBtn = new JButton("WXON");
		 WxonBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		wxonActionPerformed(e);
		 	}
		 });
		
		 TxtBtn = new JButton("TXT");
		 TxtBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		txtActionPerformed(e);
		 		
		 	}
		 });
		
		 WxaBtn = new JButton("WXA");
		 WxaBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		wxaActionPerformed(e);
		 	}
		 });
		
		 StdbyBtn = new JButton("STDBY");
		 StdbyBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		stdbyActionPerformed(e);
		 	}
		 });
		
		 OffBtn = new JButton("OFF");
		 OffBtn.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		offActionPerformed(e);
		 	}
		 });
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(WxaBtn)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(TxtBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(52)
							.addComponent(OffBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(WxonBtn)
							.addGap(4)
							.addComponent(StdbyBtn)))
					.addContainerGap(273, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(70)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(StdbyBtn)
						.addComponent(WxonBtn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(TxtBtn)
						.addComponent(WxaBtn))
					.addGap(18)
					.addComponent(OffBtn)
					.addContainerGap(91, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	private void wxonActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.WXON);
		
	}
	private void wxaActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.WXA);
	}
	private void txtActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.TXT);
	}
	private void stdbyActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.STDBY);		
	}
	private void offActionPerformed(ActionEvent e) {
		autoMode.acceptEvent(Modes.OFF);
		
	}

}
