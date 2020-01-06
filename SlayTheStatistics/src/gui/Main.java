package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import global.GlobalApp;
import run.RunApp;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class Main extends JFrame {
	private JPanel contentPane;
	private JTable runTable;
	private JTable characterSummaryTable;
	private final Object[] columnAllNames =  {"No","Floor","Health","Max health","Healed","Gold","Gold change",
			"Potion gained","Potion use","Picked","Not picked","Relic","Special"};
	private final Object[] columnBasicNames  = {"No","Floor","Health","Max health","Healed","Gold","Gold change"};
	private final Object[] columnSpecialNames = {"No","Floor","Picked","Not picked","Relic","Special"};
	private final Object[] columnCardNames = {"No","Floor","Picked","Not picked"};
	private final Object[] columnRelicNames = {"No","Floor","Relic","Ignored relics"};
	private final Object[] columnEncountersNames = {"No","Floor","Enemie(s)","Damage","Turns","Healed"};
	private final Object[] columnRestNames = {"No","Floor","Activity","Item gained/changed"};
	private final Object[] columnEventNames = {"No","Floor","Name","Choice","Enemie(s)","Damage","Turns",
			"Healed","Relic","Card(s)","Card rem.","Card upgr."};
	private final Object[] columnShopNames = {"No","Floor","Bought","Removed"};
	private RunApp myRunApp;
	private JTabbedPane tabbedPane;
	private JRadioButton rdBtnAll;
	private JRadioButton rdBtnBasic;
	private JRadioButton rdBtnSpecial;
	private JRadioButton rdBtnCards;
	private JRadioButton rdBtnRelics;
	private JRadioButton rdBtnEncounters;
	private JRadioButton rdBtnRestFloor;
	private JRadioButton rdBtnEventFloor;
	private JRadioButton rdBtnShops;
	private JLabel lblCharacterName;
	private JLabel lblMasterDeckName;
	private JLabel lblAscensionLvlName;
	private JLabel lblRelicsName;
	private JLabel lblVictoryName;
	private JLabel lblFloorReachedName;
	private JLabel lblSeedName;
	private JLabel lblSpecialSeedName;
	private static GraphBuilder pnlGold;
	private static GraphBuilder pnlHealed;
	private static GraphBuilder pnlHealth;
	private static GraphBuilder pnlMaxHealth;
	private JLabel lblHealth;
	private JLabel lblMaxHealth;
	private JLabel lblHealthChange;
	private JLabel lblGoldChange;
	private JLabel lblSpace1;
	private JLabel lblSpace2;
	private JMenuBar menuBar;
	
	private GlobalApp myGlobalApp;
	private JRadioButton rdbtnCardSummary;
	private JRadioButton rdbtnRelicSummary;
	private JButton btnShowSummary;
	
	//private String character = "IRONCLAD";
	private JLabel lblProblemSummary;


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
		//load the settings
		Settings.loadSettings();
		checkDirectory();
		myRunApp = new RunApp();
		myGlobalApp = new GlobalApp();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		buildWindow();
		addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
            	Component c = e.getComponent();
            	Main.updateGraphs((int) ((c.getWidth())*0.33), (int) (c.getHeight()*0.45));
            }
        });
		addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                Settings.writeSettings();
                ((JFrame)(e.getComponent())).dispose();
            }
        });
		setBounds(100, 100, 1600, 900);
	}

	public void buildWindow() {
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFileOptions = new JMenu("File");
		menuBar.add(menuFileOptions);
		
		JMenuItem menuOpenFile = new JMenuItem("Open...");
		menuOpenFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = FileChooser.open(Settings.GET_CHARACTER(), "Select a run file.", false);
				if (filePath != null) {
					myRunApp.setRun(filePath);
					//TODO change this part. It feels unnecesairy
					buildWindow();
					setBounds(100, 100, 1600, 900);
					changeTabName(1, "Summary of " + myRunApp.getRun());
					changeTabName(2, "Run(" + myRunApp.getRun() + ")");
				}
			}
		});
		menuFileOptions.add(menuOpenFile);
		
		JMenu menuCharacterOptions = new JMenu("Characters");
		menuBar.add(menuCharacterOptions);
		
		String[] characterNames = myRunApp.getCharacterNames();
		for (int i = 0; i < characterNames.length; i++) {
			String folderName = characterNames[i];
			JMenuItem menuCharacterName = new JMenuItem(folderName);
			menuCharacterName.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Settings.saveCharacter(folderName);
					changeTabName(0, Settings.CHARACTER + " Summary");
					rdbtnCardSummary.doClick();
				}
			});
			menuCharacterOptions.add(menuCharacterName);
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.weighty = 1.0;
		gbc_tabbedPane.weightx = 1.0;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		JPanel tabCharacterSummary = new JPanel();
		tabbedPane.addTab(Settings.CHARACTER + " Summary", null, tabCharacterSummary, null);
		GridBagConstraints gbc_scrollPaneSummary_1 = new GridBagConstraints();
		gbc_scrollPaneSummary_1.weighty = 1.0;
		gbc_scrollPaneSummary_1.weightx = 1.0;
		gbc_scrollPaneSummary_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSummary_1.gridwidth = 11;
		gbc_scrollPaneSummary_1.gridx = 0;
		gbc_scrollPaneSummary_1.gridy = 2;
		GridBagLayout gbl_tabCharacterSummary = new GridBagLayout();
		gbl_tabCharacterSummary.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_tabCharacterSummary.rowWeights = new double[]{0.0, 0.0, 0.0};
		tabCharacterSummary.setLayout(gbl_tabCharacterSummary);
		
		lblProblemSummary = new JLabel("");
		GridBagConstraints gbc_lblProblemSummary = new GridBagConstraints();
		gbc_lblProblemSummary.insets = new Insets(0, 0, 5, 5);
		gbc_lblProblemSummary.gridx = 1;
		gbc_lblProblemSummary.gridy = 0;
		tabCharacterSummary.add(lblProblemSummary, gbc_lblProblemSummary);
		
		rdbtnCardSummary = new JRadioButton("Card Summary");
		rdbtnCardSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillCharacterSummaryTable();
			}
		});
		rdbtnCardSummary.setSelected(true);
		GridBagConstraints gbc_rdbtnrdBtnCardSummary = new GridBagConstraints();
		gbc_rdbtnrdBtnCardSummary.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnrdBtnCardSummary.gridx = 0;
		gbc_rdbtnrdBtnCardSummary.gridy = 1;
		tabCharacterSummary.add(rdbtnCardSummary, gbc_rdbtnrdBtnCardSummary);
		
		rdbtnRelicSummary = new JRadioButton("Relic Summary");
		rdbtnRelicSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillCharacterSummaryTable();
			}
		});
		GridBagConstraints gbc_rdbtnRelicSummary = new GridBagConstraints();
		gbc_rdbtnRelicSummary.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRelicSummary.gridx = 1;
		gbc_rdbtnRelicSummary.gridy = 1;
		tabCharacterSummary.add(rdbtnRelicSummary, gbc_rdbtnRelicSummary);
		
		JScrollPane scrollPaneSummary = new JScrollPane();
		tabCharacterSummary.add(scrollPaneSummary, gbc_scrollPaneSummary_1);
		scrollPaneSummary.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		ButtonGroup btnGroupCharacterSummary = new ButtonGroup();
		btnGroupCharacterSummary.add(rdbtnCardSummary);
		btnGroupCharacterSummary.add(rdbtnRelicSummary);
		
		characterSummaryTable = new JTable();
		scrollPaneSummary.setViewportView(characterSummaryTable);
		
		
		
		
		
		JPanel tabSummary = new JPanel();
		tabbedPane.addTab("Summary of " + myRunApp.getRun(), null, tabSummary, null);
		GridBagLayout gbl_tabSummary = new GridBagLayout();
		gbl_tabSummary.rowHeights = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30};
		gbl_tabSummary.columnWidths = new int[] {0, 0, 0, 30, 0, 30, 0, 30};
		tabSummary.setLayout(gbl_tabSummary);
		
		lblSpace1 = new JLabel("");
		GridBagConstraints gbc_lblSpace1 = new GridBagConstraints();
		gbc_lblSpace1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpace1.gridx = 3;
		gbc_lblSpace1.gridy = 0;
		tabSummary.add(lblSpace1, gbc_lblSpace1);
		
		lblHealthChange = new JLabel("Health change:");
		GridBagConstraints gbc_lblHealthChange = new GridBagConstraints();
		gbc_lblHealthChange.weighty = 1.0;
		gbc_lblHealthChange.weightx = 1.0;
		gbc_lblHealthChange.insets = new Insets(0, 0, 5, 5);
		gbc_lblHealthChange.gridx = 4;
		gbc_lblHealthChange.gridy = 0;
		tabSummary.add(lblHealthChange, gbc_lblHealthChange);
		
		lblSpace2 = new JLabel("");
		GridBagConstraints gbc_lblSpace2 = new GridBagConstraints();
		gbc_lblSpace2.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpace2.gridx = 5;
		gbc_lblSpace2.gridy = 0;
		tabSummary.add(lblSpace2, gbc_lblSpace2);
		
		lblGoldChange = new JLabel("Gold Change");
		GridBagConstraints gbc_lblGoldChange = new GridBagConstraints();
		gbc_lblGoldChange.weighty = 1.0;
		gbc_lblGoldChange.weightx = 1.0;
		gbc_lblGoldChange.insets = new Insets(0, 0, 5, 0);
		gbc_lblGoldChange.gridx = 6;
		gbc_lblGoldChange.gridy = 0;
		tabSummary.add(lblGoldChange, gbc_lblGoldChange);
		
		JLabel lblCharacter = new JLabel("Character played:");
		GridBagConstraints gbc_lblCharacter = new GridBagConstraints();
		gbc_lblCharacter.weightx = 1.0;
		gbc_lblCharacter.weighty = 1.0;
		gbc_lblCharacter.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharacter.anchor = GridBagConstraints.LINE_START;
		gbc_lblCharacter.gridx = 1;
		gbc_lblCharacter.gridy = 1;
		tabSummary.add(lblCharacter, gbc_lblCharacter);
		
		GridBagConstraints gbc_pnlDamageGraph = new GridBagConstraints();
		gbc_pnlDamageGraph.weighty = 8.0;
		gbc_pnlDamageGraph.gridheight = 9;
		gbc_pnlDamageGraph.weightx = 8.0;
		gbc_pnlDamageGraph.insets = new Insets(0, 0, 5, 5);
		gbc_pnlDamageGraph.fill = GridBagConstraints.BOTH;
		gbc_pnlDamageGraph.gridx = 4;
		gbc_pnlDamageGraph.gridy = 1;
		pnlHealth = new GraphBuilder(myRunApp.getIntGraphValuesHealth()[0],myRunApp.getIntGraphValuesHealth()[1],
				300,200,new String[] {"Floor","Health"}, false);
		tabSummary.add(pnlHealth, gbc_pnlDamageGraph);
		pnlHealth.setLayout(new GridLayout(1, 0, 0, 0));
		
		GridBagConstraints gbc_pnlGold = new GridBagConstraints();
		gbc_pnlGold.weighty = 8.0;
		gbc_pnlGold.weightx = 8.0;
		gbc_pnlGold.gridheight = 9;
		gbc_pnlGold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlGold.fill = GridBagConstraints.BOTH;
		gbc_pnlGold.gridx = 6;
		gbc_pnlGold.gridy = 1;
		pnlGold = new GraphBuilder(myRunApp.getIntGraphValuesGold()[0],myRunApp.getIntGraphValuesGold()[1],
				300,200,new String[] {"Floor","Gold"}, false);
		tabSummary.add(pnlGold, gbc_pnlGold);
		GridBagLayout gbl_pnlGold = new GridBagLayout();
		pnlGold.setLayout(gbl_pnlGold);
		
		lblCharacterName = new JLabel("");
		GridBagConstraints gbc_lblCharacterName = new GridBagConstraints();
		gbc_lblCharacterName.weighty = 1.0;
		gbc_lblCharacterName.weightx = 1.0;
		gbc_lblCharacterName.anchor = GridBagConstraints.LINE_START;
		gbc_lblCharacterName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharacterName.gridx = 2;
		gbc_lblCharacterName.gridy = 1;
		tabSummary.add(lblCharacterName, gbc_lblCharacterName);
		
		JLabel lblMasterDeck = new JLabel("Master deck:");
		GridBagConstraints gbc_lblMasterDeck = new GridBagConstraints();
		gbc_lblMasterDeck.weighty = 1.0;
		gbc_lblMasterDeck.weightx = 1.0;
		gbc_lblMasterDeck.anchor = GridBagConstraints.LINE_START;
		gbc_lblMasterDeck.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasterDeck.gridx = 1;
		gbc_lblMasterDeck.gridy = 2;
		tabSummary.add(lblMasterDeck, gbc_lblMasterDeck);
		
		lblMasterDeckName = new JLabel("");
		GridBagConstraints gbc_lblMasterDeckName = new GridBagConstraints();
		gbc_lblMasterDeckName.weighty = 1.0;
		gbc_lblMasterDeckName.weightx = 1.0;
		gbc_lblMasterDeckName.anchor = GridBagConstraints.LINE_START;
		gbc_lblMasterDeckName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasterDeckName.gridx = 2;
		gbc_lblMasterDeckName.gridy = 2;
		tabSummary.add(lblMasterDeckName, gbc_lblMasterDeckName);
		
		JLabel lblRelics = new JLabel("Relics:");
		lblRelics.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblRelics = new GridBagConstraints();
		gbc_lblRelics.weighty = 1.0;
		gbc_lblRelics.weightx = 1.0;
		gbc_lblRelics.anchor = GridBagConstraints.LINE_START;
		gbc_lblRelics.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelics.gridx = 1;
		gbc_lblRelics.gridy = 3;
		tabSummary.add(lblRelics, gbc_lblRelics);
		
		lblRelicsName = new JLabel("");
		GridBagConstraints gbc_lblRelicsName = new GridBagConstraints();
		gbc_lblRelicsName.weighty = 1.0;
		gbc_lblRelicsName.weightx = 1.0;
		gbc_lblRelicsName.anchor = GridBagConstraints.LINE_START;
		gbc_lblRelicsName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelicsName.gridx = 2;
		gbc_lblRelicsName.gridy = 3;
		tabSummary.add(lblRelicsName, gbc_lblRelicsName);
		
		JLabel lblAscnsionLvl = new JLabel("Ascnsion level:");
		GridBagConstraints gbc_lblAscnsionLvl = new GridBagConstraints();
		gbc_lblAscnsionLvl.weighty = 1.0;
		gbc_lblAscnsionLvl.weightx = 1.0;
		gbc_lblAscnsionLvl.anchor = GridBagConstraints.LINE_START;
		gbc_lblAscnsionLvl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAscnsionLvl.gridx = 1;
		gbc_lblAscnsionLvl.gridy = 4;
		tabSummary.add(lblAscnsionLvl, gbc_lblAscnsionLvl);
		
		lblAscensionLvlName = new JLabel("");
		GridBagConstraints gbc_lblAscensionLvlName = new GridBagConstraints();
		gbc_lblAscensionLvlName.weighty = 1.0;
		gbc_lblAscensionLvlName.weightx = 1.0;
		gbc_lblAscensionLvlName.anchor = GridBagConstraints.LINE_START;
		gbc_lblAscensionLvlName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAscensionLvlName.gridx = 2;
		gbc_lblAscensionLvlName.gridy = 4;
		tabSummary.add(lblAscensionLvlName, gbc_lblAscensionLvlName);
		
		JLabel lblVictory = new JLabel("Victory:");
		GridBagConstraints gbc_lblVictory = new GridBagConstraints();
		gbc_lblVictory.weighty = 1.0;
		gbc_lblVictory.weightx = 1.0;
		gbc_lblVictory.anchor = GridBagConstraints.LINE_START;
		gbc_lblVictory.insets = new Insets(0, 0, 5, 5);
		gbc_lblVictory.gridx = 1;
		gbc_lblVictory.gridy = 5;
		tabSummary.add(lblVictory, gbc_lblVictory);
		
		lblVictoryName = new JLabel("");
		GridBagConstraints gbc_lblVictoryName = new GridBagConstraints();
		gbc_lblVictoryName.weighty = 1.0;
		gbc_lblVictoryName.weightx = 1.0;
		gbc_lblVictoryName.anchor = GridBagConstraints.LINE_START;
		gbc_lblVictoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblVictoryName.gridx = 2;
		gbc_lblVictoryName.gridy = 5;
		tabSummary.add(lblVictoryName, gbc_lblVictoryName);
		
		JLabel lblFloorReached = new JLabel("Floor reached:");
		GridBagConstraints gbc_lblFloorReached = new GridBagConstraints();
		gbc_lblFloorReached.weighty = 1.0;
		gbc_lblFloorReached.weightx = 1.0;
		gbc_lblFloorReached.anchor = GridBagConstraints.LINE_START;
		gbc_lblFloorReached.insets = new Insets(0, 0, 5, 5);
		gbc_lblFloorReached.gridx = 1;
		gbc_lblFloorReached.gridy = 6;
		tabSummary.add(lblFloorReached, gbc_lblFloorReached);
		
		lblHealth = new JLabel("Max health change:");
		GridBagConstraints gbc_lblHealth = new GridBagConstraints();
		gbc_lblHealth.weighty = 1.0;
		gbc_lblHealth.weightx = 1.0;
		gbc_lblHealth.insets = new Insets(0, 0, 5, 5);
		gbc_lblHealth.gridx = 4;
		gbc_lblHealth.gridy = 10;
		tabSummary.add(lblHealth, gbc_lblHealth);
		
		lblMaxHealth = new JLabel("Health healed:");
		GridBagConstraints gbc_lblMaxHealth = new GridBagConstraints();
		gbc_lblMaxHealth.weighty = 1.0;
		gbc_lblMaxHealth.weightx = 1.0;
		gbc_lblMaxHealth.insets = new Insets(0, 0, 5, 0);
		gbc_lblMaxHealth.gridx = 6;
		gbc_lblMaxHealth.gridy = 10;
		tabSummary.add(lblMaxHealth, gbc_lblMaxHealth);
		
		GridBagConstraints gbc_pnlMaxHealth = new GridBagConstraints();
		gbc_pnlMaxHealth.weightx = 8.0;
		gbc_pnlMaxHealth.weighty = 8.0;
		gbc_pnlMaxHealth.insets = new Insets(0, 0, 0, 5);
		gbc_pnlMaxHealth.fill = GridBagConstraints.BOTH;
		gbc_pnlMaxHealth.gridx = 4;
		gbc_pnlMaxHealth.gridy = 11;
		pnlMaxHealth = new GraphBuilder(myRunApp.getIntGraphValuesMaxHp()[0],myRunApp.getIntGraphValuesMaxHp()[1],
				300,200,new String[] {"Floor","Max health"}, false);
		tabSummary.add(pnlMaxHealth, gbc_pnlMaxHealth);
		GridBagLayout gbl_pnlMaxHealth = new GridBagLayout();
		pnlMaxHealth.setLayout(gbl_pnlMaxHealth);
		
		GridBagConstraints gbc_pnlHealed = new GridBagConstraints();
		gbc_pnlHealed.weighty = 8.0;
		gbc_pnlHealed.weightx = 8.0;
		gbc_pnlHealed.fill = GridBagConstraints.BOTH;
		gbc_pnlHealed.gridx = 6;
		gbc_pnlHealed.gridy = 11;
		pnlHealed = new GraphBuilder(myRunApp.getIntGraphValuesHealed()[0],myRunApp.getIntGraphValuesHealed()[1],
				300,200,new String[] {"Floor","Health healed"}, false);
		tabSummary.add(pnlHealed, gbc_pnlHealed);
		GridBagLayout gbl_pnlHealed = new GridBagLayout();
		pnlHealed.setLayout(gbl_pnlHealed);
		
		this.pack();
		
		lblFloorReachedName = new JLabel("");
		GridBagConstraints gbc_lblFloorReachedName = new GridBagConstraints();
		gbc_lblFloorReachedName.weighty = 1.0;
		gbc_lblFloorReachedName.weightx = 1.0;
		gbc_lblFloorReachedName.anchor = GridBagConstraints.LINE_START;
		gbc_lblFloorReachedName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFloorReachedName.gridx = 2;
		gbc_lblFloorReachedName.gridy = 6;
		tabSummary.add(lblFloorReachedName, gbc_lblFloorReachedName);
		
		JLabel lblSeed = new JLabel("Seed:");
		GridBagConstraints gbc_lblSeed = new GridBagConstraints();
		gbc_lblSeed.weighty = 1.0;
		gbc_lblSeed.weightx = 1.0;
		gbc_lblSeed.anchor = GridBagConstraints.LINE_START;
		gbc_lblSeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeed.gridx = 1;
		gbc_lblSeed.gridy = 7;
		tabSummary.add(lblSeed, gbc_lblSeed);
		
		lblSeedName = new JLabel("");
		GridBagConstraints gbc_lblSeedName = new GridBagConstraints();
		gbc_lblSeedName.weighty = 1.0;
		gbc_lblSeedName.weightx = 1.0;
		gbc_lblSeedName.anchor = GridBagConstraints.LINE_START;
		gbc_lblSeedName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeedName.gridx = 2;
		gbc_lblSeedName.gridy = 7;
		tabSummary.add(lblSeedName, gbc_lblSeedName);
		
		JLabel lblSpecialSeed = new JLabel("Special seed:");
		GridBagConstraints gbc_lblSpecialSeed = new GridBagConstraints();
		gbc_lblSpecialSeed.weighty = 1.0;
		gbc_lblSpecialSeed.weightx = 1.0;
		gbc_lblSpecialSeed.anchor = GridBagConstraints.LINE_START;
		gbc_lblSpecialSeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpecialSeed.gridx = 1;
		gbc_lblSpecialSeed.gridy = 8;
		tabSummary.add(lblSpecialSeed, gbc_lblSpecialSeed);
		
		lblSpecialSeedName = new JLabel("");
		GridBagConstraints gbc_lblSpecialSeedName = new GridBagConstraints();
		gbc_lblSpecialSeedName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpecialSeedName.weighty = 1.0;
		gbc_lblSpecialSeedName.weightx = 1.0;
		gbc_lblSpecialSeedName.anchor = GridBagConstraints.LINE_START;
		gbc_lblSpecialSeedName.gridx = 2;
		gbc_lblSpecialSeedName.gridy = 8;
		tabSummary.add(lblSpecialSeedName, gbc_lblSpecialSeedName);
		
		
		
		
		JPanel runPannel = new JPanel();
		tabbedPane.addTab("Run(" + myRunApp.getRun() + ")", null, runPannel, null);
		GridBagLayout gbl_runPannel = new GridBagLayout();
		runPannel.setLayout(gbl_runPannel);
		
		rdBtnAll = new JRadioButton("All", true);
		rdBtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRunTable(new String[] {"","path", "health","maxHealth","healed","gold","goldChange","potionGain",
				"potionUse","picked","notPicked","relic","special"}, "path", columnAllNames,
				30,100,60,80,60,60,100,100,100,200,350,200,1000);
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
				updateRunTable(new String[] {"","path", "health","maxHealth","healed","gold","goldChange"}, "path",
						columnBasicNames);
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
				updateRunTable(new String[] {"","path","picked","notPicked","relic","special"}, "special",
						columnSpecialNames, 50, 250, 250, 300, 300, 750);
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
				updateRunTable(new String[] {"","path","picked","notPicked"}, "picked", columnCardNames);
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
				updateRunTable(new String[] {"","path","relic","notRelic"}, "relic",columnRelicNames);
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
				updateRunTable(new String[] {"","path","enemies","damage","turns","healed"}, "enemies", columnEncountersNames);
			}
		});
		GridBagConstraints gbc_rdBtnEncounters = new GridBagConstraints();
		gbc_rdBtnEncounters.anchor = GridBagConstraints.WEST;
		gbc_rdBtnEncounters.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnEncounters.gridx = 5;
		gbc_rdBtnEncounters.gridy = 0;
		runPannel.add(rdBtnEncounters, gbc_rdBtnEncounters);
		
		rdBtnRestFloor = new JRadioButton("Rest sites");
		rdBtnRestFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRunTable(new String[] {"","path","activity","data"}, "activity", columnRestNames);
			}
		});
		GridBagConstraints gbc_rdBtnRestFloor = new GridBagConstraints();
		gbc_rdBtnRestFloor.anchor = GridBagConstraints.EAST;
		gbc_rdBtnRestFloor.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnRestFloor.gridx = 6;
		gbc_rdBtnRestFloor.gridy = 0;
		runPannel.add(rdBtnRestFloor, gbc_rdBtnRestFloor);
		
		rdBtnEventFloor = new JRadioButton("Events");
		rdBtnEventFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRunTable(new String[] {"","path","name","choice","enemies","damage","turns","healed","relic","picked","cardRemoved","cardUpgraded"},
						"name", columnEventNames);
			}
		});
		GridBagConstraints gbc_rdBtnChestFloor = new GridBagConstraints();
		gbc_rdBtnChestFloor.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnChestFloor.gridx = 7;
		gbc_rdBtnChestFloor.gridy = 0;
		runPannel.add(rdBtnEventFloor, gbc_rdBtnChestFloor);
		
		rdBtnShops = new JRadioButton("Shops");
		rdBtnShops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRunTable(new String[] {"","path","purchased","purged"}, "shop",columnShopNames);
			}
		});
		GridBagConstraints gbc_rdBtnShops = new GridBagConstraints();
		gbc_rdBtnShops.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnShops.gridx = 8;
		gbc_rdBtnShops.gridy = 0;
		runPannel.add(rdBtnShops, gbc_rdBtnShops);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdBtnAll);
		btnGroup.add(rdBtnBasic);
		btnGroup.add(rdBtnCards);
		btnGroup.add(rdBtnEncounters);
		btnGroup.add(rdBtnEventFloor);
		btnGroup.add(rdBtnRelics);
		btnGroup.add(rdBtnShops);
		btnGroup.add(rdBtnRestFloor);
		btnGroup.add(rdBtnSpecial);
		
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
		
		runTable = new JTable();
		scrollPaneRun.setViewportView(runTable);
		
		rdBtnAll.doClick();
		rdbtnCardSummary.doClick();
		updateTextButtons();
	}
	
	public static void updateGraphs(int width, int heigth) {
		pnlGold.redraw(pnlGold.getSize().getWidth(), pnlGold.getSize().getHeight());
		pnlHealed.redraw(pnlHealed.getSize().getWidth(), pnlHealed.getSize().getHeight());
		pnlHealth.redraw(pnlHealth.getSize().getWidth(), pnlHealth.getSize().getHeight());
		pnlMaxHealth.redraw(pnlMaxHealth.getSize().getWidth(), pnlMaxHealth.getSize().getHeight());
	}
	
	private void updateRunTable(String[] keys, String filter, Object[] columnNames, int... columnWidths) {
		runTable.setModel(myRunApp.getTableModel(keys, filter, columnNames));
		for (int i = 0; i < columnWidths.length; i++) {
			runTable.getColumnModel().getColumn(i).setMaxWidth(columnWidths[i]);
		}
		//colour cells depending on the type of floor
		runTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        c.setBackground(myRunApp.floorColor((String) runTable.getModel().getValueAt(row, 0)));		        
		        return c;
		    }
		});
	}
	
	private void updateTextButtons() {
		String[] textV =  myRunApp.getTextValues();
		lblCharacterName.setText(textV[0]);
		lblMasterDeckName.setText(textV[1]);
		lblAscensionLvlName.setText(textV[2]);
		lblRelicsName.setText(textV[3]);
		lblVictoryName.setText(textV[4]);
		lblFloorReachedName.setText(textV[5]);
		lblSeedName.setText(textV[6]);
		lblSpecialSeedName.setText(textV[7]);
	}
	
	private void changeTabName(int index, String text) {
		tabbedPane.setTitleAt(index, text);
	}

	private void fillCharacterSummaryTable() {
		TableModel t = myGlobalApp.getSummaryTableData(Settings.CHARACTER, rdbtnRelicSummary.isSelected());
		characterSummaryTable.setModel(t);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(characterSummaryTable.getModel());
		characterSummaryTable.setRowSorter(sorter);
	}
	
	private void checkDirectory() {
		try {  
			//if te file does not exist it will error and trigger the program to ask for a path
			File f = new File(Settings.STS_DIRECTORY);
			if (!f.exists()) {
				String filePath = FileChooser.open("D://", "Please choose the directory where youre .run files are saved.", true);
				Settings.saveDirectory(filePath);
			}
		} catch(NullPointerException e){
			String filePath = FileChooser.open("D://", "Please choose the directory where youre .run files are saved.", true);
			Settings.saveDirectory(filePath);
		}
	}
}
