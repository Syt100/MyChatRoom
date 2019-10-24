package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DOM4_Jwriter {

	public DOM4_Jwriter() {
		super();
		// TODO 自动生成的构造函数存根
		
	}
	public static Element useDom4JReadXml(String soucePath, Document doc){
        try {
            File file = new File(soucePath);
            SAXReader read = new SAXReader();
            doc = read.read(file);
            Element root = doc.getRootElement();
            return root;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] arg) throws DocumentException {
		String soucePath = "./rec/xml/Users.xml";
		//Element ele = DOM4_Jwriter.useDom4JReadXml(soucePath);
		
		File file = new File(soucePath);
        SAXReader read = new SAXReader();
        Document doc = read.read(file);
        Element root = doc.getRootElement();
        Element ele = root;
		
        Element foo ;
        Iterator<Element> iterator = ele.elementIterator("user");
        for (; iterator.hasNext();) {
            foo = (Element) iterator.next();
            
            String index = foo.attributeValue("index");
            String name = foo.elementText("name");
            String password = foo.elementText("password");
            System.err.println("序号"+ index);
            System.err.println("名字"+ name);
            System.err.println("密码"+ password);
            
            //foo.addElement("biaoqian").setText("123");
            //foo.element("biaoqian").detach();// 删除标签
            
        }
        //Element userElem = DocumentHelper.createElement("user");
//        Element newUser = root.addElement("user").addAttribute("index", "4");
//        newUser.addElement("name").setText("we");
//        newUser.addElement("password").setText("w1111e");
        ele.elementText("name");
        
        String pattern="/users/user[@index='4']/name";
        Node node=doc.selectSingleNode(pattern);
        //List<Node> list = doc.selectNodes(pattern);
        //Element node = root.element(pattern);
        String title = node.getText();
        System.out.println(title);
        
        try{
            //4
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            FileOutputStream fos = new FileOutputStream(soucePath);
            writer.setOutputStream(fos);
            
            //5
            writer.write(doc);
            System.out.println("写出完毕!");
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}
