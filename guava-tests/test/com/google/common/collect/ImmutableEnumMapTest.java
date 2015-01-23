/*
 * Copyright (C) 2012 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import static org.truth0.Truth.ASSERT;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.testing.AnEnum;
import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.TestEnumMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.truth0.subjects.CollectionSubject;

/**
 * Tests for {@code ImmutableEnumMap}.
 *
 * @author Louis Wasserman
 */
@GwtCompatible
public class ImmutableEnumMapTest extends TestCase {
  public static class ImmutableEnumMapGenerator extends TestEnumMapGenerator {
    @Override
    protected Map<AnEnum, String> create(Entry<AnEnum, String>[] entries) {
      Map<AnEnum, String> map = Maps.newHashMap();
      for (Entry<AnEnum, String> entry : entries) {
        map.put(entry.getKey(), entry.getValue());
      }
      return Maps.immutableEnumMap(map);
    }
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(MapTestSuiteBuilder.using(new ImmutableEnumMapGenerator())
      .named("Maps.immutableEnumMap")
      .withFeatures(CollectionSize.ANY,
          CollectionFeature.SERIALIZABLE,
          CollectionFeature.ALLOWS_NULL_QUERIES)
      .createTestSuite());
    suite.addTestSuite(ImmutableEnumMapTest.class);
    return suite;
  }

  public void testEmptyImmutableEnumMap() {
    ImmutableMap<AnEnum, String> map = Maps.immutableEnumMap(ImmutableMap.<AnEnum, String>of());
    assertEquals(ImmutableMap.of(), map);
  }

  public void testImmutableEnumMapOrdering() {
    ImmutableMap<AnEnum, String> map = Maps.immutableEnumMap(
        ImmutableMap.of(AnEnum.C, "c", AnEnum.A, "a", AnEnum.E, "e"));

    assertThat(map.entrySet()).has().allOf(
        Helpers.mapEntry(AnEnum.A, "a"),
        Helpers.mapEntry(AnEnum.C, "c"),
        Helpers.mapEntry(AnEnum.E, "e")).inOrder();
  }

  // Hack for JDK5 type inference.
  private static <T> CollectionSubject<? extends CollectionSubject<?, T, Collection<T>>, T, Collection<T>> assertThat(
      Collection<T> collection) {
    return ASSERT.<T, Collection<T>>that(collection);
  }
}