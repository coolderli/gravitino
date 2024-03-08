/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.file;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesetOperation {

    boolean deleteFile(String files, boolean recursive);

    InputStream openFile(String file);

    OutputStream writeFile(String file);

    boolean createFile(String file);

    boolean mkdir(String name);

    Iterable<FileInfo> listFiles(String path);
}
