/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.controller.freemarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.datatypes.RDFDatatype ;

import edu.cornell.mannlib.vitro.webapp.auth.permissions.SimplePermission;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.Actions;
import edu.cornell.mannlib.vitro.webapp.beans.Individual;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.FreemarkerHttpServlet;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.responsevalues.ResponseValues;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.responsevalues.TemplateResponseValues;
import edu.cornell.mannlib.vitro.webapp.dao.jena.QueryUtils;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;


public class ManageLabelsForIndividualController extends FreemarkerHttpServlet {

    private static final Log log = LogFactory.getLog(ManageLabelsForIndividualController.class.getName());
    private static final String TEMPLATE_NAME = "manageLabelsForIndividual.ftl";
    
    @Override
	protected Actions requiredActions(VitroRequest vreq) {
		return SimplePermission.DO_FRONT_END_EDITING.ACTIONS;
	}

    @Override
    protected ResponseValues processRequest(VitroRequest vreq) {

        Map<String, Object> body = new HashMap<String, Object>();

        String subjectUri = vreq.getParameter("subjectUri");
        
        body.put("subjectUri", subjectUri);


        ArrayList<Literal> labels = getLabels(subjectUri, vreq);
        log.debug("labels = " + labels) ;
        body.put("labels", labels);
        
        Individual subject = vreq.getWebappDaoFactory().getIndividualDao().getIndividualByURI(subjectUri);
        if( subject != null && subject.getName() != null ){
             body.put("subjectName", subject.getName());
        }else{
             body.put("subjectName", null);
        }

        return new TemplateResponseValues(TEMPLATE_NAME, body);
    }
  
    
    private static String LABEL_QUERY = ""
        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
        + "SELECT DISTINCT ?label WHERE { \n"
        + "    ?subject rdfs:label ?label \n"
        + "} ORDER BY ?label";
    
       
    ArrayList<Literal>  getLabels(String subjectUri, VitroRequest vreq) {
          
        String queryStr = QueryUtils.subUriForQueryVar(LABEL_QUERY, "subject", subjectUri);
        log.debug("queryStr = " + queryStr);

        ArrayList<Literal>  labels = new ArrayList<Literal>();
        try {
            ResultSet results = QueryUtils.getQueryResults(queryStr, vreq);
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal nodeLiteral = soln.get("label").asLiteral();
                labels.add(nodeLiteral); 


            }
        } catch (Exception e) {
            log.error(e, e);
        }    
       
        return labels;
    }
}

