/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.rel.expressions.literals;

import com.datastrato.gravitino.rel.expressions.Expression;
import com.datastrato.gravitino.rel.types.Type;

/**
 * Represents a constant literal value in the public expression API.
 *
 * @param <T> the JVM type of value held by the literal
 */
public interface Literal<T> extends Expression {
  /**
   * Get the literal value.
   *
   * @return The literal value.
   */
  T value();

  /**
   * Get the data type of the literal.
   *
   * @return The data type of the literal.
   */
  Type dataType();

  @Override
  default Expression[] children() {
    return EMPTY_EXPRESSION;
  }
}
