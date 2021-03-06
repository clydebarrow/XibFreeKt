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

package com.controlj.widget

import org.robovm.apple.coregraphics.CGPoint
import org.robovm.apple.coregraphics.CGRect
import org.robovm.apple.coregraphics.CGSize
import org.robovm.apple.uikit.UIBezierPath
import org.robovm.apple.uikit.UIColor
import org.robovm.apple.uikit.UIControl
import org.robovm.apple.uikit.UIControlEvents
import org.robovm.apple.uikit.UIEdgeInsets
import org.robovm.apple.uikit.UIEvent

/**
 * Created by clyde on 19/4/18.
 */
class Checkbox(var width: Double = 20.0, var height: Double = 20.0) : UIControl() {
    companion object {
        const val PADDING = 4.0
    }

    var borderWidth = 2.0
    var contentRatio = 0.5
    var borderColor = tintColor
    var checkColor = tintColor
    var padding = UIEdgeInsets(PADDING, PADDING, PADDING, PADDING)
    var touchInsets = UIEdgeInsets(-1.0, -4.0, -1.0, -4.0)
    var checked: Boolean = false
        set(value) {
            field = value
            setNeedsDisplay()
        }
    var isOn: Boolean       // for UISwitch compatibility
        get() = checked
        set(value) {
            checked = value
        }
    private var hidden = false
    private var touchFrame: CGRect = CGRect()
    private var boxPath = UIBezierPath.newRect(CGRect(0.0, 0.0, width, height))
    private var tickPath = UIBezierPath()

    override fun setFrame(frame: CGRect) {
        super.setFrame(frame)
        // create a path centered in the frame of the correct size
        val boxRect = CGRect((frame.width - width) / 2, (frame.height - height) / 2, width, height)
        boxPath = UIBezierPath.newRect(boxRect)
        tickPath = UIBezierPath()
        val insH = boxRect.width * contentRatio / 2
        val insV = boxRect.height * contentRatio / 2
        val newrect = boxRect.inset(insH, insV)
        tickPath.move(CGPoint(newrect.minX + .045 * newrect.width, newrect.minY + 0.64 * newrect.height))
        tickPath.addLine(CGPoint(newrect.minX + .35 * newrect.width, newrect.minY + 0.95 * newrect.height))
        tickPath.addLine(CGPoint(newrect.minX + .95 * newrect.width, newrect.minY + 0.05 * newrect.height))
    }

    override fun setHidden(p0: Boolean) {
        hidden = p0
        super.setHidden(p0)
    }

    init {
        backgroundColor = UIColor.clear()
        isUserInteractionEnabled = true     // don't use property syntax
        addOnTouchUpInsideListener { _, _ ->
            checked = !checked
            sendControlEventsActions(UIControlEvents.ValueChanged)
        }
    }

    override fun getSizeThatFits(p0: CGSize?): CGSize {
        return CGSize(width + padding.left + padding.right, height + padding.top + padding.bottom)
    }

    override fun draw(rect: CGRect) {
        super.draw(rect)
        if (!hidden) {
            drawOutline()
            if (checked)
                drawCheck()
        }
    }

    private fun drawOutline() {
        boxPath.lineWidth = borderWidth
        borderColor.setStroke()
        boxPath.stroke()
    }

    private fun drawCheck() {
        checkColor.setStroke()
        tickPath.lineWidth = borderWidth
        tickPath.stroke()
    }

    override fun layoutSubviews() {
        super.layoutSubviews()
        touchFrame = bounds.inset(touchInsets)
    }

    override fun isPointInside(point: CGPoint, event: UIEvent?): Boolean {
        return touchFrame.contains(point)
    }
}
