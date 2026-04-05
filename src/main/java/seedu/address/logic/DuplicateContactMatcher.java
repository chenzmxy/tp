package seedu.address.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Finds customers that share exact contact details with a given customer.
 */
public final class DuplicateContactMatcher {

    private static final int MAX_MATCHES_SHOWN = 3;

    private DuplicateContactMatcher() {}

    /**
     * Returns a warning summary if the subject shares strong contact details with any existing customer.
     */
    public static Optional<DuplicateContactWarning> findWarning(Person subject, List<Person> existingPersons) {
        assert subject != null;
        assert existingPersons != null;

        Set<String> matchedFields = new LinkedHashSet<>();
        List<DuplicateMatch> matches = new ArrayList<>();

        for (Person existingPerson : existingPersons) {
            Set<String> overlappingFields = new LinkedHashSet<>(subject.getMatchingContactFields(existingPerson));
            if (!overlappingFields.isEmpty()) {
                matchedFields.addAll(overlappingFields);
                matches.add(new DuplicateMatch(existingPerson.getName().toString(), overlappingFields));
            }
        }

        if (matchedFields.isEmpty()) {
            return Optional.empty();
        }

        matches.sort(Comparator
                .comparingInt((DuplicateMatch match) -> match.fields.size()).reversed()
                .thenComparing(match -> match.customerName.toLowerCase()));

        return Optional.of(new DuplicateContactWarning(matchedFields, matches, MAX_MATCHES_SHOWN));
    }

    /**
     * Summary of matched contact details for user-facing warnings.
     */
    public static final class DuplicateContactWarning {
        private final Set<String> matchedFields;
        private final List<DuplicateMatch> rankedMatches;
        private final int maxMatchesShown;

        private DuplicateContactWarning(Set<String> matchedFields, List<DuplicateMatch> rankedMatches,
                                        int maxMatchesShown) {
            assert matchedFields != null;
            assert rankedMatches != null;
            this.matchedFields = new LinkedHashSet<>(matchedFields);
            this.rankedMatches = List.copyOf(rankedMatches);
            this.maxMatchesShown = maxMatchesShown;
        }

        public Set<String> getMatchedFields() {
            return Collections.unmodifiableSet(new LinkedHashSet<>(matchedFields));
        }

        public List<DuplicateMatch> getTopMatches() {
            int shownCount = Math.min(rankedMatches.size(), maxMatchesShown);
            return List.copyOf(rankedMatches.subList(0, shownCount));
        }

        public int getTotalMatchCount() {
            return rankedMatches.size();
        }

        public int getShownMatchCount() {
            return Math.min(rankedMatches.size(), maxMatchesShown);
        }
    }

    /**
     * Container for a single customer match.
     */
    public static final class DuplicateMatch {
        private final String customerName;
        private final List<String> fields;

        private DuplicateMatch(String customerName, Set<String> fields) {
            assert customerName != null;
            assert fields != null;

            this.customerName = customerName;
            this.fields = new ArrayList<>(fields);
        }

        public String getCustomerName() {
            return customerName;
        }

        public List<String> getFields() {
            return List.copyOf(fields);
        }
    }
}

