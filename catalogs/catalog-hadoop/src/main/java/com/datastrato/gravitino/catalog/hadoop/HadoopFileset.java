/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.catalog.hadoop;

import com.datastrato.gravitino.catalog.file.BaseFileset;
import com.datastrato.gravitino.file.FileInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class HadoopFileset extends BaseFileset {

  private FileSystem fileSystem;

  @Override
  public boolean deleteFile(String file, boolean recursive) {
    try {
      return fileSystem.delete(new Path(file), recursive);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InputStream openFile(String file) {
    try {
      return fileSystem.open(new Path(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OutputStream writeFile(String file) {
    try {
      return fileSystem.create(new Path(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean createFile(String file) {
    try {
      return fileSystem.createNewFile(new Path(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean mkdir(String name) {
    try {
      return fileSystem.mkdirs(new Path(name));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Iterable<FileInfo> listFiles(String path) {
    try {
      RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path(path), true);
      return () -> new Iterator<FileInfo>() {
        @Override
        public boolean hasNext() {
          try {
            return files.hasNext();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public FileInfo next() {
          try {
            LocatedFileStatus fileStatus = files.next();
            return new FileInfo(
                fileStatus.getPath().toString(),
                fileStatus.getLen(),
                fileStatus.getModificationTime());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static class Builder extends BaseFilesetBuilder<Builder, HadoopFileset> {

    @Override
    protected HadoopFileset internalBuild() {
      HadoopFileset fileset = new HadoopFileset();
      fileset.name = name;
      fileset.comment = comment;
      fileset.storageLocation = storageLocation;
      fileset.type = type;
      fileset.properties = properties;
      fileset.auditInfo = auditInfo;
      return fileset;
    }
  }
}
