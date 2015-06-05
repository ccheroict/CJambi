/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.table;

import pl.lss.cjambi.ccms.bean.Item;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.controller.exception.InvalidOrderItemException;
import pl.lss.cjambi.ccms.view.dialog.ItemEditDialog;
import pl.lss.cjambi.ccms.view.widget.Refreshable;
import pl.lss.cjambi.ccms.view.widget.Table;

/**
 *
 * @author ctran
 */
public class OrderItemTable extends Table<Item> {

    private static final long ITEM_TABLE_SIZE = 999;
    private Refreshable parentView;
    private Order order;

    public OrderItemTable(Order order) {
        super(ITEM_TABLE_SIZE);
        this.order = order;
    }

    @Override
    protected void showEditDialog(Item selected) {
        ItemEditDialog dialog = new ItemEditDialog(order);
        dialog.setBean(selected);
        dialog.exec();
        refresh();
    }

    public void addOrUpdateItem(Item item) throws InvalidOrderItemException {
        if (item.product == null || item.quantity == 0 || item.requiredPack == 0) {
            return;
        }
        for (Item obj : state) {
            if (obj.equals(item)) {
                obj.add(item);
                if (!isValidOrderItem(obj)) {
                    state.remove(obj);
                    throw new InvalidOrderItemException();
                }

                refresh();
                return;
            }
        }
        state.add(item);
        refresh();
    }

    /*
     * Check one item in state is valid
     */
    private boolean isValidOrderItem(Item item) {
        return (item.quantity > 0 && item.requiredPack > 0);
    }

    public void setParentView(Refreshable parentView) {
        this.parentView = parentView;
    }

    @Override
    public void refresh() {
        super.refresh(); //To change body of generated methods, choose Tools | Templates.
        if (parentView != null) {
            parentView.refresh();
        }
    }
}
