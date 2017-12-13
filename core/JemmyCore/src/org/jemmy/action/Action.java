/*
 * Copyright (c) 2007, 2017 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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
package org.jemmy.action;

import org.jemmy.JemmyException;


/**
 *
 * @author shura, KAM
 */
public abstract class Action {

    private boolean interrupted = false;
    private long startTime = -1, endTime = 0, allowedTime = 0;
    private Throwable throwable = null;

    /**
     * Executes {@linkplain #run(java.lang.Object[]) run()} method of this
     * Action, saving the duration of its execution and storing any
     * RuntimeException and Error which may occur during its work.
     * @param parameters Parameters to pass to {@linkplain
     * #run(java.lang.Object[]) run()} method
     * @see #getThrowable()
     * @see #failed()
     */
    public final void execute(Object... parameters) {
        startTime = System.currentTimeMillis();
        try {
            run(parameters);
        } catch (Error e) {
            throwable = e;
            throw e;
        } catch (RuntimeException e) {
            throwable = e;
            throw e;
        } catch (Exception e) {
            throwable = e;
            throw new JemmyException("Exception in action " + this.toString(), e);
        } finally {
            endTime = System.currentTimeMillis();
        }
    }

    /**
     *
     * @return
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     *
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Should be used from {@linkplain #run(java.lang.Object[]) run()) method
     * to check whether execution time is withing allowed time
     * @return true if difference between current time and start time is less
     * then allowed time; false otherwice
     */
    protected boolean withinAllowedTime() {
        return System.currentTimeMillis() - startTime < allowedTime;
    }

    /**
     *
     * @return
     */
    public long getAllowedTime() {
        return allowedTime;
    }

    /**
     *
     * @param allowedTime
     */
    public void setAllowedTime(long allowedTime) {
        this.allowedTime = allowedTime;
    }

    /**
     *
     * @param parameters
     */
    public abstract void run(Object... parameters) throws Exception;

    /**
     *
     * @return
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     *
     */
    public void interrupt() {
        this.interrupted = true;
    }

    /**
     * Returns throwable that occurred during run() invocation.
     * @return Error or RuntimeException.
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Indicates whether action invocation failed.
     * @return true if some exception occurred during run() invocation.
     */
    public boolean failed() {
        return throwable != null;
    }

    /**
     * Override this method to provide action description which
     * will be printed into output.
     * @return null If nothing should be printed into output.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}