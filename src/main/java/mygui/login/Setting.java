package mygui.login;

import com.alibaba.fastjson.JSONObject;
import mygui.components.GradualChangeTabbedPaneUI;
import mygui.components.MQFontChooser;
import mygui.components.RecTabbedPaneUI;
import mygui.login.setupstorage.LogonServerType;
import mygui.login.setupstorage.NetworkAgentType;
import mygui.login.setupstorage.SetUpStorage;
import mygui.login.setupstorage.TabbedPaneStyle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 设置面板。在登录界面中显示。事件处理都在本类进行
 *
 * @author xuxin
 */
public class Setting extends JPanel {

    /**
     * 已生成的串行版本标识
     */
    private static final long serialVersionUID = 3900196068302664939L;

    /**
     * 对登录面板的引用，以便调用其方法（返回）
     */
    private final Login4 mainLogin;

    /**
     * 默认的背景图片路径
     */
    private final String defaultBackgroundImagePath = "/Images/source/img_0.png";
    /**
     * 背景图片路径
     */
    private String backgroundImagePath;

    /**
     * 选项卡面板
     */
    private JTabbedPane tabbedPane;
    /**
     * 浏览按钮
     */
    private JButton btn_browse;
    /**
     * 确定按钮
     */
    public JButton btn_confirm;
    /**
     * 返回按钮
     */
    public JButton btn_return;
    /**
     * 显示预览图片的标签
     */
    private JLabel lbl_showPreview;
    /**
     * 用于显示背景图片路径的文本框
     */
    private JTextField textField_filePath;
    /**
     * 恢复默认按钮
     */
    private JButton btn_reset;
    /**
     * 使用本机地址按钮
     */
    private JButton btn_useLocal;

