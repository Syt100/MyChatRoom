/**
 * 
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import bean.Users;

/**
 * @author xuxin
 *
 */
public class XMLOperation {
	String soucePath = "./rec/xml/Users.xml";
	File file = new File(soucePath);
	SAXReader read = new SAXReader();
	Document doc;

	public static void main(String[] arg0) {
		new XMLOperation();
	}

	/**
	 * 
	 */
	public XMLOperation() {
		// TODO 自动生成的构造函数存根
		initDocument();
		Users u = new Users("jhn", "zzz");
		//addUser(u);
	}

	private Document initDocument() {
		try {
			doc = read.read(file);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return doc;
	}

	public static Element useDom4JReadXml(String soucePath) {
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

	public void addUser(Users users) {
		try {
			Document doc = read.read(file);
			Element root = doc.getRootElement();
			if (isElementNameExit(users.getName())) {
				System.out.println("我退出了");
				return;
			}

			Element newUser = root.addElement("user").addAttribute("index", "4");
			newUser.addElement("id").setText(users.getId());
			newUser.addElement("name").setText(users.getName());
			newUser.addElement("password").setText(users.getPassword());
			newUser.addElement("friends").setText(users.getFriends());
			writeXMLFile(doc);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 以姓名查询是否存在
	 * 
	 * @param name1
	 * @return
	 */
	public boolean isElementNameExit(String name1) {
		boolean exit = false;
		initDocument();
		Element root = doc.getRootElement();

		Element foo;
		Iterator<Element> iterator = root.elementIterator("user");
		for (; iterator.hasNext();) {
			foo = (Element) iterator.next();

			String name = foo.elementText("name");
			if (name.equals(name1)) {
				exit = true;
				break;
			}
		}
		return exit;
	}
	
	public boolean isElementIdExit(String name1) {
		boolean exit = false;
		initDocument();
		Element root = doc.getRootElement();

		Element foo;
		Iterator<Element> iterator = root.elementIterator("user");
		for (; iterator.hasNext();) {
			foo = (Element) iterator.next();

			String name = foo.elementText("id");
			if (name.equals(name1)) {
				exit = true;
				break;
			}
		}
		return exit;
	}
	
	public Users getUsersById(String id) {
		if(!isElementIdExit(id)) {
			return null;
		}
		initDocument();
		Element root = doc.getRootElement();

		Element foo;
		Iterator<Element> iterator = root.elementIterator("user");
		for (; iterator.hasNext();) {
			foo = (Element) iterator.next();

			if (foo.elementText("id").equals(id)) {
				Users user = new Users(foo.elementText("id"), foo.elementText("name"), foo.elementText("password"), foo.elementText("friends"));
				return user;
			}
		}
		return null;
	}

	private boolean writeXMLFile(Document doc) {
		boolean flag = false;
		try {
			// 4
			XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
			FileOutputStream fos = new FileOutputStream(soucePath);
			writer.setOutputStream(fos);

			// 5
			writer.write(doc);
			System.out.println("写出完毕!");
			flag = true;
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
