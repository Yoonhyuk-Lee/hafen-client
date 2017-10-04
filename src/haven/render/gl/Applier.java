/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven.render.gl;

import java.util.*;
import haven.render.*;
import haven.render.sl.*;
import static haven.Utils.eq;

public class Applier {
    public final GLEnvironment env;
    /* Soft pipeline state */
    private State[] cur = new State[0];
    /* Program state */
    private ShaderMacro[] shaders = new ShaderMacro[0];
    private int shash = 0;
    private GLProgram prog;
    private Object[] uvals = new Object[0];
    /* GL states */
    public GLState[] glstates = new GLState[GLState.slots.length];

    public Applier(GLEnvironment env) {
	this.env = env;
    }

    public Applier clone() {
	Applier ret = new Applier(env);
	ret.cur = Arrays.copyOf(this.cur, this.cur.length);
	ret.shaders = Arrays.copyOf(this.shaders, this.shaders.length);
	ret.shash = this.shash;
	ret.prog = this.prog;
	ret.glstates = Arrays.copyOf(this.glstates, this.glstates.length);
	return(ret);
    }

    private void setprog(GLProgram prog) {
	this.prog = prog;
	this.uvals = new Object[prog.uniforms.length];
    }

    public GLProgram prog() {return(prog);}

    private <T> void uapply(BGL gl, int ui, Pipe pipe) {
	GLProgram prog = this.prog;
	Uniform var = prog.uniforms[ui];
	Object val = var.value.apply(pipe);
	if(val != uvals[ui]) {
	    UniformApplier.TypeMapping.apply(gl, var.type, prog.uniform(var), val);
	    uvals[ui] = val;
	}
    }

    private void assume(State[] ns) {
	int hash = 0, i;
	if(this.cur.length < ns.length) {
	    this.cur = Arrays.copyOf(this.cur, ns.length);
	    this.shaders = Arrays.copyOf(this.shaders, this.cur.length);
	}
	State[] cur = this.cur;
	ShaderMacro[] shaders = this.shaders;
	for(i = 0; i < ns.length; i++) {
	    cur[i] = ns[i];
	    ShaderMacro shader = (cur[i] == null) ? null : cur[i].shader();
	    if(shader != shaders[i]) {
		hash ^= System.identityHashCode(shaders[i]);
		shaders[i] = shader;
		hash ^= System.identityHashCode(shaders[i]);
	    }
	}
	for(; i < cur.length; i++) {
	    cur[i] = null;
	    if(shaders[i] != null) {
		hash ^= System.identityHashCode(shaders[i]);
		shaders[i] = null;
	    }
	}
	this.shash = hash;
	setprog(env.getprog(hash, shaders));
    }

    public void apply(BGL gl, Pipe to) {
	State[] ns = to.states();
	if(gl == null) {
	    assume(ns);
	    return;
	}
	if(this.cur.length < ns.length) {
	    this.cur = Arrays.copyOf(this.cur, ns.length);
	    this.shaders = Arrays.copyOf(this.shaders, this.cur.length);
	}
	State[] cur = this.cur;
	ShaderMacro[] shaders = this.shaders;
	int[] ch = new int[ns.length];
	int n = 0;
	{
	    int i = 0;
	    for(; i < ns.length; i++) {
		if(!eq(ns[i], cur[i]))
		    ch[n++] = i;
	    }
	    for(; i < cur.length; i++) {
		if(cur[i] != null)
		    ch[n++] = i;
	    }
	}
	if(n == 0)
	    return;
	int shash = this.shash;
	for(int i = 0; i < n; i++) {
	    State s = (ch[i] < ns.length) ? ns[ch[i]] : null;
	    ShaderMacro nm = ((s == null) ? null : s.shader());
	    if(nm != shaders[ch[i]]) {
		shash ^= System.identityHashCode(shaders[ch[i]]);
		shaders[ch[i]] = nm;
		shash ^= System.identityHashCode(nm);
	    }
	}
	if(shash == this.shash) {
	    GLProgram prog = this.prog;
	    boolean[] applied = new boolean[prog.uniforms.length];
	    for(int i = 0; i < n; i++) {
		if(prog.umap[ch[i]] == null)
		    continue;
		for(int ui : prog.umap[ch[i]]) {
		    if(!applied[ui]) {
			uapply(gl, ui, to);
			applied[ui] = true;
		    }
		}
	    }
	} else {
	    this.shash = shash;
	    GLProgram prog = env.getprog(shash, shaders);
	    setprog(prog);
	    prog.apply(gl);
	    for(int i = 0; i < prog.uniforms.length; i++)
		uapply(gl, i, to);
	}
    }

    public void apply(BGL gl, GLState st) {
	int slot = st.slotidx();
	if(gl == null) {
	    glstates[slot] = st;
	    return;
	}
	GLState cur = glstates[slot];
	if((cur == null) && (st != null)) {
	    st.apply(gl);
	    glstates[slot] = st;
	} else if((cur != null) && (st == null)) {
	    cur.unapply(gl);
	    glstates[slot] = null;
	} else if ((cur != null) && (st != null)) {
	    cur.applyto(gl, st);
	    glstates[slot] = st;
	}
    }
}
