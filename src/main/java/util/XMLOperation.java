package util;

import bean.Users;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * XML文件的相关操作
 * @author xuxin
 *
 */
public class XMLOperation {
	String soucePath = "./rec/xml/Users.xml";

	public static void main(String[] arg0) {
		XMLOperation xml = new XMLOperation();
		Users u = new Users("8899", "aiudnsv", "zzz", ",");
		xml.addUser(u);
	}

	/**
	 * 默认的无参构造方法
	 */
	public XMLOperation() {

	}

	/**
	 * 初始化Document
	 * 
	 * @return Document
	 */
	private Document initDocument() {
		Document doc = null;
		try {
			File file = new File(soucePath);
			SAXReader read = new SAXReader();
			doc = read.read(file);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return doc;
	}

	public void addUser(Users users) {
		Document doc = initDocument();
		Element root = doc.getRootElement();
		if (isElementIdExit(users.getId())) {
			System.out.println("该id已存在");
			return;
		}

		Element newUser = root.addElement("user").addAttribute("state", "normal");
		newUser.addElement("id").setText(users.getId());
		newUser.addElement("name").setText(users.getName());
		newUser.addElement("password").setText(users.getPassword());
		newUser.addElement("friends").setText(users.getFriends());
		writeXMLFile(doc);

	}

	/**
	 * 以姓名查询是否存在
	 * 
	 * @param name1
	 * @return
	 */
	public boolean isElementNameExit(String name1) {
		boolean exit = false;
		Document doc = initDocument();
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
		Document doc = initDocument();
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
		if (!isElementIdExit(id)) {
			return null;
		}
		Document doc = initDocument();
		Element root = doc.getRootElement();

		Element foo;
		Iterator<Element> iterator = root.elementIterator("user");
		for (; iterator.hasNext();) {
			foo = (Element) iterator.next();

			if (foo.elementText("id").equals(id)) {
				Users user = new Users(foo.elementText("id"), foo.elementText("name"), foo.elementText("password"),
						foo.elementText("friends"));
				return user;
			}
		}
		return null;
	}

	private boolean writeXMLFile(Document doc) {
		boolean flag = false;
		
		try {
			XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
			FileOutputStream fos = new FileOutputStream(soucePath);
			writer.setOutputStream(fos);

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
