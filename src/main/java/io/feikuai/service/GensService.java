package io.feikuai.service;

import java.io.IOException;

/**
 * 代码生成接口类
 * @author liudo
 * @date 2018/12/26
 */
public interface GensService {

	byte[] gensCodes() throws IOException;

	byte[] gensCode(String tableName) throws IOException;
}
