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

package org.apache.kylin.stream.coordinator;

import java.util.List;
import java.util.Map;

import org.apache.kylin.stream.core.model.CubeAssignment;
import org.apache.kylin.stream.core.model.ReplicaSet;
import org.apache.kylin.stream.core.model.Node;
import org.apache.kylin.stream.core.model.SegmentBuildState;
import org.apache.kylin.stream.core.model.StreamingCubeConsumeState;
import org.apache.kylin.stream.core.source.Partition;

public interface StreamMetadataStore {
    List<Node> getReceivers();

    List<String> getCubes();

    void addStreamingCube(String cubeName);

    void removeStreamingCube(String cubeName);

    StreamingCubeConsumeState getStreamingCubeConsumeState(String cubeName);

    void saveStreamingCubeConsumeState(String cubeName, StreamingCubeConsumeState state);

    void addReceiver(Node receiver);

    void removeReceiver(Node receiver);

    void removeCubeAssignment(String cubeName);

    void saveNewCubeAssignment(CubeAssignment newCubeAssignment);

    List<CubeAssignment> getAllCubeAssignments();

    Map<Integer, Map<String, List<Partition>>> getAllReplicaSetAssignments();

    Map<String, List<Partition>> getAssignmentsByReplicaSet(int replicaSetID);

    CubeAssignment getAssignmentsByCube(String cubeName);

    List<ReplicaSet> getReplicaSets();

    List<Integer> getReplicaSetIDs();

    ReplicaSet getReplicaSet(int rsID);

    void removeReplicaSet(int rsID);

    int createReplicaSet(ReplicaSet rs);

    void updateReplicaSet(ReplicaSet rs);

    Node getCoordinatorNode();

    void setCoordinatorNode(Node coordinator);

    /**
     * save the partition offset information
     * @param cubeName
     * @param sourceCheckpoint
     */
    void saveSourceCheckpoint(String cubeName, String segmentName, int rsID, String sourceCheckpoint);

    /**
     * get source checkpoint
     * @param cubeName
     * @return
     */
    Map<Integer, String> getSourceCheckpoint(String cubeName, String segmentName);

    /**
     * add group id to the segment info, indicate that the segment data
     * has been hand over to the remote store
     * @param cubeName
     * @param segmentName
     * @param rsID
     */
    void addCompleteReplicaSetForSegmentBuild(String cubeName, String segmentName, int rsID);

    void updateSegmentBuildState(String cubeName, String segmentName, SegmentBuildState.BuildState state);

    /**
     * get segment build state
     * @param cubeName
     * @return
     */
    List<SegmentBuildState> getSegmentBuildStates(String cubeName);

    /**
     * get segment build state
     * @param cubeName
     * @param segmentName
     * @return
     */
    SegmentBuildState getSegmentBuildState(String cubeName, String segmentName);

    boolean removeSegmentBuildState(String cubeName, String segmentName);
}
