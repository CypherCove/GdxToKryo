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

import com.badlogic.gdx.utils.ShortArray;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class ShortArraySerializer extends Serializer<ShortArray> {

    @Override
    public void write(Kryo kryo, Output output, ShortArray array) {
        output.writeVarInt(array.size, true);
        output.writeBoolean(array.ordered);
        for (int i = 0; i < array.size; i++) {
            output.writeShort(array.get(i));
        }
    }

    @Override
    public ShortArray read(Kryo kryo, Input input, Class<ShortArray> type) {
        int length = input.readVarInt(true);
        boolean ordered = input.readBoolean();
        ShortArray array = new ShortArray(ordered, length);
        for (int i = 0; i < length; i++) {
            array.add(input.readShort());
        }
        return array;
    }

    @Override
    public ShortArray copy (Kryo kryo, ShortArray original) {
        ShortArray copy = new ShortArray(original.ordered, original.size);
        kryo.reference(copy);
        copy.addAll(original);
        return copy;
    }
}
