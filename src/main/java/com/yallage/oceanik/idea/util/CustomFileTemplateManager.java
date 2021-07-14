package com.yallage.oceanik.idea.util;

import java.io.*;
import java.util.Map;
import java.util.Objects;

public class CustomFileTemplateManager {

    // 原味模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName){
        return getTemplateFromJar(fileName);
    }

    // 替换字符的模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName, Map<String, String> replace) {
        return getTemplateFromJar(fileName);
    }

    // 通过ClassLoader拿到Jar包资源流 获取的是文本 类文本资源
    // 逐行读取 会自动添加换行符
    private byte[] getTemplateFromJar(String path) {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("template/" + path);
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
}
