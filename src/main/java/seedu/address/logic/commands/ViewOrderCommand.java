package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.order.OrderList;

public class ViewOrderCommand extends Command {
    public static final String COMMAND_WORD = "vieworder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View orders by status.\n"
            + "Parameters: STATUS (pending/completed/all)\n"
            + "Example: " + COMMAND_WORD + " pending";

    public static final String MESSAGE_SUCCESS = "Orders retrieved successfully!";

    private String status;

    public ViewOrderCommand(String status) {
        this.status = status.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {
        OrderList orders;
        String resultMessage;

        switch (status) {
        case "pending":
            orders = model.getAddressBook().getPendingOrders();
            resultMessage = "=== PENDING/UPCOMING ORDERS ===\n";
            break;

        case "completed":
            orders = model.getAddressBook().getCompletedOrders();
            resultMessage = "=== COMPLETED ORDERS ===\n";
            break;

        case "all":
            orders = model.getAddressBook().getAllOrders();
            resultMessage = "=== ALL ORDERS ===\n";
            break;

        default:
            return new CommandResult("Invalid status. Use: upcoming, pending, completed, or all\n" + MESSAGE_USAGE);
        }

        if (orders.isEmpty()) {
            return new CommandResult(resultMessage + "No orders found.");
        }

        String orderList = orders.toString();

        return new CommandResult(resultMessage + orderList);
    }
}
