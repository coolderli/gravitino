/*
 *  Copyright 2024 Datastrato Pvt Ltd.
 *  This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.flink.connector.integration.test;

import com.datastrato.gravitino.Catalog;
import com.datastrato.gravitino.client.GravitinoMetalake;
import com.datastrato.gravitino.flink.connector.PropertiesConverter;
import com.datastrato.gravitino.flink.connector.store.GravitinoCatalogStoreFactoryOptions;
import com.datastrato.gravitino.integration.test.container.ContainerSuite;
import com.datastrato.gravitino.integration.test.container.HiveContainer;
import com.datastrato.gravitino.integration.test.util.AbstractIT;
import com.datastrato.gravitino.rel.Column;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.internal.TableEnvironmentImpl;
import org.apache.hadoop.fs.FileSystem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FlinkEnvIT extends AbstractIT {
  private static final Logger LOG = LoggerFactory.getLogger(FlinkEnvIT.class);
  private static final ContainerSuite containerSuite = ContainerSuite.getInstance();
  protected static final String gravitinoMetalake = "flink";

  protected static GravitinoMetalake metalake;
  protected static TableEnvironment tableEnv;

  protected static String hiveMetastoreUri = "thrift://127.0.0.1:9083";

  protected static String warehouse;

  protected static FileSystem hdfs;

  private static String gravitinoUri = "http://127.0.0.1:8090";

  @BeforeAll
  static void startUp() {
    // Start Gravitino server
    initGravitinoEnv();
    initMetalake();
    initHiveEnv();
    initHdfsEnv();
    initFlinkEnv();
    LOG.info("Startup Flink env successfully, Gravitino uri: {}.", gravitinoUri);
  }

  @AfterAll
  static void stop() {
    stopFlinkEnv();
    stopHdfsEnv();
    LOG.info("Stop Flink env successfully.");
  }

  protected String flinkByPass(String key) {
    return PropertiesConverter.FLINK_PROPERTY_PREFIX + key;
  }

  private static void initGravitinoEnv() {
    // Gravitino server is already started by AbstractIT, just construct gravitinoUrl
    int gravitinoPort = getGravitinoServerPort();
    gravitinoUri = String.format("http://127.0.0.1:%d", gravitinoPort);
  }

  private static void initMetalake() {
    metalake = client.createMetalake(gravitinoMetalake, "", Collections.emptyMap());
  }

  private static void initHiveEnv() {
    containerSuite.startHiveContainer();
    hiveMetastoreUri =
        String.format(
            "thrift://%s:%d",
            containerSuite.getHiveContainer().getContainerIpAddress(),
            HiveContainer.HIVE_METASTORE_PORT);
    warehouse =
        String.format(
            "hdfs://%s:%d/user/hive/warehouse",
            containerSuite.getHiveContainer().getContainerIpAddress(),
            HiveContainer.HDFS_DEFAULTFS_PORT);
  }

  private static void initHdfsEnv() {
    org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
    conf.set(
        "fs.defaultFS",
        String.format(
            "hdfs://%s:%d",
            containerSuite.getHiveContainer().getContainerIpAddress(),
            HiveContainer.HDFS_DEFAULTFS_PORT));
    try {
      hdfs = FileSystem.get(conf);
    } catch (IOException e) {
      LOG.error("Create HDFS filesystem failed", e);
      throw new RuntimeException(e);
    }
  }

  private static void initFlinkEnv() {
    final Configuration configuration = new Configuration();
    configuration.setString(
        "table.catalog-store.kind", GravitinoCatalogStoreFactoryOptions.GRAVITINO);
    configuration.setString("table.catalog-store.gravitino.gravitino.metalake", gravitinoMetalake);
    configuration.setString("table.catalog-store.gravitino.gravitino.uri", gravitinoUri);
    tableEnv = TableEnvironment.create(configuration);
  }

  private static void stopHdfsEnv() {
    if (hdfs != null) {
      try {
        hdfs.close();
      } catch (IOException e) {
        LOG.error("Close HDFS filesystem failed", e);
      }
    }
  }

  private static void stopFlinkEnv() {
    if (tableEnv != null) {
      try {
        TableEnvironmentImpl env = (TableEnvironmentImpl) tableEnv;
        env.getCatalogManager().close();
      } catch (Throwable throwable) {
        LOG.error("Close Flink environment failed", throwable);
      }
    }
  }

  protected static void doWithSchema(Catalog catalog, String schemaName, Consumer<Catalog> action) {
    Preconditions.checkNotNull(catalog);
    Preconditions.checkNotNull(schemaName);
    tableEnv.useCatalog(catalog.name());
    if (!catalog.asSchemas().schemaExists(schemaName)) {
      catalog.asSchemas().createSchema(schemaName, null, ImmutableMap.of());
    }
    tableEnv.useDatabase(schemaName);
    action.accept(catalog);
  }

  protected static void doWithCatalog(Catalog catalog, Consumer<Catalog> action) {
    Preconditions.checkNotNull(catalog);
    tableEnv.useCatalog(catalog.name());
    action.accept(catalog);
  }

  @FormatMethod
  protected TableResult sql(@FormatString String sql, Object... args) {
    return tableEnv.executeSql(String.format(sql, args));
  }

  protected void assertColumns(Column[] expected, Column[] actual) {
    Assertions.assertEquals(expected.length, actual.length);
    for (int i = 0; i < expected.length; i++) {
      Assertions.assertEquals(expected[i].name(), actual[i].name());
      Assertions.assertEquals(expected[i].comment(), actual[i].comment());
      Assertions.assertEquals(
          expected[i].dataType().simpleString(), actual[i].dataType().simpleString());
      Assertions.assertEquals(expected[i].defaultValue(), actual[i].defaultValue());
      Assertions.assertEquals(expected[i].autoIncrement(), actual[i].autoIncrement());
      Assertions.assertEquals(expected[i].nullable(), actual[i].nullable());
    }
  }
}
