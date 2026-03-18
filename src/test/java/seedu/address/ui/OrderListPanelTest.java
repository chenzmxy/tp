package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seedu.address.commons.core.index.Index;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class OrderListPanelTest {

    private ObservableList<Person> personList;
    private ObservableList<Order> orderList;
    private OrderListPanel orderListPanel;

    @BeforeEach
    public void setUp() {
        Person person1 = new Person(new Name("Alice Pauline"),
                new Phone("94351253"), null, null, null);
        Person person2 = new Person(new Name("Benson Meier"),
                new Phone("98765432"), null, null, null);

        personList = FXCollections.observableArrayList(Arrays.asList(person1, person2));

        Order order1 = new Order(
                Index.fromZeroBased(0),
                new Item("Pizza"),
                new Quantity("2"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("123 Main Street"),
                new Status("PREPARING")
        );

        Order order2 = new Order(
                Index.fromZeroBased(1),
                new Item("Burger"),
                new Quantity("1"),
                new DeliveryTime("2030-12-02 1230"),
                new Address("456 Side Street"),
                new Status("READY")
        );

        orderList = FXCollections.observableArrayList(Arrays.asList(order1, order2));
    }

    @Test
    public void constructor_validInputs_success() {
        orderListPanel = new OrderListPanel(orderList, personList);

        assertNotNull(orderListPanel.getRoot());
    }

    @Test
    public void constructor_itemsPopulatedCorrectly() {
        orderListPanel = new OrderListPanel(orderList, personList);

        ListView<Order> listView = getListView(orderListPanel);
        assertEquals(2, listView.getItems().size());
    }

    @Test
    public void constructor_emptyList_success() {
        ObservableList<Order> emptyOrderList = FXCollections.observableArrayList();
        orderListPanel = new OrderListPanel(emptyOrderList, personList);

        ListView<Order> listView = getListView(orderListPanel);
        assertEquals(0, listView.getItems().size());
    }

    @Test
    public void orderListViewCell_displaysOrderCards() {
        orderListPanel = new OrderListPanel(orderList, personList);

        ListView<Order> listView = getListView(orderListPanel);
        assertEquals(2, listView.getItems().size());
    }

    @Test
    public void orderListViewCell_orderCardsShowCorrectData() {
        orderListPanel = new OrderListPanel(orderList, personList);

        ListView<Order> listView = getListView(orderListPanel);

        Order firstOrder = listView.getItems().get(0);
        assertEquals(new Item("Pizza"), firstOrder.getItem());
        assertEquals(new Quantity("2"), firstOrder.getQuantity());
    }

    private ListView<Order> getListView(OrderListPanel panel) {
        try {
            java.lang.reflect.Field field = OrderListPanel.class.getDeclaredField("orderListView");
            field.setAccessible(true);
            return (ListView<Order>) field.get(panel);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
