package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";

    @FXML
    private Label ordersContextLabel;

    @FXML
    private ListView<Order> orderListView;

    /**
     * Creates an {@code OrderListPanel} with the given {@code ObservableList} of orders.
     * Orders are displayed in ascending numerical order.
     */
    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);

        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Updates the sub-heading to match the customer whose orders are in view.
     */
    public void setOrdersContext(Person person) {
        if (person == null) {
            ordersContextLabel.setText("Orders");
        } else {
            ordersContextLabel.setText("Orders for " + person.getName().fullName);
        }
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

            if (getGraphic() instanceof Region oldRoot) {
                oldRoot.maxWidthProperty().unbind();
            }

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                OrderCard card = new OrderCard(order, getIndex() + 1);
                Region cardRoot = card.getRoot();
                cardRoot.maxWidthProperty().bind(orderListView.widthProperty().subtract(32));
                setGraphic(cardRoot);
            }
        }
    }

}
