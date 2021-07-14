package com.yallage.oceanik.idea.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class CustomFileTemplateManager {

    // 原味模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName) {
        return getTemplateFromJar("template/" + fileName);
    }

    // 替换字符的模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName, Map<String, String> replace) {
        System.out.println("[DEBUG] File:" + fileName);
        byte[] bytes = getTemplateFromJar("template/" + fileName);
        // 循环遍历map和读取的bytes
        for (Map.Entry<String, String> entry : replace.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
            bytes = replaceBytes(bytes
                    , entry.getKey().getBytes(StandardCharsets.UTF_8)
                    , entry.getValue().getBytes(StandardCharsets.UTF_8));
        }
        return bytes;
    }

    /**
     * 通过ClassLoader拿到Jar包资源流 获取的是文本 类文本资源
     *
     * @param path 在jar包中的路径 相对于jar包根目录
     * @return 保持数据原汁原味 返回字节数组
     */
    private byte[] getTemplateFromJar(String path) {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(path);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = Objects.requireNonNull(is).read(buffer)) > -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    /**
     * 先用着 效率也不是很低吧
     *
     * @return 字节数组
     */
    private byte[] replaceBytes(byte[] bytes, byte[] byteSource, byte[] byteTarget) {
        System.out.println("Data: " + Arrays.toString(bytes) + " Source: " + Arrays.toString(byteSource) + " Target: " + Arrays.toString(byteTarget));
        for (int i = 0; i < bytes.length; i++) {
            boolean bl = true;//从当前下标开始的字节是否与欲替换字节相等;
            for (int j = 0; j < byteSource.length; j++) {
                if (i + j < bytes.length && bytes[i + j] == byteSource[j]) {
                    bl = true;
                } else {
                    bl = false;
                }
            }
            if (bl) {
                System.arraycopy(byteTarget, 0, bytes, i, byteTarget.length);
            }
        }
        return bytes;
    }

}
