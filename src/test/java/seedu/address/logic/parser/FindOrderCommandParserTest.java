package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

public class FindOrderCommandParserTest {

    private final FindOrderCommandParser parser = new FindOrderCommandParser();

    @Test
    public void parse_all_returnsFindOrderCommandShowingAll() throws ParseException {
        FindOrderCommand command = parser.parse("ALL");
        Order testOrder = new Order(
                Index.fromZeroBased(0),
                new Item("Test"),
                new Quantity("1"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("Test Address"),
                new Status("PREPARING")
        );
        assertTrue(command.getPredicate().test(testOrder));
    }

    @Test
    public void parse_allLowercase_returnsFindOrderCommandShowingAll() throws ParseException {
        FindOrderCommand command = parser.parse("all");
        Order testOrder = new Order(
                Index.fromZeroBased(0),
                new Item("Test"),
                new Quantity("1"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("Test Address"),
                new Status("PREPARING")
        );
        assertTrue(command.getPredicate().test(testOrder));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x/value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_itemPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " i/pizza",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza")));
    }

    @Test
    public void parse_addressPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " a/Clementi",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Clementi")));
    }

    @Test
    public void parse_customerPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " c/1",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.CUSTOMER, "1")));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, " i/pizza a/Clementi",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }
}
