package net.wohlfart.mercury.entity;

public class HasRevision<T> {

    private Revision revision;

    private T entity;

    public HasRevision(Revision revision, T entity) {
        this.revision = revision;
        this.entity = entity;
    }

    public Revision getRevision() {
        return revision;
    }

    public T getEntity() {
        return entity;
    }

}
