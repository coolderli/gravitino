/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.gravitino.flink.connector.paimon;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.factories.CatalogFactory;
import org.apache.gravitino.catalog.lakehouse.paimon.PaimonCatalogPropertiesMetadata;
import org.apache.paimon.flink.FlinkGenericCatalogFactory;

import static org.apache.gravitino.flink.connector.paimon.GravitinoPaimonCatalogFactoryOptions.IDENTIFIER;

public class GravitinoPaimonCatalogFactory implements CatalogFactory {
  private FlinkGenericCatalogFactory paimonCatalogFactory;

  @Override
  public Catalog createCatalog(Context context) {
    this.paimonCatalogFactory = new FlinkGenericCatalogFactory();
    return new GravitinoPaimonCatalog(paimonCatalogFactory, context);
  }

  @Override
  public String factoryIdentifier() {
    return IDENTIFIER;
  }

  @Override
  public Set<ConfigOption<?>> requiredOptions() {
    return ImmutableSet.of(
            ConfigOptions.key(PaimonCatalogPropertiesMetadata.URI).stringType().noDefaultValue(),
            ConfigOptions.key(PaimonCatalogPropertiesMetadata.GRAVITINO_CATALOG_BACKEND).stringType().noDefaultValue(),
            ConfigOptions.key(PaimonCatalogPropertiesMetadata.WAREHOUSE).stringType().noDefaultValue());
  }

  @Override
  public Set<ConfigOption<?>> optionalOptions() {
    return ImmutableSet.of(
            ConfigOptions.key(PaimonCatalogPropertiesMetadata.GRAVITINO_JDBC_USER).stringType().noDefaultValue(),
            ConfigOptions.key(PaimonCatalogPropertiesMetadata.GRAVITINO_JDBC_PASSWORD).stringType().noDefaultValue()
    );
  }
}
