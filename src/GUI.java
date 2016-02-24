import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Freddy
 */
public class GUI extends javax.swing.JFrame 
{
	private static final long serialVersionUID = 6060077846877793065L;
	
	private Corpus corpus;
	private List<CustomUrl> potentialUrls;
	private CustomUrl primaryUrl;
	
	public GUI() 
    {
		this.corpus = new Corpus();
		this.potentialUrls = new ArrayList<>();
        initComponents();
    }
                       
    private void initComponents() 
    {
        titleLabel = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        calculateButton = new javax.swing.JButton();
        primaryTextBox = new javax.swing.JTextField();
        primaryLabel = new javax.swing.JLabel();
        potentialLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        potentialTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSC 365 - Project #1");
        setAlwaysOnTop(true);
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Webpage Comparator - TF-IDF & Cosine Similarity");

        authorLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        authorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        authorLabel.setText("Written by Fred Morrison");

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        primaryTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        primaryLabel.setText("Primary web page");

        potentialLabel.setText("Potential web page candidates");

        potentialTextArea.setColumns(20);
        potentialTextArea.setRows(5);
        jScrollPane1.setViewportView(potentialTextArea);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(calculateButton))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(primaryLabel))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(potentialLabel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(primaryTextBox))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(potentialLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(primaryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(primaryTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(calculateButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(authorLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(authorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }                    

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {                                                
    	try
    	{
    		parseInfo();
    		addWords();
    		calculateTfIdf();
    		System.out.println("Cosine Similarity: " + 
    				FrequencyTable.calculateAngle(primaryUrl.getFreqTable(), primaryUrl.getFreqTable()));
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private void calculateTfIdf()
    {
    	for(CustomUrl url : corpus)
    		url.getFreqTable().calculate();
    }
    
    private void addWords()
    {
    	for(CustomUrl url : corpus)
    	{
    		System.out.println("Adding words from url: " + url.getUrl());
    		String body = Utils.getWebPageBody(url.getUrl());
    		String[] bodyParts = body != null ? body.split(" ") : null;
    		
    		if(bodyParts == null)
    			continue;
    		for(String s : bodyParts)
    		{
    			if(s.length() == 0)
    				continue;
    			
    			url.getFreqTable().addWord(s);
    		}
    	}
    }
    
    private void parseInfo()
    {
    	primaryUrl = new CustomUrl(primaryTextBox.getText(), corpus);
    	corpus.setPrimaryUrl(primaryUrl);
		
		//Parse potential urls
		for (String line : potentialTextArea.getText().split("\\n"))
		{
			line = line.trim();
			if(!line.isEmpty())
			{
 				potentialUrls.add(new CustomUrl(line, corpus));
			}
		}
    }
    
    public static void main(String args[]) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new GUI().setVisible(true);
            }
        });
    }
                   
    private javax.swing.JLabel authorLabel;
    private javax.swing.JButton calculateButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel potentialLabel;
    private javax.swing.JTextArea potentialTextArea;
    private javax.swing.JLabel primaryLabel;
    private javax.swing.JTextField primaryTextBox;
    private javax.swing.JLabel titleLabel;                  
}
