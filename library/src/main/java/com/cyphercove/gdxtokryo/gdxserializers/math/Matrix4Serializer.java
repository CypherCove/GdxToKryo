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
package com.cyphercove.gdxtokryo.gdxserializers.math;

import com.badlogic.gdx.math.Matrix4;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Matrix4Serializer extends Serializer<Matrix4> {
    @Override
    public void write(Kryo kryo, Output output, Matrix4 matrix4) {
        output.writeFloats(matrix4.val);
    }

    @Override
    public Matrix4 read(Kryo kryo, Input input, Class<Matrix4> type) {
        float[] values = input.readFloats(16);
        return new Matrix4(values);
    }

    @Override
    public Matrix4 copy (Kryo kryo, Matrix4 original) {
        return new Matrix4(original);
    }
}
