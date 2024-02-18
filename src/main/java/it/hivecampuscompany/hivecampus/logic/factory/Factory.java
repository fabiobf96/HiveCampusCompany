package it.hivecampuscompany.hivecampus.logic.factory;

public interface Factory {
    Object getDAO(String typePersistence) throws IllegalArgumentException;
}
