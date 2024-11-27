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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.CommonCatalogOptions;
import org.apache.gravitino.flink.connector.PropertiesConverter;

import java.util.Map;

import static org.apache.gravitino.catalog.lakehouse.paimon.PaimonCatalogPropertiesMetadata.*;

public class PaimonPropertiesConverter implements PropertiesConverter {
  private PaimonPropertiesConverter() {}

  public static final Map<String, String> PAIMON_CONFIG_TO_GRAVITINO =
          ImmutableMap.of(
                  PAIMON_METASTORE,
                  GRAVITINO_CATALOG_BACKEND,
                  WAREHOUSE,
                  WAREHOUSE,
                  URI,
                  URI,
                  PAIMON_JDBC_USER,
                  GRAVITINO_JDBC_USER,
                  PAIMON_JDBC_PASSWORD,
                  GRAVITINO_JDBC_PASSWORD);

  public static final PaimonPropertiesConverter INSTANCE = new PaimonPropertiesConverter();

  @Override
  public Map<String, String> toFlinkCatalogProperties(Map<String, String> gravitinoProperties) {
    Map<String, String> flinkCatalogProperties = Maps.newHashMap();
    flinkCatalogProperties.put(CommonCatalogOptions.CATALOG_TYPE.key(), GravitinoPaimonCatalogFactoryOptions.IDENTIFIER);

    gravitinoProperties.forEach(
            (key, value) -> {
              String flinkConfigKey = key;
              if (key.startsWith(PropertiesConverter.FLINK_PROPERTY_PREFIX)) {
                flinkConfigKey = key.substring(PropertiesConverter.FLINK_PROPERTY_PREFIX.length());
              }
              flinkCatalogProperties.put(GRAVITINO_CONFIG_TO_PAIMON.getOrDefault(flinkConfigKey, flinkConfigKey), value);
            });
    return flinkCatalogProperties;
  }

  @Override
  public Map<String, String> toGravitinoCatalogProperties(Configuration flinkConf) {
    Map<String, String> gravitinoCatalogProperties = Maps.newHashMap();

    flinkConf.toMap().forEach(
            (key, value) -> {
              boolean isGravitinoConfig = PAIMON_CONFIG_TO_GRAVITINO.containsKey(key);
              if (isGravitinoConfig) {
                gravitinoCatalogProperties.put(PAIMON_CONFIG_TO_GRAVITINO.get(key), value);
              } else {
                gravitinoCatalogProperties.put(FLINK_PROPERTY_PREFIX + key, value);
              }
            });
    return gravitinoCatalogProperties;
  }
}
