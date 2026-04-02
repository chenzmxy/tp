package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.order.Order;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Order> orderListView;
    private final Logic logic;

    /**
     * Creates an {@code OrderListPanel} with the given {@code ObservableList} of orders.
     * Orders are displayed in ascending numerical order.
     */
    public OrderListPanel(ObservableList<Order> orderList, Logic logic) {
        super(FXML);
        this.logic = logic;
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Order} using an {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {

        OrderListViewCell() {
        }

        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                String customerName = logic.getFilteredPersonList().stream()
                        .filter(p -> p.getId().equals(order.getCustomerId()))
                        .findFirst()
                        .map(p -> p.getName().fullName)
                        .orElse("Unknown");
                setGraphic(new OrderCard(order, getIndex() + 1, customerName).getRoot());
            }
        }
    }

}
