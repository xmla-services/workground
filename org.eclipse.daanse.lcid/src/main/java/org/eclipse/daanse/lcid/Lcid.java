package org.eclipse.daanse.lcid;

public record Lcid(int lcid) {

    public Lcid(short languageId, byte sortId) {

        this((sortId << 16) | languageId);
    }

    public Lcid(short languageId) {
        this(languageId, SortId.SORT_DEFAULT);
    }

    public Lcid(short languageId, SortId sortId) {


        this(languageId, SortId.getValidatedValue(languageId, sortId));
    }

}
