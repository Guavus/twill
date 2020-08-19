/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.twill.kafka.client;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Represents a combination of topic and partition.
 */
public class TopicPartition {

  private final String topic;
  private final int partition;

  public TopicPartition(String topic, int partition) {
    this.topic = topic;
    this.partition = partition;
  }

  public final String getTopic() {
    return topic;
  }

  public final int getPartition() {
    return partition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TopicPartition other = (TopicPartition) o;
    return partition == other.partition && topic.equals(other.topic);
  }

  @Override
  public int hashCode() {
    int result = topic.hashCode();
    result = 31 * result + partition;
    return result;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("topic", topic)
            .add("partition", partition)
            .toString();
  }
}
