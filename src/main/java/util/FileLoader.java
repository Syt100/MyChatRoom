package util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件加载工具类，提供文件加载相关方法
 */
public class FileLoader {
    /**
     * 从类路径开始加载文件，若文件夹不存在则创建，文件不存在则创建
     *
     * @param filePath 类路径下文件夹、路径
     * @param fileName 文件名
     * @return File对象
     * @throws IOException 若文件夹、文件创建失败，报错
     */
    public static File loadFile(String filePath, String fileName) throws IOException {
        // 获取类路径
        String rootFilePath = Objects.requireNonNull(FileLoader.class.getClassLoader().getResource("")).getPath();
        File file = new File(rootFilePath + File.separator + filePath + File.separator + fileName);
        // 返回的是File类型,可以调用exist()等方法
        File fileParent = file.getParentFile();
        // 如果文件夹不存在，则创建文件夹
        if (!fileParent.exists()) {
            boolean mkdirs = fileParent.mkdirs();// 能创建多级目录
            if (!mkdirs) {
                throw new IOException("创建文件夹" + fileParent + "失败！");
            }
        }
        // 如果文件不存在，则创建文件
        if (!file.exists()) {
            boolean newFile = file.createNewFile();// 有路径才能创建文件
            if (!newFile) {
                throw new IOException("文件" + file.getName() + "创建失败！");
            }
        }
        return file;
    }
}
