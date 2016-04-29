package com.dev.frontend.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dev.frontend.panels.ComboBoxItem;
import com.mlimavieira.client.api.CustomerClient;
import com.mlimavieira.client.api.ProductClient;
import com.mlimavieira.client.api.SaleOrderClient;
import com.mlimavieira.dto.CustomerDto;
import com.mlimavieira.dto.ProductDto;
import com.mlimavieira.dto.SaleOrderDto;

public class Services {
	public static final int TYPE_PRODUCT = 1;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_SALESORDER = 3;

	public static Object save(Object object, int objectType) {

		switch (objectType) {
		case TYPE_PRODUCT:

			return ProductClient.save((ProductDto) object);

		case TYPE_CUSTOMER:

			return CustomerClient.save((CustomerDto) object);

		case TYPE_SALESORDER:

			return SaleOrderClient.save((SaleOrderDto) object);

		default:

			throw new IllegalArgumentException("Unknow ObjectType");
		}
	}

	public static Object readRecordByCode(String code, int objectType) {

		switch (objectType) {
		case TYPE_PRODUCT:

			return ProductClient.getById(Utils.parseLong(code));

		case TYPE_CUSTOMER:

			return CustomerClient.getById(Utils.parseLong(code));
		case TYPE_SALESORDER:

			return SaleOrderClient.getById(Utils.parseLong(code));
		default:

			throw new IllegalArgumentException("Unknow ObjectType");
		}

	}

	public static boolean deleteRecordByCode(String code, int objectType) {

		switch (objectType) {
		case TYPE_PRODUCT:
			return ProductClient.delete(Utils.parseLong(code));
		case TYPE_CUSTOMER:

			return CustomerClient.delete(Utils.parseLong(code));
		case TYPE_SALESORDER:

			return SaleOrderClient.delete(Utils.parseLong(code));
		default:

			throw new IllegalArgumentException("Unknow ObjectType");
		}
	}

	public static List<Object> listCurrentRecords(int objectType) {

		switch (objectType) {
		case TYPE_PRODUCT:

			return ProductClient.list();

		case TYPE_CUSTOMER:

			return CustomerClient.list();
		case TYPE_SALESORDER:

			return SaleOrderClient.list();
		default:

			throw new IllegalArgumentException("Unknow ObjectType");
		}

	}

	public static List<ComboBoxItem> listCurrentRecordRefernces(int objectType) {
		final List<ComboBoxItem> list = new ArrayList<>();
		switch (objectType) {
		case TYPE_PRODUCT:

			final List productList = ProductClient.list();
			for (final Iterator iterator = productList.iterator(); iterator.hasNext();) {
				final ProductDto dto = (ProductDto) iterator.next();

				final String code = Utils.numberToString(dto.getId());
				list.add(new ComboBoxItem(code, dto.getDescription()));
			}
			return list;
		case TYPE_CUSTOMER:

			final List listCustomers = CustomerClient.list();
			for (final Iterator iterator = listCustomers.iterator(); iterator.hasNext();) {
				final CustomerDto dto = (CustomerDto) iterator.next();

				final String code = Utils.numberToString(dto.getId());
				list.add(new ComboBoxItem(code, dto.getName()));
			}
			return list;

		case TYPE_SALESORDER:

			final List saleList = ProductClient.list();
			for (final Iterator iterator = saleList.iterator(); iterator.hasNext();) {
				final SaleOrderDto dto = (SaleOrderDto) iterator.next();

				final String code = Utils.numberToString(dto.getId());
				final String customerID = Utils.numberToString(dto.getId());
				list.add(new ComboBoxItem(code, customerID));
			}
			return list;
		default:

			throw new IllegalArgumentException("Unknow ObjectType");
		}

	}

	public static double getProductPrice(String productCode) {

		final ProductDto productDto = ProductClient.getById(Utils.parseLong(productCode));
		if (productDto != null) {
			return productDto.getPrice();
		}
		return 0;

	}
}
