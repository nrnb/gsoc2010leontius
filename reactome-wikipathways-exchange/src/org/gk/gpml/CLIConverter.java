/*
 * This command line interface is used to convert a pathway that has a diagram inside the reactome
 * database to GPML format.
 */

package org.gk.gpml;

import java.io.FileOutputStream;

import org.gk.gpml.ReactomeToGPMLConverter;
import org.gk.model.GKInstance;
import org.gk.persistence.MySQLAdaptor;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class CLIConverter {
    
    private MySQLAdaptor adaptor;
    private ReactomeToGPMLConverter r2gConverter;
    
    private CLIConverter(MySQLAdaptor adaptor) {
        this(adaptor, new ReactomeToGPMLConverter());
    }
    
    private CLIConverter(MySQLAdaptor adaptor, ReactomeToGPMLConverter r2gConverter) {
        this.adaptor = adaptor;
        this.r2gConverter = r2gConverter;
        this.r2gConverter.setMySQLAdaptor(adaptor);
    }
    
    private void convertReactomeToGPML(Long dbID, String outputFileName) throws Exception {
        GKInstance pathway = adaptor.fetchInstance(dbID);
        convertReactomeToGPML(pathway, outputFileName);
    }
    
    private void convertReactomeToGPML(GKInstance pathway, String outputFileName) throws Exception {
        Long dbID = pathway.getDBID();
        System.out.println("converting pathway #" + dbID + " " + pathway.getDisplayName() + "...");
        Document doc = r2gConverter.convertPathway(pathway);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(doc, new FileOutputStream(outputFileName));
        /*
        // Test loading using JDOM and validation
        SAXBuilder builder = new SAXBuilder(true);
        builder.setFeature("http://apache.org/xml/features/validation/schema", true);
        builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",
                            "http://genmapp.org/GPML/2008a http://svn.bigcat.unimaas.nl/pathvisio/trunk/GPML2008a.xsd");
        doc = builder.build(new File(outputFileName));
        */
    }
    
    private static void printUsage() throws Exception {
        System.out.println("Usage: java org.gk.gpml.CLIConverter dbhost dbName user pwd port DB_ID [outputfile]");
        System.out.println();
        System.out.println("DB_ID is the Reactome ID of a pathway that has a diagram inside the database.");
    }
    
    public static void main (String[] args) throws Exception{
        if ((args.length != 6) && (args.length != 7)) {
            printUsage();
            System.exit(1);			
        }
        Long dbID = new Long(args[5]);
        MySQLAdaptor adaptor = new MySQLAdaptor(args[0],
                args[1],
                args[2], 
                args[3],
                Integer.parseInt(args[4]));
        
        GKInstance pathway = adaptor.fetchInstance(dbID);
        String outputFileName;
        if (args.length == 7) {
            outputFileName = args[6];
        }
        else {
            outputFileName = args[5]
                             + " "
                             + pathway.getDisplayName().replaceAll("[^0-9A-Za-z()_-]+", " ")
                             + ".gpml";
        }
        CLIConverter converter = new CLIConverter(adaptor);
        converter.convertReactomeToGPML(pathway, outputFileName);
    }
}
