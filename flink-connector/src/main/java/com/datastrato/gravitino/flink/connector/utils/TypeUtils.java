/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.flink.connector.utils;

import com.datastrato.gravitino.rel.types.Type;
import com.datastrato.gravitino.rel.types.Types;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.ArrayType;
import org.apache.flink.table.types.logical.BinaryType;
import org.apache.flink.table.types.logical.CharType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.MapType;
import org.apache.flink.table.types.logical.RowType;

public class TypeUtils {

  private TypeUtils() {}

  public static Type toGravitinoType(LogicalType logicalType) {
    switch (logicalType.getTypeRoot()) {
      case CHAR:
        CharType charType = (CharType) logicalType;
        return Types.FixedCharType.of(charType.getLength());
      case VARCHAR:
        return Types.StringType.get();
      case BOOLEAN:
        return Types.BooleanType.get();
      case BINARY:
        BinaryType binary = (BinaryType) logicalType;
        if (binary.getLength() == 1) {
          return Types.ByteType.get();
        } else {
          return Types.BinaryType.get();
        }
      case VARBINARY:
        return Types.BinaryType.get();
      case DECIMAL:
        DecimalType decimalType = (DecimalType) logicalType;
        return Types.DecimalType.of(decimalType.getPrecision(), decimalType.getScale());
      case TINYINT:
      case SMALLINT:
        return Types.ShortType.get();
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
      case STRUCTURED_TYPE:
      case UNRESOLVED:
      case DISTINCT_TYPE:
      case RAW:
      case SYMBOL:
      default:
        throw new UnsupportedOperationException(
            "Not support type: " + logicalType.asSummaryString());
    }
  }

  public static DataType toFlinkType(Type gravitinoType) {
    if (gravitinoType instanceof Types.ByteType) {
      return DataTypes.BINARY(1);
    } else if (gravitinoType instanceof Types.ShortType) {
      return DataTypes.SMALLINT();
    } else if (gravitinoType instanceof Types.IntegerType) {
      return DataTypes.INT();
    } else if (gravitinoType instanceof Types.LongType) {
      return DataTypes.BIGINT();
    } else if (gravitinoType instanceof Types.FloatType) {
      return DataTypes.FLOAT();
    } else if (gravitinoType instanceof Types.DoubleType) {
      return DataTypes.DOUBLE();
    } else if (gravitinoType instanceof Types.DecimalType) {
      Types.DecimalType decimalType = (Types.DecimalType) gravitinoType;
      return DataTypes.DECIMAL(decimalType.precision(), decimalType.scale());
    } else if (gravitinoType instanceof Types.StringType) {
      return DataTypes.STRING();
    } else if (gravitinoType instanceof Types.VarCharType) {
      Types.VarCharType varCharType = (Types.VarCharType) gravitinoType;
      return DataTypes.VARCHAR(varCharType.length());
    } else if (gravitinoType instanceof Types.FixedCharType) {
      Types.FixedCharType charType = (Types.FixedCharType) gravitinoType;
      return DataTypes.CHAR(charType.length());
    } else if (gravitinoType instanceof Types.BinaryType) {
      return DataTypes.BYTES();
    } else if (gravitinoType instanceof Types.BooleanType) {
      return DataTypes.BOOLEAN();
    } else if (gravitinoType instanceof Types.DateType) {
      return DataTypes.DATE();
    } else if (gravitinoType instanceof Types.TimestampType) {
      Types.TimestampType timestampType = (Types.TimestampType) gravitinoType;
      if (timestampType.hasTimeZone()) {
        return DataTypes.TIMESTAMP_LTZ();
      } else {
        return DataTypes.TIMESTAMP();
      }
    } else if (gravitinoType instanceof Types.ListType) {
      Types.ListType listType = (Types.ListType) gravitinoType;
      return DataTypes.ARRAY(toFlinkType(listType.elementType()));
    } else if (gravitinoType instanceof Types.MapType) {
      Types.MapType mapType = (Types.MapType) gravitinoType;
      return DataTypes.MAP(toFlinkType(mapType.keyType()), toFlinkType(mapType.valueType()));
    } else if (gravitinoType instanceof Types.StructType) {
      Types.StructType structType = (Types.StructType) gravitinoType;
      List<DataTypes.Field> fields =
          Arrays.stream(structType.fields())
              .map(
                  f -> {
                    if (f.comment() == null) {
                      return DataTypes.FIELD(f.name(), toFlinkType(f.type()));
                    } else {
                      return DataTypes.FIELD(f.name(), toFlinkType(f.type()), f.comment());
                    }
                  })
              .collect(Collectors.toList());
      return DataTypes.ROW(fields);
    } else if (gravitinoType instanceof Types.NullType) {
      return DataTypes.NULL();
    } else if (gravitinoType instanceof Types.TimeType) {
      return DataTypes.TIME();
    } else if (gravitinoType instanceof Types.IntervalYearType) {
      return DataTypes.INTERVAL(DataTypes.YEAR());
    } else if (gravitinoType instanceof Types.IntervalDayType) {
      return DataTypes.INTERVAL(DataTypes.DAY());
    }
    throw new UnsupportedOperationException("Not support " + gravitinoType.toString());
  }
}
