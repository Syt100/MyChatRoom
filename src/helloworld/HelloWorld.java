package helloworld;

import javax.swing.JFrame;

public class HelloWorld {
	public static void main (String args[] ){
		System.out.println("Hello World!");
		JFrame f = new JFrame();
		f.setSize(250,150);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
