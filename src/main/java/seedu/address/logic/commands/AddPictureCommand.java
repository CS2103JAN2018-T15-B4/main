package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Adds a profile picture to a person in the address book
 */
public class AddPictureCommand extends Command {

    public static final String COMMAND_WORD = "addpicture";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Profile Picture for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "A valid file must be provided.";
    public static final String MESSAGE_USAGE = "dummy";

    private final Index index;
    private final String path;

    private Person personToEdit;

    public AddPictureCommand(Index index, String path) {

        requireNonNull(index);
        requireNonNull(path);

        this.index = index;
        this.path = path;

    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        int personIndex = index.getZeroBased();

        if (personIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(personIndex);


        try {
            personToEdit.setPicture(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CommandException(MESSAGE_NOT_EDITED);
        }


        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPictureCommand // instanceof handles nulls
                && this.index.equals(((AddPictureCommand) other).index)
                && this.path.equals(((AddPictureCommand) other).path)); // state check
    }
}