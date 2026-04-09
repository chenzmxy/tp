package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final Logic logic;
    private final Consumer<Person> ordersContextConsumer;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, Logic logic,
            Consumer<Person> ordersContextConsumer) {
        super(FXML);
        this.logic = logic;
        this.ordersContextConsumer = ordersContextConsumer != null ? ordersContextConsumer : p -> {
        };
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        personListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                executeFindOrdersForPerson(newVal);
            }
            ordersContextConsumer.accept(newVal);
        });
    }

    private void executeFindOrdersForPerson(Person person) {
        String commandText = "find-o c/" + person.getId().toString();
        try {
            logic.execute(commandText);
            logger.info("Executed find-o command: " + commandText);
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to execute find-o command: " + e.getMessage());
        }
    }

    /**
     * Clears the currently selected person in the list.
     */
    public void clearSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (getGraphic() instanceof Region oldRoot) {
                oldRoot.maxWidthProperty().unbind();
            }
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                PersonCard card = new PersonCard(person, getIndex() + 1);
                Region cardRoot = card.getRoot();
                cardRoot.maxWidthProperty().bind(personListView.widthProperty().subtract(32));
                setGraphic(cardRoot);
            }
        }
    }

}
