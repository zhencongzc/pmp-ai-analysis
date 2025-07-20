package com.pmp.service.patient.impl;

import com.pmp.mapper.LabelDateMapper;
import com.pmp.mapper.PatientMapper;
import com.pmp.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = Logger.getLogger(PatientServiceImpl.class.getName());

    @Autowired
    LabelDateMapper labelDateMapper;
    @Autowired
    PatientMapper patientMapper;


}
