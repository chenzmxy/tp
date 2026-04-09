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
    public final int displayedIndex;
    @FXML
    private Label id;
    @FXML
    private Label item;
    @FXML
    private Label quantity;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label status;
    @FXML
    private Label customer;

    /**
     * Creates an {@code OrderCard} with the given {@code Order}.
     */
    public OrderCard(Order order, int displayedIndex, String customerName) {
        super(FXML);
        this.order = order;
        this.displayedIndex = displayedIndex;

        applyStatusStyle(order.getStatus().value);

        id.setText(displayedIndex + ".");
        item.setText("Order: " + order.getItem().value);
        quantity.setText("Quantity: " + order.getQuantity().value);

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);
        configureWrappingLabel(item);
        configureWrappingLabel(quantity);
        configureWrappingLabel(address);

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

        item.setText("Order " + displayedIndex + ": " + order.getItem().value
                                            + " (x " + order.getQuantity().value + ")");

        customer.setText("Customer: " + customerName);

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);

        status.setText("Status: " + order.getStatus().value);
    private void configureWrappingLabel(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
    }
}
