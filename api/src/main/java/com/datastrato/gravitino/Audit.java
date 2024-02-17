/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino;

import java.time.Instant;

/** Represents the audit information of an entity. */
public interface Audit {

  /**
   * The creator of the entity.
   *
   * @return the creator of the entity.
   */
  String creator();

  /**
   * The creation time of the entity.
   *
   * @return The creation time of the entity.
   */
  Instant createTime();

  /**
   * Return The last modifier of the entity.
   *
   * @return The last modifier of the entity.
   */
  String lastModifier();

  /**
   * Return The last modified time of the entity.
   *
   * @return The last modified time of the entity.
   */
  Instant lastModifiedTime();
}
