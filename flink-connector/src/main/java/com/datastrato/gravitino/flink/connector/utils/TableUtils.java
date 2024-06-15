/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.utils;

import com.datastrato.gravitino.rel.TableChange;

public class TableUtils {

  private TableUtils() {}

  public static TableChange.ColumnPosition toGravitinoColumnPosition(
      org.apache.flink.table.catalog.TableChange.ColumnPosition columnPosition) {
    if (columnPosition == null) {
      return null;
    }

    if (columnPosition instanceof org.apache.flink.table.catalog.TableChange.First) {
      return TableChange.ColumnPosition.first();
    } else if (columnPosition instanceof org.apache.flink.table.catalog.TableChange.After) {
      org.apache.flink.table.catalog.TableChange.After after =
          (org.apache.flink.table.catalog.TableChange.After) columnPosition;
      return TableChange.ColumnPosition.after(after.column());
    } else {
      throw new IllegalArgumentException(
          String.format("Not support column position : %s", columnPosition.getClass()));
    }
  }
}
