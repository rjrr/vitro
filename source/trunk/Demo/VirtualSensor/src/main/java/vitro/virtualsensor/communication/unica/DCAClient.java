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

package vitro.virtualsensor.communication.unica;


public class DCAClient {

    private UNICAConnection connection;
    private String subscriptionLogicalName;
    private int outgoingConnectionId;

    public DCAClient(String subscriptionLogicalName){
        this.subscriptionLogicalName = subscriptionLogicalName;
        connection = new UNICAConnection();
    }

    public DCAClient(){
        this.subscriptionLogicalName = "TestClient";
        connection = new UNICAConnection();
    }
    public UNICAConnection getConnection(){
        return connection;
    }

    public void setConnection(UNICAConnection conn) {
        this.connection = conn;
    }

    public String getSubscriptionLogicalName() {
        return subscriptionLogicalName;
    }

    public void setSubscriptionLogicalName(String subscriptionLogicalName) {
        this.subscriptionLogicalName = subscriptionLogicalName;
    }

    public int getOutgoingConnectionId() {
        return outgoingConnectionId;
    }

    public void setOutgoingConnectionId(int outgoingConnectionId) {
        this.outgoingConnectionId = outgoingConnectionId;
    }

    //TODO revisar estos 3 metodos, porque posiblemente ya se encuentren los parametros dentro de UNICAConnection
    public boolean disconnect(String outgoing) {
        disconnect(outgoing);
        return true;
    }

    public boolean unsubscribe(String logicalName, String outgoing){
        return unsubscribe(logicalName, outgoing);
    }

    public boolean subscribe() {
        return connection.subscribe();
    }

    public void show(){
        System.out.println("Outgoing ConnectionID " + this.outgoingConnectionId);
        System.out.println("SubscriptionLogicalName " + this.subscriptionLogicalName);
        this.connection.show();
    }
}
