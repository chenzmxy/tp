package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private Label item;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label status;

    /**
     * Creates an {@code OrderCard} with the given {@code Order}.
     */
    public OrderCard(Order order) {
        super(FXML);
        this.order = order;

        applyStatusStyle(order.getStatus().value);

        item.setText("Order: " + order.getItem().value + " (x" + order.getQuantity().value + ")");

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);

        status.setText("Status: " + order.getStatus().value);
    }

    private void applyStatusStyle(String statusValue) {
        status.getStyleClass().removeIf(s -> s.startsWith("status-"));
        switch (statusValue) {
        case "PREPARING":
            status.getStyleClass().add("status-preparing");
            break;
        case "READY":
            status.getStyleClass().add("status-ready");
            break;
        case "DELIVERED":
            status.getStyleClass().add("status-delivered");
            break;
        case "CANCELLED":
            status.getStyleClass().add("status-cancelled");
            break;
        default:
            status.getStyleClass().add("status-unknown");
        }
    }
}
