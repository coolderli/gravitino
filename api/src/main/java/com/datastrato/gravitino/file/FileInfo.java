/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.file;

public class FileInfo {
  private final String location;
  private final long size;
  private final long createdAtMillis;

  public FileInfo(String location, long size, long createdAtMillis) {
    this.location = location;
    this.size = size;
    this.createdAtMillis = createdAtMillis;
  }

  public String location() {
    return location;
  }

  public long size() {
    return size;
  }

  public long createdAtMillis() {
    return createdAtMillis;
  }
}
