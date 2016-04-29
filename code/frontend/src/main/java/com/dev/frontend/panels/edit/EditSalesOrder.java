package com.dev.frontend.panels.edit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.dev.frontend.panels.ComboBoxItem;
import com.dev.frontend.services.Services;
import com.dev.frontend.services.Utils;
import com.mlimavieira.dto.OrderLineDto;
import com.mlimavieira.dto.SaleOrderDto;

public class EditSalesOrder extends EditContentPanel {
	private static final long serialVersionUID = -8971249970444644844L;
	private final JTextField txtOrderNum = new JTextField();
	private final JTextField txtTotalPrice = new JTextField();

	private final JComboBox<ComboBoxItem> txtCustomer = new JComboBox<ComboBoxItem>();
	private final JTextField txtQuantity = new JTextField();
	private final JButton btnAddLine = new JButton("Add");

	private final JComboBox<ComboBoxItem> txtProduct = new JComboBox<ComboBoxItem>();
	private final DefaultTableModel defaultTableModel = new DefaultTableModel(
			new String[] { "Product", "Qty", "Price", "Total" }, 0) {

		private static final long serialVersionUID = 7058518092777538239L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private final JTable lines = new JTable(defaultTableModel);

	public EditSalesOrder() {
		final GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Order Number"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.weightx = 0.5;
		add(txtOrderNum, gbc);

		txtOrderNum.setColumns(10);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 3;
		gbc.gridy = 0;
		add(new JLabel("Customer"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		txtCustomer.setSelectedItem("Select a Customer");
		add(txtCustomer, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Total Price"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.weightx = 0.5;
		add(txtTotalPrice, gbc);
		txtTotalPrice.setColumns(10);
		txtTotalPrice.setEditable(false);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(new JLabel("Details"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 6;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		final JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(10, 1));
		gbc.fill = GridBagConstraints.BOTH;
		add(separator, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Product"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		txtProduct.setSelectedItem("Select a Product");
		add(txtProduct, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 3;
		gbc.gridy = 3;
		add(new JLabel("Quantity"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		add(txtQuantity, gbc);
		txtQuantity.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 5, 2, 5);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(btnAddLine, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 7;
		gbc.gridheight = 8;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipady = 40;
		gbc.fill = GridBagConstraints.BOTH;
		final JScrollPane scrollPane = new JScrollPane(lines, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lines.setFillsViewportHeight(true);
		add(scrollPane, gbc);
		btnAddLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRow();
			}
		});

	}

	public void addRow() {
		final ComboBoxItem comboBoxItem = (ComboBoxItem) txtProduct.getSelectedItem();
		if (comboBoxItem == null) {
			JOptionPane.showMessageDialog(this, "You must select a product");
			return;

		}
		final String productCode = comboBoxItem.getKey();
		final double price = Services.getProductPrice(productCode);
		Integer qty = 0;
		try {
			qty = Integer.parseInt(txtQuantity.getText());
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid number format in Qty field");
			return;
		}
		final double totalPrice = qty * price;
		defaultTableModel.addRow(new String[] { productCode, "" + qty, "" + price, "" + totalPrice });
		double currentValue = Utils.parseDouble(txtTotalPrice.getText());
		currentValue += totalPrice;
		txtTotalPrice.setText("" + currentValue);
	}

	@Override
	public boolean bindToGUI(Object o) {

		if (o instanceof SaleOrderDto) {
			final SaleOrderDto dto = (SaleOrderDto) o;

			txtOrderNum.setText(Utils.numberToString(dto.getId()));

			final ComboBoxItem selectedCombo = new ComboBoxItem(Utils.numberToString(dto.getCustomerId()),
					dto.getCustomerName());

			txtCustomer.setSelectedItem(selectedCombo);

			txtTotalPrice.setText(Utils.numberToString(dto.getTotalPrice()));

			for (final OrderLineDto line : dto.getOrderLines()) {
				final String totalPrice = Utils.numberToString(line.getQuantity() * line.getPrice());
				defaultTableModel.addRow(new String[] { Utils.numberToString(line.getId()),
						Utils.numberToString(line.getQuantity()), Utils.numberToString(line.getPrice()), totalPrice });
			}
			return true;
		}

		return false;
	}

	@Override
	public Object guiToObject() {

		final SaleOrderDto dto = new SaleOrderDto();

		final ComboBoxItem customerSelected = (ComboBoxItem) txtCustomer.getSelectedItem();
		dto.setCustomerId(Utils.parseLong(customerSelected.getKey()));

		final Vector dataVector = defaultTableModel.getDataVector();

		for (final Iterator iterator = dataVector.iterator(); iterator.hasNext();) {
			final Vector row = (Vector) iterator.next();

			final String selectedProductId = row.get(0).toString();
			final String quantity = row.get(1).toString();
			final String price = row.get(2).toString();

			final OrderLineDto orderLineDto = new OrderLineDto();
			orderLineDto.setProductId(Utils.parseLong(selectedProductId));
			orderLineDto.setQuantity(Utils.parseInteger(quantity));
			orderLineDto.setPrice(Utils.parseDouble(price));
			dto.getOrderLines().add(orderLineDto);
		}

		return dto;
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_SALESORDER;
	}

	@Override
	public String getCurrentCode() {
		return txtOrderNum.getText();
	}

	@Override
	public void clear() {
		txtOrderNum.setText("");
		txtCustomer.removeAllItems();
		txtProduct.removeAllItems();
		txtQuantity.setText("");
		txtTotalPrice.setText("");
		defaultTableModel.setRowCount(0);
	}

	@Override
	public void onInit() {
		final List<ComboBoxItem> customers = Services.listCurrentRecordRefernces(Services.TYPE_CUSTOMER);
		for (final ComboBoxItem item : customers) {
			txtCustomer.addItem(item);
		}

		final List<ComboBoxItem> products = Services.listCurrentRecordRefernces(Services.TYPE_PRODUCT);
		for (final ComboBoxItem item : products) {
			txtProduct.addItem(item);
		}
	}
}
