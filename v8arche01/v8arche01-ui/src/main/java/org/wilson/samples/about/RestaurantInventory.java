package org.wilson.samples.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.TextRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class RestaurantInventory extends VerticalLayout implements View {

        private final TextField productIdField = new TextField();
        private final TextField productIdDataField = new TextField();
        private final TextField itemNameField = new TextField();
        private final TextField itemNameDataField = new TextField();
        private final IntegerField quantityField = new IntegerField();
        private final TextField quantityDataField = new TextField();
        private final TextField priceField = new TextField();
        private final TextField priceDataField = new TextField();
        private final DateField dateField = new DateField();
        private final TextField dateDataField = new TextField();
        private final ComboBox supplierComboBox = new ComboBox();
        private final TextField supplierDataField = new TextField();
        private final CheckBox refrigerationCheckBox = new CheckBox();
        private final TextField refrigerationDataField = new TextField();
        private String category;

        public RestaurantInventory(String category) {
            supplierComboBox.setItems(Arrays.asList("Supplier A", "Supplier B", "Supplier C"));
            supplierComboBox.setValue("Supplier A");
            dateField.setDateFormat("yyyy-MM-dd");

            this.category = category;

            // Form Panel
            GridLayout formPanel = new GridLayout(3, 1);

            formPanel.addComponent(new Label("Product ID:"));
            formPanel.addComponent(productIdField);
            formPanel.addComponent(productIdDataField);


            formPanel.addComponent(new Label("Item Name:"));
            formPanel.addComponent(itemNameField);
            formPanel.addComponent(itemNameDataField);

            formPanel.addComponent(new Label("Quantity:"));

//            quantityField.addKeyListener(new KeyAdapter() {
//                public void keyTyped(KeyEvent e) {
//                    char c = e.getKeyChar();
//                    if (!Character.isDigit(c)) {
//                        e.consume();  // Ignore this key event
//                    }
//                }
//            });
            formPanel.addComponent(quantityField);
            formPanel.addComponent(quantityDataField);

            formPanel.addComponent(new Label("Price:"));
            formPanel.addComponent(priceField);
            formPanel.addComponent(priceDataField);

            formPanel.addComponent(new Label("Date Purchased:"));
            formPanel.addComponent(dateField);
            formPanel.addComponent(dateDataField);

            formPanel.addComponent(new Label("Supplier:"));
            formPanel.addComponent(supplierComboBox);
            formPanel.addComponent(supplierDataField);

            formPanel.addComponent(new Label("Requires Refrigeration:"));
            formPanel.addComponent(refrigerationCheckBox);
            formPanel.addComponent(refrigerationDataField);

            addComponent(formPanel);

            // Save Button
            Button saveButton = new Button("Save");
            saveButton.addClickListener(e -> saveInventoryItem(
                    productIdField.getValue() == null ? "" : productIdField.getValue(),
                    itemNameField.getValue() == null ? "" : itemNameField.getValue(),
                    quantityField.getValue() == null ? "" : quantityField.getValue(),
                    priceField.getValue() == null ? "": priceField.getValue(),
                    dateField.getValue() == null ? "" : dateField.getValue().toString(),
                    supplierComboBox.getValue() == null ? "" : (String) supplierComboBox.getValue(),
                    refrigerationCheckBox.getValue()
            ));
            addComponent(saveButton);
        }

        private void saveInventoryItem(String productId, String itemName, String quantity, String price, String datePurchased, String supplier, boolean requiresRefrigeration) {
            // Logic to save inventory item
            productIdDataField.setValue(productId);
            if (itemName.length() > 5) {
                itemNameDataField.setValue(itemName);
            } else {
                itemNameDataField.setValue("item name too short. requires at least 5 characters");
            }
            try {
                if (this.category.equals("Glassware") && Integer.parseInt(quantity) % 12 != 0) {
                    quantityDataField.setValue("Glass products must be entered in multiples of 12");
                } else if (Integer.parseInt(quantity) < 1) {
                    quantityDataField.setValue("0");
                } else {
                    quantityDataField.setValue(quantity);
                }
            } catch (Exception e) {
                quantityDataField.setValue("must be a number!");
            }
            priceDataField.setValue(price);
            try {
                SimpleDateFormat ezIsoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePurchasedDate = ezIsoDateFormat.parse(datePurchased);
                System.out.println(datePurchasedDate);
                // corporate policy dictates anything older than 7 years must be incinerated
                Calendar expirydate = new Calendar.Builder().setInstant(new Date()).build();
                if (datePurchasedDate.after(expirydate.getTime())) {
                    dateDataField.setValue("cannot buy in the future");
                    return;
                }
                expirydate.add(Calendar.YEAR, -7);
                if (datePurchasedDate.before(expirydate.getTime())) {
                    dateDataField.setValue("Product too old and must be trashed");
                } else {
                    dateDataField.setValue(new SimpleDateFormat("MMMMM dd, YYYY").format(datePurchasedDate));
                }
            } catch (ParseException pe) {
                dateDataField.setValue("invalid date. Format should be YYYY-MM-DD");
            }
            // supplier
            supplierDataField.setValue(supplier);
            // refrigeration
            refrigerationDataField.setValue(String.valueOf(requiresRefrigeration));
            System.out.println("Processed " + category + " Item: " + "Product ID: " + productId + ", Item Name: " + itemName + ", Quantity: " + quantity + ", Price: " + price + ", Date Purchased: " + datePurchased + ", Supplier: " + supplier + ", Requires Refrigeration: " + requiresRefrigeration);
        }
    }
