/*******************************************************************************
 * Copyright (c) 2013 VITRO FP7 Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 *     Antoniou Thanasis
 *     Paolo Medagliani
 *     D. Davide Lamanna
 *     Panos Trakadas
 *     Andrea Kropp
 *     Kiriakos Georgouleas
 *     Panagiotis Karkazis
 *     David Ferrer Figueroa
 *     Francesco Ficarola
 *     Stefano Puglia
 ******************************************************************************/
/**
 * missionData fileUpload package originally from:
 *   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
 *   email : plosson@users.sourceforge.net
 */
package presentation.webgui.vitroappservlet.uploadService.fileupload;

import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

public class MonitoredDiskFileItem extends DiskFileItem
{
  private MonitoredOutputStream mos = null;
  private OutputStreamListener listener;

  public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository, OutputStreamListener listener)
  {
    super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
    this.listener = listener;
  }

  public OutputStream getOutputStream() throws IOException
  {
    if (mos == null)
    {
      mos = new MonitoredOutputStream(super.getOutputStream(), listener);
    }
    return mos;
  }
}
