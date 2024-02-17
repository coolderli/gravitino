/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino;

/**
 * An auditable entity is an entity that has audit information associated with it. This audit
 * information is used to track changes to the entity.
 */
public interface Auditable {

  /**
   * Return The audit information of the entity.
   *
   * @return The audit information of the entity.
   */
  Audit auditInfo();
}
