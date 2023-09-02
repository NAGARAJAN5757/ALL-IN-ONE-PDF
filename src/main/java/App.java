import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class App implements ActionListener {
    JFrame frame;
    JButton[] selectButtons;
    File[] files;
    JTextField startPage;
    JTextField endPage;
    JTextField remPages;
    App() {
        frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension minSize = new Dimension(400, 400);
        frame.setSize(screenSize.width, screenSize.height - 50);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(minSize);
        frame.setTitle("ALL IN ONE PDFs");

        Image favIcon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\ELCOT\\IdeaProjects\\pdfmerge\\src\\main\\java\\images\\pdf-icon.png");
        frame.setIconImage(favIcon);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0x9877f2));

        JLabel titleLabel = new JLabel();
        ImageIcon icon = new ImageIcon("C:\\Users\\ELCOT\\IdeaProjects\\pdfmerge\\src\\main\\java\\images\\pdf-icon.png");
        titleLabel.setIcon(icon);
        titleLabel.setText("ALL IN ONE PDF");
        titleLabel.setFont(new Font("Alkatra", Font.BOLD, 42));
        titleLabel.setForeground(new Color(0x49f435));

        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 0, 0));

        //creating select file buttons
        JButton selBtn1 = new JButton("Select Files");
        JButton selBtn2 = new JButton("Select Files");
        JButton selBtn3 = new JButton("Select Files");
        JButton selBtn4 = new JButton("Select Files");

        selectButtons = new JButton[4];

        selectButtons[0] = selBtn1;
        selectButtons[1] = selBtn2;
        selectButtons[2] = selBtn3;
        selectButtons[3] = selBtn4;

        for (int i = 0; i < 4; i++) {
            selectButtons[i].addActionListener(this);
            selectButtons[i].setPreferredSize(new Dimension(230, 40));
            selectButtons[i].setFocusable(false);
            selectButtons[i].setFont(new Font("Alkatra", Font.PLAIN, 18));
            selectButtons[i].setBackground(Color.BLUE);
            selectButtons[i].setForeground(Color.WHITE);
        }

        JPanel[] mainSubPanels = new JPanel[4];
        for(int i=0;i<4;i++){
            mainSubPanels[i] = new JPanel();
        }

        JLabel[] titleLabels = new JLabel[4];

        titleLabels[0] = new JLabel("PDF MERGER");
        titleLabels[1] = new JLabel("PDF MERGER FOR PRINT");
        titleLabels[2] = new JLabel("PDF SPLITTER");
        titleLabels[3] = new JLabel("PDF PAGE REMOVE");

        JPanel[] titleLabelPanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            titleLabelPanels[i] = new JPanel();
        }

        JLabel subLabel1 = new JLabel("Select Files to Merge PDFs");
        JLabel subLabel2 = new JLabel("Select Files to Merge PDFs For Printing Front and Back");
        JLabel subLabel3 = new JLabel("Select File to Split the PDF");
        JLabel subLabel4 = new JLabel("Select File to Remove Pages");

        JLabel[] subLabels = new JLabel[4];

        subLabels[0] = subLabel1;
        subLabels[1] = subLabel2;
        subLabels[2] = subLabel3;
        subLabels[3] = subLabel4;

        JPanel[] buttonContainers = new JPanel[4];
        for(int i=0;i<4;i++) {
            buttonContainers[i] = new JPanel();
        }

        JPanel[] inputPanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            inputPanels[i] = new JPanel();
            inputPanels[i].add(selectButtons[i]);
            inputPanels[i].setBackground(Color.YELLOW);
        }

        buttonContainers[0].setLayout(new GridLayout(3,1,15,0));
        buttonContainers[1].setLayout(new GridLayout(3,1,15,0));
        buttonContainers[2].setLayout(new GridLayout(5,1,10,0));
        buttonContainers[3].setLayout(new GridLayout(4,1,12,0));

        Font subTitleFont = new Font("Alkatra", Font.BOLD, 24);
        Font subLabelFont = new Font("Alkatra", Font.PLAIN, 18);
        for (int i = 0; i < 4; i++) {
            //titlelabels operations
            titleLabels[i].setFont(subTitleFont);
            titleLabels[i].setForeground(Color.LIGHT_GRAY);
            titleLabelPanels[i].setBackground(Color.RED);
            titleLabelPanels[i].add(titleLabels[i]);
            //SubLabels Operations
            subLabels[i].setFont(subLabelFont);
            subLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            //ButtonContainers operations
            buttonContainers[i].add(subLabels[i]);
            buttonContainers[i].setBackground(Color.YELLOW);
            //MainSubPanel Operations
            mainSubPanels[i].setBackground(Color.ORANGE);
            mainSubPanels[i].setBorder(BorderFactory.createLineBorder(Color.GREEN, 7));
            mainSubPanels[i].setLayout(new BorderLayout());
            mainSubPanels[i].add(titleLabelPanels[i], BorderLayout.NORTH);
            mainSubPanels[i].setBackground(Color.MAGENTA);
            mainSubPanels[i].add(buttonContainers[i],BorderLayout.CENTER);
            //MainPanel operations
            mainPanel.add(mainSubPanels[i]);
        }

        Font ipLabelFont = new Font("Alkatra",Font.BOLD,16);
        Font ipFont = new Font("Alkatra",Font.PLAIN,12);

        JLabel startPageLabel = new JLabel("Enter start Page");
        startPageLabel.setFont(ipLabelFont);
        JLabel endPageLabel = new JLabel("Enter end Page");
        endPageLabel.setFont(ipLabelFont);
        startPage = new JTextField();
        startPage.setFont(ipFont);
        endPage = new JTextField();
        endPage.setFont(ipFont);
        JPanel spCont = new JPanel();
        JPanel epCont = new JPanel();
        spCont.add(startPageLabel);
        spCont.add(startPage);
        epCont.add(endPageLabel);
        epCont.add(endPage);
        spCont.setBackground(Color.YELLOW);
        epCont.setBackground(Color.YELLOW);
        startPage.setPreferredSize(new Dimension(130,30));
        endPage.setPreferredSize(new Dimension(130,30));

        buttonContainers[2].add(spCont);
        buttonContainers[2].add(epCont);

        JLabel rmPageLabel = new JLabel("Enter Page Numbers");
        rmPageLabel.setFont(ipLabelFont);
        remPages = new JTextField();
        JPanel rmpgContainer = new JPanel();
        rmpgContainer.add(rmPageLabel);
        rmpgContainer.add(remPages);
        rmpgContainer.setBackground(Color.YELLOW);
        remPages.setFont(ipFont);
        buttonContainers[3].add(rmpgContainer);
        remPages.setPreferredSize(new Dimension(200,35));

        selectButtons[2].setPreferredSize(new Dimension(150,35));
        selectButtons[3].setPreferredSize(new Dimension(200,35));

        for(int i=0;i<4;i++){
            buttonContainers[i].add(inputPanels[i]);
        }
        JLabel[] instructionLabels = new JLabel[4];
        for(int i=0;i<4;i++){
            instructionLabels[i] = new JLabel();
        }

        instructionLabels[0].setText("Select more than one PDF to merge.");
        instructionLabels[1].setText("If a pdf contains odd number of pages an empty page is inserted");
        instructionLabels[2].setText("Enter the start and end page numbers.");
        instructionLabels[3].setText("Enter the page numbers like x-y or a,b,c,d");



        for(int i=0;i<4;i++){
            instructionLabels[i].setFont(new Font("Alkatra",Font.PLAIN,16));
            instructionLabels[i].setForeground(Color.BLUE);
            instructionLabels[i].setHorizontalTextPosition(SwingConstants.CENTER);
            buttonContainers[i].add(instructionLabels[i]);
        }

        JPanel footerPanel = new JPanel();

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\ELCOT\\IdeaProjects\\pdfmerge\\src\\main\\java\\images\\heart.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(42, 42, Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);

        JLabel footerLabel = new JLabel();
        footerLabel.setIcon(imageIcon);
        footerLabel.setText("Made with        by NAGARAJAN R J");
        footerLabel.setIconTextGap(-200);
        footerLabel.setFont(new Font("Alkatra", Font.BOLD, 32));
        footerLabel.setSize(30, 30);
        footerPanel.add(footerLabel);
        footerPanel.setBackground(Color.CYAN);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public void handleFiles(JFileChooser fc){
        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("SelectedFilesChangedProperty")) {
                    if (files == null) {
                        files = (File[]) evt.getNewValue();
                    }
                    else {
                        File[] newSelection = (File[]) evt.getNewValue();

                        if (newSelection == null) {
                            files = null;
                        }
                        else {
                            List<File> orderedSel = new LinkedList<>();// add files that are still selected
                            for (File f : files) {
                                for (File f2 : newSelection) {
                                    if (f.equals(f2)) {
                                        orderedSel.add(f);
                                        break;
                                    }
                                }
                            }

                            Arrays.sort(files);
                            for (File f : newSelection) {
                                if (Arrays.binarySearch(files, f) < 0) {
                                    orderedSel.add(f);
                                }
                            }
                            int size = orderedSel.size();
                            files = orderedSel.toArray(
                                    new File[size]);
                        }
                    }
                }
            }
        });
    }

    public void handleSave(PDDocument doc,String opearion){
        JFileChooser fcSave = new JFileChooser();
        fcSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = fcSave.showSaveDialog(null);
        if(choice == JFileChooser.APPROVE_OPTION){
            String savePath = fcSave.getSelectedFile().getAbsolutePath();
            try {
                doc.save(savePath + "\\" + opearion + ".pdf");
                doc.close();
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Problem in selected pdf or directory, check both","Warning",JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void handleMergedFileSave(PDFMergerUtility PDFmerger){
        JFileChooser fcSave = new JFileChooser();
        fcSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = fcSave.showSaveDialog(null);
        if(choice == JFileChooser.APPROVE_OPTION){
            String savePath = fcSave.getSelectedFile().getAbsolutePath();
            PDFmerger.setDestinationFileName(savePath+"\\merged.pdf");
            try {
                PDFmerger.mergeDocuments(null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Problem in selected pdf or directory, check both","Warning",JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectButtons[0]) {
            JFileChooser fc1 = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
            fc1.setFileFilter(filter);
            fc1.setAcceptAllFileFilterUsed(false);
            fc1.setMultiSelectionEnabled(true);
            handleFiles(fc1);
            int choice = fc1.showOpenDialog(null);

            if (choice == JFileChooser.APPROVE_OPTION) {
//                System.out.println("FINAL selection: " + Arrays.toString(files)); //debug
                if(files.length>1){
                    PDFMergerUtility PDFmerger = new PDFMergerUtility();

                    for (File file : files) {
                        try {
                            PDFmerger.addSource(file);
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null,"File Not Found","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    handleMergedFileSave(PDFmerger);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please select More than one PDF","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else if(e.getSource() == selectButtons[1]){
            JFileChooser fc2 = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
            fc2.setFileFilter(filter);
            fc2.setAcceptAllFileFilterUsed(false);
            fc2.setMultiSelectionEnabled(true);
            handleFiles(fc2);

            int choice = fc2.showOpenDialog(null);

            // if the user did not cancel the selection
            if (choice == JFileChooser.APPROVE_OPTION) {
                System.out.println("FINAL selection: " + Arrays.toString(files)); //debug
                if(files.length>1){
                    PDFMergerUtility PDFmerger = new PDFMergerUtility();
                    for (File file : files) {
                        try {
                            PDDocument doc = PDDocument.load(file);
                            int noOfPages = doc.getNumberOfPages();
                            if (noOfPages % 2 == 1) {
                                doc.addPage(new PDPage());
                                doc.save(file);
                                doc.close();
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"Problem in selected pdf or directory, check both","Warning",JOptionPane.WARNING_MESSAGE);
                        }

                        try {
                            PDFmerger.addSource(file);
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null,"Fle Not Found","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    handleMergedFileSave(PDFmerger);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please select More than one PDF","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else if(e.getSource() == selectButtons[2]){
            JFileChooser fc3 = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
            fc3.setFileFilter(filter);
            fc3.setAcceptAllFileFilterUsed(false);

            int choice = fc3.showOpenDialog(null);

            if(choice == JFileChooser.APPROVE_OPTION){
                if(startPage.getText().isEmpty() || endPage.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Please Enter both Start and End Page numbers","Warning",JOptionPane.WARNING_MESSAGE);
                else{
                    try {
                        int startingPage = Integer.parseInt(startPage.getText());
                        int endingPage = Integer.parseInt(endPage.getText());
                        if(startingPage > endingPage){
                            JOptionPane.showMessageDialog(null,"Please Provide Correct numbers, Starting Page Number should be lesser than or equal to ending Page Number","Warning",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        else if(startingPage < 1){
                            JOptionPane.showMessageDialog(null,"Please Provide Correct Page numbers,from 1 to total number of pages of the selected pdf","Warning",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        File file = fc3.getSelectedFile();
                        try {
                            PDDocument doc = PDDocument.load(file);
                            int noOfPages= doc.getNumberOfPages();
                            if(startingPage < noOfPages && endingPage <noOfPages){
                                PDDocument newDoc = new PDDocument();
                                while(startingPage<=endingPage){
                                    newDoc.addPage(doc.getPage(startingPage-1));
                                    startingPage++;
                                }
                                handleSave(newDoc,"splittedPages");
                            }
                            doc.close();
                        }
                        catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"Error in selected file or directory,check both","Warning",JOptionPane.WARNING_MESSAGE);

                        }
                    }
                    catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null,"Please Provide valid Numbers,(Enter Integers)","Warning",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        else{
            JFileChooser fc4 = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
            fc4.setFileFilter(filter);
            fc4.setAcceptAllFileFilterUsed(false);

            int choice = fc4.showOpenDialog(null);

            if(choice == JFileChooser.APPROVE_OPTION){
                if(remPages.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter the Page numbers to remove", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    try {
                        String[] pgNums;
                        if(remPages.getText().contains("-") && remPages.getText().contains(",")) {
                            JOptionPane.showMessageDialog(null, "Please provide range of numbers using - or seperate numbers using ,", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        else if(remPages.getText().contains("-")) {
                            pgNums = remPages.getText().split("-");
                            if (pgNums.length != 2){
                                JOptionPane.showMessageDialog(null, "Please provide two numbers forming a range of numbers", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                            else {
                                int startPageNum = Integer.parseInt(pgNums[0]);
                                int endPageNum = Integer.parseInt(pgNums[1]);
                                if(startPageNum > endPageNum){
                                    JOptionPane.showMessageDialog(null, "Start Page number should be lesser than or equal to end page number", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                                else{
                                    File file = fc4.getSelectedFile();
                                    PDDocument doc = PDDocument.load(file);
                                    int noOfPages = doc.getNumberOfPages();
                                    if(endPageNum > noOfPages){
                                        doc.close();
                                        JOptionPane.showMessageDialog(null, "Entered page number exceeds the Pdf page number range", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                    else{
                                        int count = endPageNum - startPageNum + 1;
                                        while(count-- > 0){
                                            doc.removePage(startPageNum - 1);
                                        }
                                        handleSave(doc,"removedPages");
                                    }
                                }
                            }
                        }
                        else if(remPages.getText().contains(",")){
                            File file = fc4.getSelectedFile();
                            PDDocument doc = PDDocument.load(file);
                            int noOfPages = doc.getNumberOfPages();
                            pgNums = remPages.getText().split(",");
                            TreeSet<Integer> numSet = new TreeSet<>();
                            for(String pgnum:pgNums){
                                int currPgNo = Integer.parseInt(pgnum);
                                if(currPgNo > noOfPages){
                                    doc.close();
                                    JOptionPane.showMessageDialog(null, "Entered Page number exceeds the pages of selected PDF", "Warning", JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                                else{
                                    numSet.add(currPgNo);
                                }
                            }
                            Iterator<Integer> iterator = numSet.iterator();
                            int pos = -1;
                            while(iterator.hasNext()){
                                doc.removePage(iterator.next()+pos);
                                pos--;
                            }
                            handleSave(doc,"removedPages");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Enter valid Page numbers.(num1-num2) or (num1,num2,num3,etc)", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null,"Please Provide valid Numbers,(Integers)","Warning",JOptionPane.WARNING_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error in the file,check the file", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }
}
