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
package org.assertj.guava.api;

import static com.google.common.collect.ImmutableRangeSet.of;
import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Range.open;
import static com.google.common.collect.TreeRangeSet.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.internal.ErrorMessages.valuesToLookForIsEmpty;
import static org.assertj.core.internal.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.IterableUtil.iterable;
import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.error.RangeSetShouldEnclose.shouldEnclose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;

@DisplayName("RangeSetAssert encloses")
class RangeSetAssert_encloses_Test {

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    RangeSet<Integer> actual = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> assertThat(actual).encloses(closed(0, 1)));
    // THEN
    assertThat(throwable).isInstanceOf(AssertionError.class)
                         .hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_ranges_are_null() {
    // GIVEN
    RangeSet<Integer> actual = create();
    Range<Integer>[] expected = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> assertThat(actual).encloses(expected));
    // THEN
    assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                         .hasMessage(valuesToLookForIsNull());
  }

  @Test
  void should_pass_if_both_expected_values_and_actual_are_empty() {
    // GIVEN
    RangeSet<Integer> actual = create();
    // THEN
    assertThat(actual).encloses();
  }

  @Test
  void should_fail_if_expected_values_are_empty() {
    // GIVEN
    RangeSet<Integer> actual = of(closed(0, 1));
    // WHEN
    Throwable throwable = catchThrowable(() -> assertThat(actual).encloses());
    // THEN
    assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                         .hasMessage(valuesToLookForIsEmpty());
  }

  @Test
  void should_pass_if_the_given_set_encloses_ranges() {
    // GIVEN
    RangeSet<Integer> rangeSet = of(closed(0, 100));
    // THEN
    assertThat(rangeSet).encloses(closed(0, 10),
                                  open(50, 60),
                                  open(90, 100));
  }

  @Test
  void should_fail_if_the_given_set_does_not_enclose_ranges() {
    // GIVEN
    RangeSet<Integer> actual = of(closed(0, 100));
    Range<Integer>[] expected = array(closed(50, 70),
                                      closed(120, 150));
    // WHEN
    Throwable throwable = catchThrowable(() -> assertThat(actual).encloses(expected));
    // THEN
    assertThat(throwable).isInstanceOf(AssertionError.class)
                         .hasMessage(shouldEnclose(actual, expected, iterable(closed(120, 150))).create());
  }
}
