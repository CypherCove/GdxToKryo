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

import com.badlogic.gdx.utils.ObjectFloatMap;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.Iterator;

public class ObjectFloatMapSerializer extends Serializer<ObjectFloatMap> {
    private Class keyGenericType;

    public void setGenerics (Kryo kryo, Class[] generics) {
        keyGenericType = null;

        if (generics != null && generics.length > 0) {
            if (generics[0] != null && kryo.isFinal(generics[0])) keyGenericType = generics[0];
        }
    }

    public void write (Kryo kryo, Output output, ObjectFloatMap map) {
        int length = map.size;
        output.writeVarInt(length, true);
        output.writeBoolean(false); // whether type is written (in case future version of ObjectFloatMap supports type awareness)

        Serializer keySerializer = null;
        if (keyGenericType != null) {
            if (keySerializer == null) keySerializer = kryo.getSerializer(keyGenericType);
            keyGenericType = null;
        }

        for (Iterator iter = map.iterator(); iter.hasNext();) {
            ObjectFloatMap.Entry entry = (ObjectFloatMap.Entry)iter.next();
            if (keySerializer != null) {
                kryo.writeObject(output, entry.key, keySerializer);
            } else
                kryo.writeClassAndObject(output, entry.key);
            output.writeFloat(entry.value);
        }
    }

    protected ObjectFloatMap create (int size) {
        return new ObjectFloatMap(size);
    }

    public ObjectFloatMap read (Kryo kryo, Input input, Class<ObjectFloatMap> type) {
        int length = input.readVarInt(true);
        input.readBoolean(); // currently unused
        ObjectFloatMap map = create(length);

        Class keyClass = null;

        Serializer keySerializer = null;
        if (keyGenericType != null) {
            keyClass = keyGenericType;
            if (keySerializer == null) keySerializer = kryo.getSerializer(keyClass);
            keyGenericType = null;
        }

        kryo.reference(map);

        for (int i = 0; i < length; i++) {
            Object key;
            if (keySerializer != null) {
                key = kryo.readObject(input, keyClass, keySerializer);
            } else
                key = kryo.readClassAndObject(input);
            float value = input.readFloat();
            map.put(key, value);
        }
        return map;
    }

    public ObjectFloatMap copy (Kryo kryo, ObjectFloatMap original) {
        ObjectFloatMap copy = create(original.size);
        kryo.reference(copy);
        copy.putAll(original);
        return copy;
    }
}
