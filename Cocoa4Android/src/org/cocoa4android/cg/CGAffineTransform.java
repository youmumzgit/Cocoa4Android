/*
 * Copyright (C) 2012 Wu Tong,chao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cocoa4android.cg;

public class CGAffineTransform {
	public float a,b,c,d;
	public float tx,ty;
	public CGAffineTransform(float a, float b,
			float c, float d, float tx, float ty){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		this.tx = tx;
		this.ty = ty;
	}
	public CGAffineTransform(float tx, float ty){
		this.tx = tx;
		this.ty = ty;
	}
	public CGAffineTransform(){
	}
}
