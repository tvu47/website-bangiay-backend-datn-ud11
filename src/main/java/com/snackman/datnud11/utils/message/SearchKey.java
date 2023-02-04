package com.snackman.datnud11.utils.message;

public enum SearchKey {
    SEARCH_KEY_ID("ID"),
    SEARCH_KEY_NAME("NAME"),
    SEARCH_KEY_PHONE_NUMBER("PHONE_NUMBER"),
    SEARCH_KEY_PERSON_ID("PERSON_ID"),
    SEARCH_KEY_EMAIL("EMAIL"),
    ;

    private final String searchKey;

    SearchKey(final String searchKey){
        this.searchKey = searchKey;
    }

    @Override
    public String toString(){
        return searchKey;
    }
}
