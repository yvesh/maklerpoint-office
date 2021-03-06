/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Tools;

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ArrayTools {

    public static Object[] arrayPlusOne(Object[] array, String first) {
        Object[] obj = new Object[array.length + 1];

        obj[0] = first;

        for (int i = 1; i <= array.length; i++) {
            obj[i] = array[i - 1];
        }

        return obj;
    }

    public static Object[] arrayMerge(KundenObj[] A, FirmenObj[] B) {
        if(A == null && B == null)
            return null;
        else if (A == null)
            return B;
        else if (B == null)
            return A;
        
       Object[] C= new Object[A.length+B.length];
       System.arraycopy(A, 0, C, 0, A.length);
       System.arraycopy(B, 0, C, A.length, B.length);
       return C;
    }
}
