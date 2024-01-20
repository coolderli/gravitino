/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.catalog.file;

import lombok.ToString;

@ToString
public class HadoopFileset extends BaseFileset {
  public static class Builder extends BaseFilesetBuilder<Builder, HadoopFileset> {
    @Override
    protected HadoopFileset internalBuild() {
      HadoopFileset fileset = new HadoopFileset();
      fileset.name = name;
      fileset.comment = comment;
      fileset.properties = properties;
      fileset.auditInfo = auditInfo;
      fileset.type = type;
      fileset.storageLocation = storageLocation;
      return fileset;
    }
  }
}
