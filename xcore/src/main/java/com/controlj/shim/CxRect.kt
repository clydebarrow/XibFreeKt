/*
 * Copyright (c) 2019 Control-J Pty. Ltd. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * .
 *
 */

package com.controlj.shim

/**
 * Copyright (C) Control-J Pty. Ltd. ACN 103594190
 * All rights reserved
 *
 * User: clyde
 * Date: 2019-03-29
 * Time: 16:51
 */
interface CxRect : CxBase {
    var origin: CxPoint     // the origin of this rectangle
    var size: CxSize        // the size of this rectangle

    val maxX: Double
        get() = origin.x + size.width

    val minX: Double
        get() = origin.x

    val maxY: Double
        get() = origin.y + size.height

    val minY: Double
        get() = origin.y

    var width: Double
        get() = size.width
        set(value) {
            size.width = value
        }
    var height: Double
        get() = size.height
        set(value) {
            size.height = value
        }

    /**
     * Does this rectangle contain the point?
     * @param point The point of interest
     * @return true if the point is inside this rectangle
     */
    fun contains(point: CxPoint): Boolean {
        return point.x >= minX && point.x < maxX &&
                point.y >= minY && point.y < maxY
    }
}