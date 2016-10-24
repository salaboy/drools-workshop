/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.model;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.DateDt;

/**
 *
 * @author esteban
 */
public class AsthmaticPatient extends Patient {
    
    private DateDt diagnosedDate;

    public DateDt getDiagnosedDate() {
        return diagnosedDate;
    }

    public AsthmaticPatient setDiagnosedDate(DateDt diagnosedDate) {
        this.diagnosedDate = diagnosedDate;
        return this;
    }
    
}
