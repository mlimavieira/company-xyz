package com.dev.frontend.panels.edit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.CustomerDto;

public class EditCustomer extends EditContentPanel {
	private static final long serialVersionUID = -8971249970444644844L;
	private final JTextField txtCode = new JTextField();
	private final JTextField txtName = new JTextField();
	private final JTextField txtAddress = new JTextField();
	private final JTextField txtPhone1 = new JTextField();
	private final JTextField txtPhone2 = new JTextField();
	private final JTextField txtCreditLimit = new JTextField();
	private final JTextField txtCurrentCredit = new JTextField();

	public EditCustomer() {
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
		add(new JLabel("Name"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtName, gbc);
		txtName.setColumns(28);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Address"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtAddress, gbc);
		txtAddress.setColumns(28);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Phone 1"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtPhone1, gbc);
		txtPhone1.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(new JLabel("Phone 2"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 15);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtPhone2, gbc);
		txtPhone2.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("Credit Limit"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtCreditLimit, gbc);
		txtCreditLimit.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 4;
		add(new JLabel("Current Credit"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 15);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtCurrentCredit, gbc);
		txtCurrentCredit.setColumns(10);
		txtCurrentCredit.setEditable(false);

	}

	@Override
	public boolean bindToGUI(Object o) {

		if (o instanceof CustomerDto) {
			final CustomerDto dto = (CustomerDto) o;

			txtCode.setText(Utils.numberToString(dto.getId()));
			txtName.setText(dto.getName());
			txtAddress.setText(dto.getAddress());
			txtPhone1.setText(dto.getPhone1());
			txtPhone2.setText(dto.getPhone2());
			txtCreditLimit.setText(Utils.numberToString(dto.getCreditLimit()));
			txtCurrentCredit.setText(Utils.numberToString(dto.getCurrentCreditLimit()));

			return true;
		}

		return false;
	}

	@Override
	public Object guiToObject() {

		final CustomerDto cutomerDto = new CustomerDto();

		cutomerDto.setId(Utils.parseLong(txtCode.getText()));
		cutomerDto.setName(txtName.getText());
		cutomerDto.setCreditLimit(Utils.parseDouble(txtCreditLimit.getText()));
		cutomerDto.setCurrentCreditLimit(Utils.parseDouble(txtCurrentCredit.getText()));
		cutomerDto.setAddress(txtAddress.getText());
		cutomerDto.setPhone1(txtPhone1.getText());
		cutomerDto.setPhone2(txtPhone2.getText());

		return cutomerDto;
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_CUSTOMER;
	}

	@Override
	public String getCurrentCode() {
		return txtCode.getText();
	}

	@Override
	public void clear() {
		txtCode.setText("");
		txtName.setText("");
		txtPhone1.setText("");
		txtPhone2.setText("");
		txtAddress.setText("");
		txtCreditLimit.setText("");
		txtCurrentCredit.setText("");
	}

	@Override
	public void onInit() {

	}
}
