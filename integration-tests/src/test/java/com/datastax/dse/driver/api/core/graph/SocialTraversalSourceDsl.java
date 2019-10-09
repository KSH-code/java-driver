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
package com.datastax.dse.driver.api.core.graph;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategies;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.DefaultGraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.GraphStep;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class SocialTraversalSourceDsl extends GraphTraversalSource {

  public SocialTraversalSourceDsl(
      final Graph graph, final TraversalStrategies traversalStrategies) {
    super(graph, traversalStrategies);
  }

  public SocialTraversalSourceDsl(final Graph graph) {
    super(graph);
  }

  public GraphTraversal<Vertex, Vertex> persons(String... names) {
    GraphTraversalSource clone = this.clone();

    // Manually add a "start" step for the traversal in this case the equivalent of V(). GraphStep
    // is marked
    // as a "start" step by passing "true" in the constructor.
    clone.getBytecode().addStep(GraphTraversal.Symbols.V);
    GraphTraversal<Vertex, Vertex> traversal = new DefaultGraphTraversal<>(clone);
    traversal.asAdmin().addStep(new GraphStep(traversal.asAdmin(), Vertex.class, true));

    traversal = traversal.hasLabel("person");
    if (names.length > 0) traversal = traversal.has("name", P.within(names));

    return traversal;
  }
}
