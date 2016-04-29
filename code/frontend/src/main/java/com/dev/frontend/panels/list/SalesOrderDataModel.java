package com.dev.frontend.panels.list;

import java.util.ArrayList;
import java.util.List;

import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.SaleOrderDto;

public class SalesOrderDataModel extends ListDataModel {
	private static final long serialVersionUID = 7526529951747614655L;

	public SalesOrderDataModel() {
		super(new String[] { "Order Number", "Customer", "Total Price" }, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_SALESORDER;
	}

	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) {

		final List<String[]> resultList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {

			final SaleOrderDto dto = (SaleOrderDto) list.get(i);

			final String[] objectArray = new String[] { Utils.numberToString(dto.getId()),
					Utils.formartCustomerName(dto.getCustomerId(), dto.getCustomerName()),
					Utils.numberToString(dto.getTotalPrice()) };

			resultList.add(objectArray);
		}

		final String[][] resultAsArray = new String[][] {};
		return resultList.toArray(resultAsArray);

		// String[][] sampleData = new String [][]{{"22423","(01)Customer
		// 1","122.5"},{"22424","(02)Customer 2","3242.5"}};
		// return sampleData;
	}
}
