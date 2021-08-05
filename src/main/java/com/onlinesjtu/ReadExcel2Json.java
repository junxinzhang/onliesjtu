package com.onlinesjtu;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReadExcel2Json {

	public static void main(String[] args) {
		String destFilePath = "destFilePath";

		EasyExcel.read(destFilePath, DataDto.class, new DataListener()).sheet().doRead();
	}
}

class DataDto {

	@ExcelProperty(index = 0)
	private String service_access_log_id;

	@ExcelProperty(index = 1)
	private String _id;

	@ExcelProperty(index = 2)
	private String customer_lead;

	@ExcelProperty(index = 3)
	private String _phone;

	@ExcelProperty(index = 4)
	private String did_number_province;

	@ExcelProperty(index = 5)
	private String did_city;

	@ExcelProperty(index = 6)
	private String did_operator;

	@ExcelProperty(index = 7)
	private String did_number_networktime;
}

class DataListener extends AnalysisEventListener<DataDto> {


	/**
	 * 每隔3000条存储数据库，然后清理list，方便内存回收
	 */
	private static final int BATCH_COUNT = 3000;

	List<DataDto> list = new ArrayList<>();


	public DataListener() {
	}

	/**
	 * 这个每一条数据解析都会来调用
	 *
	 * @param data
	 *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
	 * @param context
	 */
	@Override
	public void invoke(DataDto data, AnalysisContext context) {
//		log.info("解析到一条数据:{}", JSON.toJSONString(data));
		list.add(data);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (list.size() >= BATCH_COUNT) {
			saveData();
			// 存储完成清理 list
			list.clear();
		}
	}

	/**
	 * 所有数据解析完成了 都会来调用
	 *
	 * @param context
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// 这里也要保存数据，确保最后遗留的数据也存储到数据库
		saveData();
//		log.info("所有数据解析完成！");
	}

	private void saveData() {
	}

}