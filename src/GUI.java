import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Freddy
 */
public class GUI extends javax.swing.JFrame 
{
	private static final long serialVersionUID = 6060077846877793065L;
	
	private Application application;
	
	public GUI(Application application) 
    {
        initComponents();
        addClosingListener();
        this.application = application;
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
        closestLabel = new javax.swing.JLabel();

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

        closestLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        closestLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closestLabel.setText("Closest:");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(primaryLabel))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(potentialLabel))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(calculateButton)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(closestLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(27, 27, 27)
                .addComponent(closestLabel)
                .addGap(27, 27, 27)
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
	
	public void addClosingListener()
	{
		addWindowListener(new WindowAdapter() 
		{
			  public void windowClosing(WindowEvent e) 
			  {
				  application.end();
			  }
		});
	}
	
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {                                                
    	application.execute();
    }
    
    public JTextArea getPotentialTextArea()
    {
    	return potentialTextArea;
    }
    
    public JTextField getPrimaryTextBox()
    {
    	return primaryTextBox;
    }
    
    public JLabel getClosestLabel()
    {
    	return closestLabel;
    }
                   
    private javax.swing.JLabel authorLabel;
    private javax.swing.JButton calculateButton;
    private javax.swing.JLabel closestLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel potentialLabel;
    private javax.swing.JTextArea potentialTextArea;
    private javax.swing.JLabel primaryLabel;
    private javax.swing.JTextField primaryTextBox;
    private javax.swing.JLabel titleLabel;            
}
