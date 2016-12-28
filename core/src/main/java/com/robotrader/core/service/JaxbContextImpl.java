/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotrader.core.service;

import com.robotrader.core.objects.ConditionalOrder;
import com.robotrader.core.objects.Security;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aanpilov
 */
public class JaxbContextImpl implements JaxbContext {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    
    public JaxbContextImpl() {
        try{
            JAXBContext jaxbInstance = JAXBContext.newInstance("com.robotrader.core.objects");
            marshaller = jaxbInstance.createMarshaller();
            unmarshaller = jaxbInstance.createUnmarshaller();
        } catch(Exception e){
            log.error("CoreObjectsMarshaller construction error", e);
        }
    }
    
    @Override
    public String marshall(Object obj) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(obj, stringWriter);
        
        return stringWriter.toString();
    }
    
    @Override
    public Object unmarshall(String str) throws JAXBException {
        return unmarshaller.unmarshal(new StringReader(str));
    }
    
    public static void main(String[] args) throws Exception {
        JaxbContextImpl marshaller = new JaxbContextImpl();
        
        Security paper = new Security(null, null, "FUT", "SBER");
        ConditionalOrder order = ConditionalOrder.buyLastUp(123, 0, paper, 5, BigDecimal.ONE, BigDecimal.TEN);
        String str = marshaller.marshall(order);
        System.out.println(str);
        System.out.println(marshaller.unmarshall(str));
    }
}
