package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

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
	public static Element useDom4JReadXml(String soucePath){
        try {
            File file = new File(soucePath);
            SAXReader read = new SAXReader();
            Document doc = read.read(file);
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
            foo.element("biaoqian").detach();// 删除标签
            
        }
        
        ele.elementText("name");
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
