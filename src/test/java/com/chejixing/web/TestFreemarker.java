package com.chejixing.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestFreemarker {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() throws IOException, TemplateException{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("/Users/hanxi/git/logic-chain/src/test/java/com/chejixing/web"));
		
		Map root = new HashMap();
		root.put("alarmMode", 1);
		
		Template temp = cfg.getTemplate("test.ftl");
		Writer out = new OutputStreamWriter(System.out);
		
		temp.process(root, out);

	}
}
