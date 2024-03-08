/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino;

import com.datastrato.gravitino.catalog.file.BaseFileset;
import com.datastrato.gravitino.file.FileInfo;
import lombok.EqualsAndHashCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

@EqualsAndHashCode(callSuper = true)
public class TestFileset extends BaseFileset {

  @Override
  public boolean deleteFile(String files, boolean recursive) {
    return false;
  }

  @Override
  public InputStream openFile(String file) {
      try {
          return new FileInputStream(file);
      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      }
  }

  @Override
  public OutputStream writeFile(String file) {
    return null;
  }

  @Override
  public boolean createFile(String file) {
    return false;
  }

  @Override
  public boolean mkdir(String name) {
    return false;
  }

  @Override
  public Iterable<FileInfo> listFiles(String path) {
    return null;
  }

  public static class Builder extends BaseFilesetBuilder<Builder, TestFileset> {

    @Override
    protected TestFileset internalBuild() {
      TestFileset fileset = new TestFileset();
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
