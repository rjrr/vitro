/*
 * #--------------------------------------------------------------------------
 * # Copyright (c) 2013 VITRO FP7 Consortium.
 * # All rights reserved. This program and the accompanying materials
 * # are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * # http://www.gnu.org/licenses/lgpl-3.0.html
 * #
 * # Contributors:
 * #     Antoniou Thanasis (Research Academic Computer Technology Institute)
 * #     Paolo Medagliani (Thales Communications & Security)
 * #     D. Davide Lamanna (WLAB SRL)
 * #     Alessandro Leoni (WLAB SRL)
 * #     Francesco Ficarola (WLAB SRL)
 * #     Stefano Puglia (WLAB SRL)
 * #     Panos Trakadas (Technological Educational Institute of Chalkida)
 * #     Panagiotis Karkazis (Technological Educational Institute of Chalkida)
 * #     Andrea Kropp (Selex ES)
 * #     Kiriakos Georgouleas (Hellenic Aerospace Industry)
 * #     David Ferrer Figueroa (Telefonica Investigación y Desarrollo S.A.)
 * #
 * #--------------------------------------------------------------------------
 */

package vitro.virtualsensor.utils;



import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author Andres
 */
public class FileWorker {
    
    public String readFile (String file) {
        return readFile(new File(file));
    }
    
    public String readFile (File f) {
        if (!(f.isFile() && f.canRead())) return null;
        try {
            FileReader fstream = new FileReader(f.getCanonicalPath());
            BufferedReader in = new BufferedReader(fstream);
            String s;
            String read = "";
            String separator = System.getProperty("line.separator");
            while ((s = in.readLine()) != null) read += s + separator;
            in.close();
            return read;
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean writeFile(String file, String text) {
        return writeFile(new File(file), text);
    }
    
    public boolean writeFile(File f, String text) {
        try {
            FileWriter fstream = new FileWriter(f.getCanonicalPath());
            BufferedWriter out = new BufferedWriter(fstream);
            StringTokenizer st = new StringTokenizer(text, "\n");
            while (st.hasMoreTokens()) {
                out.write(st.nextToken() + System.getProperty("line.separator"));
            }
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
