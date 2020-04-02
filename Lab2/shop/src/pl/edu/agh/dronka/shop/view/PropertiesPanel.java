package pl.edu.agh.dronka.shop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import pl.edu.agh.dronka.shop.controller.ShopController;
import pl.edu.agh.dronka.shop.model.ItemTypes.*;
import pl.edu.agh.dronka.shop.model.filter.ItemFilter;

public class PropertiesPanel extends JPanel {

    private static final long serialVersionUID = -2804446079853846996L;
    private ShopController shopController;

    private ItemFilter filter ;

    public PropertiesPanel(ShopController shopController) {
        this.shopController = shopController;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void fillProperties() {
        removeAll();
        this.filter = new ItemFilter( shopController.getCurrentCategory());

        filter.getItemSpec().setCategory(shopController.getCurrentCategory());
        add(createPropertyCheckbox("Tanie bo polskie", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                filter.getItemSpec().setPolish(
                        ((JCheckBox) event.getSource()).isSelected());
                shopController.filterItems(filter);
            }
        }));

        add(createPropertyCheckbox("Używany", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                filter.getItemSpec().setSecondhand(
                        ((JCheckBox) event.getSource()).isSelected());
                shopController.filterItems(filter);
            }
        }));

        switch (filter.getItemSpec().getCategory()) {
            case BOOKS:
                add(createPropertyCheckbox("Twarda oprawa", new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!(filter.getItemSpec() instanceof Books)) return;
                        ((Books) filter.getItemSpec()).setHard(
                                ((JCheckBox) event.getSource()).isSelected());
                        shopController.filterItems(filter);
                    }
                }));
                break;
            case ELECTRONICS:
                add(createPropertyCheckbox("Mobilny", new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!(filter.getItemSpec() instanceof Electronics)) return;
                        ((Electronics) filter.getItemSpec()).setMobile(
                                ((JCheckBox) event.getSource()).isSelected());
                        shopController.filterItems(filter);
                    }
                }));

                add(createPropertyCheckbox("Gwarancja", new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!(filter.getItemSpec() instanceof Electronics)) return;
                        ((Electronics) filter.getItemSpec()).setHasGuarantee(
                                ((JCheckBox) event.getSource()).isSelected());
                        shopController.filterItems(filter);
                    }
                }));
                break;
            case MUSIC:
                add(createPropertyCheckbox("Czy ma wideo", new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!(filter.getItemSpec() instanceof Music)) return;
                        ((Music) filter.getItemSpec()).setHasVideo(
                                ((JCheckBox) event.getSource()).isSelected());
                        shopController.filterItems(filter);
                    }
                }));
                break;
        }

    }

    private JCheckBox createPropertyCheckbox(String propertyName,
                                             ActionListener actionListener) {

        JCheckBox checkBox = new JCheckBox(propertyName);
        checkBox.setSelected(false);
        checkBox.addActionListener(actionListener);

        return checkBox;
    }

}
