package com.dev.frontend.panels.list;

import java.util.ArrayList;
import java.util.List;

import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.ProductDto;

public class ProductDataModel extends ListDataModel {
	private static final long serialVersionUID = 7526529951747614655L;

	public ProductDataModel() {
		super(new String[] { "Code", "Description", "Price", "Quantity" }, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_PRODUCT;
	}

	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) {

		final List<String[]> resultList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			final ProductDto dto = (ProductDto) list.get(i);

			final String[] objectArray = new String[] { Utils.numberToString(dto.getId()), dto.getDescription(),
					Utils.numberToString(dto.getPrice()), Utils.numberToString(dto.getQuantity()) };

			resultList.add(objectArray);
		}

		final String[][] resultAsArray = new String[][] {};
		return resultList.toArray(resultAsArray);
		// return new String[][] { { "01", "Product 1", "12.5", "25" }, { "02",
		// "Product 2", "10", "8" } };

	}
}
