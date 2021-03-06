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
import static org.assertj.core.error.ShouldBeEmpty.shouldBeEmpty;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import org.assertj.guava.internal.RangeSetsBaseTest;
import org.junit.jupiter.api.Test;

class RangeSets_assertEmpty_Test extends RangeSetsBaseTest {

  @Test
  void should_pass_if_actual_is_empty() {
    actual.clear();
    rangeSets.assertEmpty(someInfo(), actual);
  }

  @Test
  void should_fail_if_actual_is_not_empty() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> rangeSets.assertEmpty(someInfo(), actual))
                                                   .withMessage(shouldBeEmpty(actual).create());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> rangeSets.assertEmpty(someInfo(), null))
                                                   .withMessage(actualIsNull());
  }
}
