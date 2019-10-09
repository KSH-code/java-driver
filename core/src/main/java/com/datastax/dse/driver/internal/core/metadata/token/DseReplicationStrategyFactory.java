/*
 * Copyright DataStax, Inc.
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
package com.datastax.dse.driver.internal.core.metadata.token;

import com.datastax.oss.driver.internal.core.context.InternalDriverContext;
import com.datastax.oss.driver.internal.core.metadata.token.DefaultReplicationStrategyFactory;
import com.datastax.oss.driver.internal.core.metadata.token.ReplicationStrategy;
import com.datastax.oss.driver.shaded.guava.common.base.Preconditions;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class DseReplicationStrategyFactory extends DefaultReplicationStrategyFactory {
  public DseReplicationStrategyFactory(InternalDriverContext context) {
    super(context);
  }

  @Override
  public ReplicationStrategy newInstance(Map<String, String> replicationConfig) {
    String strategyClass = replicationConfig.get("class");
    Preconditions.checkNotNull(
        strategyClass, "Missing replication strategy class in " + replicationConfig);
    switch (strategyClass) {
      case "org.apache.cassandra.locator.EverywhereStrategy":
        return new EverywhereStrategy();
      default:
        return super.newInstance(replicationConfig);
    }
  }
}
