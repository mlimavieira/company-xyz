package com.dev.frontend.panels.list;

import java.util.ArrayList;
import java.util.List;

import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.CustomerDto;

public class CustomerDataModel extends ListDataModel {
	private static final long serialVersionUID = 7526529951747613655L;

	public CustomerDataModel() {
		super(new String[] { "Code", "Name", "Phone", "Current Credit" }, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_CUSTOMER;
	}

	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) {

		final List<String[]> resultList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			final CustomerDto dto = (CustomerDto) list.get(i);

			final String[] objectArray = new String[] { Utils.numberToString(dto.getId()), dto.getName(),
					dto.getPhone1(), Utils.numberToString(dto.getCurrentCreditLimit()) };

			resultList.add(objectArray);
		}

		final String[][] resultAsArray = new String[][] {};
		return resultList.toArray(resultAsArray);

		// String[][] sampleData = new String [][]{{"01","Customer
		// 1","+201011121314","23.4"},{"02","Customer
		// 2","+201112131415","1.4"}};
		// return sampleData;
	}
}
