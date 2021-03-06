/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.jemmy.swt.lookup;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Item;
import org.jemmy.resources.StringComparePolicy;

/**
 * @author klara
 * @author erikgreijus
 */
public class ByToolTipItem<T extends Item> extends ByStringQueueLookup<T> {

    public ByToolTipItem(String text) {
        super(text);
    }

    public ByToolTipItem(String text, StringComparePolicy policy) {
        super(text, policy);
    }

    @Override
    public String getText(final T control) {
        Method mthd;
        try {
            mthd = control.getClass().getMethod("getToolTipText");
            return (String) mthd.invoke(control);
        } catch (NoSuchMethodException e) {
            // silently ignore the error to not clutter the logs (not all objects implement the method)
        } catch (Exception e) {
            System.err.println("Exception when using reflection to get tooltip text from " + control);
            e.printStackTrace();
        }
        return null;
    }

}
