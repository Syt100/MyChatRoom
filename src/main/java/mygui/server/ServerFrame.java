/*
 * Created by JFormDesigner on Fri Oct 30 13:45:33 CST 2020
 */

package mygui.server;

import com.alee.laf.WebLookAndFeel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;


/**
 * @author 11
 */
public class ServerFrame extends JFrame {
    public static void main(String[] args) {
        WebLookAndFeel.install();
        ServerFrame frame = new ServerFrame();

    }

    public ServerFrame() {
        initComponents();
        setVisible(true);
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        // TODO add your code here
        System.out.println();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        mainPanel = new JPanel();
        statusPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        panel2 = new JPanel();
        panel1 = new JPanel();
        label5 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        panel3 = new JPanel();
        label6 = new JLabel();
        scrollPane2 = new JScrollPane();
        list1 = new JList();
        buttonPanel = new JPanel();
        closeBtn = new JButton();

        //======== this ========
        setTitle("\u670d\u52a1\u7aef");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("text");

                //---- menuItem1 ----
                menuItem1.setText("text");
                menu1.add(menuItem1);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== mainPanel ========
        {
            mainPanel.setLayout(new BorderLayout());

            //======== statusPanel ========
            {
                statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label1 ----
                label1.setText("\u670d\u52a1\u7aefIP");
                statusPanel.add(label1);

                //---- label2 ----
                label2.setText("127.0.0.1");
                statusPanel.add(label2);

                //---- label3 ----
                label3.setText("\u7aef\u53e3");
                statusPanel.add(label3);

                //---- label4 ----
                label4.setText("4444");
                statusPanel.add(label4);
            }
            mainPanel.add(statusPanel, BorderLayout.NORTH);

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //======== panel1 ========
                {
                    panel1.setBorder(new EmptyBorder(0, 5, 0, 5));
                    panel1.setLayout(new BorderLayout());

                    //---- label5 ----
                    label5.setText("text");
                    panel1.add(label5, BorderLayout.NORTH);

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setViewportView(textArea1);
                    }
                    panel1.add(scrollPane1, BorderLayout.CENTER);
                }
                panel2.add(panel1, BorderLayout.CENTER);

                //======== panel3 ========
                {
                    panel3.setBorder(new EmptyBorder(0, 5, 0, 5));
                    panel3.setLayout(new BorderLayout());

                    //---- label6 ----
                    label6.setText("text");
                    panel3.add(label6, BorderLayout.NORTH);

                    //======== scrollPane2 ========
                    {

                        //---- list1 ----
                        list1.addListSelectionListener(e -> list1ValueChanged(e));
                        scrollPane2.setViewportView(list1);
                    }
                    panel3.add(scrollPane2, BorderLayout.EAST);
                }
                panel2.add(panel3, BorderLayout.LINE_END);
            }
            mainPanel.add(panel2, BorderLayout.CENTER);

            //======== buttonPanel ========
            {
                buttonPanel.setLayout(new BorderLayout());

                //---- closeBtn ----
                closeBtn.setText("\u5173\u95ed");
                buttonPanel.add(closeBtn, BorderLayout.EAST);
            }
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        }
        contentPane.add(mainPanel, BorderLayout.CENTER);
        setSize(430, 330);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JPanel mainPanel;
    private JPanel statusPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JPanel panel2;
    private JPanel panel1;
    private JLabel label5;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JPanel panel3;
    private JLabel label6;
    private JScrollPane scrollPane2;
    private JList list1;
    private JPanel buttonPanel;
    private JButton closeBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
