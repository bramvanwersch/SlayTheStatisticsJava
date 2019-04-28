package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private final Object[] columnAllNames =  {"No","Floor","Health","Max health","Healed","Gold","Gold change","Picked","Not picked","Relic","Special"};
	private final Object[] columnBasicNames  = {"No","Floor","Health","Max health","Healed","Gold","Gold change"};
	private final Object[] columnSpecialNames = {"No","Floor","Picked","Not picked","Relic","Special"};
	private final Object[] columnCardNames = {"No","Floor","Picked","Not picked"};
	private final Object[] columnRelicNames = {"No","Floor","Relic","Ignored relics"};
	private App myApp;
	private JRadioButton rdBtnAll;
	private JRadioButton rdBtnBasic;
	private JRadioButton rdBtnSpecial;
	private JRadioButton rdBtnCards;
	private JRadioButton rdBtnRelics;
	private JRadioButton rdBtnEncounters;
	private JRadioButton rdBtnRestFloor;
	private JRadioButton rdBtnChestFloor;
	private JRadioButton rdBtnShops;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		myApp = new App();
		buildWindow();
	}
		
	public void buildWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1012, 761);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.weighty = 1.0;
		gbc_tabbedPane.weightx = 1.0;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		JPanel tabRunFIles = new JPanel();
		tabbedPane.addTab("Run files", null, tabRunFIles, null);
		
		JPanel tabSummary = new JPanel();
		tabbedPane.addTab("Summary", null, tabSummary, null);
		
		JPanel runPannel = new JPanel();
		tabbedPane.addTab("Run", null, runPannel, null);
		GridBagLayout gbl_runPannel = new GridBagLayout();
		gbl_runPannel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		runPannel.setLayout(gbl_runPannel);
		
		rdBtnAll = new JRadioButton("All", true);
		rdBtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnAll.isSelected()) {
					updateAllTable();
				}
				else {
					rdBtnAll.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnAll = new GridBagConstraints();
		gbc_rdBtnAll.anchor = GridBagConstraints.WEST;
		gbc_rdBtnAll.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnAll.gridx = 0;
		gbc_rdBtnAll.gridy = 0;
		runPannel.add(rdBtnAll, gbc_rdBtnAll);
		
		rdBtnBasic = new JRadioButton("Basic");
		rdBtnBasic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnBasic.isSelected()) {
					updateBasicTable();
				}
				else {
					rdBtnBasic.setSelected(true);
				}
			}
		});
		rdBtnBasic.setToolTipText("The basic statistics");
		GridBagConstraints gbc_rdBtnBasic = new GridBagConstraints();
		gbc_rdBtnBasic.anchor = GridBagConstraints.WEST;
		gbc_rdBtnBasic.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnBasic.gridx = 1;
		gbc_rdBtnBasic.gridy = 0;
		runPannel.add(rdBtnBasic, gbc_rdBtnBasic);
		
		rdBtnSpecial = new JRadioButton("Special");
		rdBtnSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnSpecial.isSelected()) {
					updateSpecialTable();
				}
				else {
					rdBtnSpecial.setSelected(true);
				}
			}
		});
		rdBtnSpecial.setToolTipText("All special information");
		GridBagConstraints gbc_rdBtnSpecial = new GridBagConstraints();
		gbc_rdBtnSpecial.anchor = GridBagConstraints.WEST;
		gbc_rdBtnSpecial.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnSpecial.gridx = 2;
		gbc_rdBtnSpecial.gridy = 0;
		runPannel.add(rdBtnSpecial, gbc_rdBtnSpecial);
		
		rdBtnCards = new JRadioButton("Cards");
		rdBtnCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnCards.isSelected()) {
					updateCardsTable();
				}
				else {
					rdBtnCards.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnCards = new GridBagConstraints();
		gbc_rdBtnCards.anchor = GridBagConstraints.WEST;
		gbc_rdBtnCards.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnCards.gridx = 3;
		gbc_rdBtnCards.gridy = 0;
		runPannel.add(rdBtnCards, gbc_rdBtnCards);
		
		rdBtnRelics = new JRadioButton("Relics");
		rdBtnRelics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_rdBtnRelics = new GridBagConstraints();
		gbc_rdBtnRelics.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnRelics.gridx = 4;
		gbc_rdBtnRelics.gridy = 0;
		runPannel.add(rdBtnRelics, gbc_rdBtnRelics);
		
		rdBtnEncounters = new JRadioButton("Encounters");
		rdBtnEncounters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_rdBtnEncounters = new GridBagConstraints();
		gbc_rdBtnEncounters.anchor = GridBagConstraints.WEST;
		gbc_rdBtnEncounters.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnEncounters.gridx = 5;
		gbc_rdBtnEncounters.gridy = 0;
		runPannel.add(rdBtnEncounters, gbc_rdBtnEncounters);
		
		rdBtnRestFloor = new JRadioButton("Campfires");
		rdBtnRestFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_rdBtnRestFloor = new GridBagConstraints();
		gbc_rdBtnRestFloor.anchor = GridBagConstraints.EAST;
		gbc_rdBtnRestFloor.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnRestFloor.gridx = 6;
		gbc_rdBtnRestFloor.gridy = 0;
		runPannel.add(rdBtnRestFloor, gbc_rdBtnRestFloor);
		
		rdBtnChestFloor = new JRadioButton("Chests");
		rdBtnChestFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_rdBtnChestFloor = new GridBagConstraints();
		gbc_rdBtnChestFloor.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnChestFloor.gridx = 7;
		gbc_rdBtnChestFloor.gridy = 0;
		runPannel.add(rdBtnChestFloor, gbc_rdBtnChestFloor);
		
		rdBtnShops = new JRadioButton("Shops");
		rdBtnShops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_rdBtnShops = new GridBagConstraints();
		gbc_rdBtnShops.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnShops.gridx = 8;
		gbc_rdBtnShops.gridy = 0;
		runPannel.add(rdBtnShops, gbc_rdBtnShops);
		
		JScrollPane scrollPaneRun = new JScrollPane();
		GridBagConstraints gbc_scrollPaneRun = new GridBagConstraints();
		gbc_scrollPaneRun.weighty = 1.0;
		gbc_scrollPaneRun.weightx = 1.0;
		gbc_scrollPaneRun.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneRun.gridwidth = 10;
		gbc_scrollPaneRun.gridx = 0;
		gbc_scrollPaneRun.gridy = 1;
		runPannel.add(scrollPaneRun, gbc_scrollPaneRun);
		scrollPaneRun.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		table = new JTable();
		scrollPaneRun.setViewportView(table);
		updateAllTable();
	}

	protected void updateCardsTable() {
		updateRadioButtons(new boolean[] {false, false, false, true, false, false, false, false, false});
		Object[][] data = myApp.getCardsRunTableData();
		DefaultTableModel myModel = new DefaultTableModel(data, columnCardNames);
		table.setModel(myModel);
	}

	protected void updateSpecialTable() {
		updateRadioButtons(new boolean[] {false, false, true, false, false, false, false, false, false});
		Object[][] data = myApp.getSpecialRunTableData();
		DefaultTableModel myModel = new DefaultTableModel(data, columnSpecialNames);
		table.setModel(myModel);	
		int[] columnWidthList = {50,250,250,300,300,750};
		for (int i = 0; i < myModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(columnWidthList[i]);
		}
	}

	private void updateBasicTable() {
		updateRadioButtons(new boolean[] {false, true, false, false, false, false, false, false, false});
		Object[][] data = myApp.getBasicRunTableData();
		DefaultTableModel myModel = new DefaultTableModel(data, columnBasicNames);
		table.setModel(myModel);	
	}

	private void updateAllTable() {
		updateRadioButtons(new boolean[] {true, false, false, false, false, false, false, false, false});
		Object[][] data = myApp.getAllRunTableData();
		DefaultTableModel myModel = new DefaultTableModel(data, columnAllNames);
		table.setModel(myModel);
		int[] columnWidthList = {30,100,60,80,60,60,100,200,350,200,1000};
		for (int i = 0; i < myModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(columnWidthList[i]);
		}
	}
	
	private void updateRadioButtons(boolean[] onOrOff) {
		rdBtnAll.setSelected(onOrOff[0]);
		rdBtnBasic.setSelected(onOrOff[1]);
		rdBtnSpecial.setSelected(onOrOff[2]);
		rdBtnCards.setSelected(onOrOff[3]);
		rdBtnRelics.setSelected(onOrOff[4]);
		rdBtnEncounters.setSelected(onOrOff[5]);
		rdBtnRestFloor.setSelected(onOrOff[6]);
		rdBtnChestFloor.setSelected(onOrOff[7]);
		rdBtnShops.setSelected(onOrOff[8]);
	}	

}
