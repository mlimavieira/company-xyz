package com.dev.frontend.panels.edit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.ProductDto;

public class EditProduct extends EditContentPanel {
	private static final long serialVersionUID = -8971249970444644844L;
	private final JTextField txtCode = new JTextField();
	private final JTextField txtDescription = new JTextField();
	private final JTextField txtQuantity = new JTextField();
	private final JTextField txtPrice = new JTextField();

	public EditProduct() {
		final GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Code"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(txtCode, gbc);
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		txtCode.setColumns(10);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Description"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtDescription, gbc);
		txtDescription.setColumns(28);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Price"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtPrice, gbc);
		txtPrice.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(new JLabel("Quantity"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 15);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtQuantity, gbc);
		txtQuantity.setColumns(10);
	}

	@Override
	public boolean bindToGUI(Object o) {

		if (o instanceof ProductDto) {
			final ProductDto productDto = (ProductDto) o;

			txtCode.setText(Utils.numberToString(productDto.getId()));
			txtDescription.setText(productDto.getDescription());
			txtPrice.setText(Utils.numberToString(productDto.getPrice()));
			txtQuantity.setText(Utils.numberToString(productDto.getQuantity()));

			return true;
		}

		return false;
	}

	@Override
	public Object guiToObject() {

		final ProductDto product = new ProductDto();
		final String code = txtCode.getText();
		if (StringUtils.isNotBlank(code)) {
			product.setId(Utils.parseLong(code));
		}
		product.setDescription(txtDescription.getText());
		product.setPrice(Utils.parseDouble(txtPrice.getText()));
		product.setQuantity(Utils.parseInteger(txtQuantity.getText()));

		return product;
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_PRODUCT;
	}

	@Override
	public String getCurrentCode() {
		return txtCode.getText();
	}

	@Override
	public void clear() {
		txtCode.setText("");
		txtDescription.setText("");
		txtPrice.setText("");
		txtQuantity.setText("");
	}

	@Override
	public void onInit() {

	}
}
