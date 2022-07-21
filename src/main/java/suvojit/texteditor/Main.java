package suvojit.texteditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;

class TextEditor implements ActionListener{

    JFrame f;
    JMenuBar menuBar;
    JMenu file, edit, view, help, page;
    JTextArea textarea;
    JScrollPane scroll;
    JMenuItem save, open, close, cut,paste,copy, New, selectAll, theme1, theme2, documentHelp, fontSize,fontColor;
    JPanel saveFileOptionWindow, secondWindow;
    JLabel fileLabel, dirLevel;
    JTextField fileName, dirName;
    JButton jb;
    TextEditor(){
        f=new JFrame("Untitled Document");
        Image img=Toolkit.getDefaultToolkit().getImage("src\\main\\java\\suvojit\\texteditor\\colors-icon-4.png");
        f.setIconImage(img);

        menuBar=new JMenuBar();
        file=new JMenu("File");
        edit=new JMenu("Edit");
        view=new JMenu("View");
        help=new JMenu("Help");
        page=new JMenu("Page Setup");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(page);
        menuBar.add(help);
        f.setJMenuBar(menuBar);

        New=new JMenuItem("New");
        open=new JMenuItem("Open");
        save=new JMenuItem("Save");
        close=new JMenuItem("Close");
        file.add(New);
        file.add(open);
        file.add(save);
        file.add(close);

        cut=new JMenuItem("Cut");
        copy=new JMenuItem("Copy");
        paste=new JMenuItem("Paste");
        selectAll=new JMenuItem("Select All");

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        fontSize=new JMenuItem("Font Size");
        fontColor=new JMenuItem("Font Color");

        page.add(fontSize);
        page.add(fontColor);

        theme1=new JMenuItem("Dark");
        theme2=new JMenuItem("Light");
        view.add(theme1);
        view.add(theme2);

        documentHelp=new JMenuItem("Documentation");
        help.add(documentHelp);

        textarea=new JTextArea(47,120);
        f.add(textarea);

        scroll=new JScrollPane(textarea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scroll);

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        fontSize.addActionListener(this);
        fontColor.addActionListener(this);
        New.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        close.addActionListener(this);
        theme1.addActionListener(this);
        theme2.addActionListener(this);
        documentHelp.addActionListener(this);


        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        /*KeyListener k=new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keycode=e.getKeyCode();
                if (keycode==KeyEvent.VK_S && e.isControlDown()){

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        textarea.addKeyListener(k);
        */
        f.setSize(800,500);
        f.setResizable(true);
        f.setLocation(250,100);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==cut){
            textarea.cut();
        }
        if (e.getSource()==copy){
            textarea.copy();
        }
        if (e.getSource()==paste){
            textarea.paste();
        }
        if (e.getSource()==selectAll){
            textarea.selectAll();
        }

        if (e.getSource()==fontSize){
            String sizeOfFont=JOptionPane.showInputDialog("Enter Size: ");
            if (sizeOfFont!=null){
                int temp=Integer.parseInt(sizeOfFont);
                Font font=new Font(Font.SANS_SERIF,Font.PLAIN,temp);
                textarea.setFont(font);
            }
        }
        if (e.getSource()==open){
            JFileChooser chooseFile=new JFileChooser();
            int i=chooseFile.showOpenDialog(f);
            if(i==JFileChooser.APPROVE_OPTION) {
                File file = chooseFile.getSelectedFile();
                String filepath=file.getPath();
                String name=file.getName();
                f.setTitle(name);
                try {
                    BufferedReader rd=new BufferedReader(new FileReader(filepath));
                    String t1="";
                    StringBuilder t2= new StringBuilder();
                    while ((t1= rd.readLine())!=null){
                        t2.append(t1).append("\n");
                    }
                    textarea.setText(t2.toString());
                    rd.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource()==save){
            saveFileOptionWindow=new JPanel(new GridLayout(1,1));
            secondWindow=new JPanel(new GridLayout(1,1));
            fileLabel=new JLabel("Filename: ");
            dirLevel=new JLabel("Save the file to: ");
            fileName=new JTextField();
            //dirName=new JTextField();
            jb=new JButton("Choose Location");
            saveFileOptionWindow.add(fileLabel);
            saveFileOptionWindow.add(fileName);
            secondWindow.add(dirLevel);
            //saveFileOptionWindow.add(dirName);
            secondWindow.add(jb);
            jb.addActionListener(this);
            JOptionPane.showMessageDialog(f,saveFileOptionWindow);
            String fname=fileName.getText();
            f.setTitle(fname);
            String fileContent=textarea.getText();
            JOptionPane.showMessageDialog(f,secondWindow);

            JFileChooser cf=new JFileChooser();
            cf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int i=cf.showOpenDialog(f);
            String filepath="";
            if(i==JFileChooser.APPROVE_OPTION) {
                File file = cf.getSelectedFile();
                filepath = file.getPath();
                //System.out.println(filepath);
                // }
                //String filepath=dirName.getText();
                try {
                    BufferedWriter rw=new BufferedWriter(new FileWriter(filepath+"\\"+fname));
                    rw.write(fileContent);
                    rw.close();
                    JOptionPane.showMessageDialog(f,"File Saved Successfully.");

                }catch (IOException eo){
                    eo.printStackTrace();
                }
            }
        }
        if (e.getSource()==New){
            textarea.setText("");
        }
        if (e.getSource()==close){
            System.exit(1);
        }
        if (e.getSource()==theme1){
            textarea.setBackground(Color.BLACK);
            textarea.setForeground(Color.WHITE);
        }
        if (e.getSource()==theme2){
            textarea.setBackground(Color.WHITE);
            textarea.setForeground(Color.BLACK);
        }
        if (e.getSource()==documentHelp){
            try {
                String url="https://docs.oracle.com/javase/tutorial/uiswing/";
                Desktop.getDesktop().browse(URI.create(url));
            }catch (IOException ep){
                ep.printStackTrace();
            }
        }
        if (e.getSource()==fontColor){
            String[] options={"Red","Blue","Green","Black","Yellow","White"};
            Icon col=new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {

                }

                @Override
                public int getIconWidth() {
                    return 0;
                }

                @Override
                public int getIconHeight() {
                    return 0;
                }
            };
            int x=JOptionPane.showOptionDialog(null,"Select Color: ","Colors: ",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,col,options,options[0]);
            switch (x){
                case 0: textarea.setForeground(Color.RED);
                        break;
                case 1: textarea.setForeground(Color.BLUE);
                    break;
                case 2: textarea.setForeground(Color.GREEN);
                    break;
                case 3: textarea.setForeground(Color.BLACK);
                    break;
                case 4: textarea.setForeground(Color.YELLOW);
                    break;
                case 5: textarea.setForeground(Color.WHITE);
                    break;
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        new TextEditor();
    }
}
