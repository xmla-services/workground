/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.msxmla;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.TupleType;

import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"normTuples", "membersLookup"})
@XmlRootElement(name = "NormTupleSet")
public class NormTupleSet implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "NormTuples", required = true)
    protected NormTuplesType normTuples;
    @XmlElement(name = "MembersLookup", required = true)
    protected NormTupleSet.MembersLookup membersLookup;

    public NormTuplesType getNormTuples() {
        return normTuples;
    }

    public void setNormTuples(NormTuplesType value) {
        this.normTuples = value;
    }

    public NormTupleSet.MembersLookup getMembersLookup() {
        return membersLookup;
    }

    public void setMembersLookup(NormTupleSet.MembersLookup value) {
        this.membersLookup = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"members"})
    public static class MembersLookup implements Serializable {

        private final static long serialVersionUID = 1L;
        @XmlElement(name = "Members", required = true)
        protected List<TupleType> members;

        public List<TupleType> getMembers() {
            return this.members;
        }

        public void setMembers(List<TupleType> members) {
            this.members = members;
        }
    }

}
