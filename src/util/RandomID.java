/**
 * 
 */
package util;

import java.util.Random;

/**
 * 这个类生成不重复的ID
 * 参考：https://blog.csdn.net/qq_35619711/article/details/71437616
 * 
 * @author xuxin
 *
 */
public class RandomID {
	private static int index = 0;
	private static int[] id;
	@SuppressWarnings("unused")
	private static RandomID randomID = new RandomID();
	

	/**
	 * 这个构造方法比较耗时，尤其是交换数据
	 */
	private RandomID() {
		int begin = 0;
		int end = 999999;
		int count = begin + end;
		// 生成0到99999999的所有整数
		int[] codes = new int[count + 1];
		for (int i = begin; i <= end; i++) {
			codes[i] = i;
		}
		// 随机交换数据
		int index = 0;
		int tempCode = 0;
		Random random = new Random();
		for (int i = begin; i <= end; i++) {
			index = random.nextInt(count + 1);
			tempCode = codes[index];
			codes[index] = codes[i];
			codes[i] = tempCode;
		}
		id = codes;
	}
	
	/**
	 * 生成一个ID
	 * @return int
	 */
	public static int nextID() {
		return id[index++];
	}
	
	@SuppressWarnings("unused")
	private static void show() {
		for(int i = 0;i<id.length;i++) {
			System.out.println(id[i]);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(RandomID.nextID());
		System.out.println(RandomID.nextID());
	}

}
