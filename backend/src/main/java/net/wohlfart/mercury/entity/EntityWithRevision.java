package net.wohlfart.mercury.entity;

public class EntityWithRevision <T> {

    private Revision revision;

    private T entity;

    public EntityWithRevision(Revision revision, T entity) {
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