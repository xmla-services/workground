package org.eclipse.daanse.xmla.ws.jakarta.model.xmla;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_empty.Emptyresult;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_mddataset.Mddataset;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_multipleresults.Results;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Rowset;

import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ Rowset.class, Emptyresult.class, Mddataset.class, Results.class })
public abstract class Content {

}
