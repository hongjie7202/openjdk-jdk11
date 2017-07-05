/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
 *
 */

/**
 * @test
 * @bug 8074981
 * @summary Add C2 x86 Superword support for scalar product reduction optimizations : float test
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=2 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=2 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=4 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=4 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=8 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=8 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=16 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-SuperWordReductions -XX:LoopUnrollLimit=250 -XX:LoopMaxUnroll=16 -XX:CompileThresholdScaling=0.1 ProdRed_Float
 */

public class ProdRed_Float
{
  public static void main(String[] args) throws Exception {
    float[] a = new float[256*1024];
    float[] b = new float[256*1024];
    prodReductionInit(a,b);
    float valid = 2000;
    float total = 0;
    for(int j = 0; j < 2000; j++) {
      total = j + 1;
      total = prodReductionImplement(a,b, total);
    }
    if(total == valid) {
      System.out.println("Success");
    } else {
      System.out.println("Invalid sum of elements variable in total: " + total);
      System.out.println("Expected value = " + valid);
      throw new Exception("Failed");
    }
  }

  public static void prodReductionInit(float[] a, float[] b)
  {
    for(int i = 0; i < a.length; i++)
    {
      a[i] = i + 2;
      b[i] = i + 1;
    }
  }

  public static float prodReductionImplement(float[] a, float[] b, float total)
  {
    for(int i = 0; i < a.length; i++)
    {
      total *= a[i] - b[i];
    }
    return total;
  }

}
