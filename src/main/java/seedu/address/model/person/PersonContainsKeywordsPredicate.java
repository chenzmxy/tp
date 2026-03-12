package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain the given search phrase as a case-insensitive substring.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final String searchPhrase;

    public PersonContainsKeywordsPredicate(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    @Override
    public boolean test(Person person) {
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        return person.getName().fullName.toLowerCase().contains(lowerPhrase)
                || person.getPhone().value.toLowerCase().contains(lowerPhrase)
                || person.getEmail().value.toLowerCase().contains(lowerPhrase)
                || person.getAddress().value.toLowerCase().contains(lowerPhrase)
                || person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerPhrase));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return searchPhrase.equals(otherPredicate.searchPhrase);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchPhrase", searchPhrase).toString();
    }
}
