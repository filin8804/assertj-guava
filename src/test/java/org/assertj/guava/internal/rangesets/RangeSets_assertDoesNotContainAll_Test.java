/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.guava.internal.rangesets;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.error.ShouldNotContain.shouldNotContain;
import static org.assertj.core.internal.ErrorMessages.iterableValuesToLookForIsEmpty;
import static org.assertj.core.internal.ErrorMessages.iterableValuesToLookForIsNull;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.IterableUtil.iterable;

import org.assertj.core.error.ErrorMessageFactory;
import org.assertj.guava.internal.RangeSetsBaseTest;
import org.junit.jupiter.api.Test;

class RangeSets_assertDoesNotContainAll_Test extends RangeSetsBaseTest {

  @Test
  void should_pass_if_actual_does_not_contain_values() {
    rangeSets.assertDoesNotContainAll(someInfo(), actual, iterable(-10, 12, 40));
  }

  @Test
  void should_pass_if_actual_does_not_contain_given_values_even_if_duplicated() {
    rangeSets.assertDoesNotContainAll(someInfo(), actual, iterable(-10, -10, 12, 12));
  }

  @Test
  void should_throw_error_if_array_of_values_to_look_for_is_empty() {
    assertThatIllegalArgumentException().isThrownBy(() -> rangeSets.assertDoesNotContainAll(someInfo(), actual, iterable()))
                                        .withMessage(iterableValuesToLookForIsEmpty());
  }

  @Test
  void should_throw_error_if_array_of_values_to_look_for_is_null() {
    assertThatIllegalArgumentException().isThrownBy(() -> rangeSets.assertDoesNotContainAll(someInfo(), actual, null))
                                        .withMessage(iterableValuesToLookForIsNull());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> rangeSets.assertDoesNotContainAll(someInfo(), null,
                                                                                                       iterable(1)))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_contains_all_values() {
    Iterable<Integer> expected = iterable(5, 10, 32);

    ErrorMessageFactory expectedMessage = shouldNotContain(actual, expected, expected);

    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> rangeSets.assertDoesNotContainAll(someInfo(), actual,
                                                                                                       expected))
                                                   .withMessage(expectedMessage.create());
  }

  @Test
  void should_fail_if_actual_contains_any_values() {
    Iterable<Integer> expected = iterable(5, 12, 32, 45);

    ErrorMessageFactory expectedMessage = shouldNotContain(actual, expected, iterable(5, 32));

    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> rangeSets.assertDoesNotContainAll(someInfo(), actual,
                                                                                                       expected))
                                                   .withMessage(expectedMessage.create());
  }
}
