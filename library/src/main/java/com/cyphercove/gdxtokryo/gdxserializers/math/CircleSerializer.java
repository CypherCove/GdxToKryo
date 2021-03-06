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

import com.badlogic.gdx.math.Circle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class CircleSerializer extends Serializer<Circle> {
    @Override
    public void write(Kryo kryo, Output output, Circle circle) {
        output.writeFloat(circle.x);
        output.writeFloat(circle.y);
        output.writeFloat(circle.radius);
    }

    @Override
    public Circle read(Kryo kryo, Input input, Class<Circle> type) {
        float x = input.readFloat();
        float y = input.readFloat();
        float radius = input.readFloat();
        return new Circle(x, y, radius);
    }

    @Override
    public Circle copy (Kryo kryo, Circle original) {
        return new Circle(original);
    }
}
