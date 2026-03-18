package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class OrderCardTest {

    private ObservableList<Person> personList;
    private Order order;

    @BeforeEach
    public void setUp() {
        Person person1 = new Person(new Name("Alice Pauline"),
                new Phone("94351253"), null, null, null);
        Person person2 = new Person(new Name("Benson Meier"),
                new Phone("98765432"), null, null, null);

        personList = FXCollections.observableArrayList(Arrays.asList(person1, person2));

        order = new Order(
                Index.fromZeroBased(0),
                new Item("Pizza"),
                new Quantity("2"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("123 Main Street"),
                new Status("PREPARING")
        );
    }

    @Test
    public void constructor_allFieldsProvided_success() {
        OrderCard orderCard = new OrderCard(order, 1, personList);

        assertNotNull(orderCard.getRoot());
        assertEquals("#1", getLabelText(orderCard, "id"));
        assertEquals("Pizza x2", getLabelText(orderCard, "item"));
        assertEquals("PREPARING", getLabelText(orderCard, "status"));
        assertEquals("2030-12-01 1800", getLabelText(orderCard, "date"));
    }

    @Test
    public void constructor_customerNameResolvedCorrectly() {
        OrderCard orderCard = new OrderCard(order, 1, personList);

        assertEquals("Alice Pauline", getLabelText(orderCard, "customerName"));
    }

    @Test
    public void constructor_differentCustomerIndex_resolvesCorrectly() {
        Order orderForSecondPerson = new Order(
                Index.fromZeroBased(1),
                new Item("Burger"),
                new Quantity("1"),
                new DeliveryTime("2030-12-02 1230"),
                new Address("456 Side Street"),
                new Status("READY")
        );

        OrderCard orderCard = new OrderCard(orderForSecondPerson, 2, personList);

        assertEquals("Benson Meier", getLabelText(orderCard, "customerName"));
    }

    @Test
    public void constructor_invalidCustomerIndex_returnsUnknownCustomer() {
        Order orderWithInvalidIndex = new Order(
                Index.fromZeroBased(99),
                new Item("Sushi"),
                new Quantity("3"),
                new DeliveryTime("2030-12-03 2000"),
                new Address("789 Other Street"),
                new Status("DELIVERED")
        );

        OrderCard orderCard = new OrderCard(orderWithInvalidIndex, 3, personList);

        assertEquals("Unknown Customer", getLabelText(orderCard, "customerName"));
    }

    @Test
    public void constructor_displayIndexIsCorrect() {
        OrderCard orderCard = new OrderCard(order, 5, personList);

        assertEquals("#5", getLabelText(orderCard, "id"));
    }

    private String getLabelText(OrderCard orderCard, String fieldName) {
        switch (fieldName) {
        case "id":
            return getLabelTextFromField(orderCard, "id");
        case "customerName":
            return getLabelTextFromField(orderCard, "customerName");
        case "item":
            return getLabelTextFromField(orderCard, "item");
        case "status":
            return getLabelTextFromField(orderCard, "status");
        case "date":
            return getLabelTextFromField(orderCard, "date");
        default:
            throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    private String getLabelTextFromField(OrderCard orderCard, String fieldName) {
        try {
            java.lang.reflect.Field field = OrderCard.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            javafx.scene.control.Label label = (javafx.scene.control.Label) field.get(orderCard);
            return label.getText();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
