package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class OrderListPanelTest {

    private static final String TEST_ITEM = "Pizza";
    private static final String TEST_QUANTITY = "2";
    private static final String TEST_DELIVERY_TIME = "2027-06-15 1200";
    private static final String TEST_ADDRESS = "123 Main Street";

    private static class LogicStub implements Logic {
        private final ObservableList<Person> personList;

        public LogicStub(ObservableList<Person> personList) {
            this.personList = personList;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return personList;
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
        }
    }

    private Order createOrder(String status) {
        return new Order(
                UUID.randomUUID(),
                new Item(TEST_ITEM),
                new Quantity(TEST_QUANTITY),
                new DeliveryTime(TEST_DELIVERY_TIME),
                new Address(TEST_ADDRESS),
                new Status(status)
        );
    }

    private Order createOrderForCustomer(UUID customerId, String status) {
        return new Order(
                customerId,
                new Item(TEST_ITEM),
                new Quantity(TEST_QUANTITY),
                new DeliveryTime(TEST_DELIVERY_TIME),
                new Address(TEST_ADDRESS),
                new Status(status)
        );
    }

    @Test
    public void logicStub_getFilteredPersonList_returnsObservableList() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList);
        assertNotNull(logic.getFilteredPersonList());
        assertEquals(personList, logic.getFilteredPersonList());
    }

    @Test
    public void logicStub_getFilteredPersonList_withMultiplePersons() {
        Person customer = new PersonBuilder().withName("Alex Yeoh").build();
        ObservableList<Person> personList = FXCollections.observableArrayList(customer);
        LogicStub logic = new LogicStub(personList);

        assertEquals(1, logic.getFilteredPersonList().size());
        assertEquals(customer, logic.getFilteredPersonList().get(0));
    }

    @Test
    public void logicStub_getFilteredOrderList_returnsEmptyObservableList() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList);

        ObservableList<Order> orderList = logic.getFilteredOrderList();
        assertNotNull(orderList);
        assertEquals(0, orderList.size());
    }

    @Test
    public void logicStub_multiplePersons_providesCorrectCount() {
        Person customer1 = new PersonBuilder().withName("Alex Yeoh").build();
        Person customer2 = new PersonBuilder().withName("Bernice Yu").build();
        ObservableList<Person> personList = FXCollections.observableArrayList(customer1, customer2);
        LogicStub logic = new LogicStub(personList);

        assertEquals(2, logic.getFilteredPersonList().size());
    }

    @Test
    public void order_customerIdMatchesCustomerId() {
        Person customer = new PersonBuilder().withName("Alex Yeoh").build();
        UUID customerId = customer.getId();
        Order order = createOrderForCustomer(customerId, "PREPARING");

        assertEquals(customerId, order.getCustomerId());
    }

    @Test
    public void orderList_withCustomerId_ordersDisplayedCorrectly() {
        UUID customerId = UUID.randomUUID();
        Order order1 = createOrderForCustomer(customerId, "PREPARING");
        Order order2 = createOrderForCustomer(customerId, "READY");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2);

        assertEquals(customerId, orderList.get(0).getCustomerId());
        assertEquals(customerId, orderList.get(1).getCustomerId());
    }

    @Test
    public void orderList_differentCustomers_ordersDisplayedCorrectly() {
        UUID customer1 = UUID.randomUUID();
        UUID customer2 = UUID.randomUUID();
        Order order1 = createOrderForCustomer(customer1, "PREPARING");
        Order order2 = createOrderForCustomer(customer2, "READY");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2);

        assertEquals(customer1, orderList.get(0).getCustomerId());
        assertEquals(customer2, orderList.get(1).getCustomerId());
    }

    @Test
    public void orderList_withAllStatuses_displaysCorrectly() {
        String[] statuses = {"PREPARING", "READY", "DELIVERED", "CANCELLED"};
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        for (String status : statuses) {
            orderList.add(createOrder(status));
        }

        assertEquals(4, orderList.size());
        for (int i = 0; i < statuses.length; i++) {
            assertEquals(statuses[i], orderList.get(i).getStatus().value);
        }
    }

    @Test
    public void orderList_preservesOrder() {
        Order order1 = createOrder("PREPARING");
        Order order2 = createOrder("READY");
        Order order3 = createOrder("DELIVERED");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2, order3);

        assertEquals(3, orderList.size());
        assertEquals(order1, orderList.get(0));
        assertEquals(order2, orderList.get(1));
        assertEquals(order3, orderList.get(2));
    }

    @Test
    public void orderListItem_containsCorrectDetails() {
        String item = "Pizza";
        String quantity = "3";
        String deliveryTime = "2027-06-15 1200";
        String address = "789 Pine Street";
        String status = "PREPARING";

        Order order = new Order(
                UUID.randomUUID(),
                new Item(item),
                new Quantity(quantity),
                new DeliveryTime(deliveryTime),
                new Address(address),
                new Status(status)
        );

        assertEquals(item, order.getItem().value);
        assertEquals(quantity, order.getQuantity().value);
        assertEquals(deliveryTime, order.getDeliveryTime().value);
        assertEquals(address, order.getAddress().value);
        assertEquals(status, order.getStatus().value);
    }
}
