/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.rel.partitions;

import com.datastrato.gravitino.rel.Table;
import java.util.Map;

/**
 * A partition represents a result of partitioning a table. The partition can be either a {@link
 * IdentityPartition}, {@link ListPartition} or {@link RangePartition}. It depends on the {@link
 * Table#partitioning()}.
 */
public interface Partition {

  /**
   * Get the name of the partition.
   *
   * @return The name of the partition.
   */
  String name();

  /**
   * Get the properties of the partition, such as statistics, location, etc.
   *
   * @return The properties of the partition, such as statistics, location, etc.
   */
  Map<String, String> properties();
}
