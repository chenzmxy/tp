package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_confirmationRequired() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookConfirmed_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ClearCommand clearCommand = new ClearCommand();
        ClearCommand clearCommandCopy = new ClearCommand();
        ClearCommand confirmedClearCommand = new ClearCommand(true);

        assertTrue(clearCommand.equals(clearCommand));

        assertTrue(clearCommand.equals(clearCommandCopy));

        assertFalse(clearCommand.equals(1));

        assertFalse(clearCommand.equals(null));

        assertFalse(clearCommand.equals(confirmedClearCommand));
    }

    @Test
    public void hashcode() {
        ClearCommand clearCommand = new ClearCommand();

        assertEquals(clearCommand.hashCode(), new ClearCommand().hashCode());

        assertNotEquals(clearCommand.hashCode(), new ClearCommand(true).hashCode());
    }
}
