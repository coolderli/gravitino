/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.catalog.file;

import com.datastrato.gravitino.Audit;
import com.datastrato.gravitino.file.Fileset;
import com.datastrato.gravitino.meta.AuditInfo;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.ToString;

/** A abstract class representing a base fileset.* */
@ToString
public class BaseFileset implements Fileset {

  protected String name;

  @Nullable protected String comment;

  protected Type type;

  protected AuditInfo auditInfo;

  protected String storageLocation;

  protected Map<String, String> properties;

  /** Returns the name of the fileset. */
  @Override
  public String name() {
    return name;
  }

  /** Returns the comment or description for the fileset. */
  @Nullable
  @Override
  public String comment() {
    return comment;
  }

  /** Returns the audit details of the fileset. */
  @Override
  public Audit auditInfo() {
    return auditInfo;
  }

  /** Returns the type for the fileset. */
  @Override
  public Type type() {
    return type;
  }

  /** Returns the storage location for the fileset. */
  @Override
  public String storageLocation() {
    return storageLocation;
  }

  /** Returns the associated properties of the fileset. */
  @Override
  public Map<String, String> properties() {
    return properties;
  }

  /**
   * Builder interface for creating instances of {@link BaseFileset}.
   *
   * @param <SELF> The type of the builder.
   * @param <T> The type of the fileset being built.
   */
  interface Builder<SELF extends BaseFileset.Builder<SELF, T>, T extends BaseFileset> {

    SELF withName(String name);

    SELF withComment(String comment);

    SELF withProperties(Map<String, String> properties);

    SELF withAuditInfo(AuditInfo auditInfo);

    SELF withType(Type type);

    SELF withStorageLocation(String storageLocation);

    T build();
  }

  /**
   * An abstract class implementing the builder interface for {@link BaseFileset}.
   *
   * @param <SELF> The type of the builder.
   * @param <T> The type of the fileset being built.
   */
  public abstract static class BaseFilesetBuilder<
      SELF extends Builder<SELF, T>, T extends BaseFileset>
      implements Builder<SELF, T> {
    protected String name;
    protected String comment;
    protected Map<String, String> properties;
    protected AuditInfo auditInfo;
    protected Type type;
    protected String storageLocation;

    /**
     * Sets the name of the fileset.
     *
     * @param name The name of the fileset.
     * @return The builder instance.
     */
    @Override
    public SELF withName(String name) {
      this.name = name;
      return self();
    }

    /**
     * Sets the comment of the fileset.
     *
     * @param comment The comment or description for the fileset.
     * @return The builder instance.
     */
    @Override
    public SELF withComment(String comment) {
      this.comment = comment;
      return self();
    }

    /**
     * Sets the associated properties of the fileset.
     *
     * @param properties The associated properties of the fileset.
     * @return The builder instance.
     */
    @Override
    public SELF withProperties(Map<String, String> properties) {
      this.properties = properties;
      return self();
    }

    /**
     * Sets the audit details of the fileset.
     *
     * @param auditInfo The audit details of the fileset.
     * @return The builder instance.
     */
    @Override
    public SELF withAuditInfo(AuditInfo auditInfo) {
      this.auditInfo = auditInfo;
      return self();
    }

    /**
     * Sets the type of the fileset.
     * @param type The type of the fileset.
     * @return The builder instance.
     */
    public SELF withType(Type type) {
      this.type = type;
      return self();
    }

    /**
     * Sets the storage location of the fileset.
     * @param storageLocation The storage location of the fileset.
     * @return The builder instance.
     */
    public SELF withStorageLocation(String storageLocation) {
      this.storageLocation = storageLocation;
      return self();
    }

    /**
     * Builds the instance of the fileset with the provided attributes.
     *
     * @return The built fileset instance.
     */
    @Override
    public T build() {
      T t = internalBuild();
      return t;
    }

    private SELF self() {
      return (SELF) this;
    }

    protected abstract T internalBuild();
  }
}
