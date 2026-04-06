package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

public class MainWindowTest {

    private static class LogicStub implements Logic {
        private final ObservableList<Person> personList;
        private final ObservableList<Order> orderList;

        public LogicStub(ObservableList<Person> personList, ObservableList<Order> orderList) {
            this.personList = personList;
            this.orderList = orderList;
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
            return orderList;
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

    @Test
    public void logicStub_providesBothPersonAndOrderLists() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList, orderList);

        assertNotNull(logic.getFilteredPersonList());
        assertNotNull(logic.getFilteredOrderList());
        assertEquals(personList, logic.getFilteredPersonList());
        assertEquals(orderList, logic.getFilteredOrderList());
    }

    @Test
    public void logicStub_withOrdersAndPersons_providesCorrectLists() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList, orderList);

        assertEquals(0, logic.getFilteredPersonList().size());
        assertEquals(0, logic.getFilteredOrderList().size());
    }

    @Test
    public void logicStub_getGuiSettings_returnsDefaultSettings() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList, orderList);

        GuiSettings settings = logic.getGuiSettings();
        assertNotNull(settings);
    }

    @Test
    public void logicStub_setGuiSettings_doesNotThrow() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList, orderList);

        GuiSettings settings = new GuiSettings();
        logic.setGuiSettings(settings);
    }

    @Test
    public void logicStub_execute_returnsEmptyCommandResult() throws CommandException, ParseException {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        LogicStub logic = new LogicStub(personList, orderList);

        CommandResult result = logic.execute("test command");
        assertNotNull(result);
    }
}