    /**
     * 全局字体设置
     */
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);

    /**
     * 网络代理设置中的代理类型
     */
    private JComboBox<String> comboBox_NetworkType;
    /**
     * 网络代理设置中的代理地址
     */
    private JTextField textField_NetworkAddress;
    /**
     * 网络代理设置中的代理端口
     */
    private JTextField textField_NetworkPort;
    /**
     * 网络代理设置中的代理用户名
     */
    private JTextField textField_NetworkUserName;
    /**
     * 网络代理设置中的代理密码
     */
    private JTextField textField_NetworkPassword;
    /**
     * 网络代理设置中的代理域
     */
    private JTextField textField_NetworkField;

    /**
     * 登录服务器设置中的登录服务器类型
     */
    private JComboBox<String> comboBox_loginType;
    /**
     * 登录服务器设置中的登录服务器地址
     */
    private JTextField textField_loginAddress;
    /**
     * 登录服务器设置中的登录服务器端口
     */
    private JTextField textField_loginPort;

    /**
     * 单选按钮
     */
    private JRadioButton rdbtn_sty1;
    /**
     * 单选按钮
     */
    private JRadioButton rdbtn_sty2;
    /**
     * 选择字体
     */
    private JButton btn_other_chooseFont;
    /**
     * 显示字体样式
     */
    private JLabel lbl_other_fontFamily;

    /**
     * @param mainLogin 登录界面
     */
    public Setting(Login4 mainLogin) {
        this.mainLogin = mainLogin;
        initGUI();
    }

    /**
     * 初始化面板组件
     */
    private void initGUI() {
        setSize(430, 281);
        setLayout(new BorderLayout(0, 0));

        // 点击事件监听器
        MyActionListener listener = new MyActionListener();
        // 项目改变监听器
        MyItemListener itemListener = new MyItemListener();

        // 选项卡面板
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(font);
        add(tabbedPane);

        // 背景设置面板
        JPanel panel_background = new JPanel();
        panel_background.setOpaque(false);
        tabbedPane.addTab("登录背景", null, panel_background, null);
        panel_background.setLayout(null);

        JLabel lbl_backgroundImage = new JLabel("背景图片");
        lbl_backgroundImage.setFont(font);
        lbl_backgroundImage.setBounds(10, 9, 48, 15);
        panel_background.add(lbl_backgroundImage);

        textField_filePath = new JTextField();
        textField_filePath.setBounds(68, 6, 285, 21);
        textField_filePath.setColumns(10);
        panel_background.add(textField_filePath);

        btn_browse = new JButton("浏览");
        btn_browse.setMargin(new Insets(2, 10, 2, 10));
        btn_browse.setFont(font);
        btn_browse.setBounds(363, 5, 57, 23);
        btn_browse.addActionListener(listener);// 设置事件监听
        panel_background.add(btn_browse);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane
                .setBorder(new TitledBorder(null, "\u9884\u89C8", TitledBorder.LEADING, TitledBorder.TOP, font, null));
        scrollPane.setBounds(10, 34, 343, 171);
        panel_background.add(scrollPane);

        lbl_showPreview = new JLabel("");
        scrollPane.setViewportView(lbl_showPreview);

        JButton btn_preview = new JButton("预览");
        btn_preview.setMargin(new Insets(2, 10, 2, 10));
        btn_preview.setFont(font);
        btn_preview.setBounds(363, 38, 57, 23);
        panel_background.add(btn_preview);

        // 网络设置面板
        // 网络代理部分
        JPanel panel_socket = new JPanel();
        tabbedPane.addTab("网络设置", null, panel_socket, null);
        panel_socket.setLayout(null);

        JLabel lbl_NetworkAgent = new JLabel("网络代理");
        lbl_NetworkAgent.setFont(font);
        lbl_NetworkAgent.setBounds(10, 3, 48, 20);
        panel_socket.add(lbl_NetworkAgent);

        JLabel lbl_NetworkType = new JLabel("类型:");
        lbl_NetworkType.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_NetworkType.setFont(font);
        lbl_NetworkType.setBounds(0, 33, 48, 23);
        panel_socket.add(lbl_NetworkType);

        comboBox_NetworkType = new JComboBox<>();
        comboBox_NetworkType.addItemListener(itemListener);// 添加项目改变监听
        comboBox_NetworkType
                .setModel(new DefaultComboBoxModel<String>(new String[]{"不使用代理", "HTTP代理", "SOCKS5代理", "使用浏览器设置"}));
        comboBox_NetworkType.setSelectedIndex(0);
        comboBox_NetworkType.setFont(font);
        comboBox_NetworkType.setBounds(50, 33, 133, 23);
        panel_socket.add(comboBox_NetworkType);

        JLabel lbl_NetworkAddress = new JLabel("地址:");
        lbl_NetworkAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_NetworkAddress.setFont(font);
        lbl_NetworkAddress.setBounds(186, 33, 36, 23);
        panel_socket.add(lbl_NetworkAddress);

        textField_NetworkAddress = new JTextField();
        textField_NetworkAddress.setBounds(224, 33, 91, 23);
        panel_socket.add(textField_NetworkAddress);
        textField_NetworkAddress.setColumns(10);

        JLabel lbl_NetworkPort = new JLabel("端口:");
        lbl_NetworkPort.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_NetworkPort.setFont(font);
        lbl_NetworkPort.setBounds(318, 33, 36, 23);
        panel_socket.add(lbl_NetworkPort);

        textField_NetworkPort = new JTextField();
        textField_NetworkPort.setBounds(355, 33, 60, 23);
        panel_socket.add(textField_NetworkPort);
        textField_NetworkPort.setColumns(4);

        JLabel lbl_NetworkUserName = new JLabel("用户名:");
        lbl_NetworkUserName.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_NetworkUserName.setFont(font);
        lbl_NetworkUserName.setBounds(0, 66, 48, 23);
        panel_socket.add(lbl_NetworkUserName);

        textField_NetworkUserName = new JTextField();
        textField_NetworkUserName.setBounds(50, 66, 133, 23);
        panel_socket.add(textField_NetworkUserName);

        JLabel label_NetworkPassword = new JLabel("密码:");
        label_NetworkPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        label_NetworkPassword.setFont(font);
        label_NetworkPassword.setBounds(186, 66, 36, 23);
        panel_socket.add(label_NetworkPassword);

        textField_NetworkPassword = new JTextField();
        textField_NetworkPassword.setColumns(10);
        textField_NetworkPassword.setBounds(224, 66, 91, 23);
        panel_socket.add(textField_NetworkPassword);

        JLabel lbl_NetworkField = new JLabel("域:");
        lbl_NetworkField.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_NetworkField.setFont(font);
        lbl_NetworkField.setBounds(318, 66, 36, 23);
        panel_socket.add(lbl_NetworkField);

        textField_NetworkField = new JTextField();
        textField_NetworkField.setColumns(4);
        textField_NetworkField.setBounds(355, 66, 60, 23);
        panel_socket.add(textField_NetworkField);

        setNetworkAgentEnable(false);// 默认禁用

        // 登录服务器部分
        JLabel lbl_logonServer = new JLabel("登录服务器");
        lbl_logonServer.setFont(font);
        lbl_logonServer.setBounds(10, 111, 72, 20);
        panel_socket.add(lbl_logonServer);

        JLabel lbl_loginType = new JLabel("类型:");
        lbl_loginType.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_loginType.setFont(font);
        lbl_loginType.setBounds(0, 140, 42, 23);
        panel_socket.add(lbl_loginType);

        comboBox_loginType = new JComboBox<>();
        comboBox_loginType.setModel(new DefaultComboBoxModel<>(new String[]{"不使用高级选项", "UDP类型", "TCP类型"}));
        comboBox_loginType.setSelectedIndex(0);
        comboBox_loginType.setFont(font);
        comboBox_loginType.setBounds(50, 140, 133, 23);
        comboBox_loginType.addItemListener(itemListener);// 添加项目改变监听
        panel_socket.add(comboBox_loginType);

        JLabel lbl_loginAddress = new JLabel("地址:");
        lbl_loginAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_loginAddress.setFont(font);
        lbl_loginAddress.setBounds(186, 140, 36, 23);
        panel_socket.add(lbl_loginAddress);

        textField_loginAddress = new JTextField();
        textField_loginAddress.setColumns(10);
        textField_loginAddress.setBounds(224, 140, 91, 23);
        panel_socket.add(textField_loginAddress);

        JLabel lbl_loginPort = new JLabel("端口:");
        lbl_loginPort.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_loginPort.setFont(font);
        lbl_loginPort.setBounds(318, 140, 36, 23);
        panel_socket.add(lbl_loginPort);

        textField_loginPort = new JTextField();
        textField_loginPort.setColumns(4);
        textField_loginPort.setBounds(355, 140, 60, 23);
        panel_socket.add(textField_loginPort);

        btn_useLocal = new JButton("使用本机地址");
        btn_useLocal.setMargin(new Insets(2, 6, 2, 6));
        btn_useLocal.setFont(font);
        btn_useLocal.setBounds(224, 173, 91, 25);
        panel_socket.add(btn_useLocal);
        btn_useLocal.addActionListener(listener);

        setLoginServerEnable(false);// 默认禁用登录服务器的几个输入框

        // 其他设置
        JPanel panel_other = new JPanel();
        tabbedPane.addTab("其他设置", null, panel_other, null);
        panel_other.setLayout(null);

        // 设置界面样式
        JLabel lbl_other_style = new JLabel("设置界面样式(重启后生效)");
        lbl_other_style.setFont(font);
        lbl_other_style.setBounds(10, 10, 184, 15);
        panel_other.add(lbl_other_style);

        rdbtn_sty1 = new JRadioButton("样式一");
        rdbtn_sty1.setSelected(true);
        rdbtn_sty1.setFont(font);
        rdbtn_sty1.setBounds(44, 31, 121, 23);
        rdbtn_sty1.addItemListener(itemListener);
        panel_other.add(rdbtn_sty1);

        rdbtn_sty2 = new JRadioButton("样式二");
        rdbtn_sty2.setFont(font);
        rdbtn_sty2.setBounds(179, 31, 121, 23);
        rdbtn_sty2.addItemListener(itemListener);
        panel_other.add(rdbtn_sty2);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rdbtn_sty1);
        btnGroup.add(rdbtn_sty2);

        // 字体设置
        JLabel lbl_other_font = new JLabel("界面字体");
        lbl_other_font.setFont(font);
        lbl_other_font.setBounds(10, 60, 155, 15);
        panel_other.add(lbl_other_font);

        btn_other_chooseFont = new JButton("选择");
        btn_other_chooseFont.setMargin(new Insets(2, 6, 2, 6));
        btn_other_chooseFont.setFont(font);
        btn_other_chooseFont.setBounds(187, 81, 49, 25);
        btn_other_chooseFont.addActionListener(listener);
        panel_other.add(btn_other_chooseFont);

        lbl_other_fontFamily = new JLabel("界面字体");
        lbl_other_fontFamily.setFont(font);
        lbl_other_fontFamily.setBounds(44, 85, 121, 15);
        lbl_other_fontFamily.setText(font.getFamily() + " " + font.getStyle() + " " + font.getSize());
        panel_other.add(lbl_other_fontFamily);

        // 底栏
        JPanel panel_bottom = new JPanel();
        FlowLayout fl_panel_bottom = (FlowLayout) panel_bottom.getLayout();
        fl_panel_bottom.setVgap(8);
        fl_panel_bottom.setHgap(8);
        fl_panel_bottom.setAlignment(FlowLayout.RIGHT);
        add(panel_bottom, BorderLayout.SOUTH);

        btn_reset = new JButton("恢复默认");
        panel_bottom.add(btn_reset);
        btn_reset.setMargin(new Insets(2, 5, 2, 5));
        btn_reset.setFont(font);
        btn_reset.addActionListener(listener);// 添加监听

        btn_confirm = new JButton("确定");
        panel_bottom.add(btn_confirm);
        btn_confirm.setMargin(new Insets(2, 10, 2, 10));
        btn_confirm.setFont(font);
        btn_confirm.addActionListener(listener);// 添加监听

        btn_return = new JButton("返回");
        panel_bottom.add(btn_return);
        btn_return.setFont(font);
        btn_return.setMargin(new Insets(2, 10, 2, 10));
        btn_return.addActionListener(listener);// 添加监听
    }

    /**
     * 下拉列表项目改变监听器
     *
     * @author xuxin
     */
    private class MyItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == comboBox_NetworkType) {
                int index = comboBox_NetworkType.getSelectedIndex();
                if (index == 0 || index == 3) {
                    setNetworkAgentEnable(false);
                } else {
                    setNetworkAgentEnable(true);
                }
            }

            if (e.getSource() == comboBox_loginType) {
                int index = comboBox_loginType.getSelectedIndex();
                if (index == 0) {// 不使用高级选项
                    setLoginServerEnable(false);
                } else if (index == 1) {// UDP类型
                    setLoginServerEnable(true);
                    textField_loginAddress.setText("127.0.0.1");
                    textField_loginPort.setText("8000");
                } else if (index == 2) {// TCP类型
                    setLoginServerEnable(true);
                    textField_loginAddress.setText("127.0.0.1");
                    textField_loginPort.setText("4444");
                }
            }

            // 单选按钮事件
            if (e.getSource() == rdbtn_sty1 && e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("按钮1被选择");
                // tabbedPane.setUI(new RecTabbedPaneUI());
                // tabbedPane.updateUI();
            }
            if (e.getSource() == rdbtn_sty2 && e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("按钮2被选择");
                // tabbedPane.setUI(new GradualChangeTabbedPaneUI("#FF3030", "#C0FF3E"));
//				tabbedPane.getUI().uninstallUI(tabbedPane);
//				tabbedPane.setUI(new BasicTabbedPaneUI());
                //TODO uninstallUI方法有问题
            }
        }

    }

    private class MyActionListener implements ActionListener {// 点击按钮监听

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn_browse) {// 点击浏览按钮
                JFileChooser fd;// java文件选择器
                if (backgroundImagePath != null && !defaultBackgroundImagePath.equals(backgroundImagePath)) {
                    File file = new File(backgroundImagePath);
                    fd = new JFileChooser(file);// 从上一次打开的文件创建文件选择器
                    fd.setSelectedFile(file);
                } else {
                    // 从当前路径构造文件选择器对象
                    fd = new JFileChooser("./");
                }

                // 设置文件过滤器，只列出JPG或GIF格式的图片
                FileFilter filter = new FileNameExtensionFilter("图像文件（jpg/gif/png）", "JPG", "JPEG", "GIF", "png");
                fd.setFileFilter(filter);
                // 显示窗口
                fd.showOpenDialog(null);
                File f = fd.getSelectedFile();
                if (f != null) {
                    System.out.println(f.getPath());
                    backgroundImagePath = f.getPath();
                    textField_filePath.setText(backgroundImagePath);
                    lbl_showPreview.setIcon(new ImageIcon(backgroundImagePath));
                }
            }
            if (e.getSource() == btn_reset) {// 点击恢复默认按钮
                backgroundImagePath = null;
                textField_filePath.setText(defaultBackgroundImagePath);
                lbl_showPreview.setIcon(new ImageIcon(getClass().getResource(defaultBackgroundImagePath)));
            }
            if (e.getSource() == btn_return) { // 点击返回按钮
                mainLogin.returnToMain();// 隐藏设置面板
            }
            if (e.getSource() == btn_confirm) {// 点击确认按钮
                saveSetting();// 保存设置
                if (backgroundImagePath != null) {// 如果选择了背景图片，则设置
                    mainLogin.setBackgroundImage(new ImageIcon(backgroundImagePath));
                } else {
                    mainLogin.setBackgroundImage(new ImageIcon(getClass().getResource(defaultBackgroundImagePath)));
                }
                mainLogin.returnToMain();// 隐藏设置面板
                mainLogin.showTipsByTimer("设置已保存");
            }
            if (e.getSource() == btn_useLocal) {// 点击使用本机地址按钮
                InetAddress myIP = null;
                try {
                    myIP = InetAddress.getLocalHost();
                    textField_loginAddress.setText(myIP.getHostAddress());
                } catch (UnknownHostException e1) {
                    JOptionPane.showMessageDialog(Setting.this, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == btn_other_chooseFont) {// 点击选择字体按钮
                MQFontChooser fonChooser = new MQFontChooser(font);
                fonChooser.showFontDialog(null);
                if (font != fonChooser.getSelectFont()) {// 如果选择字体改变了
                    font = fonChooser.getSelectFont();
                    setAllFont();
                }
            }
        }
    }

    /**
     * 设置全局字体
     */
    private void setAllFont() {
        lbl_other_fontFamily.setText(font.getFamily() + " " + font.getStyle() + " " + font.getSize());
        findAllComponents(this);
    }

    /**
     * 递归查找所有子组件，设置字体
     *
     * @param c 组件
     */
    private void findAllComponents(Component c) {
        if (c instanceof Container) {
            Component[] cs = ((Container) c).getComponents();
            for (Component component : cs) {
                component.setFont(font);
                findAllComponents(component);
            }
        } else {
            c.setFont(font);
        }
    }

    /**
     * 批量改变
     *
     * @param f 网络代理区域输入框启用状态
     */
    private void setNetworkAgentEnable(boolean f) {
        textField_NetworkAddress.setEnabled(f);
        textField_NetworkPort.setEnabled(f);
        textField_NetworkUserName.setEnabled(f);
        textField_NetworkPassword.setEnabled(f);
        textField_NetworkField.setEnabled(f);
    }

    /**
     * 批量改变登录服务器区域输入框启用状态
     *
     * @param f 登录服务器区域输入框启用状态
     */
    private void setLoginServerEnable(boolean f) {
        textField_loginAddress.setEnabled(f);
        textField_loginPort.setEnabled(f);
    }

    /**
     * 保存设置
     *
     * @throws NumberFormatException NumberFormatException
     */
    private void saveSetting() throws NumberFormatException {
        SetUpStorage set = SetUpStorage.getStorage();
        set.backgroundImagePath = backgroundImagePath;

        // 保存设置
        if (comboBox_loginType.getSelectedIndex() == 0) {
            set.logonServerType = LogonServerType.Default;
        } else if (comboBox_loginType.getSelectedIndex() == 1) {
            set.logonServerType = LogonServerType.UDP;
            set.loginAddress = textField_loginAddress.getText();
            set.loginPort = Integer.parseInt(textField_loginPort.getText());
        } else if (comboBox_loginType.getSelectedIndex() == 2) {
            set.logonServerType = LogonServerType.TCP;
            set.loginAddress = textField_loginAddress.getText();
            set.loginPort = Integer.parseInt(textField_loginPort.getText());
        }

        if (comboBox_NetworkType.getSelectedIndex() == 0) {// "不使用代理"
            set.networkAgentType = NetworkAgentType.None;
        } else if (comboBox_NetworkType.getSelectedIndex() == 1) {// "HTTP代理"
            set.networkAgentType = NetworkAgentType.HTTPAgent;
            set.networkAgentAddress = textField_NetworkAddress.getText();
            set.networkAgentPort = Integer.parseInt(textField_NetworkPort.getText());
            set.networkAgentUserName = textField_NetworkUserName.getText();
            set.networkAgentPassword = textField_NetworkPassword.getText();
            set.networkAgentField = textField_NetworkField.getText();
        } else if (comboBox_NetworkType.getSelectedIndex() == 2) {// "SOCKS5代理"
            set.networkAgentType = NetworkAgentType.SOCKS5Agent;
            set.networkAgentAddress = textField_NetworkAddress.getText();
            set.networkAgentPort = Integer.parseInt(textField_NetworkPort.getText());
            set.networkAgentUserName = textField_NetworkUserName.getText();
            set.networkAgentPassword = textField_NetworkPassword.getText();
            set.networkAgentField = textField_NetworkField.getText();
        } else if (comboBox_NetworkType.getSelectedIndex() == 3) {// "使用浏览器设置"
            set.networkAgentType = NetworkAgentType.UseBrowserAgent;
            set.networkAgentAddress = textField_NetworkAddress.getText();
            set.networkAgentPort = Integer.parseInt(textField_NetworkPort.getText());
            set.networkAgentUserName = textField_NetworkUserName.getText();
            set.networkAgentPassword = textField_NetworkPassword.getText();
            set.networkAgentField = textField_NetworkField.getText();
        }

        set.tabbedPaneStyle = rdbtn_sty1.isSelected() ? TabbedPaneStyle.style1 : TabbedPaneStyle.style2;
        set.font = font;

        set.writeToFile();// 保存设置到文件
        String s = JSONObject.toJSONString(set);
        System.out.println(getClass() + s);
    }

    /**
     * 从文件加载设置。由登录界面调用
     *
     * @throws IOException IOException
     * @throws Exception   Exception
     */
    public void readSetting() throws IOException, Exception {
        SetUpStorage.loadSetting();
        SetUpStorage set = SetUpStorage.getStorage();

        if (set.backgroundImagePath != null) {
            backgroundImagePath = set.backgroundImagePath;
            mainLogin.setBackgroundImage(new ImageIcon(backgroundImagePath));
            textField_filePath.setText(backgroundImagePath);
        }

        if (set.networkAgentType == NetworkAgentType.None) {
            comboBox_NetworkType.setSelectedIndex(0);
            setNetworkAgentEnable(false);
        } else if (set.networkAgentType == NetworkAgentType.HTTPAgent) {
            comboBox_NetworkType.setSelectedIndex(1);
            textField_NetworkAddress.setText(set.networkAgentAddress);
            textField_NetworkPort.setText(set.networkAgentPort + "");
            textField_NetworkUserName.setText(set.networkAgentUserName);
            textField_NetworkPassword.setText(set.networkAgentPassword);
            textField_NetworkField.setText(set.networkAgentField);
        } else if (set.networkAgentType == NetworkAgentType.SOCKS5Agent) {
            comboBox_NetworkType.setSelectedIndex(2);
            textField_NetworkAddress.setText(set.networkAgentAddress);
            textField_NetworkPort.setText(set.networkAgentPort + "");
            textField_NetworkUserName.setText(set.networkAgentUserName);
            textField_NetworkPassword.setText(set.networkAgentPassword);
            textField_NetworkField.setText(set.networkAgentField);
        } else if (set.networkAgentType == NetworkAgentType.UseBrowserAgent) {
            comboBox_NetworkType.setSelectedIndex(3);
            textField_NetworkAddress.setText(set.networkAgentAddress);
            textField_NetworkPort.setText(set.networkAgentPort + "");
            textField_NetworkUserName.setText(set.networkAgentUserName);
            textField_NetworkPassword.setText(set.networkAgentPassword);
            textField_NetworkField.setText(set.networkAgentField);
        }

        if (set.logonServerType == LogonServerType.Default) {
            comboBox_loginType.setSelectedIndex(0);
            setLoginServerEnable(false);
        } else if (set.logonServerType == LogonServerType.UDP) {
            comboBox_loginType.setSelectedIndex(1);
            textField_loginAddress.setText(set.loginAddress);
            textField_loginPort.setText(set.loginPort + "");
        } else if (set.logonServerType == LogonServerType.TCP) {
            comboBox_loginType.setSelectedIndex(2);
            textField_loginAddress.setText(set.loginAddress);
            textField_loginPort.setText(set.loginPort + "");
        }

        if (set.tabbedPaneStyle == TabbedPaneStyle.style1) {
            rdbtn_sty1.setSelected(true);
            tabbedPane.setUI(new GradualChangeTabbedPaneUI("#FFFFFF", "#000000"));
        } else {
            rdbtn_sty2.setSelected(true);
            tabbedPane.setUI(new RecTabbedPaneUI());
        }

        font = set.font;
        setAllFont();

        System.out.println(getClass() + "设置加载完毕");
    }
}
