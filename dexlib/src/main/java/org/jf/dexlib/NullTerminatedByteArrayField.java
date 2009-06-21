/*
 * [The "BSD licence"]
 * Copyright (c) 2009 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib;

import org.jf.dexlib.util.AnnotatedOutput;
import org.jf.dexlib.util.ByteArray;
import org.jf.dexlib.util.Input;

public class NullTerminatedByteArrayField implements Field<NullTerminatedByteArrayField> {
    protected byte[] value;
    private final String fieldName;

    public NullTerminatedByteArrayField(String fieldName) {
        this.fieldName = fieldName;
    }

    public NullTerminatedByteArrayField(byte[] value, String fieldName) {
        this(fieldName);
        this.value = value.clone();
    }

    public void writeTo(AnnotatedOutput out) {
        out.annotate(fieldName);
        out.write(value);
        out.writeByte(0);
    }

    public void readFrom(Input in) {
        int startPosition = in.getCursor();
        while (in.readByte() != 0);
        int size = in.getCursor() - startPosition - 1;

        in.setCursor(startPosition);
        value = in.readBytes(size);
        in.skipBytes(1);
    }

    public int place(int offset) {
        return offset + value.length + 1;
    }

    public void copyTo(DexFile dexFile, NullTerminatedByteArrayField copy) {
        copy.value = value;
    }

    public int hashCode() {
        int h=1;
        for (int i = 0; i < value.length; i++) {
            h = h*7 + value[i];
        }
        return h;
    }

    public boolean equals(Object o) {
        if (!(o instanceof NullTerminatedByteArrayField)) {
            return false;
        }

        NullTerminatedByteArrayField other = (NullTerminatedByteArrayField)o;
        if (value.length != other.value.length) {
            return false;
        }

        for (int i = 0; i < value.length; i++) {
            if (value[i] != other.value[i]) {
                return false;
            }
        }

        return true;
    }
}
