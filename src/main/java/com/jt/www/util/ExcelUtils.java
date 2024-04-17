package com.jt.www.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: sunyuan
 * @Date: 2019/6/21 22:02
 * @Version 1.0
 */
@Slf4j
public class ExcelUtils {

	private static Sheet initSheet;

	static {
		initSheet = new Sheet(1, 0);
		initSheet.setSheetName("sheet");
		// 设置自适应宽度
		initSheet.setAutoWidth(Boolean.TRUE);
	}

	/**
	 * 生成excel
	 * 
	 * @param response http响应
	 * @param fileName 文件名
	 * @param data 数据源
	 * @param sheet excel页面样式
	 * @param head 表头
	 */
	public static void writeSimpleBySheet(HttpServletResponse response, String fileName, List<List<Object>> data, List<String> head, Sheet sheet) {
		sheet = (sheet != null) ? sheet : initSheet;
		if (head != null) {
			List<List<String>> list = new ArrayList<>();
			head.forEach(h -> list.add(Collections.singletonList(h)));
			sheet.setHead(list);
		}
		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			response.setContentType("application/msexcel");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
			outputStream = response.getOutputStream();
			writer = EasyExcelFactory.getWriter(outputStream);
			writer.write1(data, sheet);
		} catch (IOException e) {
			log.error("excel文件导出失败, 失败原因：{}", e);
		} finally {
			try {
				if (writer != null) {
					writer.finish();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				log.error("excel文件导出失败, 失败原因：{}", e);
			}
		}

	}

}
