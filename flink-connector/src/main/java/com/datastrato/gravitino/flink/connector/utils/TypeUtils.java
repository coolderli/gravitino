/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.utils;

import com.datastrato.gravitino.rel.types.Type;
import com.datastrato.gravitino.rel.types.Types;
import org.apache.flink.table.types.logical.ArrayType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.MapType;
import org.apache.flink.table.types.logical.RowType;

public class TypeUtils {

  private TypeUtils() {}

  public static Type toGravitinoType(LogicalType logicalType) {
    switch (logicalType.getTypeRoot()) {
      case CHAR:
      case VARCHAR:
        return Types.StringType.get();
      case BOOLEAN:
        return Types.BooleanType.get();
      case BINARY:
      case VARBINARY:
        return Types.BinaryType.get();
      case DECIMAL:
        DecimalType decimalType = (DecimalType) logicalType;
        return Types.DecimalType.of(decimalType.getPrecision(), decimalType.getScale());
      case TINYINT:
      case SMALLINT:
      case INTEGER:
        return Types.IntegerType.get();
      case DATE:
        return Types.DateType.get();
      case INTERVAL_YEAR_MONTH:
        return Types.IntervalYearType.get();
      case TIME_WITHOUT_TIME_ZONE:
        return Types.TimeType.get();
      case TIMESTAMP_WITHOUT_TIME_ZONE:
        return Types.TimestampType.withoutTimeZone();
      case BIGINT:
        return Types.LongType.get();
      case INTERVAL_DAY_TIME:
        return Types.IntervalDayType.get();
      case FLOAT:
        return Types.FloatType.get();
      case DOUBLE:
        return Types.DoubleType.get();
      case TIMESTAMP_WITH_LOCAL_TIME_ZONE:
      case TIMESTAMP_WITH_TIME_ZONE:
        return Types.TimestampType.withTimeZone();
      case ARRAY:
        ArrayType arrayType = (ArrayType) logicalType;
        Type elementType = toGravitinoType(arrayType.getElementType());
        return Types.ListType.of(elementType, arrayType.isNullable());
      case MULTISET:
      case MAP:
        MapType mapType = (MapType) logicalType;
        Type keyType = toGravitinoType(mapType.getKeyType());
        Type valueType = toGravitinoType(mapType.getValueType());
        return Types.MapType.of(keyType, valueType, mapType.isNullable());
      case ROW:
      case STRUCTURED_TYPE:
        RowType rowType = (RowType) logicalType;
        Types.StructType.Field[] fields =
            rowType.getFields().stream()
                .map(
                    field -> {
                      LogicalType fieldLogicalType = field.getType();
                      Type fieldType = toGravitinoType(fieldLogicalType);
                      return Types.StructType.Field.of(
                          field.getName(),
                          fieldType,
                          fieldLogicalType.isNullable(),
                          field.getDescription().orElse(null));
                    })
                .toArray(Types.StructType.Field[]::new);
        return Types.StructType.of(fields);
      case NULL:
        return Types.NullType.get();
      case UNRESOLVED:
        return Types.UnparsedType.of(logicalType.getTypeRoot().name());
      case DISTINCT_TYPE:
      case RAW:
      case SYMBOL:
      default:
        throw new UnsupportedOperationException(
            "Not support type: " + logicalType.asSummaryString());
    }
  }
}
