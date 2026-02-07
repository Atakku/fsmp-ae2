/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AlgorithmX2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package appeng.api.util;

public enum TLCableType {
    /**
     * No Cable present.
     */
    NONE(TLCableVariant.NONE, TLCableSize.NONE),

    /**
     * Connections to this block should render as glass.
     */
    GLASS(TLCableVariant.GLASS, TLCableSize.NORMAL),

    /**
     * Connections to this block should render as smart.
     */
    SMART(TLCableVariant.SMART, TLCableSize.NORMAL),

    /**
     * Smart Dense Cable, represents a tier 2 block that can carry 32 channels.
     */
    DENSE_SMART(TLCableVariant.SMART, TLCableSize.DENSE);

    public static final TLCableType[] VALIDCABLES = { GLASS, SMART, DENSE_SMART };

    private final TLCableVariant variant;
    private final TLCableSize size;

    TLCableType(TLCableVariant variant, TLCableSize size) {
        this.variant = variant;
        this.size = size;
    }

    public TLCableSize size() {
        return this.size;
    }

    public TLCableVariant variant() {
        return this.variant;
    }

    public boolean isValid() {
        return this.variant != TLCableVariant.NONE && this.size != TLCableSize.NONE;
    }

    public boolean isDense() {
        return this.size == TLCableSize.DENSE;
    }

    public boolean isSmart() {
        return this.variant == TLCableVariant.SMART;
    }

    public static TLCableType min(TLCableType a, TLCableType b) {
        final TLCableVariant v = TLCableVariant.min(a.variant(), b.variant());
        final TLCableSize s = TLCableSize.min(a.size(), b.size());

        return TLCableType.from(v, s);
    }

    public static TLCableType max(TLCableType a, TLCableType b) {
        final TLCableVariant v = TLCableVariant.max(a.variant(), b.variant());
        final TLCableSize s = TLCableSize.max(a.size(), b.size());

        return TLCableType.from(v, s);
    }

    private static TLCableType from(TLCableVariant variant, TLCableSize size) {
        switch (variant) {
            case GLASS:
                switch (size) {
                    case NORMAL:
                        return GLASS;
                    default:
                        break;
                }

                break;
            case SMART:
                switch (size) {
                    case NORMAL:
                        return SMART;
                    case DENSE:
                        return DENSE_SMART;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        return NONE;
    }
}
