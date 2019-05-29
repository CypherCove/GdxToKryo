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

import com.badlogic.gdx.utils.IntMap;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.Iterator;

public class IntMapSerializer extends Serializer<IntMap> {
    private Class valueGenericType;

    public void setGenerics (Kryo kryo, Class[] generics) {
        valueGenericType = null;

        if (generics != null && generics.length > 0) {
            if (generics[0] != null && kryo.isFinal(generics[0])) valueGenericType = generics[0];
        }
    }

    public void write (Kryo kryo, Output output, IntMap map) {
        int length = map.size;
        output.writeVarInt(length, true);
        output.writeBoolean(false); // whether type is written (in case future version of IntMap supports type awareness)
        Serializer valueSerializer = null;
        if (valueGenericType != null) {
            if (valueSerializer == null) valueSerializer = kryo.getSerializer(valueGenericType);
            valueGenericType = null;
        }

        for (Iterator iter = map.iterator(); iter.hasNext();) {
            IntMap.Entry entry = (IntMap.Entry)iter.next();
            output.writeInt(entry.key);
            if (valueSerializer != null) {
                kryo.writeObjectOrNull(output, entry.value, valueSerializer);
            } else
                kryo.writeClassAndObject(output, entry.value);
        }
    }

    public IntMap read (Kryo kryo, Input input, Class<IntMap> type) {
        int length = input.readVarInt(true);
        input.readBoolean(); // currently unused
        IntMap map = new IntMap(length);

        Class valueClass = null;

        Serializer valueSerializer = null;
        if (valueGenericType != null) {
            valueClass = valueGenericType;
            if (valueSerializer == null) valueSerializer = kryo.getSerializer(valueClass);
            valueGenericType = null;
        }

        kryo.reference(map);

        for (int i = 0; i < length; i++) {
            int key = input.readInt();
            Object value;
            if (valueSerializer != null) {
                value = kryo.readObjectOrNull(input, valueClass, valueSerializer);
            } else
                value = kryo.readClassAndObject(input);
            map.put(key, value);
        }
        return map;
    }

    public IntMap copy (Kryo kryo, IntMap original) {
        IntMap copy = new IntMap(original.size);
        kryo.reference(copy);
        copy.putAll(original);
        return copy;
    }
}
