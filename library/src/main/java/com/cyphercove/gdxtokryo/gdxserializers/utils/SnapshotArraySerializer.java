/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.cyphercove.gdxtokryo.gdxserializers.utils;

import com.badlogic.gdx.utils.SnapshotArray;

public class SnapshotArraySerializer extends ArraySerializer<SnapshotArray> {

    @Override
    protected SnapshotArray create (boolean ordered, int capacity, Class type) {
        return new SnapshotArray(ordered, capacity, type);
    }
}
