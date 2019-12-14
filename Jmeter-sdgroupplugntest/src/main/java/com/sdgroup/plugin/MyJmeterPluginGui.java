package com.sdgroup.plugin;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledChoice;

import javax.swing.*;
import java.awt.*;

/**
 * @author liwen
 * @Title: MyJmeterPluginGui
 * @Description: 自定义插件
 * @date 2019/12/13 / 12:57
 */
public class MyJmeterPluginGui extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;

    /**
     * 域
     */
    private JTextField domain;
    /**
     * 端口
     */
    private JTextField port;
    /**
     * 编码
     */
    private JTextField contentEncoding;
    /**
     * 路径
     */
    private JTextField path;
    /**
     * 链接方式
     */
    private JCheckBox useKeepAlive;
    /**
     * 请求方式
     */
    private JLabeledChoice method;

    // area区域
    private JSyntaxTextArea postBodyContent = JSyntaxTextArea.getInstance(30, 50);
    // 滚动条
    private JTextScrollPane textPanel = JTextScrollPane.getInstance(postBodyContent);
    private JLabel textArea = new JLabel("Message");

    private JPanel getDomainPanel() {
        domain = new JTextField(10);
        JLabel label = new JLabel("IP"); // $NON-NLS-1$
        label.setLabelFor(domain);

        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(domain, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPortPanel() {
        port = new JTextField(10);

        JLabel label = new JLabel(JMeterUtils.getResString("web_server_port")); // $NON-NLS-1$
        label.setLabelFor(port);

        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(port, BorderLayout.CENTER);

        return panel;
    }

    protected JPanel getContentEncoding() {

        // CONTENT_ENCODING
        contentEncoding = new JTextField(10);
        JLabel contentEncodingLabel = new JLabel("contentEncoding"); // $NON-NLS-1$
        contentEncodingLabel.setLabelFor(contentEncoding);

        JPanel panel = new HorizontalPanel();
        panel.setMinimumSize(panel.getPreferredSize());
        panel.add(Box.createHorizontalStrut(5));

        panel.add(contentEncodingLabel, BorderLayout.WEST);
        panel.add(contentEncoding, BorderLayout.CENTER);
        panel.setMinimumSize(panel.getPreferredSize());
        return panel;
    }

    protected Component getPath() {
        path = new JTextField(15);

        //$NON-NLS-1$
        JLabel label = new JLabel(JMeterUtils.getResString("path"));
        label.setLabelFor(path);

        JPanel pathPanel = new HorizontalPanel();
        pathPanel.add(label);
        pathPanel.add(path);

        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(pathPanel);

        return panel;
    }

    protected Component getMethodAndUseKeepAlive() {
        // $NON-NLS-1$
        useKeepAlive = new JCheckBox(JMeterUtils.getResString("use_keepalive"));
        useKeepAlive.setFont(null);
        useKeepAlive.setSelected(true);
        JPanel optionPanel = new HorizontalPanel();
        optionPanel.setMinimumSize(optionPanel.getPreferredSize());
        optionPanel.add(useKeepAlive);
        String Marry[] = {"GET", "POST"};
        // $NON-NLS-1$
        method = new JLabeledChoice(JMeterUtils.getResString("method"),
                Marry, true, false);
        // method.addChangeListener(this);
        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(optionPanel, BorderLayout.WEST);
        panel.add(method, BorderLayout.WEST);
        return panel;
    }

    protected Component getpostBodyContent() {

        JPanel panel = new HorizontalPanel();
        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
        messageContentPanel.add(this.textArea, BorderLayout.NORTH);
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Content"));
        panel.add(ContentPanel);
        return panel;
    }

    public MyJmeterPluginGui() {
        super();
        init();
    }

    private void init() { // WARNING: called from ctor so must not be overridden (i.e. must be private or
        // final)
        creatPanel();
    }

    public void creatPanel() {
        JPanel settingPanel = new VerticalPanel(5, 0);
        settingPanel.add(getDomainPanel());
        settingPanel.add(getPortPanel());
        settingPanel.add(getContentEncoding());
        settingPanel.add(getPath());
        settingPanel.add(getMethodAndUseKeepAlive());
        settingPanel.add(getpostBodyContent());
        JPanel dataPanel = new JPanel(new BorderLayout(5, 0));

        dataPanel.add(settingPanel, BorderLayout.NORTH);
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        // Add the standard title
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
    }


    @Override
    public String getLabelResource() {
        return "7D Group";
    }

    @Override
    public TestElement createTestElement() {
        MyPluginSampler sampler = new MyPluginSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public void modifyTestElement(TestElement testElement) {

        testElement.clear();
        configureTestElement(testElement);

        testElement.setProperty(MyPluginSampler.domain, domain.getText());
        testElement.setProperty(MyPluginSampler.port, port.getText());
        testElement.setProperty(MyPluginSampler.contentEncoding, contentEncoding.getText());
        testElement.setProperty(MyPluginSampler.path, path.getText());
        testElement.setProperty(MyPluginSampler.method, method.getText());
        testElement.setProperty(MyPluginSampler.postBodyContent, postBodyContent.getText());
        testElement.setProperty(new BooleanProperty(MyPluginSampler.useKeepAlive, useKeepAlive.isSelected()));
    }
}
